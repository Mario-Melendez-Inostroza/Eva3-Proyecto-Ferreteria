-- =============================================================
-- init.sql — PostgreSQL
-- =============================================================
-- Crea una base de datos por cada microservicio.
-- Cada microservicio tiene su propia BD (principio de microservicios).
-- Flyway crea las tablas dentro de cada BD al arrancar.
-- =============================================================

CREATE DATABASE auth_db;
CREATE DATABASE product_db;
CREATE DATABASE inventory_db;
CREATE DATABASE sales_db;
CREATE DATABASE notification_db;
