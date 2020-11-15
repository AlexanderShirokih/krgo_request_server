CREATE TABLE IF NOT EXISTS district
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS street
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(32) NOT NULL,
    district_id INT,
    FOREIGN KEY (district_id) REFERENCES district (id)
);

CREATE TABLE IF NOT EXISTS counter_types
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    name          VARCHAR(24) NOT NULL,
    accuracy      TINYINT(1)  NOT NULL,
    bits          TINYINT(1)  NOT NULL,
    single_phased BOOL        NOT NULL
);

CREATE TABLE IF NOT EXISTS counter_point
(
    counter_type_id INT,
    counter_number  VARCHAR(24) NOT NULL,
    tp_name         VARCHAR(8)  NULL,
    feeder_number   TINYINT(2)  NULL,
    pillar_number   TINYINT(3)  NULL,
    power           FLOAT       NULL,
    check_year      TINYINT(4)  NULL,
    check_quarter   TINYINT(1)  NULL,
    PRIMARY KEY (counter_type_id, counter_number),
    FOREIGN KEY (counter_type_id) REFERENCES counter_types (id),
    CHECK ( check_year >= 1980 AND check_year <= 2100),
    CHECK ( check_quarter > 0 AND check_quarter < 5)
);

CREATE TABLE IF NOT EXISTS account_info
(
    base_id          INT         NOT NULL,
    revision         INT         NOT NULL DEFAULT 0,
    name             VARCHAR(80) NOT NULL,
    home_number      VARCHAR(6)  NOT NULL,
    apartment_number VARCHAR(6)  NOT NULL,
    street_id        INT         NOT NULL,
    FOREIGN KEY (street_id) REFERENCES street (id),
    PRIMARY KEY (base_id, revision)
);

CREATE TABLE IF NOT EXISTS account_info_to_counter_point
(
    id                    INT         NOT NULL AUTO_INCREMENT,
    account_id            INT         NOT NULL,
    account_rev           INT         NOT NULL,
    counter_type_id       INT         NOT NULL,
    counting_point_number VARCHAR(24) NOT NULL,

    PRIMARY KEY (id),
    UNIQUE KEY (account_id, account_rev, counter_type_id, counting_point_number),
    FOREIGN KEY (account_id, account_rev) REFERENCES account_info (base_id, revision),
    FOREIGN KEY (counter_type_id, counting_point_number) REFERENCES counter_point (counter_type_id, counter_number)
);

CREATE TABLE IF NOT EXISTS request_type
(
    id         INT(3) AUTO_INCREMENT,
    short_name VARCHAR(16) NOT NULL UNIQUE,
    full_name  VARCHAR(64) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS request
(
    id                          INT AUTO_INCREMENT,
    additional                  VARCHAR(80) NOT NULL DEFAULT '',
    reason                      VARCHAR(64) NOT NULL DEFAULT '',
    type_id                     INT(3)      NOT NULL,
    account_id                  INT         NULL,
    account_rev                 INT         NULL,
    counting_point_reference_id INT         NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (account_id, account_rev) REFERENCES account_info (base_id, revision),
    FOREIGN KEY (counting_point_reference_id) REFERENCES account_info_to_counter_point (id),
    FOREIGN KEY (type_id) REFERENCES request_type (id)
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

CREATE TABLE IF NOT EXISTS users
(
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(50) NOT NULL,
    enabled  BOOL        NOT NULL
);

CREATE TABLE IF NOT EXISTS authorities
(
    username  VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username),
    UNIQUE (username, authority)
);