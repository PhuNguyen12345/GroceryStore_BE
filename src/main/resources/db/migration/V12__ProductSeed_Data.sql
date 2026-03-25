-- =========================
-- CATEGORY CHA
-- =========================

INSERT INTO categories (name, parent_id, slug, description, is_active)
VALUES
    ('Đồ uống', NULL, 'do-uong', 'Nhóm sản phẩm đồ uống các loại', 1),
    ('Bánh kẹo', NULL, 'banh-keo', 'Nhóm sản phẩm bánh kẹo và đồ ăn vặt', 1),
    ('Thực phẩm ăn liền', NULL, 'thuc-pham-an-lien', 'Nhóm thực phẩm chế biến nhanh, tiện lợi', 1),
    ('Gia vị - đồ bếp nhỏ', NULL, 'gia-vi-do-bep-nho', 'Nhóm gia vị và vật dụng bếp cơ bản', 1),
    ('Hóa mỹ phẩm', NULL, 'hoa-my-pham', 'Nhóm sản phẩm chăm sóc cá nhân và vệ sinh', 1),
    ('Đồ dùng cá nhân', NULL, 'do-dung-ca-nhan', 'Nhóm vật dụng phục vụ nhu cầu cá nhân hằng ngày', 1),
    ('Văn phòng phẩm - tiện ích', NULL, 'van-phong-pham-tien-ich', 'Nhóm văn phòng phẩm và đồ tiện ích nhỏ', 1),
    ('Đồ gia dụng mini', NULL, 'do-gia-dung-mini', 'Nhóm đồ gia dụng kích thước nhỏ, tiện lợi', 1),
    ('Đồ đông lạnh', NULL, 'do-dong-lanh', 'Nhóm thực phẩm và sản phẩm bảo quản đông lạnh', 1);

-- =========================
-- CATEGORY CON: ĐỒ UỐNG
-- =========================

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Nước suối', id, 'nuoc-suoi', 'Các loại nước suối đóng chai', 1
FROM categories WHERE slug = 'do-uong';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Nước ngọt', id, 'nuoc-ngot', 'Các loại nước ngọt có gas và không gas', 1
FROM categories WHERE slug = 'do-uong';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Trà đóng chai', id, 'tra-dong-chai', 'Các loại trà chai tiện lợi', 1
FROM categories WHERE slug = 'do-uong';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Cà phê lon/chai', id, 'ca-phe-lon-chai', 'Các loại cà phê đóng lon hoặc chai', 1
FROM categories WHERE slug = 'do-uong';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Sữa', id, 'sua', 'Các loại sữa hộp, sữa chai, sữa uống liền', 1
FROM categories WHERE slug = 'do-uong';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Nước tăng lực', id, 'nuoc-tang-luc', 'Các loại nước tăng lực', 1
FROM categories WHERE slug = 'do-uong';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Nước ép', id, 'nuoc-ep', 'Các loại nước ép trái cây đóng hộp hoặc chai', 1
FROM categories WHERE slug = 'do-uong';

-- =========================
-- CATEGORY CON: BÁNH KẸO
-- =========================

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Bánh quy', id, 'banh-quy', 'Các loại bánh quy, bánh giòn', 1
FROM categories WHERE slug = 'banh-keo';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Kẹo', id, 'keo', 'Các loại kẹo ngọt, kẹo cứng, kẹo mềm', 1
FROM categories WHERE slug = 'banh-keo';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Socola', id, 'socola', 'Các loại socola thanh, viên, hộp', 1
FROM categories WHERE slug = 'banh-keo';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Snack', id, 'snack', 'Các loại snack, bim bim, khoai tây lát', 1
FROM categories WHERE slug = 'banh-keo';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Hạt dinh dưỡng', id, 'hat-dinh-duong', 'Các loại hạt ăn liền và hạt dinh dưỡng', 1
FROM categories WHERE slug = 'banh-keo';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Rong biển ăn liền', id, 'rong-bien-an-lien', 'Các loại rong biển ăn liền đóng gói', 1
FROM categories WHERE slug = 'banh-keo';

-- =========================
-- CATEGORY CON: THỰC PHẨM ĂN LIỀN
-- =========================

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Mì gói', id, 'mi-goi', 'Các loại mì gói ăn liền', 1
FROM categories WHERE slug = 'thuc-pham-an-lien';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Cháo gói', id, 'chao-goi', 'Các loại cháo ăn liền đóng gói', 1
FROM categories WHERE slug = 'thuc-pham-an-lien';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Miến / phở ăn liền', id, 'mien-pho-an-lien', 'Các loại miến, phở ăn liền', 1
FROM categories WHERE slug = 'thuc-pham-an-lien';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Cơm tự sôi', id, 'com-tu-soi', 'Các loại cơm tự sôi tiện lợi', 1
FROM categories WHERE slug = 'thuc-pham-an-lien';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Đồ hộp', id, 'do-hop', 'Các loại thực phẩm đóng hộp', 1
FROM categories WHERE slug = 'thuc-pham-an-lien';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Xúc xích', id, 'xuc-xich', 'Các loại xúc xích ăn liền', 1
FROM categories WHERE slug = 'thuc-pham-an-lien';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Lạp xưởng', id, 'lap-xuong', 'Các loại lạp xưởng đóng gói', 1
FROM categories WHERE slug = 'thuc-pham-an-lien';

-- =========================
-- CATEGORY CON: GIA VỊ - ĐỒ BẾP NHỎ
-- =========================

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Nước mắm', id, 'nuoc-mam', 'Các loại nước mắm', 1
FROM categories WHERE slug = 'gia-vi-do-bep-nho';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Nước tương', id, 'nuoc-tuong', 'Các loại nước tương', 1
FROM categories WHERE slug = 'gia-vi-do-bep-nho';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Tương ớt', id, 'tuong-ot', 'Các loại tương ớt', 1
FROM categories WHERE slug = 'gia-vi-do-bep-nho';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Tương cà', id, 'tuong-ca', 'Các loại tương cà', 1
FROM categories WHERE slug = 'gia-vi-do-bep-nho';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Dầu ăn mini', id, 'dau-an-mini', 'Các loại dầu ăn dung tích nhỏ', 1
FROM categories WHERE slug = 'gia-vi-do-bep-nho';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Đường', id, 'duong', 'Các loại đường đóng gói', 1
FROM categories WHERE slug = 'gia-vi-do-bep-nho';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Muối', id, 'muoi', 'Các loại muối ăn', 1
FROM categories WHERE slug = 'gia-vi-do-bep-nho';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Bột nêm', id, 'bot-nem', 'Các loại bột nêm, hạt nêm', 1
FROM categories WHERE slug = 'gia-vi-do-bep-nho';

-- =========================
-- CATEGORY CON: HÓA MỸ PHẨM
-- =========================

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Dầu gội', id, 'dau-goi', 'Các loại dầu gội đầu', 1
FROM categories WHERE slug = 'hoa-my-pham';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Sữa tắm', id, 'sua-tam', 'Các loại sữa tắm', 1
FROM categories WHERE slug = 'hoa-my-pham';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Kem đánh răng', id, 'kem-danh-rang', 'Các loại kem đánh răng', 1
FROM categories WHERE slug = 'hoa-my-pham';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Nước súc miệng', id, 'nuoc-suc-mieng', 'Các loại nước súc miệng', 1
FROM categories WHERE slug = 'hoa-my-pham';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Nước rửa tay', id, 'nuoc-rua-tay', 'Các loại nước rửa tay', 1
FROM categories WHERE slug = 'hoa-my-pham';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Khăn giấy', id, 'khan-giay', 'Các loại khăn giấy khô', 1
FROM categories WHERE slug = 'hoa-my-pham';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Giấy ướt', id, 'giay-uot', 'Các loại khăn giấy ướt', 1
FROM categories WHERE slug = 'hoa-my-pham';

-- =========================
-- CATEGORY CON: ĐỒ DÙNG CÁ NHÂN
-- =========================

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Bàn chải', id, 'ban-chai', 'Các loại bàn chải đánh răng', 1
FROM categories WHERE slug = 'do-dung-ca-nhan';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Dao cạo', id, 'dao-cao', 'Các loại dao cạo râu, dao cạo cá nhân', 1
FROM categories WHERE slug = 'do-dung-ca-nhan';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Tăm bông', id, 'tam-bong', 'Các loại tăm bông vệ sinh cá nhân', 1
FROM categories WHERE slug = 'do-dung-ca-nhan';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Khẩu trang', id, 'khau-trang', 'Các loại khẩu trang dùng hằng ngày', 1
FROM categories WHERE slug = 'do-dung-ca-nhan';


INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Lược', id, 'luoc', 'Các loại lược chải tóc', 1
FROM categories WHERE slug = 'do-dung-ca-nhan';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Gương mini', id, 'guong-mini', 'Các loại gương mini cá nhân', 1
FROM categories WHERE slug = 'do-dung-ca-nhan';

