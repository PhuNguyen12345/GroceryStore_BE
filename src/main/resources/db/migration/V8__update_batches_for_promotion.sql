-- 4. Cập nhật Inventory Batch (Discount theo lô - Theo ý kiến số 6)
ALTER TABLE inventory_batches
    ADD COLUMN discount_percent INT DEFAULT 0, -- Ví dụ: 30 nghĩa là giảm 30%
    ADD COLUMN is_discounted BOOLEAN DEFAULT FALSE;

