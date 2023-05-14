CREATE TABLE vdef.wallet
(
    userId BIGINT PRIMARY KEY NOT NULL,
    username VARCHAR(20) NOT NULL,
    balance DOUBLE,
    status INT
);

CREATE TABLE vdef.wallet_exchange
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    userId BIGINT,
    username VARCHAR(50),
    service VARCHAR(50),
    exchange DOUBLE,
    balance DOUBLE,
    detail text, #Json string includes other relate information IF necessary
    createTime BIGINT
);

DROP procedure IF EXISTS `add_wallet_balance`;
CREATE DEFINER=`vdef`@`%` PROCEDURE `add_wallet_balance`(
    IN in_userId BIGINT,
    IN in_username VARCHAR(50),
    IN in_amount DOUBLE,
    IN in_service VARCHAR(50),
    IN in_reference VARCHAR(50),
    IN in_detail VARCHAR(2048),
    IN in_createTime BIGINT,
    OUT out_balance DOUBLE,
    OUT out_exchange_id BIGINT
)

BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
    DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
    START TRANSACTION;
    SELECT balance FROM vdef.wallet
    WHERE userId = in_userId FOR UPDATE;
    UPDATE vdef.wallet SET balance = balance + in_amount
    WHERE  userId = in_userId;
    SET out_balance = (SELECT balance FROM vdef.wallet WHERE
            userId = in_userId);
    INSERT INTO vdef.wallet_exchange (userId, username, service,
                                      exchange, balance, reference, detail, createTime)
    VALUES (in_userId, in_username, in_service, in_amount,
            out_balance, in_reference, in_detail, in_createTime);
    SET out_exchange_id = LAST_INSERT_ID();
    COMMIT;
END;
DROP procedure IF EXISTS `sub_wallet_balance`;
CREATE DEFINER=`vdef`@`%` PROCEDURE `sub_wallet_balance`(
    IN in_userId BIGINT,
    IN in_username VARCHAR(50),
    IN in_amount DOUBLE,
    IN in_service VARCHAR(50),
    IN in_reference VARCHAR(50),
    IN in_detail VARCHAR(2048),
    IN in_createTime BIGINT,
    OUT out_balance DOUBLE,
    OUT out_exchange_id BIGINT
)

BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
    DECLARE EXIT HANDLER FOR SQLWARNING ROLLBACK;
    START TRANSACTION;
    SELECT balance FROM vdef.wallet
    WHERE userId = in_userId FOR UPDATE;

    UPDATE vdef.wallet SET balance = balance - in_amount
    WHERE  userId = in_userId;
    SET out_balance = (SELECT balance FROM vdef.wallet WHERE
            userId = in_userId);
    IF (out_balance < 0) THEN
    SELECT 'err' as error;
    else
    INSERT INTO vdef.wallet_exchange (userId, username, service,
                                      exchange, balance, reference, detail, createTime)
    VALUES (in_userId, in_username, in_service, in_amount,
            out_balance, in_reference, in_detail, in_createTime);
    SET out_exchange_id = LAST_INSERT_ID();
    COMMIT;
    END IF;
END;