-- =========================
-- CATEGORY CON: VĂN PHÒNG PHẨM - TIỆN ÍCH
-- =========================

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Bút', id, 'but', 'Các loại bút viết', 1
FROM categories WHERE slug = 'van-phong-pham-tien-ich';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Sổ tay', id, 'so-tay', 'Các loại sổ tay, sổ ghi chép', 1
FROM categories WHERE slug = 'van-phong-pham-tien-ich';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Pin', id, 'pin', 'Các loại pin tiểu và pin sử dụng hằng ngày', 1
FROM categories WHERE slug = 'van-phong-pham-tien-ich';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Bật lửa', id, 'bat-lua', 'Các loại bật lửa tiện ích', 1
FROM categories WHERE slug = 'van-phong-pham-tien-ich';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Áo mưa', id, 'ao-mua', 'Các loại áo mưa tiện lợi', 1
FROM categories WHERE slug = 'van-phong-pham-tien-ich';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Ly giấy', id, 'ly-giay', 'Các loại ly giấy dùng một lần', 1
FROM categories WHERE slug = 'van-phong-pham-tien-ich';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Ống hút', id, 'ong-hut', 'Các loại ống hút dùng một lần', 1
FROM categories WHERE slug = 'van-phong-pham-tien-ich';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Túi zip', id, 'tui-zip', 'Các loại túi zip nhỏ tiện lợi', 1
FROM categories WHERE slug = 'van-phong-pham-tien-ich';

-- =========================
-- CATEGORY CON: ĐỒ GIA DỤNG MINI
-- =========================

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Túi rác', id, 'tui-rac', 'Các loại túi rác gia dụng', 1
FROM categories WHERE slug = 'do-gia-dung-mini';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Nước rửa chén', id, 'nuoc-rua-chen', 'Các loại nước rửa chén', 1
FROM categories WHERE slug = 'do-gia-dung-mini';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Mút rửa chén', id, 'mut-rua-chen', 'Các loại mút rửa chén', 1
FROM categories WHERE slug = 'do-gia-dung-mini';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Màng bọc thực phẩm', id, 'mang-boc-thuc-pham', 'Các loại màng bọc thực phẩm', 1
FROM categories WHERE slug = 'do-gia-dung-mini';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Giấy bạc', id, 'giay-bac', 'Các loại giấy bạc bọc thực phẩm', 1
FROM categories WHERE slug = 'do-gia-dung-mini';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Hộp nhựa', id, 'hop-nhua', 'Các loại hộp nhựa gia dụng nhỏ', 1
FROM categories WHERE slug = 'do-gia-dung-mini';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Chén / muỗng / đũa dùng một lần', id, 'chen-muong-dua-dung-mot-lan', 'Các loại chén, muỗng, đũa dùng một lần', 1
FROM categories WHERE slug = 'do-gia-dung-mini';

-- =========================
-- CATEGORY CON: ĐỒ ĐÔNG LẠNH
-- =========================

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Kem', id, 'kem', 'Các loại kem đông lạnh', 1
FROM categories WHERE slug = 'do-dong-lanh';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Cá viên', id, 'ca-vien', 'Các loại cá viên đông lạnh', 1
FROM categories WHERE slug = 'do-dong-lanh';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Bò viên', id, 'bo-vien', 'Các loại bò viên đông lạnh', 1
FROM categories WHERE slug = 'do-dong-lanh';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Chả cá', id, 'cha-ca', 'Các loại chả cá đông lạnh', 1
FROM categories WHERE slug = 'do-dong-lanh';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Xúc xích đông lạnh', id, 'xuc-xich-dong-lanh', 'Các loại xúc xích đông lạnh', 1
FROM categories WHERE slug = 'do-dong-lanh';

INSERT INTO categories (name, parent_id, slug, description, is_active)
SELECT 'Há cảo / bánh bao đông lạnh', id, 'ha-cao-banh-bao-dong-lanh', 'Các loại há cảo và bánh bao đông lạnh', 1
FROM categories WHERE slug = 'do-dong-lanh';

#
# SET @logo_url_column_exists = (
#     SELECT COUNT(*)
#     FROM information_schema.COLUMNS
#     WHERE TABLE_SCHEMA = DATABASE()
#       AND TABLE_NAME = 'brands'
#       AND COLUMN_NAME = 'logo_url'
# );
#
# SET @add_logo_url_column_sql = IF(
#     @logo_url_column_exists = 0,
#     'ALTER TABLE brands ADD COLUMN logo_url VARCHAR(255)',
#     'SELECT 1'
# );
#
# PREPARE add_logo_url_column_stmt FROM @add_logo_url_column_sql;
# EXECUTE add_logo_url_column_stmt;
# DEALLOCATE PREPARE add_logo_url_column_stmt;
#
# INSERT INTO categories (id, name, slug, description, is_active)
# SELECT ref.id,
#        CONCAT('Seed Category #', ref.id),
#        CONCAT('seed-category-', ref.id),
#        'Auto-generated placeholder for V12 compatibility',
#        1
# FROM (
#          SELECT 10 AS id UNION ALL SELECT 11 UNION ALL SELECT 12 UNION ALL SELECT 13 UNION ALL SELECT 14
#          UNION ALL SELECT 15 UNION ALL SELECT 16 UNION ALL SELECT 17 UNION ALL SELECT 18 UNION ALL SELECT 19
#          UNION ALL SELECT 20 UNION ALL SELECT 22 UNION ALL SELECT 23 UNION ALL SELECT 24 UNION ALL SELECT 25
#          UNION ALL SELECT 27 UNION ALL SELECT 28 UNION ALL SELECT 30 UNION ALL SELECT 31 UNION ALL SELECT 32
#          UNION ALL SELECT 37 UNION ALL SELECT 38 UNION ALL SELECT 39 UNION ALL SELECT 40 UNION ALL SELECT 42
#          UNION ALL SELECT 43 UNION ALL SELECT 45 UNION ALL SELECT 46 UNION ALL SELECT 49 UNION ALL SELECT 52
#          UNION ALL SELECT 53 UNION ALL SELECT 54 UNION ALL SELECT 55 UNION ALL SELECT 60 UNION ALL SELECT 61
#          UNION ALL SELECT 62 UNION ALL SELECT 67 UNION ALL SELECT 68 UNION ALL SELECT 71
#      ) ref
# LEFT JOIN categories c ON c.id = ref.id
# WHERE c.id IS NULL;
#
# INSERT INTO brands (id, name, description, logo_url, is_active)
# SELECT ref.id,
#        CONCAT('Seed Brand #', ref.id),
#        'Auto-generated placeholder for V12 compatibility',
#        '/uploads/images/placeholder-brand.png',
#        1
# FROM (
#          SELECT 1 AS id UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5
#          UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 13
#          UNION ALL SELECT 14 UNION ALL SELECT 15 UNION ALL SELECT 16 UNION ALL SELECT 17 UNION ALL SELECT 18
#          UNION ALL SELECT 21 UNION ALL SELECT 23 UNION ALL SELECT 25 UNION ALL SELECT 26 UNION ALL SELECT 28
#          UNION ALL SELECT 29 UNION ALL SELECT 31 UNION ALL SELECT 33 UNION ALL SELECT 35 UNION ALL SELECT 38
#          UNION ALL SELECT 40 UNION ALL SELECT 42 UNION ALL SELECT 43 UNION ALL SELECT 44 UNION ALL SELECT 45
#          UNION ALL SELECT 46 UNION ALL SELECT 49 UNION ALL SELECT 51 UNION ALL SELECT 52 UNION ALL SELECT 54
#          UNION ALL SELECT 55 UNION ALL SELECT 56 UNION ALL SELECT 57 UNION ALL SELECT 58 UNION ALL SELECT 59
#          UNION ALL SELECT 60 UNION ALL SELECT 61 UNION ALL SELECT 63 UNION ALL SELECT 64
#      ) ref
# LEFT JOIN brands b ON b.id = ref.id
# WHERE b.id IS NULL;



