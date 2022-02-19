DROP TABLE IF EXISTS `user_authority_joins`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `authors`;

CREATE TABLE `users`
(
    `id`          INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `username`    VARCHAR(32)  NOT NULL UNIQUE,
    `email`       VARCHAR(256) NOT NULL UNIQUE,
    `password`    VARCHAR(256) NOT NULL,
    `firstname`   VARCHAR(64),
    `lastname`    VARCHAR(64),
    `created_at`  LONG,
    `modified_at` LONG
);

CREATE TABLE `authorities`
(
    `id`          INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`        VARCHAR(32)  NOT NULL UNIQUE,
    `explanation` VARCHAR(256) DEFAULT NULL,
    `created_by`  INT,
    `modified_by` INT,
    `created_at`  LONG,
    `modified_at` LONG
);

CREATE TABLE `user_authority_joins`
(
    `user_id`      INT UNSIGNED NOT NULL,
    `authority_id` INT UNSIGNED NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    FOREIGN KEY (`authority_id`) REFERENCES `authorities` (`id`)
);

CREATE TABLE `authors`
(
    `id`          INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name`        VARCHAR(64)  NOT NULL,
    `surname`     VARCHAR(64)  NOT NULL,
    `biography`   MEDIUMTEXT,
    `nation`      VARCHAR(64),
    `birth_year`  INT UNSIGNED,
    `death_year`  INT UNSIGNED,
    `created_by`  INT,
    `modified_by` INT,
    `created_at`  LONG,
    `modified_at` LONG
);

CREATE TABLE books
(
    `id`           INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `isbn`         VARCHAR(32)  NOT NULL UNIQUE,
    `author`       INT UNSIGNED NOT NULL,
    `name`         VARCHAR(64)  NOT NULL,
    `explanation`  MEDIUMTEXT,
    `release_year` INT UNSIGNED,
    `page_count`   INT UNSIGNED,
    `created_by`   INT,
    `modified_by`  INT,
    `created_at`   LONG,
    `modified_at`  LONG,
    UNIQUE (`author`, `name`),
    FOREIGN KEY (`author`) REFERENCES `authors` (`id`)
);

INSERT INTO users(id, username, email, password, firstname, lastname, created_at, modified_at)
VALUES (1, 'username-01', 'email-01@mail.com', '{noop}password', 'firstname-01', 'lastname-01', UNIX_TIMESTAMP(), 0),
       (2, 'username-02', 'email-02@mail.com', '{noop}password', 'firstname-02', 'lastname-02', UNIX_TIMESTAMP(), 0),
       (3, 'username-03', 'email-03@mail.com', '{noop}password', 'firstname-03', 'lastname-03', UNIX_TIMESTAMP(), 0),
       (4, 'username-04', 'email-04@mail.com', '{noop}password', 'firstname-04', 'lastname-04', UNIX_TIMESTAMP(), 0),
       (5, 'username-05', 'email-05@mail.com', '{noop}password', 'firstname-05', 'lastname-05', UNIX_TIMESTAMP(), 0);

INSERT INTO authorities (id, name, explanation, created_by, modified_by, created_at, modified_at)
VALUES (1, 'admin', 'admin explanation', 1, 1, UNIX_TIMESTAMP(), 0);

INSERT INTO user_authority_joins (user_id, authority_id)
VALUES (1, 1);

INSERT INTO authors(id, name, surname, biography, nation, birth_year, death_year, created_by, modified_by, created_at,
                    modified_at)
VALUES (1, 'name', 'surname', 'biography', 'nation', 1000, 1100, 1, 1, UNIX_TIMESTAMP(), 0);

INSERT INTO books(id, isbn, author, name, explanation, release_year, page_count, created_by, modified_by, created_at,
                  modified_at)
VALUES (1, 'isbn', 1, 'name', 'explanation', 1050, 700, 1, 1, UNIX_TIMESTAMP(), 0);
