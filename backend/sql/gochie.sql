CREATE TABLE vdef.gochie_notification(
     id BIGINT PRIMARY KEY AUTO_INCREMENT,
     userId BIGINT NOT NULL DEFAULT 0, #0 is notification for all users
     content VARCHAR(2048),
     type varchar(100),
     createTime BIGINT NOT NULL
);

CREATE TABLE vdef.gochie_notification_subscribe(
  userId BIGINT NOT NULL ,
  exceptTypes VARCHAR(2048) NOT NULL DEFAULT '[]',
  channels VARCHAR(50) NOT NULL DEFAULT '["phone","zalo","messenger","telegram","email"]'
);

CREATE TABLE vdef.gochie_wallet(
    userId BIGINT NOT NULL primary key ,
    balance_coin BIGINT,
    balance_diamond BIGINT
);
