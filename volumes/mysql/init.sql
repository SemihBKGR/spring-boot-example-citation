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
