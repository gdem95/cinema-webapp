-- Creazione database
CREATE DATABASE cinemadb;

-- Creazione utente per cinemadb
CREATE USER 'cinemadb_user'@'localhost' IDENTIFIED BY '[YOUR_PASSWORD]';

-- Attribuzione privilegi a cinemadb_user
GRANT ALL PRIVILEGES ON cinemadb.* TO 'cinemadb_user'@'localhost';

FLUSH PRIVILEGES;
