ALTER TABLE inventory_transactions
    ADD COLUMN warehouse_id BIGINT,
    ADD CONSTRAINT fk_inv_transaction_warehouse FOREIGN KEY (warehouse_id) references warehouses(id);

ALTER TABLE brands
    ADD COLUMN logo_url VARCHAR(255);