CREATE TABLE vdef.wc_profile
(
    userId      BIGINT PRIMARY KEY,
    nickName VARCHAR(30) NOT NULL UNIQUE,
    phoneNumber VARCHAR(15) NOT NULL UNIQUE,
    country     VARCHAR(50),
    city        VARCHAR(50),
    district    VARCHAR(50),
    role        INT         NOT NULL DEFAULT 0, #0: customer, 1: provider
    gender      INT,
    status      INT,        #online:1, offline:0
    detail      JSON        #phoneNumber varchar(15), exactlyAddress varchar(50), language varchar(50),  email varchar(30),
                          -- gender int, age int
);
drop table vdef.wc_profile;
INSERT INTO wc_profile (userId, phoneNumber, role) VALUE (1, 1, 1);
INSERT INTO wc_profile (userId, phoneNumber, role) VALUE (2, 2, 1);
INSERT INTO wc_profile (userId, phoneNumber, role) VALUE (3, 3, 0);

CREATE TABLE vdef.wc_provider
(
    userId        BIGINT PRIMARY KEY,
    createService TEXT,
    dateOfBirth   VARCHAR(50),
    detail        JSON #identificationNumber int, identificationPhotoFront text, identificationPhotoBack text, workPermitNumber int
                     -- permanentAddress varchar(50),
);
INSERT INTO wc_provider (userId, createService, dateOfBirth) VALUE (1, 1, 1);
INSERT INTO wc_provider (userId, createService, dateOfBirth) VALUE (2, 2, 2);

CREATE TABLE vdef.wc_service
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(50) NOT NULL,
    type            INT         NOT NULL,
    userId          BIGINT      NOT NULL,
    workingHour     VARCHAR(30),
    workingDay      VARCHAR(10),
    status          INT, #0: not pass test, 1 : pass test
    letCustomerBook INT, #0:no,1:yes
    detail          JSON #avatar(varchar),coverImage(varchar),intro(varchar),option (varchar), image(text)
);
INSERT INTO wc_service(name, type, userId) VALUE (1, 1, 1);
INSERT INTO wc_service(name, type, userId) VALUE (2, 2, 2);
CREATE TABLE vdef.wc_servicePack
(
    id        INT  NOT NULL PRIMARY KEY AUTO_INCREMENT,
    serviceId INT  NOT NULL,
    price     INT  NOT NULL,
    hour      INT  NOT NULL,
    include   TEXT NOT NULL DEFAULT ''
);
INSERT INTO wc_servicePack(serviceId, price, hour) VALUE (1, 1, 1);
INSERT INTO wc_servicePack(serviceId, price, hour) VALUE (1, 2, 2);

CREATE TABLE vdef.wc_bookingSchedule
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    providerId    BIGINT,
    customerId    BIGINT,
    serviceId     INT NOT NULL,
    servicePackId INT,
    paymentMethod INT NOT NULL,
    status        INT, #-1 decline, 0 pending, 1 accept, 2 serving, 3 done
    detail        JSON #customerFullName(varchar), customerPhoneNumber(varchar),customerCountry(varchar),
    # customerCity(varchar),customerDistrict(varchar),customerFullAddress(varchar),day(varchar), startAt(timestamp), hour (int), declineReason (int),
    #  option (varchar), note(varchar)
);
INSERT INTO wc_bookingSchedule(providerId, customerId, serviceId, servicePackId, paymentMethod,
                               detail) VALUE (3, 1, 1, 1, 1, 1);
INSERT INTO wc_bookingSchedule(providerId, customerId, serviceId, servicePackId, paymentMethod,
                               detail) VALUE (3, 2, 2, 2, 2, 2);

CREATE TABLE vdef.wc_personalSchedule
(
    id     INT PRIMARY KEY AUTO_INCREMENT,
    userId BIGINT NOT NULL,
    detail JSON #name(varchar), startAt (timestamp), time (int), note (varchar)
);

INSERT INTO wc_personalSchedule(id, userId, detail) VALUE (1, 1, '{
  "name": "1",
  "startAt": "1",
  "int": "1"
}');
INSERT INTO wc_personalSchedule(id, userId, detail) VALUE (2, 2, '{
  "name": "2",
  "startAt": "2",
  "int": "2"
}');

CREATE TABLE vdef.wc_review
(
    scheduleId   INT PRIMARY KEY,
    serviceId    INT,
    customerId   BIGINT NOT NULL,
    serviceStar  INT,
    customerStar INT
);

CREATE TABLE vdef.wc_testQuestion
(
    id      VARCHAR(10) NOT NULL PRIMARY KEY,
    serviceType     INT         NOT NULL,
    questionContent TEXT        NOT NULL,
    answer          TEXT        NOT NULL
);
INSERT INTO wc_testQuestion(serviceType, questionID, questionContent, answer) VALUE (1, 1, 1, 1);
INSERT INTO wc_testQuestion(serviceType, questionID, questionContent, answer) VALUE (1, 2, 2, 2);





