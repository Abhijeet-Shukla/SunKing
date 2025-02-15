-- Create 'orders' table
CREATE TABLE IF NOT EXISTS orders (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id UUID NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create 'inventory' table
CREATE TABLE IF NOT EXISTS inventory (
    product_id UUID PRIMARY KEY,
    stock INT NOT NULL CHECK (stock >= 0)
);

-- Insert sample inventory data
INSERT INTO inventory (product_id, stock) VALUES
    ('123e4567-e89b-12d3-a456-426614174000', 100),
    ('234e4567-e89b-12d3-a456-426614174001', 50),
    ('345e4567-e89b-12d3-a456-426614174002', 200)
ON CONFLICT (product_id) DO NOTHING;

-- Insert sample orders
INSERT INTO orders (id, product_id, quantity, status) VALUES
    ('111e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174000', 2, 'PENDING'),
    ('222e4567-e89b-12d3-a456-426614174001', '234e4567-e89b-12d3-a456-426614174001', 1, 'SHIPPED')
ON CONFLICT (id) DO NOTHING;
