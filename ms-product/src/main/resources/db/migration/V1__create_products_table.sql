CREATE TABLE products (
    id          UUID           PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre      VARCHAR(255)   NOT NULL,
    descripcion VARCHAR(500),
    precio      NUMERIC(10,2)  NOT NULL,
    categoria   VARCHAR(100),
    activo      BOOLEAN        NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP      NOT NULL DEFAULT NOW()
);
