DROP DATABASE certificate_base;

CREATE DATABASE IF NOT EXISTS certificate_base;

-- -----------------------------------------------------
-- Table certificate_base.tag
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS tag
(
    tag_id       BIGINT UNSIGNED AUTO_INCREMENT,
    tag_name VARCHAR(25) NOT NULL,
    PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Table certificate_base.gift_certificate
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS gift_certificate
(
    gift_certificate_id  BIGINT UNSIGNED AUTO_INCREMENT,
    gift_certificate_name  VARCHAR(45)   NOT NULL,
    description      TEXT(450),
    price            DECIMAL(6, 2) UNSIGNED NOT NULL,
    duration         TINYINT(90) UNSIGNED      NOT NULL,
    create_date      TIMESTAMP(6)            NOT NULL,
    last_update_date TIMESTAMP(6)            NOT NULL,
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
    FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (gift_certificate_id),
    FOREIGN KEY (tag_id) REFERENCES tag (tag_id)
);