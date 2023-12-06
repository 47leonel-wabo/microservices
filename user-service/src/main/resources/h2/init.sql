create table users (
    id bigint primary key auto_increment not null,
    name varchar(55) not null,
    balance double not null
);

create table user_transaction (
    id bigint primary key auto_increment not null,
    user_id bigint references users(id),
    amount double not null,
    transaction_date datetime not null
);