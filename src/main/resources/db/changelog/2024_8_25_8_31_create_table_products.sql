CREATE TABLE products (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(35),
    description VARCHAR(255),
    brand VARCHAR(35),
    price DECIMAL(10, 2),
    release_date DATE,
    product_available TINYINT(1),
    stock_quantity INT,
    image_name VARCHAR(35),
    image_type VARCHAR(35),
    image_data MEDIUMBLOB,
    category_id INT NOT NULL,

    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES categories(id)
) ENGINE=InnoDB;