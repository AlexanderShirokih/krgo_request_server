INSERT INTO users (username, password, enabled)
VALUES ('test_user', '$2b$10$76IWUXeaFKaE89XIJZOy../3IHN.0gqG87KI235lWW37FmU3SoXne', true),
       ('test_admin', '$2b$10$76IWUXeaFKaE89XIJZOy../3IHN.0gqG87KI235lWW37FmU3SoXne', true);

INSERT INTO authorities (username, authority)
VALUES ('test_user', 'USER'),
       ('test_admin', 'ADMIN');