# spring-batch-data-ingestion

Spring Boot + Spring Batch application that reads paginated `Order` records
from a REST endpoint and persists them to PostgreSQL via JPA, using
chunk-oriented, fault-tolerant batch processing.

**Stack:** Spring Boot 3.5.10 · JDK 21 · Maven · Spring Batch · Spring Data JPA · PostgreSQL

## Features

- **Chunk-oriented processing** — reads/processes/writes in configurable batches (default 200), not row-by-row.
- **Paginated REST reader** (`OrderApiItemReader`) — pages through the source API, persists paging progress in the job's `ExecutionContext` so a restarted job resumes near where it left off.
- **`@Retryable` REST calls** (`OrdersApiClient`) — retries transient 5xx/timeout/IO errors with exponential backoff (1s → 2s → 4s), skips retry for 4xx client errors. Kept as its own bean rather than a method on the reader — see its Javadoc for why (Spring AOP self-invocation).
- **JPA persistence with upsert semantics** — `OrderItemProcessor` looks up each order by its natural key (`orderNumber`); existing orders are loaded and overwritten (→ UPDATE), new ones are built transient (→ INSERT). The `JpaItemWriter` is configured with `usePersist(false)` so it always goes through `EntityManager.merge()`, making re-runs of the job idempotent.
- **Sequence-based ID generation** — the `Order` entity uses `GenerationType.SEQUENCE` (allocation size 200) rather than `IDENTITY`. This matters: Hibernate cannot batch JDBC inserts with `IDENTITY` generation (it needs each generated key back before continuing), which would silently defeat the configured `hibernate.jdbc.batch_size`.
- **Chunk-level retry** — `faultTolerant().retry(...)` retries transient DB/REST exceptions at the Spring Batch step level, independent of the `@Retryable` REST-call-level retry.
- **Skip logic** — invalid records (missing/blank required fields, bad quantity/amount) or constraint violations are skipped (up to `skip-limit`) rather than failing the whole job; skipped items are logged by `OrderSkipListener`.
- **Listeners** — `JobExecutionListener`, `StepExecutionListener`, `ChunkListener`, `SkipListener`, and a `RetryListener`, all logging lifecycle/progress events (ready to swap for metrics/alerting).
- **Multi-threaded chunk processing** — `taskExecutor` + `throttleLimit` on the step for concurrent chunk processing/writing. Safe with the JPA writer: each chunk gets its own thread-bound transaction/persistence context via `JpaTransactionManager`.

## Project layout

```
src/main/java/com/dekapx/apps/
├── SpringBatchDataIngestionApplication.java  # main class, @EnableRetry
├── config/
│   ├── BatchConfig.java              # job/step/reader/writer wiring
│   └── RestClientConfig.java         # WebClient bean (timeouts)
├── entity/Order.java                 # JPA entity (SEQUENCE id generation)
├── repository/OrderRepository.java   # Spring Data JPA repo (findByOrderNumber)
├── dto/OrderDto.java                 # REST payload shape
├── client/OrdersApiClient.java       # @Retryable REST call (own bean, see Javadoc)
├── reader/OrderApiItemReader.java    # paginated ItemReader
├── processor/OrderItemProcessor.java # validation + DTO→entity mapping + upsert lookup
├── listener/
│   ├── JobCompletionListener.java
│   ├── OrderStepExecutionListener.java
│   ├── OrderChunkListener.java
│   ├── OrderSkipListener.java
│   └── OrderRetryListener.java
└── exception/OrderProcessingException.java

src/main/resources/
├── application.yml
└── reference-schema.sql              # DDL reference for prod migration tooling (not auto-run)
```

## Prerequisites

- JDK 21
- Maven 3.9+
- Docker (for local Postgres via `docker-compose`), or an existing PostgreSQL instance

## Running locally

1. **Start Postgres:**
   ```bash
   docker compose up -d
   ```

2. **Point at your source REST API.** Set the base URL (and adjust
   `app.rest.orders-path` / the JSON shape in `OrderDto` to match your actual
   API contract):
   ```bash
   export SOURCE_API_BASE_URL=https://your-api.example.com
   ```

