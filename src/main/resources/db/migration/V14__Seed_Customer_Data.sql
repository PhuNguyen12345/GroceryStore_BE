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
    'Nguyen Van An',
    'nguyenvanan@example.com',
    '123 Le Loi, Quan 1, TP HCM',
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
    'Tran Thi Bich',
    'tranthibich@example.com',
    '45 Nguyen Hue, Quan 1, TP HCM',
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
    'Le Minh Khoa',
    'leminhkhoa@example.com',
    '78 Cach Mang Thang 8, Quan 3, TP HCM',
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
    'Pham Thu Ha',
    'phamthuha@example.com',
    '12 Vo Van Tan, Quan 3, TP HCM',
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
    'Hoang Gia Bao',
    'hoanggiabao@example.com',
    '210 Phan Xich Long, Phu Nhuan, TP HCM',
    60,
    'BRONZE',
    '2025-08-18 13:25:00',
    '2026-01-05 08:40:00',
    FALSE
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE phone = '0945678901'
);
