create table user(
    id BIGINT primary key AUTO_INCREMENT,
    username varchar(50) not null unique,
    password varchar(100) not null,
    avatar varchar(255),
    created_at datetime default current_timestamp
);

create table article(
    id BIGINT primary key auto_increment,
    title varchar(200) not null,
    content text not null,
    user_id bigint not null,
    category_id bigint,
    create_at datetime default current_timestamp,
    update_at datetime default current_timestamp
);

create table category (
    id bigint primary key auto_increment,
    name varchar(50) not null
);

insert into category (name) values ('技术'),('生活'),('随笔'),('学习'),('游戏');