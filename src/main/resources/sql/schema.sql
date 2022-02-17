DROP TABLE IF EXISTS `users`;

CREATE TABLE `users`
(
    `id`            INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `username`      VARCHAR(32)  NOT NULL UNIQUE,
    `email`         VARCHAR(256) NOT NULL UNIQUE,
    `password`      VARCHAR(256) NOT NULL,
    `firstname`     VARCHAR(64),
    `lastname`      VARCHAR(64),
    `creation_time` LONG
);

CREATE TABLE `tales`
(
    `id`                INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `author`            INT UNSIGNED NOT NULL,
    `title`             VARCHAR(32)  NOT NULL,
    `description`       LONGTEXT,
    `creation_time`     LONG,
    `modification_time` LONG,
    FOREIGN KEY (`author`) REFERENCES `users` (`id`),
    UNIQUE (`author`, `title`)
);

INSERT INTO users(username, email, password, firstname, lastname, creation_time)
VALUES ('username-01', 'email-01@mail.com', '{noop}password', 'firstname-01', 'lastname-01', UNIX_TIMESTAMP()),
       ('username-02', 'email-02@mail.com', '{noop}password', 'firstname-02', 'lastname-02', UNIX_TIMESTAMP()),
       ('username-03', 'email-03@mail.com', '{noop}password', 'firstname-03', 'lastname-03', UNIX_TIMESTAMP()),
       ('username-04', 'email-04@mail.com', '{noop}password', 'firstname-04', 'lastname-04', UNIX_TIMESTAMP()),
       ('username-05', 'email-05@mail.com', '{noop}password', 'firstname-05', 'lastname-05', UNIX_TIMESTAMP());
