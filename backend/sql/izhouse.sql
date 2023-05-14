CREATE TABLE vdef.iz_profile
(
    id BIGINT PRIMARY KEY,
    phone_number VARCHAR(2048),
    name VARCHAR(2048),
    avatar VARCHAR(2048),
    details JSON /* gender, date_of_birth, address, CCCD, job, FB, Tele */
);

CREATE TABLE vdef.iz_house
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    type int, /* 0: nha nguyen can, 1: phong tro */
    name VARCHAR(2048),
    address VARCHAR(2048),
    status int, /*-1: deleted, 1: rented, 0: empty */
    area float,
    description VARCHAR(2048),
    image VARCHAR(2048),
    homeowner_id int,
    renter_name VARCHAR(2048),
    renter_phone_number VARCHAR(32),
    price int,
    num_of_rooms int,
    num_of_rooms_rented int,
    num_of_rooms_posted int,
    is_posted int, /* 0: chua duoc dang, 1: da duoc dang*/
    created_time BIGINT
);

CREATE TABLE vdef.iz_room
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    house_id int,
    name VARCHAR(2048),
    price int,
    status int,/* 0: empty, 1: rented, -1: deleted */
    area float,
    description VARCHAR(2048),
    image VARCHAR(2048),
    renter_name VARCHAR(2048),
    renter_phone_number VARCHAR(32),
    is_posted int, /* 0: chua duoc dang, 1: da duoc dang*/
    created_time BIGINT
);

CREATE TABLE vdef.iz_post
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    user_id BIGINT,
    type int, /* 0: nha nguyen can, 1: phong tro */
    house_id BIGINT,
    room_id BIGINT,
    title VARCHAR(2048),
    address VARCHAR(2048),
    price int,
    area float,
    description VARCHAR(2048),
    image VARCHAR(2048),
    is_registered int, /* 0: false, 1: true */
    status int, /* 0: hienthi, 1: da an */
    created_time BIGINT
);

CREATE TABLE vdef.iz_request
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    post_id BIGINT,
    user_id BIGINT,
    user_name VARCHAR(2048),
    user_phone_number VARCHAR(32),
    type int, /* 0: nha nguyen can, 1: phong tro */
    house_id BIGINT,
    room_id BIGINT,
    title VARCHAR(2048),
    address VARCHAR(2048),
    price int,
    area float,
    description VARCHAR(2048),
    image VARCHAR(2048),
    status INT,/* 0: hien thi, 1: da an*/
    created_time BIGINT,
    request_created_time BIGINT,
    request_status INT /* 0: chua dc xac nhan, 1: da dc xac nhan */
);

CREATE TABLE vdef.iz_saved_post
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    post_id BIGINT,
    user_id BIGINT,
    type int, /* 0: nha nguyen can, 1: phong tro */
    house_id BIGINT,
    room_id BIGINT,
    title VARCHAR(2048),
    address VARCHAR(2048),
    price int,
    area float,
    description VARCHAR(2048),
    image VARCHAR(2048),
    status INT,/* 0: hien thi, 1: da an*/
    created_time BIGINT
);

CREATE TABLE vdef.iz_notification
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    user_id BIGINT,
    type INT,/* 0: chu nha, 1: nguoi thue */
    post_id BIGINT,
    name VARCHAR(2048),
    phone_number VARCHAR(2048),
    content VARCHAR(2048),
    created_time BIGINT
)

