-- 1. Xóa khóa ngoại cũ đi (Cắt đứt liên kết)
ALTER TABLE vouchers DROP FOREIGN KEY fk_voucher_promo; -- (Tên constraint có thể khác tùy máy bạn)
ALTER TABLE vouchers DROP COLUMN promotion_id;

-- 2. "Bơm" sức mạnh cho bảng Vouchers (Để nó tự đứng vững)
ALTER TABLE vouchers
    ADD COLUMN description VARCHAR(255),
ADD COLUMN discount_type ENUM('PERCENTAGE', 'FIXED_AMOUNT') NOT NULL DEFAULT 'FIXED_AMOUNT',
ADD COLUMN discount_value DECIMAL(15, 2) NOT NULL, -- Tiền giảm hoặc % giảm nằm ngay tại đây
ADD COLUMN start_date DATETIME,
ADD COLUMN end_date DATETIME,
ADD COLUMN is_active BOOLEAN DEFAULT TRUE;

-- 3. Sửa bảng Promotions (Chỉ dùng để hiển thị Banner/Marketing)
ALTER TABLE promotions
    ADD COLUMN banner_url VARCHAR(500) NULL,
    DROP COLUMN discount_type,
    DROP COLUMN discount_value;