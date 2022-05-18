INSERT INTO tags(tag_name)
VALUES ('sea'),('dolphin'),('joy');

insert into gift_certificates(gift_certificate_name,description,price,duration,create_date,last_update_date)
VALUES ('Swimming with dolphins',
        'The cutest swimming lessons in your life',
         '108.13',
         '80',
         '2022-04-11 14:10:20.000000',
         '2022-04-11 14:08:17.000000');

insert into gift_certificates(gift_certificate_name,description,price,duration,create_date,last_update_date)
VALUES ('Swimming with sharks',
        'Not dangerous and very exciting',
         '300.58',
         '60',
         '2022-04-11 14:13:29.000000',
          '2022-04-13 15:15:28.000000');

insert into gift_certificate_tag (gift_certificate_id, tag_id) VALUES (1,1);
insert into gift_certificate_tag (gift_certificate_id, tag_id) VALUES (1,2);
insert into gift_certificate_tag (gift_certificate_id, tag_id) VALUES (1,3);
insert into gift_certificate_tag (gift_certificate_id, tag_id) VALUES (2,1);
insert into gift_certificate_tag (gift_certificate_id, tag_id) VALUES (2,3);

insert into users(user_name)
VALUES ('Sofiya'),('Anna');

insert into orders(price,purchase_time,user_id)
VALUES (108.13, '2022-05-07 10:14:13', 1),
       (408.17, '2022-05-07 12:18:44', 2);

insert into order_certificate(order_id,gift_certificate_id)
VALUES(1,1),
      (1,1),
      (2,1),
      (2,2);