INSERT INTO brands (name, description, logo_url, is_active) VALUES
                                                                ('Aquafina', 'Thương hiệu nước uống đóng chai của PepsiCo', '/uploads/images/logo_aquafina.jpg', 1),
                                                                ('La Vie', 'Thương hiệu nước khoáng thiên nhiên La Vie', '/uploads/images/logo_lavie.jpg', 1),
                                                                ('Coca-Cola', 'Thương hiệu nước giải khát Coca-Cola', '/uploads/images/logo_cocacola.jpg', 1),
                                                                ('Pepsi', 'Thương hiệu nước giải khát Pepsi', '/uploads/images/logo_pepsi.png', 1),
                                                                ('C2', 'Thương hiệu trà đóng chai C2', '/uploads/images/logo_C2.jpg', 1),
                                                                ('TEA+', 'Thương hiệu trà TEA+ của Suntory PepsiCo', '/uploads/images/logo_tea.jpg', 1),
                                                                ('Birdy', 'Thương hiệu cà phê lon/chai Birdy', '/uploads/images/logo_birdy.jpg', 1),
                                                                ('Vinamilk', 'Thương hiệu sữa Vinamilk', '/uploads/images/logo_vinamilk.jpg', 1),
                                                                ('TH true MILK', 'Thương hiệu sữa TH true MILK', '/uploads/images/logo_TH.jpg', 1),
                                                                ('Red Bull', 'Thương hiệu nước tăng lực Red Bull', '/uploads/images/logo_redbull.png', 1),
                                                                ('Sting', 'Thương hiệu nước tăng lực Sting', '/uploads/images/logo_sting.png', 1),
                                                                ('Vfresh', 'Thương hiệu nước ép Vfresh của Vinamilk', '/uploads/images/logo_vfresh.jpg', 1),
                                                                ('Cosy', 'Thương hiệu bánh quy Cosy', '/uploads/images/logo_cosy.jpg', 1),
                                                                ('Oreo', 'Thương hiệu bánh quy Oreo', '/uploads/images/logo_oreo.png', 1),
                                                                ('Alpenliebe', 'Thương hiệu kẹo Alpenliebe', '/uploads/images/logo_alpenliebe.jpg', 1),
                                                                ('Mentos', 'Thương hiệu kẹo Mentos', '/uploads/images/logo_mentos.jpg', 1),
                                                                ('KitKat', 'Thương hiệu socola KitKat', '/uploads/images/logo_kitkat.jpg', 1),
                                                                ('Cadbury', 'Thương hiệu socola Cadbury', '/uploads/images/logo_cadbury.jpg', 1),
                                                                ('Poca', 'Thương hiệu snack Poca', '/uploads/images/logo_poca.jpg', 1),
                                                                ('Oishi', 'Thương hiệu snack Oishi', '/uploads/images/logo_oishi.png', 1),
                                                                ('Tong Garden', 'Thương hiệu hạt dinh dưỡng Tong Garden', '/uploads/images/logo_tonggarden.png', 1),
                                                                ('Tao Kae Noi', 'Thương hiệu rong biển ăn liền Tao Kae Noi', '/uploads/images/logo_taokaenoi.jpg', 1),
                                                                ('Hảo Hảo', 'Thương hiệu mì ăn liền Hảo Hảo', '/uploads/images/logo__haohao.png', 1),
                                                                ('Omachi', 'Thương hiệu mì ăn liền Omachi', '/uploads/images/logo_machi.png', 1),
                                                                ('Gấu Đỏ', 'Thương hiệu cháo ăn liền Gấu Đỏ', '/uploads/images/logo_gaudo.jpg', 1),
                                                                ('Phở Đệ Nhất', 'Thương hiệu phở ăn liền Phở Đệ Nhất', '/uploads/images/logo_phodenhat.jpg', 1),
                                                                ('SG Food', 'Thương hiệu thực phẩm tiện lợi SG Food', '/uploads/images/logo_sgfood.png', 1),
                                                                ('Hạ Long Canfoco', 'Thương hiệu đồ hộp Hạ Long Canfoco', '/uploads/images/logo_halongcanfoco.jpg', 1),
                                                                ('Ayam Brand', 'Thương hiệu đồ hộp Ayam Brand', '/uploads/images/logo_ayambrand.jpg', 1),
                                                                ('Ponnie', 'Thương hiệu xúc xích tiệt trùng Ponnie', '/uploads/images/logo_ponnie.png', 1),
                                                                ('Vissan', 'Thương hiệu thực phẩm Vissan', '/uploads/images/logo_vissan.jpg', 1),
                                                                ('Nam Ngư', 'Thương hiệu nước mắm Nam Ngư', '/uploads/images/logo_namngu.png', 1),
                                                                ('CHINSU', 'Thương hiệu gia vị CHIN-SU', '/uploads/images/logo_chinsu.png', 1),
                                                                ('Maggi', 'Thương hiệu gia vị Maggi', '/uploads/images/logo_maggi.png', 1),
                                                                ('Heinz', 'Thương hiệu tương cà Heinz', '/uploads/images/logo_heinz.png', 1),
                                                                ('Neptune', 'Thương hiệu dầu ăn Neptune', '/uploads/images/logo_eptune.jpg', 1),
                                                                ('Biên Hòa', 'Thương hiệu đường Biên Hòa', '/uploads/images/logo_duongbienhoa.png', 1),
                                                                ('An Duyên', 'Thương hiệu muối và gia vị An Duyên', '/uploads/images/logo_anduyen.png', 1),
                                                                ('Knorr', 'Thương hiệu hạt nêm Knorr', '/uploads/images/logo_knorr.png', 1),
                                                                ('Clear Men', 'Thương hiệu dầu gội Clear Men', '/uploads/images/logo_clearmen.jpg', 1),
                                                                ('Head & Shoulders', 'Thương hiệu dầu gội Head & Shoulders', '/uploads/images/logo_headshoulders.png', 1),
                                                                ('Lifebuoy', 'Thương hiệu chăm sóc cá nhân Lifebuoy', '/uploads/images/logo_lifebuoy.jpg', 1),
                                                                ('P/S', 'Thương hiệu chăm sóc răng miệng P/S', '/uploads/images/logo_lifebuoy.jpg', 1),
                                                                ('Colgate', 'Thương hiệu chăm sóc răng miệng Colgate', '/uploads/images/logo_colgate.png', 1),
                                                                ('Listerine', 'Thương hiệu nước súc miệng Listerine', '/uploads/images/logo_listerine.png', 1),
                                                                ('Dettol', 'Thương hiệu nước rửa tay Dettol', '/uploads/images/logo_dettol.jpg', 1),
                                                                ('Bless You', 'Thương hiệu khăn giấy Bless You', '/uploads/images/logo_blessyou.png', 1),
                                                                ('Mamamy', 'Thương hiệu giấy ướt Mamamy', '/uploads/images/logo_mamamy.png', 1),
                                                                ('Jomi', 'Thương hiệu tăm bông Jomi', '/uploads/images/logo_jomi.jpg', 1),
                                                                ('Gillette', 'Thương hiệu dao cạo Gillette', '/uploads/images/logo_gillette.jpg', 1),
                                                                ('Unicharm', 'Thương hiệu khẩu trang Unicharm', '/uploads/images/logo_unicharm.png', 1),
                                                                ('Miniso', 'Thương hiệu đồ dùng cá nhân Miniso', '/uploads/images/logo_miniso.png', 1),
                                                                ('Thiên Long', 'Thương hiệu văn phòng phẩm Thiên Long', '/uploads/images/logo_thienlong.png', 1),
                                                                ('Pilot', 'Thương hiệu bút Pilot', '/uploads/images/logo_pilot.png', 1),
                                                                ('Hồng Hà', 'Thương hiệu văn phòng phẩm Hồng Hà', '/uploads/images/logo_hongha.png', 1),
                                                                ('Panasonic', 'Thương hiệu pin Panasonic', '/uploads/images/logo_panasonic.png', 1),
                                                                ('Energizer', 'Thương hiệu pin Energizer', '/uploads/images/logo_energizer.png', 1),
                                                                ('Cricket', 'Thương hiệu bật lửa Cricket', '/uploads/images/logo_cricket.jpg', 1),
                                                                ('Rando', 'Thương hiệu áo mưa Rando', '/uploads/images/logo_rando.png', 1),
                                                                ('AnEco', 'Thương hiệu sản phẩm dùng một lần thân thiện môi trường', '/uploads/images/logo_aneco.png', 1),
                                                                ('Bapobio', 'Thương hiệu ống hút sinh học Bapobio', '/uploads/images/logo_bapobio.jpg', 1),
                                                                ('Clean Wrap', 'Thương hiệu túi zip và màng bọc Clean Wrap', '/uploads/images/logo_cleanwrap.png', 1),
                                                                ('Opec', 'Thương hiệu túi rác Opec', '/uploads/images/logo_opec.jpg', 1),
                                                                ('Sunlight', 'Thương hiệu nước rửa chén Sunlight', '/uploads/images/logo_sunlight.jpg', 1),
                                                                ('Scotch-Brite', 'Thương hiệu mút rửa chén Scotch-Brite', '/uploads/images/logo_scotchbrite.jpg', 1),
                                                                ('Ecook', 'Thương hiệu giấy bạc Ecook', '/uploads/images/logo_ecook.jpg', 1),
                                                                ('Lock&Lock', 'Thương hiệu hộp nhựa Lock&Lock', '/uploads/images/logo_locklock.png', 1),
                                                                ('Merino', 'Thương hiệu kem Merino', '/uploads/images/logo_merino.jpg', 1),
                                                                ('C.P.', 'Thương hiệu thực phẩm C.P.', '/uploads/images/logo_cp.png', 1),
                                                                ('Cầu Tre', 'Thương hiệu thực phẩm đông lạnh Cầu Tre', '/uploads/images/logo_cautre.png', 1),
                                                                ('Đức Việt', 'Thương hiệu xúc xích Đức Việt', '/uploads/images/logo_ducviet.png', 1);



INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Nước suối Aquafina 500ml',
       'Nước suối tinh khiết Aquafina chai 500ml',
       c.id, b.id, '/uploads/images/product_nuoc-suoi-aquafina-500ml.jpg', 1
FROM categories c, brands b
WHERE c.slug='nuoc-suoi' AND b.name='Aquafina';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Nước khoáng La Vie 500ml',
       'Nước khoáng thiên nhiên La Vie chai 500ml',
       c.id, b.id, '/uploads/images/product_nuoc-khoang-la-vie-500ml.jpg', 1
FROM categories c, brands b
WHERE c.slug='nuoc-suoi' AND b.name='La Vie';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Nước khoáng La Vie Sport',
       'Nước khoáng La Vie nắp thể thao',
       c.id, b.id, '/uploads/images/product_nuoc-khoang-la-vie-sport.jpg', 1
FROM categories c, brands b
WHERE c.slug='nuoc-suoi' AND b.name='La Vie';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Coca-Cola lon 330ml',
       'Nước ngọt Coca-Cola có gas lon 330ml',
       c.id, b.id, '/uploads/images/product_coca-cola-lon-330ml.jpg', 1
FROM categories c, brands b
WHERE c.slug='nuoc-ngot' AND b.name='Coca-Cola';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Pepsi lon 330ml',
       'Nước ngọt Pepsi có gas lon 330ml',
       c.id, b.id, '/uploads/images/product_pepsi-lon-330ml.jpg', 1
