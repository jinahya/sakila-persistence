CREATE USER 'user'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON sakila.* TO 'user'@'%';
FLUSH PRIVILEGES;
