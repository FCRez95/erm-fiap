create table users (
    id bigint not null auto_increment,
    name varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    type varchar(8) not null,

    primary key (id)
);