FROM categories c, brands b
WHERE c.slug='nuoc-ngot' AND b.name='Pepsi';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Coca-Cola Zero lon',
       'Coca-Cola không đường lon',
       c.id, b.id, '/uploads/images/product_coca-cola-zero-lon.jpg', 1
FROM categories c, brands b
WHERE c.slug='nuoc-ngot' AND b.name='Coca-Cola';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Trà xanh C2 455ml',
       'Trà xanh đóng chai C2 hương tự nhiên',
       c.id, b.id, '/uploads/images/product_tra-xanh-c2-455ml.jpg', 1
FROM categories c, brands b
WHERE c.slug='tra-dong-chai' AND b.name='C2';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Trà TEA+ Oolong 450ml',
       'Trà ô long TEA+ ít đường',
       c.id, b.id, '/uploads/images/product_tra-tea-plus-oolong-450ml.jpg', 1
FROM categories c, brands b
WHERE c.slug='tra-dong-chai' AND b.name='TEA+';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Trà C2 chanh 455ml',
       'Trà chanh C2 đóng chai',
       c.id, b.id, '/uploads/images/product_tra-c2-chanh-455ml.jpg', 1
FROM categories c, brands b
WHERE c.slug='tra-dong-chai' AND b.name='C2';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Trà TEA+ mật ong',
       'Trà TEA+ hương mật ong',
       c.id, b.id, '/uploads/images/product_tra-tea-plus-mat-ong.jpg', 1
FROM categories c, brands b
WHERE c.slug='tra-dong-chai' AND b.name='TEA+';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Trà TEA+ đào',
       'Trà ô long TEA+ vị đào',
       c.id, b.id, '/uploads/images/product_tra-tea-plus-dao.jpg', 1
FROM categories c, brands b
WHERE c.slug='tra-dong-chai' AND b.name='TEA+';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Cà phê sữa Birdy lon 170ml',
       'Cà phê sữa Birdy uống liền',
       c.id, b.id, '/uploads/images/product_ca-phe-sua-birdy-lon-170ml.jpg', 1
FROM categories c, brands b
WHERE c.slug='ca-phe-lon-chai' AND b.name='Birdy';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Sữa tươi Vinamilk 100%',
       'Sữa tươi tiệt trùng Vinamilk hộp 180ml',
       c.id, b.id, '/uploads/images/product_sua-tuoi-vinamilk-100.jpg', 1
FROM categories c, brands b
WHERE c.slug='sua' AND b.name='Vinamilk';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Sữa tươi TH true MILK ít đường',
       'Sữa tươi TH true MILK hộp 180ml',
       c.id, b.id, '/uploads/images/product_sua-tuoi-th-true-milk-it-duong.jpg', 1
FROM categories c, brands b
WHERE c.slug='sua' AND b.name='TH true MILK';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Red Bull Thái 250ml',
       'Nước tăng lực Red Bull lon 250ml',
       c.id, b.id, '/uploads/images/product_red-bull-thai-250ml.jpg', 1
FROM categories c, brands b
WHERE c.slug='nuoc-tang-luc' AND b.name='Red Bull';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Sting dâu 330ml',
       'Nước tăng lực Sting hương dâu',
       c.id, b.id, '/uploads/images/product_sting-dau-330ml.jpg', 1
FROM categories c, brands b
WHERE c.slug='nuoc-tang-luc' AND b.name='Sting';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Nước ép cam Vfresh',
       'Nước ép cam Vfresh hộp giấy',
       c.id, b.id, '/uploads/images/product_nuoc-ep-cam-vfresh.jpg', 1
FROM categories c, brands b
WHERE c.slug='nuoc-ep' AND b.name='Vfresh';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Bánh quy Cosy Marie',
       'Bánh quy bơ Cosy Marie hộp giấy',
       c.id, b.id, '/uploads/images/product_banh-quy-cosy-marie.jpg', 1
FROM categories c, brands b
WHERE c.slug='banh-quy' AND b.name='Cosy';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Bánh Cosy Marie dừa',
       'Bánh quy Cosy vị dừa',
       c.id, b.id, '/uploads/images/product_banh-cosy-marie-dua.jpg', 1
FROM categories c, brands b
WHERE c.slug='banh-quy' AND b.name='Cosy';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Oreo Chocolate Cream',
       'Bánh Oreo kem socola',
       c.id, b.id, '/uploads/images/product_oreo-chocolate-cream.jpg', 1
FROM categories c, brands b
WHERE c.slug='banh-quy' AND b.name='Oreo';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Oreo Matcha',
       'Bánh Oreo vị trà xanh',
       c.id, b.id, '/uploads/images/product_oreo-matcha.jpg', 1
FROM categories c, brands b
WHERE c.slug='banh-quy' AND b.name='Oreo';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Bánh Oreo Original',
       'Bánh quy socola kẹp kem Oreo',
       c.id, b.id, '/uploads/images/product_oreo-original.jpg', 1
FROM categories c, brands b
WHERE c.slug='banh-quy' AND b.name='Oreo';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Kẹo Alpenliebe sữa',
       'Kẹo cứng Alpenliebe vị sữa',
       c.id, b.id, '/uploads/images/product_keo-alpenliebe-sua.jpg', 1
FROM categories c, brands b
WHERE c.slug='keo' AND b.name='Alpenliebe';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Kẹo Mentos bạc hà',
       'Kẹo dẻo Mentos vị bạc hà',
       c.id, b.id, '/uploads/images/product_keo-mentos-bac-ha.jpg', 1
FROM categories c, brands b
WHERE c.slug='keo' AND b.name='Mentos';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Socola KitKat 4F',
       'Socola KitKat 4 thanh',
       c.id, b.id, '/uploads/images/product_socola-kitkat-4f.jpg', 1
FROM categories c, brands b
WHERE c.slug='socola' AND b.name='KitKat';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Socola Cadbury Dairy Milk',
       'Socola sữa Cadbury thanh lớn',
       c.id, b.id, '/uploads/images/product_socola-cadbury-dairy-milk.jpg', 1
FROM categories c, brands b
WHERE c.slug='socola' AND b.name='Cadbury';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Snack khoai tây Poca vị bò lúc lắc',
       'Snack khoai tây lát Poca',
       c.id, b.id, '/uploads/images/product_snack-poca-vi-bo-luc-lac.jpg', 1
FROM categories c, brands b
WHERE c.slug='snack' AND b.name='Poca';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Snack bắp ngọt Oishi',
       'Snack bắp Oishi giòn tan',
       c.id, b.id, '/uploads/images/product_snack-bap-ngot-oishi.jpg', 1
FROM categories c, brands b
WHERE c.slug='snack' AND b.name='Oishi';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Hạt điều rang muối Tong Garden',
       'Hạt điều rang muối cao cấp',
       c.id, b.id, '/uploads/images/product_hat-dieu-rang-muoi-tong-garden.jpg', 1
FROM categories c, brands b
WHERE c.slug='hat-dinh-duong' AND b.name='Tong Garden';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Rong biển Tao Kae Noi giòn',
       'Rong biển sấy giòn ăn liền',
       c.id, b.id, '/uploads/images/product_rong-bien-tao-kae-noi-gion.jpg', 1
FROM categories c, brands b
WHERE c.slug='rong-bien-an-lien' AND b.name='Tao Kae Noi';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Mì Hảo Hảo tôm chua cay',
       'Mì ăn liền Hảo Hảo vị tôm chua cay',
       c.id, b.id, '/uploads/images/product_mi-hao-hao-tom-chua-cay.jpg', 1
FROM categories c, brands b
WHERE c.slug='mi-goi' AND b.name='Hảo Hảo';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Mì Omachi sườn hầm',
       'Mì khoai tây Omachi vị sườn hầm',
       c.id, b.id, '/uploads/images/product_mi-omachi-suon-ham.jpg', 1
FROM categories c, brands b
WHERE c.slug='mi-goi' AND b.name='Omachi';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Cháo Gấu Đỏ thịt bằm',
       'Cháo ăn liền Gấu Đỏ thịt bằm',
       c.id, b.id, '/uploads/images/product_chao-gau-do-thit-bam.jpg', 1
FROM categories c, brands b
WHERE c.slug='chao-goi' AND b.name='Gấu Đỏ';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Phở bò Phở Đệ Nhất',
       'Phở ăn liền Phở Đệ Nhất vị bò',
       c.id, b.id, '/uploads/images/product_pho-bo-pho-de-nhat.jpg', 1
FROM categories c, brands b
WHERE c.slug='mien-pho-an-lien' AND b.name='Phở Đệ Nhất';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Cơm tự sôi thịt kho SG Food',
       'Cơm tự sôi tiện lợi SG Food',
       c.id, b.id, '/uploads/images/product_com-tu-soi-thit-kho-sg-food.jpg', 1
FROM categories c, brands b
WHERE c.slug='com-tu-soi' AND b.name='SG Food';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Cá hộp Hạ Long sốt cà',
       'Cá hộp Hạ Long Canfoco sốt cà',
       c.id, b.id, '/uploads/images/product_ca-hop-ha-long-sot-ca.jpg', 1
FROM categories c, brands b
WHERE c.slug='do-hop' AND b.name='Hạ Long Canfoco';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Cá ngừ hộp Ayam Brand',
       'Cá ngừ đóng hộp Ayam Brand',
       c.id, b.id, '/uploads/images/product_ca-ngu-hop-ayam-brand.jpg', 1
