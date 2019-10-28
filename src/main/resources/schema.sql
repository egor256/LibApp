CREATE TABLE IF NOT EXISTS users (
    principal VARCHAR(255),
    credentials VARCHAR(255),
    role VARCHAR(255),
    email VARCHAR(255),
);

CREATE TABLE IF NOT EXISTS books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    author VARCHAR(255),
    quantity INT,
);

CREATE TABLE IF NOT EXISTS tags (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
);

CREATE TABLE IF NOT EXISTS bookTags (
    bookId INT,
    tagId INT,
);

CREATE TABLE IF NOT EXISTS bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    bookId INT,
    principal VARCHAR(255),
    status INT,
);
