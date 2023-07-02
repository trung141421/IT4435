CREATE TABLE room.user
(
    id BIGINT PRIMARY KEY NOT NULL,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(2048) NOT NULL
);

CREATE TABLE room.room
(
    id BIGINT PRIMARY KEY NOT NULL,
    userid BIGINT,
    name VARCHAR(2048),
    address VARCHAR(2048),
    description VARCHAR(2048),
    price BIGINT ,
    renter_name VARCHAR(2048)
);
