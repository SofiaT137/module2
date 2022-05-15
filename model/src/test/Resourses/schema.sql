CREATE SCHEMA certificate_base;

CREATE TABLE tags (
  id bigint unsigned NOT NULL AUTO_INCREMENT,
  tag_name varchar(35) NOT NULL UNIQUE,
  PRIMARY KEY (id)
);

CREATE TABLE users (
  id bigint unsigned NOT NULL AUTO_INCREMENT,
  user_name varchar(25) NOT NULL unique,
  PRIMARY KEY (id)
);

CREATE TABLE gift_certificates (
  id bigint unsigned NOT NULL AUTO_INCREMENT,
  gift_certificate_name varchar(45) NOT NULL,
  description text(450),
  price decimal(6,2) NOT NULL,
  duration int NOT NULL,
  create_date timestamp(6) NOT NULL,
  last_update_date timestamp(6) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE gift_certificate_tag (
  gift_certificate_id bigint unsigned NOT NULL,
  tag_id bigint unsigned NOT NULL,
  PRIMARY KEY (gift_certificate_id,tag_id),
  CONSTRAINT gift_certificate_fk FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificates (id),
  CONSTRAINT tag_fk FOREIGN KEY (tag_id) REFERENCES tags (id)
);

CREATE TABLE orders (
  id bigint unsigned NOT NULL AUTO_INCREMENT,
  price decimal(8,2) NOT NULL,
  purchase_time timestamp NOT NULL,
  user_id bigint unsigned DEFAULT NULL,
  PRIMARY KEY (id),
  CONSTRAINT orders_ibfk_1 FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE order_certificate (
  id bigint unsigned NOT NULL AUTO_INCREMENT,
  order_id bigint unsigned NOT NULL,
  gift_certificate_id bigint unsigned NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT order_certificate_ibfk_1 FOREIGN KEY (order_id) REFERENCES orders (id),
  CONSTRAINT order_certificate_ibfk_2 FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificates (id)
);