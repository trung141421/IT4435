CREATE TABLE godoo_profile
(
    userId               BIGINT primary key,
    nickname             VARCHAR(200),
    picture              VARCHAR(2048),
    date_of_birth        VARCHAR(100),
    zodiac               VARCHAR(100),
    gender               VARCHAR(50),
    introduction         VARCHAR(200),
    relationship         TINYINT,
    additional_information JSON
--                                hobbies VARCHAR(100),height VARCHAR(20),career JSON,education VARCHAR(50),
--                                hometown VARCHAR(200),living_in VARCHAR(200), sexual_orientation VARCHAR(100),drinking VARCHAR(100),
    --                                religion VARCHAR(100), smoking VARCHAR(100),children VARCHAR(100), favourite_place VARCHAR(200),
--                                political VARCHAR(100),language VARCHAR(100), location VARCHAR(200)
);
CREATE TABLE godoo_gift_template
(
    id           INT primary key AUTO_INCREMENT,
    name         VARCHAR(200),
    icon         VARCHAR(2048),
    introduction VARCHAR(1024),
    price        BIGINT
);
CREATE TABLE godoo_gifts
(
    id               BIGINT primary key AUTO_INCREMENT,
    gift_template_id INT,
    time_bought      BIGINT,
    time_gave        BIGINT,
    time_received    BIGINT,
    buyer_id         BIGINT,
    receiver_id      BIGINT,
    message          VARCHAR(2048)
);
CREATE TABLE godoo_relationship
(
    id VARCHAR(50) PRIMARY KEY,
    user_id_1  BIGINT,
    user_id_2  BIGINT,
    relation   TINYINT,
    status_user_1 TINYINT DEFAULT 0,
    status_user_2 TINYINT DEFAULT 0
);
CREATE TABLE godoo_liked_beliked
(
    userId       BIGINT primary key,
    liked_list   TEXT,
    beliked_list TEXT,
    hiden_user   TEXT
);
CREATE TABLE godoo_wallet
(
    userId  BIGINT PRIMARY KEY,
    balance BIGINT NOT NULL DEFAULT 0
);
CREATE TABLE godoo_report
(
    reportId       BIGINT primary key,
    reporter_id    BIGINT,
    be_reported_id BIGINT,
    content        VARCHAR(1024),
    report_time    BIGINT
);
CREATE TABLE godoo_schedule
(
    id          bigint primary key auto_increment,
    theme       varchar(2048),
    title       varchar(200),
    content     varchar(2048),
    time        bigint,
    remind_time bigint,
    address     varchar(400),
    status      tinyint #1: accepted   0:  declined
);
CREATE TABLE godoo_transaction_history
(
    id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    userId  BIGINT,
    title   VARCHAR(200),
    content VARCHAR(2048),
    time    BIGINT
);
CREATE TABLE godoo_notification
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    userId     BIGINT NOT NULL DEFAULT 0, #0 is notification for all users
        content    VARCHAR (2048),
    type       varchar(100),
    create_time BIGINT NOT NULL
);
CREATE TABLE godoo_notification_subscribe
(
    userId      BIGINT        NOT NULL,
    exceptTypes VARCHAR(2048) NOT NULL DEFAULT '[]',
    channels    VARCHAR(50)   NOT NULL DEFAULT '["messenger","telegram"]'
);

