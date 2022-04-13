CREATE SCHEMA certificates;

create table gift_certificate(
    gift_certificate_id bigint primary key auto_increment,
    gift_certificate_name varchar(45),
    description text(450),
    price decimal(6,2),
    duration tinyint(90),
    create_date timestamp(6),
    last_update_date timestamp(6)
);

create table tag(
     tag_id bigint primary key not null auto_increment,
     tag_name not null varchar(25)
);

create table gift_certificate_tag(
    tag_id bigint,
    gift_certificate_id bigint,
    constraint tag_id_foreign_key
    foreign key (tag_id) references tag(tag_id)
    on delete cascade on update cascade,
    constraint gift_certificate_id_foreign_key
    foreign key (gift_certificate_id) references gift_certificate(gift_certificate_id)
    on delete cascade on update cascade
);