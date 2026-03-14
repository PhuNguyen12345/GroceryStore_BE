-- ==========================================================================
-- SEED DATA FOR VOUCHERS
-- ==========================================================================

INSERT INTO vouchers (
    code,
    quantity_limit,
    quantity_used,
    min_order_value,
    description,
    discount_type,
    discount_value,
    start_date,
    end_date,
    is_active
)
SELECT
    'WELCOME50K',
    100,
    0,
    300000.00,
    'Giảm 50.000 VND cho đơn hàng từ 300.000 VND',
    'FIXED_AMOUNT',
    50000.00,
    '2026-03-01 00:00:00',
    '2026-12-31 23:59:59',
    TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM vouchers WHERE code = 'WELCOME50K'
);

INSERT INTO vouchers (
    code,
    quantity_limit,
    quantity_used,
    min_order_value,
    description,
    discount_type,
    discount_value,
    start_date,
    end_date,
    is_active
)
SELECT
    'SAVE10',
    200,
    0,
    200000.00,
    'Giảm 10 phần trăm cho đơn hàng từ 200.000 VND',
    'PERCENTAGE',
    10.00,
    '2026-03-01 00:00:00',
    '2026-09-30 23:59:59',
    TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM vouchers WHERE code = 'SAVE10'
);

INSERT INTO vouchers (
    code,
    quantity_limit,
    quantity_used,
    min_order_value,
    description,
    discount_type,
    discount_value,
    start_date,
    end_date,
    is_active
)
SELECT
    'FREESHIP20',
    150,
    0,
    150000.00,
    'Voucher ưu đãi 20.000 VND cho đơn từ 150.000 VND',
    'FIXED_AMOUNT',
    20000.00,
    '2026-03-15 00:00:00',
    '2026-08-31 23:59:59',
    TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM vouchers WHERE code = 'FREESHIP20'
);

INSERT INTO vouchers (
    code,
    quantity_limit,
    quantity_used,
    min_order_value,
    description,
    discount_type,
    discount_value,
    start_date,
    end_date,
    is_active
)
SELECT
    'VIP15',
    50,
    0,
    500000.00,
    'Giảm 15 phần trăm cho đơn hàng giá trị cao',
    'PERCENTAGE',
    15.00,
    '2026-03-01 00:00:00',
    '2026-06-30 23:59:59',
    TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM vouchers WHERE code = 'VIP15'
);

INSERT INTO vouchers (
    code,
    quantity_limit,
    quantity_used,
    min_order_value,
    description,
    discount_type,
    discount_value,
    start_date,
    end_date,
    is_active
)
SELECT
    'OLD2025',
    80,
    80,
    100000.00,
    'Voucher hết hạn để test trạng thái không còn hiệu lực',
    'FIXED_AMOUNT',
    30000.00,
    '2025-01-01 00:00:00',
    '2025-12-31 23:59:59',
    FALSE
WHERE NOT EXISTS (
    SELECT 1 FROM vouchers WHERE code = 'OLD2025'
);