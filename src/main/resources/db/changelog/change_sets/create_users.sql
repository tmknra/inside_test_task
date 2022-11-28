create table users
(
    id   serial primary key,
    name varchar(45) not null unique,
    password varchar(255) not null
);