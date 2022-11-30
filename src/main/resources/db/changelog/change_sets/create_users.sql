create table users
(
    id   serial primary key,
    name varchar(45) not null unique,
    password varchar(255) not null
);

insert into users(name, password) VALUES ('test', 123456), ('test1', 123456), ('test2', 'qwerty');