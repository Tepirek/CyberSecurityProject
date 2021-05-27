CREATE DATABASE IF NOT EXISTS cybersecurity;
USE cybersecurity;

CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    user_login TEXT NOT NULL,
    user_password TEXT NOT NULL,
    user_email TEXT,
    created_at TIMESTAMP
                                 );