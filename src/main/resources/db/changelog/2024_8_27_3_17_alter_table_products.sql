ALTER TABLE products
DROP COLUMN image_name,
DROP COLUMN image_type,
DROP COLUMN image_data,
ADD COLUMN image_path VARCHAR(255);
