CREATE DATABASE IF NOT EXISTS area;
GRANT USAGE ON *.* TO 'area'@'%' IDENTIFIED BY 'djh7.HDi5332jczj';
GRANT ALL PRIVILEGES ON area.* TO 'area'@'%';
FLUSH PRIVILEGES;
CREATE TABLE `area`.`token` ( `id` INT NOT NULL AUTO_INCREMENT , `fk_token_user` INT NOT NULL , `api_name` TEXT NOT NULL , `value` TEXT NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;
CREATE TABLE `area`.`user` ( `id` INT NOT NULL AUTO_INCREMENT , `name` TEXT NOT NULL , `password` TEXT NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;
ALTER TABLE `area`.`token` ADD FOREIGN KEY (`fk_token_user`) REFERENCES `user`(`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;