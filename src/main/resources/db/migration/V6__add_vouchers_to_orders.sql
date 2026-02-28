-- Thêm cột voucher_id vào bảng orders
ALTER TABLE orders
    ADD COLUMN voucher_id BIGINT NULL, -- Null được vì có đơn không dùng voucher
ADD CONSTRAINT fk_order_voucher
FOREIGN KEY (voucher_id) REFERENCES vouchers(id);

-- (Tùy chọn) Thêm cột để lưu tên voucher tại thời điểm mua
-- Để sau này lỡ admin xóa voucher đó đi thì vẫn biết đơn này dùng voucher tên gì
ALTER TABLE orders
    ADD COLUMN voucher_code VARCHAR(50) NULL;