FROM categories c, brands b
WHERE c.slug='do-hop' AND b.name='Ayam Brand';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Xúc xích Ponnie tiệt trùng',
       'Xúc xích tiệt trùng Ponnie gói nhỏ',
       c.id, b.id, '/uploads/images/product_xuc-xich-ponnie-tiet-trung.jpg', 1
FROM categories c, brands b
WHERE c.slug='xuc-xich' AND b.name='Ponnie';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Lạp xưởng tươi Vissan',
       'Lạp xưởng tươi đóng gói Vissan',
       c.id, b.id, '/uploads/images/product_lap-xuong-tuoi-vissan.jpg', 1
FROM categories c, brands b
WHERE c.slug='lap-xuong' AND b.name='Vissan';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Nước mắm Nam Ngư 500ml',
       'Nước mắm Nam Ngư chai 500ml',
       c.id, b.id, '/uploads/images/product_nuoc-mam-nam-ngu-500ml.jpg', 1
FROM categories c, brands b
WHERE c.slug='nuoc-mam' AND b.name='Nam Ngư';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Nước tương CHIN-SU',
       'Nước tương CHIN-SU đậm đặc',
       c.id, b.id, '/uploads/images/product_nuoc-tuong-chinsu.jpg', 1
FROM categories c, brands b
WHERE c.slug='nuoc-tuong' AND b.name='CHINSU';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Nước mắm Nam Ngư đệ nhị',
       'Nước mắm Nam Ngư cao cấp',
       c.id, b.id, '/uploads/images/product_nuoc-mam-nam-ngu-de-nhi.jpg', 1
FROM categories c, brands b
WHERE c.slug='nuoc-mam' AND b.name='Nam Ngư';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Nước mắm Nam Ngư chai lớn',
       'Nước mắm Nam Ngư 1L',
       c.id, b.id, '/uploads/images/product_nuoc-mam-nam-ngu-chai-lon.jpg', 1
FROM categories c, brands b
WHERE c.slug='nuoc-mam' AND b.name='Nam Ngư';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Nước mắm Nam Ngư nhãn vàng',
       'Nước mắm truyền thống Nam Ngư',
       c.id, b.id, '/uploads/images/product_nuoc-mam-nam-ngu-nhan-vang.jpg', 1
FROM categories c, brands b
WHERE c.slug='nuoc-mam' AND b.name='Nam Ngư';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Nước tương Maggi',
       'Nước tương Maggi chai nhỏ',
       c.id, b.id, '/uploads/images/product_nuoc-tuong-maggi.jpg', 1
FROM categories c, brands b
WHERE c.slug='nuoc-tuong' AND b.name='Maggi';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Tương ớt CHIN-SU',
       'Tương ớt CHIN-SU chai 250g',
       c.id, b.id, '/uploads/images/product_tuong-ot-chinsu.jpg', 1
FROM categories c, brands b
WHERE c.slug='tuong-ot' AND b.name='CHINSU';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Tương cà Heinz',
       'Tương cà chua Heinz chai nhỏ',
       c.id, b.id, '/uploads/images/product_tuong-ca-heinz.jpg', 1
FROM categories c, brands b
WHERE c.slug='tuong-ca' AND b.name='Heinz';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Dầu ăn Neptune Light 400ml',
       'Dầu ăn Neptune chai nhỏ',
       c.id, b.id, '/uploads/images/product_dau-an-neptune-light-400ml.jpg', 1
FROM categories c, brands b
WHERE c.slug='dau-an-mini' AND b.name='Neptune';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Đường tinh luyện Biên Hòa',
       'Đường cát trắng Biên Hòa',
       c.id, b.id, '/uploads/images/product_duong-tinh-luyen-bien-hoa.jpg', 1
FROM categories c, brands b
WHERE c.slug='duong' AND b.name='Biên Hòa';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Muối biển An Duyên',
       'Muối biển tinh khiết An Duyên',
       c.id, b.id, '/uploads/images/product_muoi-bien-an-duyen.jpg', 1
FROM categories c, brands b
WHERE c.slug='muoi' AND b.name='An Duyên';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Hạt nêm Knorr thịt thăn',
       'Hạt nêm Knorr vị thịt',
       c.id, b.id, '/uploads/images/product_hat-nem-knorr-thit-than.jpg', 1
FROM categories c, brands b
WHERE c.slug='bot-nem' AND b.name='Knorr';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Dầu gội Clear Men bạc hà',
       'Dầu gội nam Clear Men mát lạnh',
       c.id, b.id, '/uploads/images/product_dau-goi-clear-men-bac-ha.jpg', 1
FROM categories c, brands b
WHERE c.slug='dau-goi' AND b.name='Clear Men';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Clear Men Deep Clean',
       'Dầu gội Clear Men sạch sâu',
       c.id, b.id, '/uploads/images/product_clear-men-deep-clean.jpg', 1
FROM categories c, brands b
WHERE c.slug='dau-goi' AND b.name='Clear Men';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Head & Shoulders bạc hà',
       'Dầu gội Head & Shoulders mát lạnh',
       c.id, b.id, '/uploads/images/product_head-shoulders-bac-ha.jpg', 1
FROM categories c, brands b
WHERE c.slug='dau-goi' AND b.name='Head & Shoulders';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Head & Shoulders suôn mượt',
       'Dầu gội Head & Shoulders mềm tóc',
       c.id, b.id, '/uploads/images/product_head-shoulders-suon-muot.jpg', 1
FROM categories c, brands b
WHERE c.slug='dau-goi' AND b.name='Head & Shoulders';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Dầu gội Head & Shoulders',
       'Dầu gội sạch gàu Head & Shoulders',
       c.id, b.id, '/uploads/images/product_dau-goi-head-shoulders.jpg', 1
FROM categories c, brands b
WHERE c.slug='dau-goi' AND b.name='Head & Shoulders';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Sữa tắm Lifebuoy bảo vệ da',
       'Sữa tắm Lifebuoy diệt khuẩn',
       c.id, b.id, '/uploads/images/product_sua-tam-lifebuoy-bao-ve-da.jpg', 1
FROM categories c, brands b
WHERE c.slug='sua-tam' AND b.name='Lifebuoy';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Kem đánh răng P/S than hoạt tính',
       'Kem đánh răng P/S trắng răng',
       c.id, b.id, '/uploads/images/product_kem-danh-rang-ps-than-hoat-tinh.jpg', 1
FROM categories c, brands b
WHERE c.slug='kem-danh-rang' AND b.name='P/S';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Kem đánh răng Colgate Total',
       'Kem đánh răng Colgate bảo vệ toàn diện',
       c.id, b.id, '/uploads/images/product_kem-danh-rang-colgate-total.jpg', 1
FROM categories c, brands b
WHERE c.slug='kem-danh-rang' AND b.name='Colgate';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Nước súc miệng Listerine Cool Mint',
       'Nước súc miệng Listerine bạc hà',
       c.id, b.id, '/uploads/images/product_nuoc-suc-mieng-listerine-cool-mint.jpg', 1
FROM categories c, brands b
WHERE c.slug='nuoc-suc-mieng' AND b.name='Listerine';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Nước rửa tay Dettol',
       'Nước rửa tay diệt khuẩn Dettol',
       c.id, b.id, '/uploads/images/product_nuoc-rua-tay-dettol.jpg', 1
FROM categories c, brands b
WHERE c.slug='nuoc-rua-tay' AND b.name='Dettol';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Khăn giấy Bless You',
       'Khăn giấy khô Bless You 2 lớp',
       c.id, b.id, '/uploads/images/product_khan-giay-bless-you.jpg', 1
FROM categories c, brands b
WHERE c.slug='khan-giay' AND b.name='Bless You';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Khăn ướt Mamamy không mùi',
       'Khăn giấy ướt Mamamy dịu nhẹ',
       c.id, b.id, '/uploads/images/product_khan-uot-mamamy-khong-mui.jpg', 1
FROM categories c, brands b
WHERE c.slug='giay-uot' AND b.name='Mamamy';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Bàn chải đánh răng P/S lông mềm',
       'Bàn chải đánh răng P/S chăm sóc nướu',
       c.id, b.id, '/uploads/images/product_ban-chai-danh-rang-ps-long-mem.jpg', 1
FROM categories c, brands b
WHERE c.slug='ban-chai' AND b.name='P/S';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Dao cạo râu Gillette Blue 3',
       'Dao cạo râu Gillette 3 lưỡi',
       c.id, b.id, '/uploads/images/product_dao-cao-rau-gillette-blue-3.jpg', 1
FROM categories c, brands b
WHERE c.slug='dao-cao' AND b.name='Gillette';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Tăm bông Jomi hộp tròn',
       'Tăm bông vệ sinh tai Jomi',
       c.id, b.id, '/uploads/images/product_tam-bong-jomi-hop-tron.jpg', 1
FROM categories c, brands b
WHERE c.slug='tam-bong' AND b.name='Jomi';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Khẩu trang y tế Unicharm 3D',
       'Khẩu trang Unicharm lọc bụi mịn',
       c.id, b.id, '/uploads/images/product_khau-trang-y-te-unicharm-3d.jpg', 1
FROM categories c, brands b
WHERE c.slug='khau-trang' AND b.name='Unicharm';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Lược chải tóc Miniso',
       'Lược nhựa chải tóc Miniso',
       c.id, b.id, '/uploads/images/product_luoc-chai-toc-miniso.jpg', 1
