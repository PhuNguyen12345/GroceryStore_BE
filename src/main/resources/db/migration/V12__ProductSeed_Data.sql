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
SELECT 'Băng vệ sinh', id, 'bang-ve-sinh', 'Các loại băng vệ sinh', 1
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


SET @logo_url_column_exists = (
    SELECT COUNT(*)
    FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'brands'
      AND COLUMN_NAME = 'logo_url'
);

SET @add_logo_url_column_sql = IF(
    @logo_url_column_exists = 0,
    'ALTER TABLE brands ADD COLUMN logo_url VARCHAR(255)',
    'SELECT 1'
);

PREPARE add_logo_url_column_stmt FROM @add_logo_url_column_sql;
EXECUTE add_logo_url_column_stmt;
DEALLOCATE PREPARE add_logo_url_column_stmt;

INSERT INTO categories (id, name, slug, description, is_active)
SELECT ref.id,
       CONCAT('Seed Category #', ref.id),
       CONCAT('seed-category-', ref.id),
       'Auto-generated placeholder for V12 compatibility',
       1
FROM (
         SELECT 10 AS id UNION ALL SELECT 11 UNION ALL SELECT 12 UNION ALL SELECT 13 UNION ALL SELECT 14
         UNION ALL SELECT 15 UNION ALL SELECT 16 UNION ALL SELECT 17 UNION ALL SELECT 18 UNION ALL SELECT 19
         UNION ALL SELECT 20 UNION ALL SELECT 22 UNION ALL SELECT 23 UNION ALL SELECT 24 UNION ALL SELECT 25
         UNION ALL SELECT 27 UNION ALL SELECT 28 UNION ALL SELECT 30 UNION ALL SELECT 31 UNION ALL SELECT 32
         UNION ALL SELECT 37 UNION ALL SELECT 38 UNION ALL SELECT 39 UNION ALL SELECT 40 UNION ALL SELECT 42
         UNION ALL SELECT 43 UNION ALL SELECT 45 UNION ALL SELECT 46 UNION ALL SELECT 49 UNION ALL SELECT 52
         UNION ALL SELECT 53 UNION ALL SELECT 54 UNION ALL SELECT 55 UNION ALL SELECT 60 UNION ALL SELECT 61
         UNION ALL SELECT 62 UNION ALL SELECT 67 UNION ALL SELECT 68 UNION ALL SELECT 71
     ) ref
LEFT JOIN categories c ON c.id = ref.id
WHERE c.id IS NULL;

INSERT INTO brands (id, name, description, logo_url, is_active)
SELECT ref.id,
       CONCAT('Seed Brand #', ref.id),
       'Auto-generated placeholder for V12 compatibility',
       '/uploads/images/placeholder-brand.png',
       1
FROM (
         SELECT 1 AS id UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5
         UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9 UNION ALL SELECT 13
         UNION ALL SELECT 14 UNION ALL SELECT 15 UNION ALL SELECT 16 UNION ALL SELECT 17 UNION ALL SELECT 18
         UNION ALL SELECT 21 UNION ALL SELECT 23 UNION ALL SELECT 25 UNION ALL SELECT 26 UNION ALL SELECT 28
         UNION ALL SELECT 29 UNION ALL SELECT 31 UNION ALL SELECT 33 UNION ALL SELECT 35 UNION ALL SELECT 38
         UNION ALL SELECT 40 UNION ALL SELECT 42 UNION ALL SELECT 43 UNION ALL SELECT 44 UNION ALL SELECT 45
         UNION ALL SELECT 46 UNION ALL SELECT 49 UNION ALL SELECT 51 UNION ALL SELECT 52 UNION ALL SELECT 54
         UNION ALL SELECT 55 UNION ALL SELECT 56 UNION ALL SELECT 57 UNION ALL SELECT 58 UNION ALL SELECT 59
         UNION ALL SELECT 60 UNION ALL SELECT 61 UNION ALL SELECT 63 UNION ALL SELECT 64
     ) ref
LEFT JOIN brands b ON b.id = ref.id
WHERE b.id IS NULL;



