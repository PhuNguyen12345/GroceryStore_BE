-- 1. Tách bảng Brands (Theo ý kiến số 5)
CREATE TABLE brands (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        description TEXT,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 2. Cập nhật Products (Link tới Brand)
ALTER TABLE products
    ADD COLUMN brand_id BIGINT,
    DROP COLUMN brand,
ADD CONSTRAINT fk_product_brand FOREIGN KEY (brand_id) REFERENCES brands(id);

-- 3. Cập nhật Categories (Audit log - Theo ý kiến số 4)
ALTER TABLE categories
    ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    aDD COLUMN is_active BOOLEAN DEFAULT TRUE;


