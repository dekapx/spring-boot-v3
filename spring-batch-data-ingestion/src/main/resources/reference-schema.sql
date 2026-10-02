-- Reference DDL matching the JPA `Order` entity mapping.
-- NOT executed automatically (spring.jpa.hibernate.ddl-auto=update handles
-- schema creation for local/dev use). For production, feed this into a
-- migration tool such as Flyway or Liquibase and set ddl-auto to 'validate'.

CREATE SEQUENCE IF NOT EXISTS orders_id_seq START WITH 1 INCREMENT BY 200;

CREATE TABLE IF NOT EXISTS orders (
    id                      BIGINT PRIMARY KEY DEFAULT nextval('orders_id_seq'),
    order_number            VARCHAR(64)  NOT NULL,
    customer_name           VARCHAR(255) NOT NULL,
    item_name               VARCHAR(255) NOT NULL,
    quantity                INTEGER      NOT NULL,
    total_amount            NUMERIC(19,2) NOT NULL,
    status                  VARCHAR(32)  NOT NULL,
    order_date              DATE         NOT NULL,
    estimated_delivery_date DATE,
    tracking_number         VARCHAR(64),
    carrier                 VARCHAR(64),
    current_location        VARCHAR(255),
    delivery_address        VARCHAR(512),
    cancellation_reason     VARCHAR(512),
    CONSTRAINT uk_orders_order_number UNIQUE (order_number)
);

CREATE INDEX IF NOT EXISTS idx_orders_status ON orders (status);
CREATE INDEX IF NOT EXISTS idx_orders_order_date ON orders (order_date);
