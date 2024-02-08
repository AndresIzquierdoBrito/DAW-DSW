CREATE TABLE pedidos (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         usuario VARCHAR(30) NOT NULL CHECK (LENGTH(usuario) >= 3 AND LENGTH(usuario) <= 30),
                         precio_total FLOAT NOT NULL,
                         fecha DATE NOT NULL
);