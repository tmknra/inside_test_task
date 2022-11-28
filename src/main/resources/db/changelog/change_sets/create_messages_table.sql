create table messages
(
    id      serial primary key,
    message varchar(255),
    user_id BIGINT,
    foreign key (user_id) references users (id) on delete cascade
);