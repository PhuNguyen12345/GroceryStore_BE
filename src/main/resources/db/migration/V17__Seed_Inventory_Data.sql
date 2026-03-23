-- =========================================================================
-- 1. BẢNG WAREHOUSES & SUPPLIERS (Giữ nguyên như cũ)
-- =========================================================================
INSERT INTO warehouses (id, name, address, is_active) VALUES
                                                          (1, 'Kho Tổng (Central)', '123 Nguyễn Văn Linh, Quận 7, TP.HCM', true),
                                                          (2, 'Kho Chi Nhánh Q1', '456 Lê Lợi, Quận 1, TP.HCM', true);

INSERT INTO suppliers (id, name, contact_person, phone, address, is_active, logo_url) VALUES
                                                                                          (1, 'Công ty Cổ phần Sữa TH', 'Nguyễn Văn A', '0901234567', 'Nghệ An', true, 'https://example.com/th-logo.png'),
                                                                                          (2, 'Công ty Acecook Việt Nam', 'Trần Thị B', '0987654321', 'KCN Tân Bình, TP.HCM', true, 'https://example.com/acecook-logo.png'),
                                                                                          (3, 'Nhà phân phối Bia Heineken', 'Lê Văn C', '0911222333', 'Quận 12, TP.HCM', true, NULL);

-- =========================================================================
-- 2. BẢNG INVENTORY_TRANSACTIONS (Tạo 3 phiếu nhập, 1 phiếu xuất lịch sử)
-- Giả định employee_id = 1
-- =========================================================================
INSERT INTO inventory_transactions (id, transaction_type, warehouse_id, employee_id, note, created_at) VALUES
                                                                                                           (1, 'IMPORT', 1, 1, 'Nhập sữa TH đợt 1 (Cận date)', '2025-12-01 08:00:00'),
                                                                                                           (2, 'IMPORT', 1, 1, 'Nhập sữa TH đợt 2 + Mì Hảo Hảo', '2026-01-15 09:30:00'),
                                                                                                           (3, 'IMPORT', 1, 1, 'Nhập sữa TH đợt 3 (Date xa)', '2026-02-20 10:00:00'),
                                                                                                           (4, 'EXPORT', 1, 1, 'Xuất bán lẻ lịch sử (Làm cạn 1 lô Mì)', '2026-03-01 14:00:00');

-- =========================================================================
-- 3. BẢNG INVENTORY_BATCHES (Tạo các lô hàng cực kỳ phân mảnh để test FIFO)
-- Giả định:
-- product_unit_id = 1 (Thùng Sữa TH)
-- product_unit_id = 2 (Thùng Mì Hảo Hảo)
-- =========================================================================
INSERT INTO inventory_batches (id, product_unit_id, warehouse_id, supplier_id, batch_code, expiry_date, quantity_available, import_price, discount_percent, is_discounted, created_at) VALUES
-- CÁC LÔ SỮA TH (ID = 1) ĐỂ TEST FIFO CHI TIẾT
-- Lô 1: Cận date nhất (T5/2026). Số lượng nhập ban đầu 20, hiện tại còn 10.
(1, 1, 1, 1, 'TH-MAY-001', '2026-05-01 00:00:00', 10, 270000.00, 0, false, '2025-12-01 08:00:00'),

-- Lô 2: Date T8/2026. Nhập trước. Số lượng 50.
(2, 1, 1, 1, 'TH-AUG-001', '2026-08-15 00:00:00', 50, 280000.00, 0, false, '2026-01-15 09:30:00'),

-- Lô 3: Date T8/2026 (Trùng date lô 2). Nhập sau. Số lượng 100.
(3, 1, 1, 1, 'TH-AUG-002', '2026-08-15 00:00:00', 100, 285000.00, 0, false, '2026-02-20 10:00:00'),

-- CÁC LÔ MÌ HẢO HẢO (ID = 2) ĐỂ TEST "TRẠNG THÁI HẾT HÀNG" VÀ "KHÁC KHO"
-- Lô 4: Date T10/2026. Đã bán sạch (Quantity = 0). Thuật toán phải lờ đi.
(4, 2, 1, 2, 'AC-OCT-001', '2026-10-01 00:00:00', 0, 110000.00, 0, false, '2026-01-15 09:30:00'),

-- Lô 5: Date T12/2026. Đang còn 200 thùng.
(5, 2, 1, 2, 'AC-DEC-001', '2026-12-01 00:00:00', 200, 115000.00, 5, true, '2026-01-15 09:30:00'),

-- Lô 6: Cũng Mì Hảo Hảo nhưng nằm ở KHO CHI NHÁNH (Warehouse 2). Xuất kho Tổng không được đụng vào đây.
(6, 2, 2, 2, 'AC-BRANCH-01', '2027-01-01 00:00:00', 50, 115000.00, 5, true, '2026-01-15 09:30:00');

-- =========================================================================
-- 4. BẢNG TRANSACTION_DETAILS (Map số lượng lịch sử)
-- =========================================================================
INSERT INTO transaction_details (id, transaction_id, inventory_batch_id, quantity) VALUES
-- Lịch sử của Phiếu nhập 1, 2, 3
(1, 1, 1, 20),  -- Nhập 20 thùng lô 1
(2, 2, 2, 50),  -- Nhập 50 thùng lô 2
(3, 2, 4, 30),  -- Nhập 30 thùng lô 4 (mì Hảo Hảo)
(4, 2, 5, 200), -- Nhập 200 thùng lô 5
(5, 2, 6, 50),  -- Nhập 50 thùng lô 6 cho chi nhánh
(6, 3, 3, 100), -- Nhập 100 thùng lô 3

-- Lịch sử của Phiếu xuất 4 (Giải thích vì sao Lô 1 còn 10, Lô 4 còn 0)
(7, 4, 1, 10),  -- Đã xuất 10 thùng sữa từ Lô 1 trong quá khứ
(8, 4, 4, 30);  -- Đã xuất sạch 30 thùng mì từ Lô 4