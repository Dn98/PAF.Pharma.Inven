create database final;

use final;
create table medicine(
    id int auto_increment primary key,
    generic_name varchar(100),
    brand_name varchar(100),
    expiration_date date,
    unit_price double default 0.0,
    quantity int default 0
);

insert into medicine(generic_name, brand_name, expiration_date, unit_price, quantity) values
("acetaminophen","Actamin","2020-12-12", 10, 50),
("citalopram ","CeleXA","2020-12-12", 5, 50),
("clindamycin","Actamin","2020-12-12", 4, 50),
("alprazolam ","Xanax","2020-12-12", 6, 50);

create table pharmacist(
    id int auto_increment primary key,
    f_name varchar(100),
    l_name varchar(100),
    contact_no varchar(10),
    address varchar(100),
    email varchar(100) unique,
    pass varchar(100),
    roll int, /* 1:pharmacist 2:patient 3:doctor*/
    created_at timestamp default current_timestamp()
);

insert into pharmacist(f_name, l_name, contact_no, address, email, pass, roll) values
("Sachin","Kodagoda","0765742200","296 B, Motemulla, Yogiyana","duminda.g.k@gmail.com","pass123", 1);