3. **Run the app** — the batch job launches automatically on startup:
   ```bash
   mvn spring-boot:run
   ```

   Or build and run the jar:
   ```bash
   mvn clean package
   java -jar target/spring-batch-data-ingestion.jar
   ```

4. The app exits automatically once the job completes (`System.exit` in
   `Application`), with a non-zero exit code if the
   job failed.

## Configuration reference (`application.yml` / env vars)

| Property | Env var | Default | Purpose |
|---|---|---|---|
| `app.rest.base-url` | `SOURCE_API_BASE_URL` | `https://api.example.com` | Source REST API base URL |
| `app.rest.orders-path` | — | `/api/orders` | Path for the paginated orders endpoint |
| `app.rest.page-size` | — | 200 | Records requested per REST page |
| `app.batch.chunk-size` | — | 200 | Records per DB commit/chunk (kept aligned with `hibernate.jdbc.batch_size`) |
| `app.batch.skip-limit` | — | 100 | Max skipped (invalid) records before the job fails |
| `app.batch.write-retry-limit` | — | 3 | Chunk-level retry attempts for transient DB/REST errors |
| `app.batch.concurrency` | — | 4 | Worker threads for chunk processing/writing |
| `spring.datasource.url` | `DB_HOST`, `DB_PORT`, `DB_NAME` | `jdbc:postgresql://localhost:5432/orderdb` | Target Postgres DB |
| `spring.datasource.username`/`password` | `DB_USER`, `DB_PASSWORD` | `batchuser` / `batchpass` | DB credentials |
| `spring.jpa.hibernate.ddl-auto` | — | `update` | Set to `validate` in prod, managed by Flyway/Liquibase using `reference-schema.sql` |

## Performance notes

- **`hibernate.jdbc.batch_size` is set to 200, matching `app.batch.chunk-size`.**
  Keep these aligned (or make `batch_size` an even divisor of `chunk-size`)
  so each Spring Batch chunk maps cleanly onto Hibernate's JDBC batches.
- **`order_inserts`/`order_updates` are enabled** so Hibernate groups same-type
  statements together, maximizing batch efficiency even when a chunk mixes
  new orders and updates.
- **One `findByOrderNumber` lookup per record** happens in `OrderItemProcessor`
  to support upsert. For very high-throughput ingestion where the vast
  majority of records are brand-new (not updates), this per-item lookup is
  the main thing to optimize further — e.g. an `ItemReadListener` or a custom
  composite reader that pre-fetches the set of existing order numbers for an
  entire chunk in one `IN (...)` query, rather than N individual lookups.
- **`GenerationType.SEQUENCE`, not `IDENTITY`** — see the note in `Order.java`;
  this is what allows Hibernate to actually batch INSERT statements.

## Adjusting for your actual REST API

This sample assumes the source API supports `?page=&size=` query-param
pagination and returns a JSON array of objects shaped like `OrderDto`. In
practice you'll likely need to:

- Change `OrderDto` fields to match your actual payload (or add a mapping layer).
- If the API uses cursor/token pagination instead of page numbers, change
  `OrderApiItemReader`'s state from an `int currentPage` to a `String cursor`
  and persist that in the `ExecutionContext` instead.
- If the API wraps results in an envelope (e.g. `{"data": [...], "nextPage": ...}`),
  change `OrdersApiClient.fetchPage` to deserialize into that wrapper type.
- Add an `Authorization` header / OAuth token supplier in `RestClientConfig`
  if the API requires auth.

## Scaling beyond a single JVM

For very large or very frequent imports (millions of records, or a recurring
ETL job rather than an occasional sync), consider Spring Batch **remote
partitioning** (via Kafka/JMS) to distribute chunks across multiple worker
instances, or move the workload to Spark (reading from a staged extract,
writing via a JDBC sink) if it becomes a genuinely big-data recurring job.
For "thousands of records" this single-JVM, multi-threaded chunk-oriented
setup is the right-sized solution.
