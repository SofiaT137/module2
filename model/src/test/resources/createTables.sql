CREATE SCHEMA certificate_base;

set mode MySQL;

CREATE TABLE gift_certificate (
  gift_certificate_id bigint primary key NOT NULL AUTO_INCREMENT,
  gift_certificate_name varchar(45) NOT NULL,
  description text(300),
  price decimal(6,2)  NOT NULL,
  duration integer  NOT NULL,
  create_date timestamp(6) NOT NULL,
  last_update_date timestamp(6) NOT NULL
);


CREATE TABLE tag (
  tag_id bigint  NOT NULL AUTO_INCREMENT,
  tag_name varchar(25) NOT NULL UNIQUE,
  PRIMARY KEY (tag_id)
);


CREATE TABLE gift_certificate_tag (
  gift_certificate_id bigint,
  tag_id bigint,
  CONSTRAINT gift_certificate_tag_fk1
  FOREIGN KEY (gift_certificate_id) REFERENCES gift_certificate (gift_certificate_id)
  ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT gift_certificate_tag_fk2
  FOREIGN KEY (tag_id) REFERENCES tag (tag_id)
  ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT pk_gift_certificate_tag primary key (gift_certificate_id,tag_id)
);