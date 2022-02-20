DROP TABLE IF EXISTS `user_authority_joins`;
DROP TABLE IF EXISTS `citations`;
DROP TABLE IF EXISTS `books`;
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

CREATE TABLE `books`
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

CREATE TABLE `citations`
(
    `id`          INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user`        INT UNSIGNED NOT NULL,
    `book`        INT UNSIGNED NOT NULL,
    `content`     LONGTEXT     NOT NULL,
    `created_by`  INT,
    `modified_by` INT,
    `created_at`  LONG,
    `modified_at` LONG,
    FOREIGN KEY (`user`) REFERENCES `users` (`id`),
    FOREIGN KEY (`book`) REFERENCES `books` (`id`)
);

INSERT INTO users(id, username, email, password, firstname, lastname, created_at, modified_at)
VALUES (1, 'admin', 'admin@mail.com', '{noop}password', 'firstname', 'lastname', UNIX_TIMESTAMP(), 0);

INSERT INTO authorities (id, name, explanation, created_by, modified_by, created_at, modified_at)
VALUES (1, 'ROLE_ADMIN', 'explanation', 1, 1, UNIX_TIMESTAMP(), 0),
       (1, 'ROLE_MODERATOR', 'explanation', 1, 1, UNIX_TIMESTAMP(), 0),
       (1, 'ROLE_EDITOR', 'explanation', 1, 1, UNIX_TIMESTAMP(), 0);

INSERT INTO user_authority_joins (user_id, authority_id)
VALUES (1, 1);
