CREATE TABLE IF NOT EXISTS zone
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS street
(
    id      INT PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(32) NOT NULL,
    zone_id INT,
    FOREIGN KEY (zone_id) REFERENCES zone (id)
);

CREATE TABLE IF NOT EXISTS counter_types
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(24) NOT NULL,
    accuracy     TINYINT(1)  NOT NULL,
    bits         TINYINT(1)  NOT NULL,
    singlePhased BOOL        NOT NULL
);

CREATE TABLE IF NOT EXISTS counter_point
(
    id              INT         NOT NULL DEFAULT 0,
    revision        INT         NOT NULL DEFAULT 0,
    counter_number  VARCHAR(24) NOT NULL,
    counter_type_id INT,
    tp_name         VARCHAR(8)  NULL,
    fider_number    TINYINT(2)  NULL,
    power           FLOAT       NULL,
    check_year      TINYINT(4)  NOT NULL,
    check_quarter   TINYINT(1)  NOT NULL,
    FOREIGN KEY (counter_type_id) REFERENCES counter_types (id),
    PRIMARY KEY (id, revision),
    CHECK ( check_year >= 1980 AND check_year <= 2100),
    CHECK ( check_quarter > 0 AND check_quarter < 5)
);

CREATE TABLE IF NOT EXISTS account_info
(
    id               INT         NOT NULL,
    revision         INT         NOT NULL DEFAULT 0,
    name             VARCHAR(80) NOT NULL,
    home_number      VARCHAR(6)  NOT NULL,
    apartment_number VARCHAR(6)  NOT NULL,
    street_id        INT         NOT NULL,
    FOREIGN KEY (street_id) REFERENCES street (id),
    PRIMARY KEY (id, revision)
);

CREATE TABLE IF NOT EXISTS account_info_to_counter_point
(
    account_id        INT NOT NULL,
    account_rev       INT NOT NULL,
    counter_point_id  INT NOT NULL,
    counter_point_rev INT NOT NULL,

    PRIMARY KEY (account_id, account_rev, counter_point_id, counter_point_rev),
    FOREIGN KEY (account_id, account_rev) REFERENCES account_info (id, revision),
    FOREIGN KEY (counter_point_id, counter_point_rev) REFERENCES counter_point (id, revision)
);

CREATE TABLE IF NOT EXISTS request_type
(
    id         INT(3) AUTO_INCREMENT,
    short_name VARCHAR(16) NOT NULL,
    full_name  VARCHAR(64) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS request
(
    id         INT AUTO_INCREMENT,
    additional VARCHAR(80) NOT NULL DEFAULT '',
    reason     VARCHAR(64) NOT NULL DEFAULT '',
    type_id    INT(3)      NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (type_id) REFERENCES request_type (id)
);

CREATE TABLE IF NOT EXISTS request_to_account_info
(
    request_id  INT,
    account_id  INT NOT NULL,
    account_rev INT NOT NULL,
    PRIMARY KEY (request_id, account_id, account_rev),
    FOREIGN KEY (request_id) REFERENCES request (id),
    FOREIGN KEY (account_id, account_rev) REFERENCES account_info (id, revision)
);

CREATE TABLE IF NOT EXISTS positions
(
    id   INT(3) AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS employee
(
    id           INT AUTO_INCREMENT,
    name         VARCHAR(80)             NOT NULL,
    position_id  INT(3)                  NOT NULL,
    access_level TINYINT(1)              NOT NULL,
    status       ENUM ('works', 'fired') NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (position_id) REFERENCES positions (id)
);

CREATE TABLE IF NOT EXISTS request_set
(
    id   INT AUTO_INCREMENT,
    name VARCHAR(24) NOT NULL,
    date DATE        NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS assignee
(
    request_set_id INT                              NOT NULL,
    worker_id      INT                              NOT NULL,
    type           ENUM ('member', 'main', 'chief') NOT NULL,
    PRIMARY KEY (request_set_id, worker_id),
    FOREIGN KEY (request_set_id) REFERENCES request_set (id),
    FOREIGN KEY (worker_id) REFERENCES employee (id)
);

CREATE TABLE IF NOT EXISTS work_request_to_request
(
    request_id     INT NOT NULL,
    request_set_id INT NOT NULL,
    PRIMARY KEY (request_id, request_set_id),
    FOREIGN KEY (request_id) REFERENCES request (id),
    FOREIGN KEY (request_set_id) REFERENCES request_set (id)
);

DELIMITER $$

CREATE TRIGGER fetchNextAccountPointRevision
    BEFORE INSERT
    ON counter_point
    FOR EACH ROW
BEGIN
    DECLARE newId INT;
    DECLARE newRevision INT;

    IF new.id = 0 THEN
        SELECT IFNULL((SELECT MAX(id) FROM counter_point), 0) + 1 INTO newId;
        SET newRevision = 1;
    ELSE
        SELECT IFNULL((SELECT MAX(revision) FROM counter_point WHERE id = @lastId), 0) + 1 INTO newRevision;
    END IF;

    SET new.revision = @newRevision;
    SET new.id = @newId;
END $$

CREATE TRIGGER fetchNextAccountRevisionId
    BEFORE INSERT
    ON account_info
    FOR EACH ROW
BEGIN
    DECLARE newRevision INT;

    SELECT IFNULL((SELECT MAX(revision) FROM account_info WHERE id = @lastId), 0) + 1 INTO newRevision;
    SET new.revision = @newRevision;
END $$

DELIMITER ;