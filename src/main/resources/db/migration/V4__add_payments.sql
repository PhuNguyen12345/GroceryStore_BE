-- 1. Tạo bảng Payment để quản lý chi tiết giao dịch (QR, Thẻ, Tiền mặt)
CREATE TABLE payments (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          order_id BIGINT NOT NULL,

    -- Số tiền thanh toán trong giao dịch này (Hỗ trợ trả thiếu/trả đủ)
                          amount DECIMAL(15, 2) NOT NULL,

    -- Phương thức: CASH, QR_CODE, BANK_TRANSFER, CARD
                          payment_method ENUM('CASH', 'QR_CODE', 'BANK_TRANSFER', 'CARD', 'POINTS') NOT NULL,

    -- Mã giao dịch từ Ngân hàng/Cổng thanh toán (Để đối soát)
                          transaction_ref VARCHAR(100),

    -- Trạng thái riêng của giao dịch này (Khác với trạng thái đơn hàng)
                          status ENUM('PENDING', 'SUCCESS', 'FAILED', 'REFUNDED') DEFAULT 'PENDING',

    -- Dữ liệu JSON hoặc Text để tái tạo lại mã QR nếu cần
                          payment_payload TEXT,

                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    -- Tạo khóa ngoại liên kết chặt chẽ với đơn hàng
                          CONSTRAINT fk_payment_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

-- Thêm index để tìm kiếm đơn hàng theo trạng thái thanh toán nhanh hơn
CREATE INDEX idx_order_status ON orders(status);

-- Thêm index để tìm kiếm giao dịch theo mã ngân hàng (Phục vụ Webhook)
CREATE INDEX idx_payment_ref ON payments(transaction_ref);