-- Create Database
CREATE DATABASE IF NOT EXISTS `WILMA_DB`;
USE `WILMA_DB`;

-- Create User
FLUSH PRIVILEGES;
CREATE USER IF NOT EXISTS 'WILMA_USER'@'%' IDENTIFIED BY 'WILMA_P455W0RD';

-- Grand database access
GRANT ALL ON WILMA_DB.* TO 'WILMA_USER'@'%';
FLUSH PRIVILEGES;