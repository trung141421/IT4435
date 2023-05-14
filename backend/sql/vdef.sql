CREATE TABLE vdef.user
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(2048) NOT NULL,
    token VARCHAR(2048),
    role INT,
    status INT,
    createTime BIGINT NOT NULL
);
CREATE UNIQUE INDEX user_username_uindex ON vdef.user (username);
CREATE UNIQUE INDEX user_token_uindex ON vdef.user (token);

CREATE TABLE vdef.gg_user
(
    ggId VARCHAR(128) PRIMARY KEY NOT NULL,
    userId BIGINT UNIQUE NOT NULL,
    gg_token VARCHAR(2048),
    gg_token_expired BIGINT NOT NULL,
    gg_info VARCHAR(2048) -- self formatted json String with related information provided by Google
);

CREATE TABLE vdef.fb_user
(
    fbId VARCHAR(128) PRIMARY KEY NOT NULL,
    userId BIGINT UNIQUE NOT NULL,
    fb_token VARCHAR(2048),
    fb_token_expired BIGINT NOT NULL,
    fb_info VARCHAR(2048) -- self formatted json String with related information provided by Facebook
);

CREATE TABLE vdef.ap_user
(
    email VARCHAR(2048) UNIQUE NOT NULL,
    userId BIGINT UNIQUE NOT NULL,
    ap_access_token VARCHAR(2048),
    ap_access_token_expired BIGINT NOT NULL,
    ap_info VARCHAR(2048) -- self formatted json String with related information provided by Facebook
);

CREATE TABLE vdef.telegram_user
(
    userId BIGINT PRIMARY KEY  NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    telegramId BIGINT,
    phone_number VARCHAR(20) UNIQUE,
    active_code VARCHAR(6) NOT NULL,
    telegram_info VARCHAR(2048) -- self formatted json String with related information provided by Telegram
);


CREATE TABLE vdef.random_otp
(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    userId BIGINT NOT NULL,
    random_otp INT UNIQUE NOT NULL,
    expired_time BIGINT NOT NULL ,
    status INT NOT NULL ,-- default = 0 and used = 1
    type tinyint
);
