-- =========================================================================
-- ĐẠI TIỆC AUTO-SEED VÀ AUTO-SUPPLIER (MULTI-WAREHOUSE HOÀN CHỈNH)
-- Nghiệp vụ: Mỗi kho có 2 Phiếu Nhập + 2 Phiếu Xuất (Admin & Thủ kho)
-- Dữ liệu: Đầy đủ các đơn vị (Base Unit & Đơn vị lớn) cho TẤT CẢ các kho
-- =========================================================================

-- 1. TỰ ĐỘNG SINH NHÀ CUNG CẤP TỪ BẢNG BRANDS
INSERT INTO suppliers (name, contact_person, phone, address, is_active, logo_url)
SELECT CONCAT('NPP ', name), 'Đại diện KD', '19008888', 'TP.HCM', 1, logo_url
FROM brands
WHERE name NOT IN ('TH true MILK', 'Hảo Hảo');


-- 2. TẠO CÁC PHIẾU GIAO DỊCH NHẬP/XUẤT (TỔNG CỘNG 8 PHIẾU)
-- Phân quyền: Admin (ID=1) và Inventory Staff (ID=3)
INSERT INTO inventory_transactions (id, transaction_type, warehouse_id, employee_id, note, created_at) VALUES
-- KHO 1 (KHO TỔNG): 2 Nhập, 2 Xuất
(101, 'IMPORT', 1, 1, 'Kho Tổng: Admin nhập hàng đợt 1', CURRENT_TIMESTAMP),
(102, 'IMPORT', 1, 3, 'Kho Tổng: Thủ kho nhập hàng đợt 2', CURRENT_TIMESTAMP),
(201, 'EXPORT', 1, 1, 'Kho Tổng: Admin xuất kho xử lý lỗi', CURRENT_TIMESTAMP),
(202, 'EXPORT', 1, 3, 'Kho Tổng: Thủ kho xuất hàng cận date', CURRENT_TIMESTAMP),

-- KHO 2 (CHI NHÁNH Q1): 2 Nhập, 2 Xuất
(103, 'IMPORT', 2, 1, 'Chi Nhánh: Admin nhập hàng đợt 1', CURRENT_TIMESTAMP),
(104, 'IMPORT', 2, 3, 'Chi Nhánh: Thủ kho nhập bổ sung đợt 2', CURRENT_TIMESTAMP),
(203, 'EXPORT', 2, 1, 'Chi Nhánh: Admin điều chuyển hàng', CURRENT_TIMESTAMP),
(204, 'EXPORT', 2, 3, 'Chi Nhánh: Thủ kho xuất hàng móp méo', CURRENT_TIMESTAMP);


-- 3. SINH LÔ HÀNG ĐỒNG LOẠT CHO TẤT CẢ CÁC KHO (CROSS JOIN)
-- Bao gồm cả Chai/Lon/Gói và Thùng/Hộp/Lốc
INSERT INTO inventory_batches (product_unit_id, warehouse_id, supplier_id, batch_code, expiry_date, quantity_available, import_price, discount_percent, is_discounted, created_at)
SELECT
    u.id,
    w.id, -- Phép màu CROSS JOIN: Tự động lặp qua mọi warehouse_id
    (SELECT s.id FROM suppliers s WHERE s.name = CONCAT('NPP ', b.name) LIMIT 1),
    CONCAT('BATCH-V18-', p.id, '-', u.id, '-W', w.id), -- VD: BATCH-V18-1-1-W1 (Kho 1) và W2 (Kho 2)
    '2026-12-31 00:00:00',
    45, -- Tồn kho thực tế (Do Nhập 50 - Xuất 5)
    u.selling_price * 0.7,
    0,
    false,
    CURRENT_TIMESTAMP
FROM product_units u
         JOIN products p ON u.product_id = p.id
         JOIN brands b ON p.brand_id = b.id
         CROSS JOIN warehouses w
WHERE p.name NOT IN ('Sữa tươi TH true MILK ít đường', 'Mì Hảo Hảo tôm chua cay'); -- Bỏ qua món test của V17


-- 4. CHI TIẾT PHIẾU NHẬP (Nhập 50 đơn vị)
-- Thuật toán chia bài: Hàng kho nào sẽ được rải đều (MOD 2) vào 2 phiếu nhập của kho đó
INSERT INTO transaction_details (transaction_id, inventory_batch_id, quantity)
SELECT
    CASE
        WHEN warehouse_id = 1 THEN 101 + (id % 2) -- Kho 1: Chia ngẫu nhiên vào Phiếu 101 hoặc 102
        WHEN warehouse_id = 2 THEN 103 + (id % 2) -- Kho 2: Chia ngẫu nhiên vào Phiếu 103 hoặc 104
        END,
    id,
    50
FROM inventory_batches
WHERE batch_code LIKE 'BATCH-V18-%';


-- 5. CHI TIẾT PHIẾU XUẤT (Xuất 5 đơn vị)
-- Thuật toán chia bài: Hàng kho nào sẽ được rải đều (MOD 2) vào 2 phiếu xuất của kho đó
INSERT INTO transaction_details (transaction_id, inventory_batch_id, quantity)
SELECT
    CASE
        WHEN warehouse_id = 1 THEN 201 + (id % 2) -- Kho 1: Chia ngẫu nhiên vào Phiếu 201 hoặc 202
        WHEN warehouse_id = 2 THEN 203 + (id % 2) -- Kho 2: Chia ngẫu nhiên vào Phiếu 203 hoặc 204
        END,
    id,
    5
FROM inventory_batches
WHERE batch_code LIKE 'BATCH-V18-%';