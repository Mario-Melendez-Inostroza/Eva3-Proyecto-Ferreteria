CREATE TABLE notifications (
    id          UUID         DEFAULT gen_random_uuid() PRIMARY KEY,
    tipo        VARCHAR(50)  NOT NULL,
    mensaje     TEXT         NOT NULL,
    product_id  UUID,
    leida       BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);
