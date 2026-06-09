CREATE TABLE ventas (
    id          UUID          PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id  UUID          NOT NULL,
    cantidad    INTEGER       NOT NULL,
    precio_unit NUMERIC(10,2) NOT NULL,
    total       NUMERIC(10,2) NOT NULL,
    cliente     VARCHAR(255),
    created_at  TIMESTAMP     NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_cantidad_positiva CHECK (cantidad > 0)
);
