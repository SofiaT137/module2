set mode MySQL;

insert into tag(tag_name) VALUES ('sea'),('dolphin'),('joy');

insert into gift_certificate(gift_certificate_name,description,price,duration,create_date,last_update_date)
VALUES ('Swimming with dolphins', 'The cutest swimming lessons in your life', '108.13', '80', '2022-04-11 14:10:20.000000', '2022-04-11 14:08:17.000000');
insert into gift_certificate(gift_certificate_name,description,price,duration,create_date,last_update_date)
VALUES ('Swimming with sharks', 'Not dangerous and very exciting', '300.58', '60', '2022-04-11 14:13:29.000000', '2022-04-13 15:15:28.000000');

insert into gift_certificate_tag (gift_certificate_id, tag_id) VALUES (1,1);
insert into gift_certificate_tag (gift_certificate_id, tag_id) VALUES (1,2);
insert into gift_certificate_tag (gift_certificate_id, tag_id) VALUES (1,3);
insert into gift_certificate_tag (gift_certificate_id, tag_id) VALUES (2,1);
insert into gift_certificate_tag (gift_certificate_id, tag_id) VALUES (2,3);

