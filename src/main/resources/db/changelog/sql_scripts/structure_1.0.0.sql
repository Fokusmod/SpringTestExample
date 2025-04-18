--liquibase formatted sql

--changeset fokusmod:1 create wallets table

DROP TABLE IF EXISTS wallets;

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE wallets (
        client_id uuid DEFAULT gen_random_uuid(),
        balance DECIMAL(15, 2) DEFAULT 0.00 NOT NULL check (balance >= 0.00),
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (client_id)
);

INSERT INTO wallets (balance) values (1000.00), (5000.00);