FROM categories c, brands b
WHERE c.slug='luoc' AND b.name='Miniso';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Gương mini gấp gọn Miniso',
       'Gương soi cá nhân bỏ túi Miniso',
       c.id, b.id, '/uploads/images/product_guong-mini-gap-gon-miniso.jpg', 1
FROM categories c, brands b
WHERE c.slug='guong-mini' AND b.name='Miniso';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Túi rác cuộn Opec',
       'Túi rác gia đình Opec loại cuộn',
       c.id, b.id, '/uploads/images/product_tui-rac-cuon-opec.jpg', 1
FROM categories c, brands b
WHERE c.slug='tui-rac' AND b.name='Opec';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Nước rửa chén Sunlight chanh',
       'Nước rửa chén Sunlight sạch dầu mỡ',
       c.id, b.id, '/uploads/images/product_nuoc-rua-chen-sunlight-chanh.jpg', 1
FROM categories c, brands b
WHERE c.slug='nuoc-rua-chen' AND b.name='Sunlight';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Mút rửa chén Scotch-Brite',
       'Mút rửa chén Scotch-Brite siêu bền',
       c.id, b.id, '/uploads/images/product_mut-rua-chen-scotch-brite.jpg', 1
FROM categories c, brands b
WHERE c.slug='mut-rua-chen' AND b.name='Scotch-Brite';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Màng bọc thực phẩm Clean Wrap',
       'Màng bọc thực phẩm an toàn Clean Wrap',
       c.id, b.id, '/uploads/images/product_mang-boc-thuc-pham-clean-wrap.jpg', 1
FROM categories c, brands b
WHERE c.slug='mang-boc-thuc-pham' AND b.name='Clean Wrap';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Giấy bạc Ecook 5m',
       'Giấy bạc bọc thực phẩm Ecook',
       c.id, b.id, '/uploads/images/product_giay-bac-ecook-5m.jpg', 1
FROM categories c, brands b
WHERE c.slug='giay-bac' AND b.name='Ecook';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Hộp nhựa Lock&Lock 1L',
       'Hộp bảo quản thực phẩm Lock&Lock',
       c.id, b.id, '/uploads/images/product_hop-nhua-lock-and-lock-1l.jpg', 1
FROM categories c, brands b
WHERE c.slug='hop-nhua' AND b.name='Lock&Lock';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Bộ muỗng nhựa AnEco 10 cái',
       'Muỗng nhựa sinh học AnEco',
       c.id, b.id, '/uploads/images/product_bo-muong-nhua-aneco-10-cai.jpg', 1
FROM categories c, brands b
WHERE c.slug='chen-muong-dua-dung-mot-lan' AND b.name='AnEco';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Kem Merino chocolate',
       'Kem que Merino vị chocolate',
       c.id, b.id, '/uploads/images/product_kem-merino-chocolate.jpg', 1
FROM categories c, brands b
WHERE c.slug='kem' AND b.name='Merino';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Cá viên C.P.',
       'Cá viên đông lạnh C.P.',
       c.id, b.id, '/uploads/images/product_ca-vien-cp.jpg', 1
FROM categories c, brands b
WHERE c.slug='ca-vien' AND b.name='C.P.';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Bò viên Cầu Tre',
       'Bò viên đông lạnh Cầu Tre',
       c.id, b.id, '/uploads/images/product_bo-vien-cau-tre.jpg', 1
FROM categories c, brands b
WHERE c.slug='bo-vien' AND b.name='Cầu Tre';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Chả cá Cầu Tre',
       'Chả cá đông lạnh Cầu Tre',
       c.id, b.id, '/uploads/images/product_cha-ca-cau-tre.jpg', 1
FROM categories c, brands b
WHERE c.slug='cha-ca' AND b.name='Cầu Tre';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Xúc xích Đức Việt đông lạnh',
       'Xúc xích Đức Việt bảo quản đông lạnh',
       c.id, b.id, '/uploads/images/product_xuc-xich-duc-viet-dong-lanh.jpg', 1
FROM categories c, brands b
WHERE c.slug='xuc-xich-dong-lanh' AND b.name='Đức Việt';

INSERT INTO products (name, description, category_id, brand_id, image_url, is_active)
SELECT 'Há cảo C.P. đông lạnh',
       'Há cảo tôm thịt đông lạnh C.P.',
       c.id, b.id, '/uploads/images/product_ha-cao-cp-dong-lanh.jpg', 1
FROM categories c, brands b
WHERE c.slug='ha-cao-banh-bao-dong-lanh' AND b.name='C.P.';


