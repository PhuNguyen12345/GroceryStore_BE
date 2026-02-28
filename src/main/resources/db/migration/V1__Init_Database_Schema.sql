/* =============================================================================
DỰ ÁN: GROCERY STORE MANAGEMENT (MÔ HÌNH BÁCH HÓA XANH)
DATABASE: MySQL
MODULES: HR, Product (Multi-unit), Inventory (Batch/FEFO), POS, CRM (OTP)
=============================================================================
*/

-- ==========================================================================
-- MODULE 1: HR & SHIFT (NHÂN SỰ & CA LÀM VIỆC)
-- ==========================================================================

-- Bảng Nhân viên (Vẫn cần Password để đăng nhập hệ thống nội bộ)
CREATE TABLE employees (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           username VARCHAR(50) NOT NULL UNIQUE,
                           password_hash VARCHAR(255) NOT NULL, -- Mật khẩu đã mã hóa (BCrypt)
                           full_name VARCHAR(100) NOT NULL,
                           email VARCHAR(100) UNIQUE,
                           phone VARCHAR(15),
                           role ENUM('ADMIN', 'STORE_MANAGER', 'INVENTORY_STAFF', 'CASHIER') NOT NULL DEFAULT 'CASHIER',
                           is_active TINYINT(1) DEFAULT 1, -- 1: Active, 0: Inactive
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bảng Ca làm việc mẫu (Sáng, Chiều, Tối)
CREATE TABLE shifts (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(50) NOT NULL,
                        start_time TIME NOT NULL,
                        end_time TIME NOT NULL,
                        description VARCHAR(255)
);

-- Bảng Phân công & Chấm công
CREATE TABLE work_schedules (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                employee_id BIGINT NOT NULL,
                                shift_id BIGINT NOT NULL,
                                work_date DATE NOT NULL,
                                is_present TINYINT(1) DEFAULT 0, -- 0: Vắng, 1: Có mặt
                                check_in_time DATETIME NULL,
                                check_out_time DATETIME NULL,
                                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                CONSTRAINT fk_ws_employee FOREIGN KEY (employee_id) REFERENCES employees(id),
                                CONSTRAINT fk_ws_shift FOREIGN KEY (shift_id) REFERENCES shifts(id)
);

-- ==========================================================================
-- MODULE 2: PRODUCT (SẢN PHẨM ĐA ĐƠN VỊ TÍNH)
-- ==========================================================================

-- Danh mục đệ quy (Ví dụ: Đồ uống -> Có gas -> Coca)
CREATE TABLE categories (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            parent_id BIGINT NULL, -- Null nếu là danh mục gốc
                            slug VARCHAR(150),
                            description TEXT,
                            CONSTRAINT fk_category_parent FOREIGN KEY (parent_id) REFERENCES categories(id) ON DELETE SET NULL
);

-- Sản phẩm gốc (Chứa thông tin chung, không chứa giá)
CREATE TABLE products (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(200) NOT NULL,
                          description TEXT,
                          brand VARCHAR(100),
                          category_id BIGINT,
                          image_url VARCHAR(500),
                          is_active TINYINT(1) DEFAULT 1,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- Đơn vị tính & Quy đổi (Quan trọng: Lon, Lốc, Thùng)
CREATE TABLE product_units (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               product_id BIGINT NOT NULL,
                               unit_name VARCHAR(50) NOT NULL, -- Ví dụ: 'Lon', 'Thùng 24'
                               conversion_factor INT DEFAULT 1, -- 1 (Lon), 24 (Thùng)
                               barcode VARCHAR(50) UNIQUE, -- Mã vạch riêng từng đơn vị
                               selling_price DECIMAL(15, 2) NOT NULL, -- Giá bán lẻ
                               reorder_level INT DEFAULT 10, -- Cảnh báo tồn kho thấp
                               is_base_unit TINYINT(1) DEFAULT 0, -- Đánh dấu đây là đơn vị cơ sở (nhỏ nhất)
                               CONSTRAINT fk_unit_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- ==========================================================================
-- MODULE 3: INVENTORY (KHO & QUẢN LÝ LÔ - FEFO)
-- ==========================================================================

CREATE TABLE suppliers (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(150) NOT NULL,
                           contact_person VARCHAR(100),
                           phone VARCHAR(15),
                           address TEXT
);

CREATE TABLE warehouses (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100) NOT NULL, -- Ví dụ: Kho Chính, Kho Cửa Hàng
                            address TEXT
);

-- Bảng Lô hàng (Batch) - Trái tim của việc quản lý hạn sử dụng
CREATE TABLE inventory_batches (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   product_unit_id BIGINT NOT NULL, -- Nhập theo đơn vị nào (thường là Thùng)
                                   warehouse_id BIGINT NOT NULL,
                                   batch_code VARCHAR(50) NOT NULL, -- Mã lô trên bao bì
                                   expiry_date DATE NOT NULL, -- Hạn sử dụng (Logic FEFO dựa vào cột này)
                                   quantity_available INT NOT NULL DEFAULT 0, -- Tồn kho thực tế của lô này
                                   import_price DECIMAL(15, 2) NOT NULL, -- Giá vốn nhập vào
                                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   CONSTRAINT fk_batch_unit FOREIGN KEY (product_unit_id) REFERENCES product_units(id),
                                   CONSTRAINT fk_batch_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouses(id)
);

-- Lịch sử giao dịch kho (Audit Log)
CREATE TABLE inventory_transactions (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        transaction_type ENUM('IMPORT', 'EXPORT', 'ADJUSTMENT', 'RETURN') NOT NULL,
                                        employee_id BIGINT, -- Ai thực hiện
                                        note TEXT,
                                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                        CONSTRAINT fk_trans_employee FOREIGN KEY (employee_id) REFERENCES employees(id)
);

CREATE TABLE transaction_details (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     transaction_id BIGINT NOT NULL,
                                     inventory_batch_id BIGINT NOT NULL,
                                     quantity INT NOT NULL, -- Số lượng thay đổi (+ hoặc -)
                                     CONSTRAINT fk_detail_trans FOREIGN KEY (transaction_id) REFERENCES inventory_transactions(id),
                                     CONSTRAINT fk_detail_batch FOREIGN KEY (inventory_batch_id) REFERENCES inventory_batches(id)
);

-- ==========================================================================
-- MODULE 4: CRM & PROMOTION (KHÁCH HÀNG PASSWORDLESS & KHUYẾN MÃI)
-- ==========================================================================

-- Khách hàng: Không có cột Password
CREATE TABLE customers (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           phone VARCHAR(15) NOT NULL UNIQUE, -- Định danh duy nhất
                           full_name VARCHAR(100),
                           email VARCHAR(100),
                           address TEXT,
                           loyalty_points INT DEFAULT 0, -- Điểm tích lũy
                           customer_tier ENUM('BRONZE', 'SILVER', 'GOLD', 'DIAMOND') DEFAULT 'BRONZE',
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE promotions (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(200) NOT NULL,
                            description TEXT,
                            start_date DATETIME NOT NULL,
                            end_date DATETIME NOT NULL,
                            discount_type ENUM('PERCENTAGE', 'FIXED_AMOUNT') NOT NULL,
                            discount_value DECIMAL(15, 2) NOT NULL,
                            is_active TINYINT(1) DEFAULT 1
);

CREATE TABLE vouchers (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          code VARCHAR(20) NOT NULL UNIQUE,
                          promotion_id BIGINT,
                          quantity_limit INT DEFAULT 100,
                          quantity_used INT DEFAULT 0,
                          min_order_value DECIMAL(15, 2) DEFAULT 0,
                          CONSTRAINT fk_voucher_promo FOREIGN KEY (promotion_id) REFERENCES promotions(id)
);

-- ==========================================================================
-- MODULE 5: POS (ĐƠN HÀNG & THANH TOÁN)
-- ==========================================================================

CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        order_code VARCHAR(50) NOT NULL UNIQUE, -- Ví dụ: ORD-20260227-001
                        customer_id BIGINT NULL, -- Null nếu khách vãng lai
                        employee_id BIGINT NOT NULL, -- Thu ngân
                        total_amount DECIMAL(15, 2) NOT NULL, -- Tổng tiền hàng chưa giảm
                        discount_amount DECIMAL(15, 2) DEFAULT 0, -- Tiền giảm giá
                        final_amount DECIMAL(15, 2) NOT NULL, -- Tiền khách phải trả
                        payment_method ENUM('CASH', 'CREDIT_CARD', 'QR_CODE', 'POINTS') DEFAULT 'CASH',
                        status ENUM('PENDING', 'COMPLETED', 'CANCELLED', 'RETURNED') DEFAULT 'COMPLETED',
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) REFERENCES customers(id),
                        CONSTRAINT fk_order_employee FOREIGN KEY (employee_id) REFERENCES employees(id)
);

CREATE TABLE order_details (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               order_id BIGINT NOT NULL,
                               product_unit_id BIGINT NOT NULL, -- Mua đơn vị nào (Lon hay Thùng)
                               quantity INT NOT NULL,
                               unit_price DECIMAL(15, 2) NOT NULL, -- Lưu cứng giá tại thời điểm bán
                               subtotal DECIMAL(15, 2) NOT NULL,
                               CONSTRAINT fk_odetail_order FOREIGN KEY (order_id) REFERENCES orders(id),
                               CONSTRAINT fk_odetail_unit FOREIGN KEY (product_unit_id) REFERENCES product_units(id)
);