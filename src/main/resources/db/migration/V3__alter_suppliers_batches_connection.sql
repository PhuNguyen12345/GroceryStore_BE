-- 1. Thêm cột supplier_id
ALTER TABLE inventory_batches
    ADD COLUMN supplier_id BIGINT NOT NULL AFTER warehouse_id;

-- 2. Tạo khóa ngoại nối sang bảng suppliers
ALTER TABLE inventory_batches
    ADD CONSTRAINT fk_batch_supplier
        FOREIGN KEY (supplier_id) REFERENCES suppliers(id);