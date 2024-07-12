CREATE TABLE IF NOT EXISTS `coin_operated_soda_machine`.`products` (
  `product_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `price` DECIMAL NOT NULL,
  `quantity` VARCHAR(45) NOT NULL,
  `image_url` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`product_id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `coin_operated_soda_machine`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `coin_operated_soda_machine`.`transactions` (
  `transaction_id` INT NOT NULL AUTO_INCREMENT,
  `product_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `amount_paid` DECIMAL NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  `transaction_time` TIMESTAMP NOT NULL,
  PRIMARY KEY (`transaction_id`, `product_id`, `user_id`),
  INDEX `fk_transactions_products_idx` (`product_id` ASC) VISIBLE,
  INDEX `fk_transactions_users1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_transactions_products`
    FOREIGN KEY (`product_id`)
    REFERENCES `coin_operated_soda_machine`.`products` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_transactions_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `coin_operated_soda_machine`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `coin_operated_soda_machine`.`promotions` (
  `promotion_id` INT NOT NULL AUTO_INCREMENT,
  `win_rate` DECIMAL NOT NULL,
  `daily_budget` INT NOT NULL,
  `remaining_budget` INT NOT NULL,
  PRIMARY KEY (`promotion_id`))
ENGINE = InnoDB;

INSERT INTO `coin_operated_soda_machine`.`products` (name, price, quantity, image_url) VALUES ("Coke", 10000, 100, "https://www.contis.ph/cdn/shop/products/CokeinCan.jpg?v=1689558538");
INSERT INTO `coin_operated_soda_machine`.`products` (name, price, quantity, image_url) VALUES ("Pepsi", 10000, 100, "https://minhcaumart.vn/media/com_eshop/products/8934588012228%201.jpg");
INSERT INTO `coin_operated_soda_machine`.`products` (name, price, quantity, image_url) VALUES ("Soda", 20000, 100, "https://www.lottemart.vn/media/catalog/product/cache/0x0/8/9/8934588692116.jpg.webp");

INSERT INTO `coin_operated_soda_machine`.`promotions` (win_rate, daily_budget, remaining_budget) values (10.0, 50000, 50000);