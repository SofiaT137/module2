DROP DATABASE certificate_base;

CREATE DATABASE IF NOT EXISTS certificate_base;

USE certificate_base;

-- -----------------------------------------------------
-- Table certificate_base.tag
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS tag
(
    id       BIGINT UNSIGNED AUTO_INCREMENT,
    name VARCHAR(25) NOT NULL,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table certificate_base.gift_certificate
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS gift_certificate
(
    id               BIGINT UNSIGNED AUTO_INCREMENT,
    name             VARCHAR(45)            NOT NULL,
    description      TEXT(450),
    price            DECIMAL(6, 2) UNSIGNED NOT NULL,
    duration         TINYINT(90) UNSIGNED      NOT NULL,
    create_date      VARCHAR(30)            NOT NULL,
    last_update_date VARCHAR(30)            NOT NULL,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table certificate_base.gift_certificate_tag
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS gift_certificate_tag
(
    id                  BIGINT UNSIGNED AUTO_INCREMENT,
    gift_certificate_id BIGINT UNSIGNED,
    tag_id              BIGINT UNSIGNED,
    PRIMARY KEY (id),
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (id),
    FOREIGN KEY (tag_id) REFERENCES tag (id)
);