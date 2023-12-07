drop table if exists users cascade;
drop table if exists user_transaction cascade;

create table users(
    id bigserial primary key,
    name varchar(55) not null,
    balance numeric not null
);

create table user_transaction(
    id bigserial primary key,
    user_id bigserial not null,
    amount numeric not null,
    transaction_date timestamp,
    constraint fk_user_id foreign key(user_id) references users(id) on delete cascade
);

delete from users;

insert into users (name, balance) values ('leonel ka', 25000);
insert into users (name, balance) values ('leona', 80000);
insert into users (name, balance) values ('Genji', 10500);

select * from users;