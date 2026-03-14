-- ==========================================================================
-- SEED DATA FOR PROMOTIONS
-- ==========================================================================

INSERT INTO promotions (
    name,
    description,
    start_date,
    end_date,
    is_active,
    banner_url
)
SELECT
    'Khai truong mua xuan',
    'Chuong trinh quang ba khai truong mua xuan danh cho khach hang mua sam tai cua hang.',
    '2026-03-01 00:00:00',
    '2026-03-31 23:59:59',
    TRUE,
    NULL
WHERE NOT EXISTS (
    SELECT 1 FROM promotions WHERE name = 'Khai truong mua xuan'
);

INSERT INTO promotions (
    name,
    description,
    start_date,
    end_date,
    is_active,
    banner_url
)
SELECT
    'Tuan le hang tieu dung',
    'Banner marketing cho tuan le hang tieu dung thiet yeu tai sieu thi mini.',
    '2026-04-01 00:00:00',
    '2026-04-07 23:59:59',
    TRUE,
    NULL
WHERE NOT EXISTS (
    SELECT 1 FROM promotions WHERE name = 'Tuan le hang tieu dung'
);

INSERT INTO promotions (
    name,
    description,
    start_date,
    end_date,
    is_active,
    banner_url
)
SELECT
    'Mua he tiet kiem',
    'Chien dich truyen thong mua he, hien thi banner tren giao dien va tai quay ban hang.',
    '2026-05-15 00:00:00',
    '2026-06-15 23:59:59',
    TRUE,
    NULL
WHERE NOT EXISTS (
    SELECT 1 FROM promotions WHERE name = 'Mua he tiet kiem'
);

INSERT INTO promotions (
    name,
    description,
    start_date,
    end_date,
    is_active,
    banner_url
)
SELECT
    'Tri an thanh vien',
    'Noi dung banner danh cho nhom khach hang than thiet va thanh vien quay lai mua sam.',
    '2026-07-01 00:00:00',
    '2026-07-31 23:59:59',
    TRUE,
    NULL
WHERE NOT EXISTS (
    SELECT 1 FROM promotions WHERE name = 'Tri an thanh vien'
);

INSERT INTO promotions (
    name,
    description,
    start_date,
    end_date,
    is_active,
    banner_url
)
SELECT
    'Su kien cuoi nam 2025',
    'Du lieu cu de test chuong trinh da ngung kich hoat.',
    '2025-12-01 00:00:00',
    '2025-12-31 23:59:59',
    FALSE,
    NULL
WHERE NOT EXISTS (
    SELECT 1 FROM promotions WHERE name = 'Su kien cuoi nam 2025'
);
