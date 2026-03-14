-- ==========================================================================
-- SEED DATA FOR CUSTOMERS
-- ==========================================================================

INSERT INTO customers (
    phone,
    full_name,
    email,
    address,
    loyalty_points,
    customer_tier,
    created_at,
    updated_at,
    is_active
)
SELECT
    '0901234567',
    'Nguyễn Văn An',
    'nguyenvanan@example.com',
    '123 Lê Lợi, Quận 1, TP. HCM',
    120,
    'SILVER',
    '2026-01-15 08:30:00',
    '2026-03-10 09:00:00',
    TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE phone = '0901234567'
);

INSERT INTO customers (
    phone,
    full_name,
    email,
    address,
    loyalty_points,
    customer_tier,
    created_at,
    updated_at,
    is_active
)
SELECT
    '0912345678',
    'Trần Thị Bích',
    'tranthibich@example.com',
    '45 Nguyễn Huệ, Quận 1, TP. HCM',
    340,
    'GOLD',
    '2025-12-20 10:15:00',
    '2026-03-12 14:20:00',
    TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE phone = '0912345678'
);

INSERT INTO customers (
    phone,
    full_name,
    email,
    address,
    loyalty_points,
    customer_tier,
    created_at,
    updated_at,
    is_active
)
SELECT
    '0923456789',
    'Lê Minh Khoa',
    'leminhkhoa@example.com',
    '78 Cách Mạng Tháng 8, Quận 3, TP. HCM',
    0,
    'BRONZE',
    '2026-02-05 11:00:00',
    '2026-02-05 11:00:00',
    TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE phone = '0923456789'
);

INSERT INTO customers (
    phone,
    full_name,
    email,
    address,
    loyalty_points,
    customer_tier,
    created_at,
    updated_at,
    is_active
)
SELECT
    '0934567890',
    'Phạm Thu Hà',
    'phamthuha@example.com',
    '12 Võ Văn Tần, Quận 3, TP. HCM',
    780,
    'DIAMOND',
    '2025-10-01 07:45:00',
    '2026-03-13 16:10:00',
    TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE phone = '0934567890'
);

INSERT INTO customers (
    phone,
    full_name,
    email,
    address,
    loyalty_points,
    customer_tier,
    created_at,
    updated_at,
    is_active
)
SELECT
    '0945678901',
    'Hoàng Gia Bảo',
    'hoanggiabao@example.com',
    '210 Phan Xích Long, Phú Nhuận, TP. HCM',
    60,
    'BRONZE',
    '2025-08-18 13:25:00',
    '2026-01-05 08:40:00',
    FALSE
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE phone = '0945678901'
);