INSERT INTO brands (name, description, logo_url, is_active) VALUES
                                                                ('Coca-Cola', 'Thương hiệu nước giải khát nổi tiếng với các sản phẩm nước ngọt, nước trái cây và trà đóng chai.', '/uploads/images/89c59da2-e36e-43e8-9774-ea2ebb4097f4.jpg', 1),
                                                                ('Pepsi', 'Thương hiệu đồ uống với các sản phẩm nước ngọt, nước tăng lực và trà giải khát.', '/uploads/images/d6596947-8de5-4b1c-80b1-b0ea47a8f274.png', 1),
                                                                ('TH True Milk', 'Thương hiệu sữa và các sản phẩm từ sữa phổ biến tại Việt Nam.', '/uploads/images/5b45c6c0-8dd2-4449-8497-862059612b24.jpg', 1),
                                                                ('Vinamilk', 'Thương hiệu sữa và thực phẩm dinh dưỡng hàng đầu Việt Nam.', '/uploads/images/7131d4e5-8aac-424d-831c-eb49e71ce4b9.jpg', 1),
                                                                ('Nestlé', 'Thương hiệu thực phẩm và đồ uống quốc tế với nhiều sản phẩm tiêu dùng nhanh.', '/uploads/images/00644810-f26c-47cb-9219-5ce24dffb2b0.png', 1),
                                                                ('Milo', 'Thương hiệu đồ uống lúa mạch và ca cao dành cho trẻ em và gia đình.', '/uploads/images/61ea8a16-c4a3-44bf-b2e0-e4ba6bef08b3.jpg', 1),
                                                                ('Nescafé', 'Thương hiệu cà phê hòa tan và cà phê uống liền.', '/uploads/images/0136d2bf-f551-4c31-a9b5-f7c2455f02e6.png', 1),
                                                                ('Oishi', 'Thương hiệu snack, bánh kẹo và đồ ăn vặt quen thuộc.', '/uploads/images/575a4b68-05b2-4004-8f6d-dbed329b8186.png', 1),
                                                                ('Orion', 'Thương hiệu bánh kẹo nổi tiếng với bánh quy, bánh mềm và snack.', '/uploads/images/94a4a77d-a053-4d17-9a14-becdc14deebe.png', 1),
                                                                ('Kinh Đô', 'Thương hiệu bánh kẹo và thực phẩm chế biến sẵn.', '/uploads/images/055e4856-2de3-4c16-a775-f8f7d841be37.jpg', 1),
                                                                ('Bibica', 'Thương hiệu bánh kẹo nội địa phổ biến.', '/uploads/images/74c27e2e-6d2a-45ce-bda2-e96c9701a616.png', 1),
                                                                ('Acecook', 'Thương hiệu mì ăn liền và thực phẩm tiện lợi.', '/uploads/images/6f3309e0-90fc-4264-b3a3-b23cd8e4296d.png', 1),
                                                                ('Masan', 'Thương hiệu gia vị, thực phẩm tiện lợi và hàng tiêu dùng.', '/uploads/images/04007e78-7335-439e-8044-506cee4acd90.jpg', 1),
                                                                ('Chin-su', 'Thương hiệu gia vị và nước chấm phổ biến thuộc Masan.', '/uploads/images/355b394c-00f4-4c37-9f92-27607ed3f454.png', 1),
                                                                ('Nam Ngư', 'Thương hiệu nước mắm phổ biến tại Việt Nam.', '/uploads/images/678506ec-73d2-45d0-84a6-0b2cb7cc415e.png', 1),
                                                                ('Omachi', 'Thương hiệu mì ăn liền và thực phẩm tiện lợi.', '/uploads/images/b687b3d9-4034-4242-85b8-7b9163a4fc8a.jpg', 1),
                                                                ('Kokomi', 'Thương hiệu mì ăn liền giá phổ thông.', '/uploads/images/60352a95-f385-441e-b1b9-47659c9096d6.png', 1),
                                                                ('Hảo Hảo', 'Thương hiệu mì ăn liền rất phổ biến tại Việt Nam.', '/uploads/images/637e2085-0c44-476e-b7b2-893f6a90f688.jpg', 1),
                                                                ('Unilever', 'Tập đoàn sở hữu nhiều thương hiệu hóa mỹ phẩm và chăm sóc cá nhân.', '/uploads/images/5671cee9-a674-4f6c-8ce9-98e130f0f359.png', 1),
                                                                ('P&G', 'Tập đoàn hàng tiêu dùng với nhiều sản phẩm chăm sóc cá nhân và gia dụng.', '/uploads/images/cb9a3e23-f0bb-4afd-b2c3-18aa74807f5e.jpg', 1),
                                                                ('Pantene', 'Thương hiệu dầu gội và chăm sóc tóc.', '/uploads/images/ec34ee4b-222a-406a-a701-00b3bd28903e.png', 1),
                                                                ('Head & Shoulders', 'Thương hiệu dầu gội chuyên về sạch gàu.', '/uploads/images/baaade48-fc25-4e65-a8f4-e23bb397e3af.png', 1),
                                                                ('Clear', 'Thương hiệu dầu gội và chăm sóc tóc phổ biến.', '/uploads/images/d0776a2a-4779-4e1c-a457-8293ffa2ead9.jpg', 1),
                                                                ('Sunsilk', 'Thương hiệu dầu gội và chăm sóc tóc.', '/uploads/images/a227083f-06b1-4f57-9719-ff13e55328f4.jpg', 1),
                                                                ('Colgate', 'Thương hiệu kem đánh răng và chăm sóc răng miệng.', '/uploads/images/47053cb3-41f3-4b9d-ab1b-48c1a96c83df.png', 1),
                                                                ('P/S', 'Thương hiệu kem đánh răng và sản phẩm chăm sóc răng miệng.', '/uploads/images/a53c878c-c8e2-4aae-851c-da5525e74eeb.png', 1),
                                                                ('Closeup', 'Thương hiệu kem đánh răng và chăm sóc răng miệng.', '/uploads/images/8b5fe0d8-cff6-4e42-8d85-9a6cbc0a65a2.jpg', 1),
                                                                ('Lifebuoy', 'Thương hiệu xà phòng và nước rửa tay diệt khuẩn.', '/uploads/images/3f7e0e7a-d0b0-4906-b4e6-06f657711452.jpg', 1),
                                                                ('Dove', 'Thương hiệu sữa tắm, dầu gội và sản phẩm chăm sóc cá nhân.', '/uploads/images/13f422f5-e149-4815-b849-90cfb864c3ab.jpg', 1),
                                                                ('Lux', 'Thương hiệu sữa tắm và chăm sóc cơ thể.', '/uploads/images/d711cb9d-41bf-4a44-8223-429c46288a05.jpg', 1),
                                                                ('Sunlight', 'Thương hiệu nước rửa chén và sản phẩm tẩy rửa gia dụng.', '/uploads/images/421d65f4-6f93-47c1-942e-caf562240e8f.jpg', 1),
                                                                ('Comfort', 'Thương hiệu nước xả vải và sản phẩm chăm sóc quần áo.', '/uploads/images/8d053cf6-5c42-4231-b7f8-1e7700e3c1dc.png', 1),
                                                                ('Scotch-Brite', 'Thương hiệu mút rửa chén và dụng cụ vệ sinh gia dụng.', '/uploads/images/43f92d39-6282-445e-91b2-81e35777677d.jpg', 1),
                                                                ('3M', 'Thương hiệu sản phẩm gia dụng, vệ sinh và tiện ích tiêu dùng.', '/uploads/images/402670f3-0df5-4567-8db2-596665883df5.png', 1),
                                                                ('Bless You', 'Thương hiệu khăn giấy và giấy tiêu dùng.', '/uploads/images/a78b68a9-80ca-4b3e-ae1f-a0da9b18f106.jpg', 1),
                                                                ('Pulppy', 'Thương hiệu khăn giấy và giấy tiêu dùng.', '/uploads/images/3edfca9f-05b5-48ee-8349-3c78547b4e47.png', 1),
                                                                ('Tempo', 'Thương hiệu khăn giấy cao cấp.', '/uploads/images/5fdc5204-68d3-4fe0-bf4b-b54913ab831e.png', 1),
                                                                ('Kotex', 'Thương hiệu băng vệ sinh và sản phẩm chăm sóc phụ nữ.', '/uploads/images/d21a509e-c911-42e0-8a70-2e4412b4a886.png', 1),
                                                                ('Whisper', 'Thương hiệu băng vệ sinh phổ biến.', '/uploads/images/03dd0d8a-66e3-4c98-b69b-9793479c28f9.jpg', 1),
                                                                ('Diana', 'Thương hiệu băng vệ sinh và chăm sóc cá nhân cho nữ.', '/uploads/images/0f18f991-3e36-4aa8-b000-941d373752c5.png', 1),
                                                                ('Romano', 'Thương hiệu chăm sóc cá nhân dành cho nam.', '/uploads/images/3bfd1577-2330-4bba-9b7d-0a438441b984.png', 1),
                                                                ('Gillette', 'Thương hiệu dao cạo và sản phẩm cạo râu.', '/uploads/images/51cf2832-351e-467c-91be-f8a5c50815cb.png', 1),
                                                                ('Bic', 'Thương hiệu bút viết, bật lửa và đồ dùng tiện ích.', '/uploads/images/2df2f807-08c7-4b2a-98cd-cca41636c83c.png', 1),
                                                                ('Thiên Long', 'Thương hiệu văn phòng phẩm nổi tiếng tại Việt Nam.', '/uploads/images/f69e9796-a94e-4118-a129-e45b5b418434.png', 1),
                                                                ('Double A', 'Thương hiệu giấy và sổ tay văn phòng.', '/uploads/images/c12e32c7-a1b2-410d-b454-3a66993588ff.jpg', 1),
                                                                ('Energizer', 'Thương hiệu pin và sản phẩm năng lượng di động.', '/uploads/images/a41401cf-445d-49e7-999a-7fe59439a52f.png', 1),
                                                                ('Panasonic', 'Thương hiệu pin và đồ điện tử tiêu dùng.', '/uploads/images/ee7f3223-d3d3-41ec-88bd-e83da31a7a99.png', 1),
                                                                ('Duracell', 'Thương hiệu pin tiêu dùng phổ biến.', '/uploads/images/cc458a19-c6b0-480c-9784-e39dcbefd2f7.jpg', 1),
                                                                ('Rạng Đông', 'Thương hiệu đồ gia dụng và sản phẩm tiêu dùng trong nước.', '/uploads/images/e6a6d600-9fa3-49dc-896f-39d8369df329.png', 1),
                                                                ('Tràng An', 'Thương hiệu bánh kẹo và đồ ăn nhẹ.', '/uploads/images/a82df755-4995-4cec-87e2-a621b7c0b5b8.png', 1),
                                                                ('Lay''s', 'Thương hiệu snack khoai tây nổi tiếng.', '/uploads/images/8dae2f58-d688-4bbf-9377-697e3e9fc789.jpg', 1),
                                                                ('Poca', 'Thương hiệu snack khoai tây và đồ ăn vặt.', '/uploads/images/0b6eb0fd-57e7-4d88-9203-f842032734ea.jpg', 1),
                                                                ('Cheetos', 'Thương hiệu snack ngô và đồ ăn vặt.', '/uploads/images/5670273b-9c08-458f-b16e-c5e621771ac7.jpg', 1),
                                                                ('Meiji', 'Thương hiệu bánh kẹo, sữa và thực phẩm tiêu dùng.', '/uploads/images/b059992f-956f-47ab-a0f3-aa179a43f37b.png', 1),
                                                                ('Want Want', 'Thương hiệu bánh gạo và snack châu Á.', '/uploads/images/ed7a0283-9e8c-4fb6-a801-ebc432d47626.png', 1),
                                                                ('Ajinomoto', 'Thương hiệu gia vị, bột nêm và thực phẩm tiện lợi.', '/uploads/images/b88892f2-fcf5-478c-805b-c668f0d95d90.png', 1),
                                                                ('Maggi', 'Thương hiệu nước chấm, gia vị và thực phẩm tiện lợi.', '/uploads/images/54e56f34-6dbd-4661-9821-aae314b256ee.png', 1),
                                                                ('Knorr', 'Thương hiệu hạt nêm, gia vị và thực phẩm chế biến.', '/uploads/images/5940b8a4-ed28-446e-9674-2f992fffc6e5.png', 1),
                                                                ('Vifon', 'Thương hiệu mì, phở và cháo ăn liền.', '/uploads/images/ecdd3da6-9ff7-4705-87f8-5988c435c655.jpg', 1),
                                                                ('Cầu Tre', 'Thương hiệu thực phẩm chế biến và đồ đông lạnh.', '/uploads/images/357fcea0-c3f6-4fc7-9f50-7a5e7f72aa5d.jpg', 1),
                                                                ('CP', 'Thương hiệu thực phẩm chế biến, xúc xích và đồ đông lạnh.', '/uploads/images/079a4f4d-88d8-4cd6-886b-f51ac4af891c.png', 1),
                                                                ('Đức Việt', 'Thương hiệu xúc xích, giò chả và thực phẩm chế biến.', '/uploads/images/c808b129-82fc-44d4-87cb-95813c6ee072.png', 1),
                                                                ('Merino', 'Thương hiệu kem phổ biến tại Việt Nam.', '/uploads/images/77f26bc8-a515-49e8-9da8-74ebde41db5b.jpg', 1),
                                                                ('Celano', 'Thương hiệu kem cao cấp phổ biến.', '/uploads/images/00dc8ad8-61bc-46a2-923b-4ae0e9343eae.jpg', 1);




