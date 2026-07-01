CREATE TABLE inventory (
    id           UUID         DEFAULT gen_random_uuid() PRIMARY KEY,
    product_id   UUID         NOT NULL UNIQUE,
    stock        INTEGER      NOT NULL DEFAULT 0,
    stock_minimo INTEGER      NOT NULL DEFAULT 5,
    bodega       VARCHAR(100) NOT NULL DEFAULT 'Principal',
    updated_at   TIMESTAMP    NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_stock_positivo CHECK (stock >= 0)
);
