create table messages
(
    id      serial primary key,
    message varchar(255),
    user_name varchar(45),
    foreign key (user_name) references users (name) on delete cascade
);