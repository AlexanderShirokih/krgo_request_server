INSERT INTO users (username, password, enabled)
VALUES ('test_user', '$2b$10$76IWUXeaFKaE89XIJZOy../3IHN.0gqG87KI235lWW37FmU3SoXne', true),
       ('test_admin', '$2b$10$76IWUXeaFKaE89XIJZOy../3IHN.0gqG87KI235lWW37FmU3SoXne', true);

INSERT INTO authorities (username, authority)
VALUES ('test_user', 'ROLE_USER'),
       ('test_admin', 'ROLE_ADMIN');

-- Default request types
INSERT INTO request_type(short_name, full_name)
VALUES ('замена', 'Замена ПУ'),
       ('вывод', 'Распломбировка'),
       ('опломб.', 'Опломбировка'),
       ('распломб.', 'Распломбировка'),
       ('тех. пров.', 'Проверка схем учёта'),
       ('ЦОП', 'Проверка схем учёта'),
       ('подкл.', 'Подключение'),
       ('откл.', 'Отключение');