INSERT INTO products (id, name, description, category_id, image_url, is_active, created_at, brand_id) VALUES
                                                                                                          (1, 'Coca-Cola Original 330ml', 'Nước ngọt có gas vị truyền thống, lon 330ml', 11, '/uploads/images/3b28ae2d-a741-4388-961a-f730864d2e5a.jpg', 1, '2026-03-14 22:06:29', 1),
                                                                                                          (2, 'Pepsi Cola 330ml', 'Nước ngọt có gas Pepsi lon 330ml', 11, '/uploads/images/4aa471ee-d462-4128-8563-8406f3b7d628.jpg', 1, '2026-03-14 22:06:29', 2),
                                                                                                          (3, 'Aquafina 500ml', 'Nước suối tinh khiết chai 500ml', 10, '/uploads/images/9c37c73f-8e58-4191-b85f-2629c1a98d93.jpg', 1, '2026-03-14 22:06:29', 2),
                                                                                                          (4, 'Dasani 500ml', 'Nước suối đóng chai 500ml', 10, '/uploads/images/f989f0b4-af32-4d1f-a0d1-cbfde42cecd9.jpg', 1, '2026-03-14 22:06:29', 1),
                                                                                                          (5, 'Trà Xanh Không Độ 455ml', 'Trà xanh đóng chai thanh mát', 12, '/uploads/images/ee413530-c9f7-48ee-922c-d96924d86f14.jpg', 1, '2026-03-14 22:06:29', 1),
                                                                                                          (6, 'Nescafé Café Việt 180ml', 'Cà phê sữa đá đóng lon tiện lợi', 13, '/uploads/images/a3724d1a-688e-4fdf-9b5e-9643b25aad24.jpg', 1, '2026-03-14 22:06:29', 7),
                                                                                                          (7, 'Milo UHT 180ml', 'Sữa lúa mạch Milo hộp 180ml', 14, '/uploads/images/bd88f5bf-4832-4337-b2f7-e4d56797f9ef.jpg', 1, '2026-03-14 22:06:29', 6),
                                                                                                          (8, 'TH True Milk Có Đường 180ml', 'Sữa tươi tiệt trùng có đường', 14, '/uploads/images/d1f5f47a-9b6c-4509-8ea1-5f89702b507c.webp', 1, '2026-03-14 22:06:29', 3),
                                                                                                          (9, 'Vinamilk 100% Sữa Tươi 180ml', 'Sữa tươi tiệt trùng Vinamilk hộp nhỏ', 14, '/uploads/images/ed249dc5-a009-43fd-8ae6-df6ebf7bb0e5.webp', 1, '2026-03-14 22:06:29', 4),
                                                                                                          (10, 'Sting Dâu 330ml', 'Nước tăng lực vị dâu lon 330ml', 15, '/uploads/images/b60528ec-2a55-4a83-9c03-bb5eda5d318f.jpg', 1, '2026-03-14 22:06:29', 2),
                                                                                                          (11, 'Minute Maid Cam 300ml', 'Nước ép cam đóng chai', 16, '/uploads/images/40950ee1-52b1-484e-8737-30dd77b67546.jpg', 1, '2026-03-14 22:06:29', 1),
                                                                                                          (12, 'Bánh ChocoPie Orion Hộp 6 cái', 'Bánh phủ socola nhân marshmallow', 17, '/uploads/images/77442f4d-e211-426d-9ff5-483f4e4d20f7.jpg', 1, '2026-03-14 22:06:29', 9),
                                                                                                          (13, 'Snack Oishi Tôm Cay 45g', 'Snack tôm cay giòn', 20, '/uploads/images/a989abf9-ed6a-4928-b2a4-5ad8e2f631fa.jpg', 1, '2026-03-14 22:06:29', 8),
                                                                                                          (14, 'Lay''s Khoai Tây Vị Tự Nhiên 52g', 'Snack khoai tây lát vị nguyên bản', 20, '/uploads/images/50338052-7c2e-46b0-9b3f-0d8ab8ce9d07.jpg', 1, '2026-03-14 22:06:29', 51),
                                                                                                          (15, 'Poca Snack Vị Bò Lúc Lắc 52g', 'Snack khoai tây vị bò lúc lắc', 20, '/uploads/images/0a8d05c1-a651-42f7-80ef-07f8154fa370.jpg', 1, '2026-03-14 22:06:29', 52),
                                                                                                          (16, 'Kẹo Mềm Alpenliebe 40g', 'Kẹo mềm vị sữa caramel', 18, '/uploads/images/dda471b6-85e2-47e7-8c4f-3908dfd9970d.jpg', 1, '2026-03-14 22:06:29', 5),
                                                                                                          (17, 'Socola Meiji Milk Chocolate 50g', 'Socola sữa thanh 50g', 19, '/uploads/images/b846e0d0-2454-4980-82b1-b2f28b692d80.jpg', 1, '2026-03-14 22:06:29', 54),
                                                                                                          (18, 'Rong Biển Want Want 3 Gói', 'Rong biển ăn liền đóng gói nhỏ', 22, '/uploads/images/caa7e1be-0649-445c-be8b-3ebb438b99d5.jpg', 1, '2026-03-14 22:06:29', 55),
                                                                                                          (19, 'Mì Hảo Hảo Tôm Chua Cay 75g', 'Mì gói vị tôm chua cay', 23, '/uploads/images/cc88ccbd-ef0a-4a60-9d46-9d4b1c0ed923.jpg', 1, '2026-03-14 22:06:29', 18),
                                                                                                          (20, 'Mì Omachi Sườn Hầm Ngũ Quả 80g', 'Mì khoai tây vị sườn hầm', 23, '/uploads/images/fae1337d-5cb9-40c7-9e17-dc31dda730f9.jpg', 1, '2026-03-14 22:06:29', 16),
                                                                                                          (21, 'Mì Kokomi 90 Tôm Chua Cay 65g', 'Mì gói phổ thông vị tôm chua cay', 23, '/uploads/images/ad8681e8-ebc7-4563-a47b-c233067d2fbf.jpg', 1, '2026-03-14 22:06:29', 17),
                                                                                                          (22, 'Phở Bò Vifon 65g', 'Phở ăn liền vị bò', 25, '/uploads/images/860a99d2-375e-4897-b319-8aaa01b1132c.jpg', 1, '2026-03-14 22:06:29', 59),
                                                                                                          (23, 'Cháo Gói Vifon Thịt Bằm 50g', 'Cháo ăn liền vị thịt bằm', 24, '/uploads/images/2fb43edf-c2ac-4386-ab32-f72c7ef8d2b1.png', 1, '2026-03-14 22:06:29', 59),
                                                                                                          (24, 'Xúc Xích Tiệt Trùng CP 40g', 'Xúc xích ăn liền tiện lợi', 28, '/uploads/images/bdd746cf-ea74-4dc8-98c8-86a4a1e6bd98.jpg', 1, '2026-03-14 22:06:29', 61),
                                                                                                          (25, 'Cá Hộp Sốt Cà Masan', 'Đồ hộp tiện lợi dùng với cơm', 27, '/uploads/images/ac23638e-8d47-48fb-beb2-33b748c08da9.jpg', 1, '2026-03-14 22:06:29', 13),
                                                                                                          (26, 'Nước Mắm Nam Ngư 500ml', 'Nước mắm truyền thống chai 500ml', 30, '/uploads/images/22cb474a-9337-4dbc-8f8e-18bb6a754c7a.jpg', 1, '2026-03-14 22:06:29', 15),
                                                                                                          (27, 'Nước Tương Maggi 300ml', 'Nước tương đậm vị dùng chấm và nấu ăn', 31, '/uploads/images/3d5b12fc-9788-413a-8dea-9f7258f830a7.jpg', 1, '2026-03-14 22:06:29', 57),
                                                                                                          (28, 'Tương Ớt Chin-su 250g', 'Tương ớt cay đậm vị', 32, '/uploads/images/ea6b4e3a-bc1f-4971-87b1-7c3077e82fe5.webp', 1, '2026-03-14 22:06:29', 14),
                                                                                                          (29, 'Hạt Nêm Knorr Thịt Thăn Xương 170g', 'Hạt nêm nêm canh tiện lợi', 37, '/uploads/images/a8a050ad-4936-4f89-a30d-d542ff277949.jpg', 1, '2026-03-14 22:06:29', 58),
                                                                                                          (30, 'Bột Ngọt Ajinomoto 454g', 'Gia vị bột ngọt phổ biến', 37, '/uploads/images/175f04a5-9154-4347-adab-11e39c8de386.jpg', 1, '2026-03-14 22:06:29', 56),
                                                                                                          (31, 'Dầu Gội Pantene Mượt Mà 170g', 'Dầu gội chăm sóc tóc mềm mượt', 38, '/uploads/images/f465c415-97a0-4798-a4b3-96e21630dc4c.jpg', 1, '2026-03-14 22:06:29', 21),
                                                                                                          (32, 'Dầu Gội Clear Bạc Hà 180g', 'Dầu gội sạch gàu mát lạnh', 38, '/uploads/images/0658d83a-1787-409f-b7f8-4aaa5cfe630e.jpg', 1, '2026-03-14 22:06:29', 23),
                                                                                                          (33, 'Sữa Tắm Dove Dưỡng Ẩm 200g', 'Sữa tắm dưỡng ẩm cho da mềm mịn', 39, '/uploads/images/437aea12-0528-45ac-9f1a-616a88173143.jpg', 1, '2026-03-14 22:06:29', 29),
                                                                                                          (34, 'Kem Đánh Răng Colgate MaxFresh 150g', 'Kem đánh răng thơm mát dài lâu', 40, '/uploads/images/5afb834f-b4d9-43bf-8d2f-23d160a5f603.jpg', 1, '2026-03-14 22:06:29', 25),
                                                                                                          (35, 'Kem Đánh Răng P/S Bảo Vệ 120g', 'Kem đánh răng bảo vệ răng chắc khỏe', 40, '/uploads/images/ad50ac97-076a-4c86-9c6d-c395463d8078.jpg', 1, '2026-03-14 22:06:29', 26),
                                                                                                          (36, 'Nước Rửa Tay Lifebuoy 180g', 'Nước rửa tay bảo vệ khỏi vi khuẩn', 42, '/uploads/images/a59c0eff-aacc-46d2-9bc5-a9923187611c.jpg', 1, '2026-03-14 22:06:29', 28),
                                                                                                          (37, 'Khăn Giấy Bless You 180 Tờ', 'Khăn giấy khô mềm mại dùng hằng ngày', 43, '/uploads/images/b461ecc7-2150-4526-9ab5-dd4b0fc5a362.jpg', 1, '2026-03-14 22:06:29', 35),
                                                                                                          (38, 'Bàn Chải Đánh Răng Colgate SlimSoft', 'Bàn chải lông mềm làm sạch răng miệng', 45, '/uploads/images/a72e0876-fb6e-4a0f-ab9e-a945187edeaa.webp', 1, '2026-03-14 22:06:29', 25),
                                                                                                          (39, 'Dao Cạo Râu Gillette Blue 3', 'Dao cạo râu dùng cá nhân', 46, '/uploads/images/ac12e2ab-1cb6-4a00-9d99-2657b34e3154.jpg', 1, '2026-03-14 22:06:29', 42),
                                                                                                          (40, 'Băng Vệ Sinh Kotex Hàng Ngày', 'Băng vệ sinh mỏng nhẹ dùng hằng ngày', 49, '/uploads/images/72eac2a7-6206-410a-ac73-0e240a77d1e2.jpg', 1, '2026-03-14 22:06:29', 38),
                                                                                                          (41, 'Băng Vệ Sinh Diana Sensi', 'Băng vệ sinh ban ngày tiện lợi', 49, '/uploads/images/72ce13ac-2dad-46c2-86b9-cb78cf296bcf.jpg', 1, '2026-03-14 22:06:29', 40),
                                                                                                          (42, 'Bút Bi Thiên Long TL-027', 'Bút bi viết trơn, mực đều', 52, '/uploads/images/dca47016-2e2f-46d6-92ac-6dc86a71044a.webp', 1, '2026-03-14 22:06:29', 44),
                                                                                                          (43, 'Sổ Tay Double A A5', 'Sổ tay ghi chép giấy mịn', 53, '/uploads/images/e7367a3f-4bc6-4d3e-b4c1-48f9add42361.jpg', 1, '2026-03-14 22:06:29', 45),
                                                                                                          (44, 'Pin AA Energizer 2 Viên', 'Pin tiểu AA dùng cho thiết bị gia dụng', 54, '/uploads/images/6a2385ce-b593-4d22-b823-528d1aeb35e4.jpg', 1, '2026-03-14 22:06:29', 46),
                                                                                                          (45, 'Bật Lửa Bic Mini', 'Bật lửa nhỏ gọn tiện ích', 55, '/uploads/images/761a4008-562a-4162-a3ce-e6ef7c5b1c50.jpg', 1, '2026-03-14 22:06:29', 43),
                                                                                                          (46, 'Nước Rửa Chén Sunlight Chanh 750ml', 'Nước rửa chén sạch dầu mỡ', 61, '/uploads/images/eea92c7f-c6c8-40ed-bea0-9754c4233ce7.jpg', 1, '2026-03-14 22:06:29', 31),
                                                                                                          (47, 'Mút Rửa Chén Scotch-Brite 2 Miếng', 'Mút rửa chén vệ sinh nhà bếp', 62, '/uploads/images/094bcfc0-1fb3-42d0-a00c-a8ba17eeb0b8.jpg', 1, '2026-03-14 22:06:29', 33),
                                                                                                          (48, 'Túi Rác Gia Dụng Rạng Đông', 'Túi rác cuộn tiện lợi dùng hằng ngày', 60, '/uploads/images/ab562137-f6cb-4f25-846f-b8936b8e3008.jpg', 1, '2026-03-14 22:06:29', 49),
                                                                                                          (49, 'Kem Merino Sô Cô La', 'Kem que vị sô cô la', 67, '/uploads/images/27ff2719-79e2-42e0-986a-94328c67c519.jpg', 1, '2026-03-14 22:06:29', 63),
                                                                                                          (50, 'Kem Celano Vani Hạnh Nhân', 'Kem cao cấp vị vani hạnh nhân', 67, '/uploads/images/a25234bd-1241-4a6b-a80a-60787d50e875.jpg', 1, '2026-03-14 22:06:29', 64),
                                                                                                          (51, 'Cá Viên Cầu Tre 500g', 'Cá viên đông lạnh dùng chiên hoặc nấu lẩu', 68, '/uploads/images/e95a9ea8-97ae-41cc-8bbe-4400a62f3dc8.jpg', 1, '2026-03-14 22:06:29', 60),
                                                                                                          (52, 'Xúc Xích Đông Lạnh CP 500g', 'Xúc xích đông lạnh tiện chế biến', 71, '/uploads/images/e27e5c60-97f3-4a2a-9c5c-121745eda248.jpg', 1, '2026-03-14 22:06:29', 61);


