DROP DATABASE certificate_base;

CREATE SCHEMA IF NOT EXISTS `certificate_base`;

USE `certificate_base`;

CREATE SCHEMA IF NOT EXISTS `certificate_base` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `certificate_base` ;

-- -----------------------------------------------------
-- Table `certificate_base`.`certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `certificate_base`.`certificate` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` TEXT(400) NULL,
  `price` DECIMAL(6,2) UNSIGNED NOT NULL,
  `duration` TINYINT(90) UNSIGNED NULL,
  `creation_date` VARCHAR(45) NOT NULL,
  `last_update_date` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `description_UNIQUE` (`description` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `certificate_base`.`tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `certificate_base`.`tag` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `certificate_base`.`certificate_has_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `certificate_base`.`certificate_has_tag` (
  `id` VARCHAR(45) NOT NULL,
  `Certificate_id` INT NOT NULL,
  `tag_id` INT NOT NULL,
  PRIMARY KEY (`id`, `Certificate_id`, `tag_id`),
  INDEX `fk_Certificate_has_tag_tag1_idx` (`tag_id` ASC) VISIBLE,
  CONSTRAINT `fk_Certificate_has_tag_Certificate`
    FOREIGN KEY (`Certificate_id`)
    REFERENCES `certificate_base`.`certificate` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Certificate_has_tag_tag1`
    FOREIGN KEY (`tag_id`)
    REFERENCES `certificate_base`.`tag` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



    `