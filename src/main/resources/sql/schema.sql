DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `authors`;

CREATE TABLE `users`
(
    `id`                INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `username`          VARCHAR(32)  NOT NULL UNIQUE,
    `email`             VARCHAR(256) NOT NULL UNIQUE,
    `password`          VARCHAR(256) NOT NULL,
    `firstname`         VARCHAR(64),
    `lastname`          VARCHAR(64),
    `creation_time`     LONG,
    `modification_time` LONG
);

CREATE TABLE `authors`
(
    `id`                INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`              VARCHAR(64)  NOT NULL,
    `surname`           VARCHAR(64)  NOT NULL,
    `biography`         MEDIUMTEXT,
    `nation`            VARCHAR(64),
    `birth_year`        INT UNSIGNED,
    `death_year`        INT UNSIGNED,
    `creation_time`     LONG,
    `modification_time` LONG
);

INSERT INTO users(username, email, password, firstname, lastname, creation_time)
VALUES ('username-01', 'email-01@mail.com', '{noop}password', 'firstname-01', 'lastname-01', UNIX_TIMESTAMP()),
       ('username-02', 'email-02@mail.com', '{noop}password', 'firstname-02', 'lastname-02', UNIX_TIMESTAMP()),
       ('username-03', 'email-03@mail.com', '{noop}password', 'firstname-03', 'lastname-03', UNIX_TIMESTAMP()),
       ('username-04', 'email-04@mail.com', '{noop}password', 'firstname-04', 'lastname-04', UNIX_TIMESTAMP()),
       ('username-05', 'email-05@mail.com', '{noop}password', 'firstname-05', 'lastname-05', UNIX_TIMESTAMP());
