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
    'Khai trương mùa xuân',
    'Chương trình quảng bá khai trương mùa xuân dành cho khách hàng mua sắm tại cửa hàng.',
    '2026-03-01 00:00:00',
    '2026-03-31 23:59:59',
    TRUE,
    '/uploads/images/banner2.png'
    WHERE NOT EXISTS (
    SELECT 1 FROM promotions WHERE name = 'Khai trương mùa xuân'
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
    'Tuần lễ hàng tiêu dùng',
    'Banner marketing cho tuần lễ hàng tiêu dùng thiết yếu tại siêu thị mini.',
    '2026-04-01 00:00:00',
    '2026-04-07 23:59:59',
    TRUE,
    '/uploads/images/banner6.png'
    WHERE NOT EXISTS (
    SELECT 1 FROM promotions WHERE name = 'Tuần lễ hàng tiêu dùng'
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
    'Mùa hè tiết kiệm',
    'Chiến dịch truyền thông mùa hè, hiển thị banner trên giao diện và tại quầy bán hàng.',
    '2026-05-15 00:00:00',
    '2026-06-15 23:59:59',
    TRUE,
    '/uploads/images/banner5.png'
    WHERE NOT EXISTS (
    SELECT 1 FROM promotions WHERE name = 'Mùa hè tiết kiệm'
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
    'Tri ân thành viên',
    'Nội dung banner dành cho nhóm khách hàng thân thiết và thành viên quay lại mua sắm.',
    '2026-07-01 00:00:00',
    '2026-07-31 23:59:59',
    TRUE,
    '/uploads/images/banner2.png'
    WHERE NOT EXISTS (
    SELECT 1 FROM promotions WHERE name = 'Tri ân thành viên'
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
    'Săn sale cuối tuần',
    'Chương trình ưu đãi cuối tuần dành cho khách hàng mua sắm tại cửa hàng.',
    '2026-08-01 00:00:00',
    '2026-08-03 23:59:59',
    TRUE,
    '/uploads/images/banner4.png'
    WHERE NOT EXISTS (
    SELECT 1 FROM promotions WHERE name = 'Săn sale cuối tuần'
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
    'Back to School',
    'Chiến dịch khuyến mãi dành cho mùa tựu trường với nhiều ưu đãi hấp dẫn.',
    '2026-08-15 00:00:00',
    '2026-09-10 23:59:59',
    TRUE,
    '/uploads/images/banner3.png'
    WHERE NOT EXISTS (
    SELECT 1 FROM promotions WHERE name = 'Back to School'
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
    'Mừng Quốc khánh',
    'Chương trình khuyến mãi nhân dịp Quốc khánh với nhiều sản phẩm ưu đãi.',
    '2026-09-01 00:00:00',
    '2026-09-05 23:59:59',
    TRUE,
    '/uploads/images/banner7.png'
    WHERE NOT EXISTS (
    SELECT 1 FROM promotions WHERE name = 'Mừng Quốc khánh'
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
    'Ưu đãi tháng 10',
    'Chương trình kích cầu mua sắm trong tháng 10 dành cho mọi khách hàng.',
    '2026-10-01 00:00:00',
    '2026-10-31 23:59:59',
    TRUE,
    '/uploads/images/banner6.png'
    WHERE NOT EXISTS (
    SELECT 1 FROM promotions WHERE name = 'Ưu đãi tháng 10'
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
    'Ngày hội khách hàng',
    'Sự kiện tri ân khách hàng với nhiều hoạt động và ưu đãi mua sắm hấp dẫn.',
    '2026-11-05 00:00:00',
    '2026-11-10 23:59:59',
    TRUE,
    '/uploads/images/banner5.png'
    WHERE NOT EXISTS (
    SELECT 1 FROM promotions WHERE name = 'Ngày hội khách hàng'
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
    'Black Friday',
    'Chiến dịch giảm giá lớn trong dịp Black Friday với nhiều ưu đãi đặc biệt.',
    '2026-11-27 00:00:00',
    '2026-11-27 23:59:59',
    TRUE,
    '/uploads/images/banner1.jpg'
    WHERE NOT EXISTS (
    SELECT 1 FROM promotions WHERE name = 'Black Friday'
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
    'Sự kiện cuối năm 2025',
    'Dữ liệu cũ để test chương trình đã ngừng kích hoạt.',
    '2025-12-01 00:00:00',
    '2025-12-31 23:59:59',
    FALSE,
    '/uploads/images/banner2.png'
    WHERE NOT EXISTS (
    SELECT 1 FROM promotions WHERE name = 'Sự kiện cuối năm 2025'
);