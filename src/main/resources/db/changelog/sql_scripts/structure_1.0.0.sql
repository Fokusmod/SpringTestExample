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

INSERT INTO wallets (client_id, balance) values
('6b15f4f4-c478-44f3-b026-3abf2dcb08ae',1000.00),
('1b15f4f4-c478-44f3-b026-3abf2dcb08ea', 5000.00);