INSERT INTO product_units
(product_id, unit_name, conversion_factor, barcode, selling_price, reorder_level, is_base_unit, is_active)
VALUES
    (1, 'Lon', 1, 'SP000001', 10000.00, 10, 1, 1),
    (2, 'Lon', 1, 'SP000002', 10000.00, 10, 1, 1),
    (3, 'Chai', 1, 'SP000003', 5000.00, 10, 1, 1),
    (4, 'Chai', 1, 'SP000004', 5000.00, 10, 1, 1),
    (5, 'Chai', 1, 'SP000005', 12000.00, 10, 1, 1),
    (6, 'Lon', 1, 'SP000006', 15000.00, 10, 1, 1),
    (7, 'Hộp', 1, 'SP000007', 8000.00, 10, 1, 1),
    (8, 'Hộp', 1, 'SP000008', 9000.00, 10, 1, 1),
    (9, 'Hộp', 1, 'SP000009', 9000.00, 10, 1, 1),
    (10, 'Lon', 1, 'SP000010', 11000.00, 10, 1, 1),
    (11, 'Chai', 1, 'SP000011', 14000.00, 10, 1, 1),
    (12, 'Hộp', 1, 'SP000012', 28000.00, 10, 1, 1),
    (13, 'Gói', 1, 'SP000013', 8000.00, 10, 1, 1),
    (14, 'Gói', 1, 'SP000014', 12000.00, 10, 1, 1),
    (15, 'Gói', 1, 'SP000015', 12000.00, 10, 1, 1),
    (16, 'Gói', 1, 'SP000016', 7000.00, 10, 1, 1),
    (17, 'Thanh', 1, 'SP000017', 18000.00, 10, 1, 1),
    (18, 'Lốc', 1, 'SP000018', 15000.00, 10, 1, 1),
    (19, 'Gói', 1, 'SP000019', 5000.00, 10, 1, 1),
    (20, 'Gói', 1, 'SP000020', 8000.00, 10, 1, 1),
    (21, 'Gói', 1, 'SP000021', 4000.00, 10, 1, 1),
    (22, 'Gói', 1, 'SP000022', 9000.00, 10, 1, 1),
    (23, 'Gói', 1, 'SP000023', 7000.00, 10, 1, 1),
    (24, 'Cây', 1, 'SP000024', 5000.00, 10, 1, 1),
    (25, 'Hộp', 1, 'SP000025', 25000.00, 10, 1, 1),
    (26, 'Chai', 1, 'SP000026', 22000.00, 10, 1, 1),
    (27, 'Chai', 1, 'SP000027', 18000.00, 10, 1, 1),
    (28, 'Chai', 1, 'SP000028', 17000.00, 10, 1, 1),
    (29, 'Gói', 1, 'SP000029', 22000.00, 10, 1, 1),
    (30, 'Gói', 1, 'SP000030', 30000.00, 10, 1, 1),
    (31, 'Chai', 1, 'SP000031', 65000.00, 10, 1, 1),
    (32, 'Chai', 1, 'SP000032', 70000.00, 10, 1, 1),
    (33, 'Chai', 1, 'SP000033', 75000.00, 10, 1, 1),
    (34, 'Tuýp', 1, 'SP000034', 42000.00, 10, 1, 1),
    (35, 'Tuýp', 1, 'SP000035', 35000.00, 10, 1, 1),
    (36, 'Chai', 1, 'SP000036', 38000.00, 10, 1, 1),
    (37, 'Gói', 1, 'SP000037', 22000.00, 10, 1, 1),
    (38, 'Cây', 1, 'SP000038', 25000.00, 10, 1, 1),
    (39, 'Cái', 1, 'SP000039', 45000.00, 10, 1, 1),
    (40, 'Gói', 1, 'SP000040', 12000.00, 10, 1, 1),
    (41, 'Gói', 1, 'SP000041', 15000.00, 10, 1, 1),
    (42, 'Cây', 1, 'SP000042', 5000.00, 10, 1, 1),
    (43, 'Quyển', 1, 'SP000043', 18000.00, 10, 1, 1),
    (44, 'Vỉ', 1, 'SP000044', 25000.00, 10, 1, 1),
    (45, 'Cái', 1, 'SP000045', 7000.00, 10, 1, 1),
    (46, 'Chai', 1, 'SP000046', 32000.00, 10, 1, 1),
    (47, 'Gói', 1, 'SP000047', 12000.00, 10, 1, 1),
    (48, 'Cuộn', 1, 'SP000048', 18000.00, 10, 1, 1),
    (49, 'Cây', 1, 'SP000049', 10000.00, 10, 1, 1),
    (50, 'Hộp', 1, 'SP000050', 25000.00, 10, 1, 1),
    (51, 'Gói', 1, 'SP000051', 55000.00, 10, 1, 1),
    (52, 'Gói', 1, 'SP000052', 65000.00, 10, 1, 1);



