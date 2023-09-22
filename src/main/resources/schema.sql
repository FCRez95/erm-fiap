create table users (
    id bigint not null auto_increment,
    name varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    type varchar(8) not null,
    token_access varchar(512),

    primary key (id)
);

create table campaing_entity (
    id bigint not null auto_increment,
    id_user bigint not null,
    name varchar(255) not null,
    product varchar(64),
    click_auth varchar(512),

    primary key (id),
    foreign key (id_user) references users(id)
);

create table click (
    id bigint not null auto_increment,
    id_campaing bigint not null,
    site_origin varchar(128) not null,
    income DOUBLE PRECISION,
    paid_click tinyint,
    click_token varchar(512),

    primary key (id),
    foreign key (id_campaing) references campaing_entity(id)
)