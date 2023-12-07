create table users (
    id bigint primary key auto_increment not null,
    name varchar(55) not null,
    balance double not null
);

create table user_transaction (
    id bigint primary key auto_increment not null,
    user_id bigint not null,
    amount double not null,
    transaction_date datetime not null,
    foreign key(user_id) references users(id) on delete cascade
);

insert into users (name, balance) values ('leonel ka', 25000);
insert into users (name, balance) values ('leona', 80000);
insert into users (name, balance) values ('Genji', 10500);