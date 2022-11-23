# spring-boot-v3
Spring Boot 3 Samples


# Run Postgres with volume in Docker
$ docker volume create pgdata

$ docker container run \
    --name docker-postgres -p 5432:5432 \
    -e POSTGRES_USER=dekapx -e POSTGRES_PASSWORD=passw0rd \
    -e POSTGRES_DB=testdb -v pgdata:/var/lib/postgresql/data postgres:14.1