-- =========================
-- PRODUCT_UNITS: MỖI SẢN PHẨM 2 ĐƠN VỊ
-- =========================

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 7000, 10, 1, 1 FROM products WHERE name = 'Nước suối Aquafina 500ml'
UNION ALL
SELECT id, 'thùng', 24, NULL, 160000, 5, 0, 1 FROM products WHERE name = 'Nước suối Aquafina 500ml';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 8000, 10, 1, 1 FROM products WHERE name = 'Nước khoáng La Vie 500ml'
UNION ALL
SELECT id, 'thùng', 24, NULL, 180000, 5, 0, 1 FROM products WHERE name = 'Nước khoáng La Vie 500ml';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 9000, 10, 1, 1 FROM products WHERE name = 'Nước khoáng La Vie Sport'
UNION ALL
SELECT id, 'thùng', 24, NULL, 200000, 5, 0, 1 FROM products WHERE name = 'Nước khoáng La Vie Sport';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'lon', 1, NULL, 10000, 10, 1, 1 FROM products WHERE name = 'Coca-Cola lon 330ml'
UNION ALL
SELECT id, 'thùng', 24, NULL, 220000, 5, 0, 1 FROM products WHERE name = 'Coca-Cola lon 330ml';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'lon', 1, NULL, 10000, 10, 1, 1 FROM products WHERE name = 'Pepsi lon 330ml'
UNION ALL
SELECT id, 'thùng', 24, NULL, 220000, 5, 0, 1 FROM products WHERE name = 'Pepsi lon 330ml';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'lon', 1, NULL, 10000, 10, 1, 1 FROM products WHERE name = 'Coca-Cola Zero lon'
UNION ALL
SELECT id, 'thùng', 24, NULL, 220000, 5, 0, 1 FROM products WHERE name = 'Coca-Cola Zero lon';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 10000, 10, 1, 1 FROM products WHERE name = 'Trà xanh C2 455ml'
UNION ALL
SELECT id, 'thùng', 24, NULL, 220000, 5, 0, 1 FROM products WHERE name = 'Trà xanh C2 455ml';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 12000, 10, 1, 1 FROM products WHERE name = 'Trà TEA+ Oolong 450ml'
UNION ALL
SELECT id, 'thùng', 24, NULL, 260000, 5, 0, 1 FROM products WHERE name = 'Trà TEA+ Oolong 450ml';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 10000, 10, 1, 1 FROM products WHERE name = 'Trà C2 chanh 455ml'
UNION ALL
SELECT id, 'thùng', 24, NULL, 220000, 5, 0, 1 FROM products WHERE name = 'Trà C2 chanh 455ml';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 12000, 10, 1, 1 FROM products WHERE name = 'Trà TEA+ mật ong'
UNION ALL
SELECT id, 'thùng', 24, NULL, 260000, 5, 0, 1 FROM products WHERE name = 'Trà TEA+ mật ong';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 12000, 10, 1, 1 FROM products WHERE name = 'Trà TEA+ đào'
UNION ALL
SELECT id, 'thùng', 24, NULL, 260000, 5, 0, 1 FROM products WHERE name = 'Trà TEA+ đào';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'lon', 1, NULL, 15000, 10, 1, 1 FROM products WHERE name = 'Cà phê sữa Birdy lon 170ml'
UNION ALL
SELECT id, 'thùng', 24, NULL, 330000, 5, 0, 1 FROM products WHERE name = 'Cà phê sữa Birdy lon 170ml';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'hộp', 1, NULL, 9000, 10, 1, 1 FROM products WHERE name = 'Sữa tươi Vinamilk 100%'
UNION ALL
SELECT id, 'thùng', 48, NULL, 390000, 5, 0, 1 FROM products WHERE name = 'Sữa tươi Vinamilk 100%';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'hộp', 1, NULL, 10000, 10, 1, 1 FROM products WHERE name = 'Sữa tươi TH true MILK ít đường'
UNION ALL
SELECT id, 'thùng', 48, NULL, 430000, 5, 0, 1 FROM products WHERE name = 'Sữa tươi TH true MILK ít đường';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'lon', 1, NULL, 15000, 10, 1, 1 FROM products WHERE name = 'Red Bull Thái 250ml'
UNION ALL
SELECT id, 'thùng', 24, NULL, 330000, 5, 0, 1 FROM products WHERE name = 'Red Bull Thái 250ml';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 12000, 10, 1, 1 FROM products WHERE name = 'Sting dâu 330ml'
UNION ALL
SELECT id, 'thùng', 24, NULL, 260000, 5, 0, 1 FROM products WHERE name = 'Sting dâu 330ml';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'hộp', 1, NULL, 12000, 10, 1, 1 FROM products WHERE name = 'Nước ép cam Vfresh'
UNION ALL
SELECT id, 'thùng', 48, NULL, 520000, 5, 0, 1 FROM products WHERE name = 'Nước ép cam Vfresh';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'hộp', 1, NULL, 25000, 10, 1, 1 FROM products WHERE name = 'Bánh quy Cosy Marie'
UNION ALL
SELECT id, 'thùng', 12, NULL, 270000, 5, 0, 1 FROM products WHERE name = 'Bánh quy Cosy Marie';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'hộp', 1, NULL, 25000, 10, 1, 1 FROM products WHERE name = 'Bánh Cosy Marie dừa'
UNION ALL
SELECT id, 'thùng', 12, NULL, 270000, 5, 0, 1 FROM products WHERE name = 'Bánh Cosy Marie dừa';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'gói', 1, NULL, 12000, 10, 1, 1 FROM products WHERE name = 'Oreo Chocolate Cream'
UNION ALL
SELECT id, 'thùng', 24, NULL, 260000, 5, 0, 1 FROM products WHERE name = 'Oreo Chocolate Cream';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'gói', 1, NULL, 12000, 10, 1, 1 FROM products WHERE name = 'Oreo Matcha'
UNION ALL
SELECT id, 'thùng', 24, NULL, 260000, 5, 0, 1 FROM products WHERE name = 'Oreo Matcha';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'gói', 1, NULL, 12000, 10, 1, 1 FROM products WHERE name = 'Bánh Oreo Original'
UNION ALL
SELECT id, 'thùng', 24, NULL, 260000, 5, 0, 1 FROM products WHERE name = 'Bánh Oreo Original';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'gói', 1, NULL, 10000, 10, 1, 1 FROM products WHERE name = 'Kẹo Alpenliebe sữa'
UNION ALL
SELECT id, 'thùng', 20, NULL, 180000, 5, 0, 1 FROM products WHERE name = 'Kẹo Alpenliebe sữa';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'cuộn', 1, NULL, 12000, 10, 1, 1 FROM products WHERE name = 'Kẹo Mentos bạc hà'
UNION ALL
SELECT id, 'hộp', 24, NULL, 260000, 5, 0, 1 FROM products WHERE name = 'Kẹo Mentos bạc hà';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'thanh', 1, NULL, 12000, 10, 1, 1 FROM products WHERE name = 'Socola KitKat 4F'
UNION ALL
SELECT id, 'hộp', 24, NULL, 260000, 5, 0, 1 FROM products WHERE name = 'Socola KitKat 4F';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'thanh', 1, NULL, 18000, 10, 1, 1 FROM products WHERE name = 'Socola Cadbury Dairy Milk'
UNION ALL
SELECT id, 'hộp', 24, NULL, 390000, 5, 0, 1 FROM products WHERE name = 'Socola Cadbury Dairy Milk';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'gói', 1, NULL, 12000, 10, 1, 1 FROM products WHERE name = 'Snack khoai tây Poca vị bò lúc lắc'
UNION ALL
SELECT id, 'thùng', 30, NULL, 330000, 5, 0, 1 FROM products WHERE name = 'Snack khoai tây Poca vị bò lúc lắc';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'gói', 1, NULL, 10000, 10, 1, 1 FROM products WHERE name = 'Snack bắp ngọt Oishi'
UNION ALL
SELECT id, 'thùng', 30, NULL, 270000, 5, 0, 1 FROM products WHERE name = 'Snack bắp ngọt Oishi';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'gói', 1, NULL, 30000, 10, 1, 1 FROM products WHERE name = 'Hạt điều rang muối Tong Garden'
UNION ALL
SELECT id, 'thùng', 20, NULL, 540000, 5, 0, 1 FROM products WHERE name = 'Hạt điều rang muối Tong Garden';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'gói', 1, NULL, 18000, 10, 1, 1 FROM products WHERE name = 'Rong biển Tao Kae Noi giòn'
UNION ALL
SELECT id, 'thùng', 24, NULL, 390000, 5, 0, 1 FROM products WHERE name = 'Rong biển Tao Kae Noi giòn';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'gói', 1, NULL, 5000, 10, 1, 1 FROM products WHERE name = 'Mì Hảo Hảo tôm chua cay'
UNION ALL
SELECT id, 'thùng', 30, NULL, 135000, 5, 0, 1 FROM products WHERE name = 'Mì Hảo Hảo tôm chua cay';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'ly', 1, NULL, 8000, 10, 1, 1 FROM products WHERE name = 'Mì Omachi sườn hầm'
UNION ALL
SELECT id, 'thùng', 24, NULL, 175000, 5, 0, 1 FROM products WHERE name = 'Mì Omachi sườn hầm';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'gói', 1, NULL, 8000, 10, 1, 1 FROM products WHERE name = 'Cháo Gấu Đỏ thịt bằm'
UNION ALL
SELECT id, 'thùng', 30, NULL, 215000, 5, 0, 1 FROM products WHERE name = 'Cháo Gấu Đỏ thịt bằm';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'tô', 1, NULL, 12000, 10, 1, 1 FROM products WHERE name = 'Phở bò Phở Đệ Nhất'
UNION ALL
SELECT id, 'thùng', 24, NULL, 260000, 5, 0, 1 FROM products WHERE name = 'Phở bò Phở Đệ Nhất';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'khay', 1, NULL, 35000, 10, 1, 1 FROM products WHERE name = 'Cơm tự sôi thịt kho SG Food'
UNION ALL
SELECT id, 'thùng', 12, NULL, 390000, 5, 0, 1 FROM products WHERE name = 'Cơm tự sôi thịt kho SG Food';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'hộp', 1, NULL, 28000, 10, 1, 1 FROM products WHERE name = 'Cá hộp Hạ Long sốt cà'
UNION ALL
SELECT id, 'thùng', 24, NULL, 600000, 5, 0, 1 FROM products WHERE name = 'Cá hộp Hạ Long sốt cà';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'hộp', 1, NULL, 42000, 10, 1, 1 FROM products WHERE name = 'Cá ngừ hộp Ayam Brand'
UNION ALL
SELECT id, 'thùng', 24, NULL, 900000, 5, 0, 1 FROM products WHERE name = 'Cá ngừ hộp Ayam Brand';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'gói', 1, NULL, 18000, 10, 1, 1 FROM products WHERE name = 'Xúc xích Ponnie tiệt trùng'
UNION ALL
SELECT id, 'thùng', 20, NULL, 320000, 5, 0, 1 FROM products WHERE name = 'Xúc xích Ponnie tiệt trùng';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'gói', 1, NULL, 65000, 10, 1, 1 FROM products WHERE name = 'Lạp xưởng tươi Vissan'
UNION ALL
SELECT id, 'thùng', 10, NULL, 600000, 5, 0, 1 FROM products WHERE name = 'Lạp xưởng tươi Vissan';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 25000, 10, 1, 1 FROM products WHERE name = 'Nước mắm Nam Ngư 500ml'
UNION ALL
SELECT id, 'thùng', 12, NULL, 270000, 5, 0, 1 FROM products WHERE name = 'Nước mắm Nam Ngư 500ml';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 18000, 10, 1, 1 FROM products WHERE name = 'Nước tương CHIN-SU'
UNION ALL
SELECT id, 'thùng', 12, NULL, 195000, 5, 0, 1 FROM products WHERE name = 'Nước tương CHIN-SU';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 30000, 10, 1, 1 FROM products WHERE name = 'Nước mắm Nam Ngư đệ nhị'
UNION ALL
SELECT id, 'thùng', 12, NULL, 325000, 5, 0, 1 FROM products WHERE name = 'Nước mắm Nam Ngư đệ nhị';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 45000, 10, 1, 1 FROM products WHERE name = 'Nước mắm Nam Ngư chai lớn'
UNION ALL
SELECT id, 'thùng', 12, NULL, 490000, 5, 0, 1 FROM products WHERE name = 'Nước mắm Nam Ngư chai lớn';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 35000, 10, 1, 1 FROM products WHERE name = 'Nước mắm Nam Ngư nhãn vàng'
UNION ALL
SELECT id, 'thùng', 12, NULL, 380000, 5, 0, 1 FROM products WHERE name = 'Nước mắm Nam Ngư nhãn vàng';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 17000, 10, 1, 1 FROM products WHERE name = 'Nước tương Maggi'
UNION ALL
SELECT id, 'thùng', 12, NULL, 185000, 5, 0, 1 FROM products WHERE name = 'Nước tương Maggi';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 18000, 10, 1, 1 FROM products WHERE name = 'Tương ớt CHIN-SU'
UNION ALL
SELECT id, 'thùng', 12, NULL, 195000, 5, 0, 1 FROM products WHERE name = 'Tương ớt CHIN-SU';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 22000, 10, 1, 1 FROM products WHERE name = 'Tương cà Heinz'
UNION ALL
SELECT id, 'thùng', 12, NULL, 240000, 5, 0, 1 FROM products WHERE name = 'Tương cà Heinz';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 28000, 10, 1, 1 FROM products WHERE name = 'Dầu ăn Neptune Light 400ml'
UNION ALL
SELECT id, 'thùng', 12, NULL, 305000, 5, 0, 1 FROM products WHERE name = 'Dầu ăn Neptune Light 400ml';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'kg', 1, NULL, 22000, 10, 1, 1 FROM products WHERE name = 'Đường tinh luyện Biên Hòa'
UNION ALL
SELECT id, 'bao', 20, NULL, 400000, 5, 0, 1 FROM products WHERE name = 'Đường tinh luyện Biên Hòa';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'gói', 1, NULL, 8000, 10, 1, 1 FROM products WHERE name = 'Muối biển An Duyên'
UNION ALL
SELECT id, 'thùng', 20, NULL, 145000, 5, 0, 1 FROM products WHERE name = 'Muối biển An Duyên';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'gói', 1, NULL, 20000, 10, 1, 1 FROM products WHERE name = 'Hạt nêm Knorr thịt thăn'
UNION ALL
SELECT id, 'thùng', 20, NULL, 360000, 5, 0, 1 FROM products WHERE name = 'Hạt nêm Knorr thịt thăn';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 85000, 10, 1, 1 FROM products WHERE name = 'Dầu gội Clear Men bạc hà'
UNION ALL
SELECT id, 'thùng', 12, NULL, 920000, 5, 0, 1 FROM products WHERE name = 'Dầu gội Clear Men bạc hà';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 85000, 10, 1, 1 FROM products WHERE name = 'Clear Men Deep Clean'
UNION ALL
SELECT id, 'thùng', 12, NULL, 920000, 5, 0, 1 FROM products WHERE name = 'Clear Men Deep Clean';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 90000, 10, 1, 1 FROM products WHERE name = 'Head & Shoulders bạc hà'
UNION ALL
SELECT id, 'thùng', 12, NULL, 970000, 5, 0, 1 FROM products WHERE name = 'Head & Shoulders bạc hà';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 90000, 10, 1, 1 FROM products WHERE name = 'Head & Shoulders suôn mượt'
UNION ALL
SELECT id, 'thùng', 12, NULL, 970000, 5, 0, 1 FROM products WHERE name = 'Head & Shoulders suôn mượt';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 90000, 10, 1, 1 FROM products WHERE name = 'Dầu gội Head & Shoulders'
UNION ALL
SELECT id, 'thùng', 12, NULL, 970000, 5, 0, 1 FROM products WHERE name = 'Dầu gội Head & Shoulders';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 85000, 10, 1, 1 FROM products WHERE name = 'Sữa tắm Lifebuoy bảo vệ da'
UNION ALL
SELECT id, 'thùng', 12, NULL, 920000, 5, 0, 1 FROM products WHERE name = 'Sữa tắm Lifebuoy bảo vệ da';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'tuýp', 1, NULL, 28000, 10, 1, 1 FROM products WHERE name = 'Kem đánh răng P/S than hoạt tính'
UNION ALL
SELECT id, 'hộp', 12, NULL, 300000, 5, 0, 1 FROM products WHERE name = 'Kem đánh răng P/S than hoạt tính';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'tuýp', 1, NULL, 32000, 10, 1, 1 FROM products WHERE name = 'Kem đánh răng Colgate Total'
UNION ALL
SELECT id, 'hộp', 12, NULL, 345000, 5, 0, 1 FROM products WHERE name = 'Kem đánh răng Colgate Total';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 75000, 10, 1, 1 FROM products WHERE name = 'Nước súc miệng Listerine Cool Mint'
UNION ALL
SELECT id, 'thùng', 12, NULL, 810000, 5, 0, 1 FROM products WHERE name = 'Nước súc miệng Listerine Cool Mint';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 45000, 10, 1, 1 FROM products WHERE name = 'Nước rửa tay Dettol'
UNION ALL
SELECT id, 'thùng', 12, NULL, 485000, 5, 0, 1 FROM products WHERE name = 'Nước rửa tay Dettol';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'gói', 1, NULL, 18000, 10, 1, 1 FROM products WHERE name = 'Khăn giấy Bless You'
UNION ALL
SELECT id, 'thùng', 20, NULL, 320000, 5, 0, 1 FROM products WHERE name = 'Khăn giấy Bless You';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'gói', 1, NULL, 25000, 10, 1, 1 FROM products WHERE name = 'Khăn ướt Mamamy không mùi'
UNION ALL
SELECT id, 'thùng', 20, NULL, 450000, 5, 0, 1 FROM products WHERE name = 'Khăn ướt Mamamy không mùi';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'cây', 1, NULL, 12000, 10, 1, 1 FROM products WHERE name = 'Bàn chải đánh răng P/S lông mềm'
UNION ALL
SELECT id, 'vỉ', 12, NULL, 130000, 5, 0, 1 FROM products WHERE name = 'Bàn chải đánh răng P/S lông mềm';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'cái', 1, NULL, 35000, 10, 1, 1 FROM products WHERE name = 'Dao cạo râu Gillette Blue 3'
UNION ALL
SELECT id, 'vỉ', 4, NULL, 125000, 5, 0, 1 FROM products WHERE name = 'Dao cạo râu Gillette Blue 3';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'hộp', 1, NULL, 20000, 10, 1, 1 FROM products WHERE name = 'Tăm bông Jomi hộp tròn'
UNION ALL
SELECT id, 'thùng', 24, NULL, 430000, 5, 0, 1 FROM products WHERE name = 'Tăm bông Jomi hộp tròn';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'hộp', 1, NULL, 45000, 10, 1, 1 FROM products WHERE name = 'Khẩu trang y tế Unicharm 3D'
UNION ALL
SELECT id, 'thùng', 20, NULL, 810000, 5, 0, 1 FROM products WHERE name = 'Khẩu trang y tế Unicharm 3D';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'cái', 1, NULL, 25000, 10, 1, 1 FROM products WHERE name = 'Lược chải tóc Miniso'
UNION ALL
SELECT id, 'hộp', 12, NULL, 270000, 5, 0, 1 FROM products WHERE name = 'Lược chải tóc Miniso';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'cái', 1, NULL, 30000, 10, 1, 1 FROM products WHERE name = 'Gương mini gấp gọn Miniso'
UNION ALL
SELECT id, 'hộp', 12, NULL, 325000, 5, 0, 1 FROM products WHERE name = 'Gương mini gấp gọn Miniso';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'cuộn', 1, NULL, 18000, 10, 1, 1 FROM products WHERE name = 'Túi rác cuộn Opec'
UNION ALL
SELECT id, 'lốc', 10, NULL, 160000, 5, 0, 1 FROM products WHERE name = 'Túi rác cuộn Opec';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'chai', 1, NULL, 35000, 10, 1, 1 FROM products WHERE name = 'Nước rửa chén Sunlight chanh'
UNION ALL
SELECT id, 'thùng', 12, NULL, 380000, 5, 0, 1 FROM products WHERE name = 'Nước rửa chén Sunlight chanh';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'miếng', 1, NULL, 12000, 10, 1, 1 FROM products WHERE name = 'Mút rửa chén Scotch-Brite'
UNION ALL
SELECT id, 'gói', 10, NULL, 105000, 5, 0, 1 FROM products WHERE name = 'Mút rửa chén Scotch-Brite';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'cuộn', 1, NULL, 22000, 10, 1, 1 FROM products WHERE name = 'Màng bọc thực phẩm Clean Wrap'
UNION ALL
SELECT id, 'thùng', 24, NULL, 475000, 5, 0, 1 FROM products WHERE name = 'Màng bọc thực phẩm Clean Wrap';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'cuộn', 1, NULL, 18000, 10, 1, 1 FROM products WHERE name = 'Giấy bạc Ecook 5m'
UNION ALL
SELECT id, 'thùng', 24, NULL, 390000, 5, 0, 1 FROM products WHERE name = 'Giấy bạc Ecook 5m';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'hộp', 1, NULL, 85000, 10, 1, 1 FROM products WHERE name = 'Hộp nhựa Lock&Lock 1L'
UNION ALL
SELECT id, 'thùng', 12, NULL, 920000, 5, 0, 1 FROM products WHERE name = 'Hộp nhựa Lock&Lock 1L';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'gói', 1, NULL, 15000, 10, 1, 1 FROM products WHERE name = 'Bộ muỗng nhựa AnEco 10 cái'
UNION ALL
SELECT id, 'thùng', 20, NULL, 270000, 5, 0, 1 FROM products WHERE name = 'Bộ muỗng nhựa AnEco 10 cái';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'que', 1, NULL, 10000, 10, 1, 1 FROM products WHERE name = 'Kem Merino chocolate'
UNION ALL
SELECT id, 'hộp', 20, NULL, 180000, 5, 0, 1 FROM products WHERE name = 'Kem Merino chocolate';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'gói', 1, NULL, 45000, 10, 1, 1 FROM products WHERE name = 'Cá viên C.P.'
UNION ALL
SELECT id, 'thùng', 10, NULL, 410000, 5, 0, 1 FROM products WHERE name = 'Cá viên C.P.';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'gói', 1, NULL, 50000, 10, 1, 1 FROM products WHERE name = 'Bò viên Cầu Tre'
UNION ALL
SELECT id, 'thùng', 10, NULL, 455000, 5, 0, 1 FROM products WHERE name = 'Bò viên Cầu Tre';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'gói', 1, NULL, 48000, 10, 1, 1 FROM products WHERE name = 'Chả cá Cầu Tre'
UNION ALL
SELECT id, 'thùng', 10, NULL, 435000, 5, 0, 1 FROM products WHERE name = 'Chả cá Cầu Tre';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'gói', 1, NULL, 55000, 10, 1, 1 FROM products WHERE name = 'Xúc xích Đức Việt đông lạnh'
UNION ALL
SELECT id, 'thùng', 10, NULL, 500000, 5, 0, 1 FROM products WHERE name = 'Xúc xích Đức Việt đông lạnh';

INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
SELECT id, 'gói', 1, NULL, 60000, 10, 1, 1 FROM products WHERE name = 'Há cảo C.P. đông lạnh'
UNION ALL
SELECT id, 'thùng', 10, NULL, 545000, 5, 0, 1 FROM products WHERE name = 'Há cảo C.P. đông lạnh';

