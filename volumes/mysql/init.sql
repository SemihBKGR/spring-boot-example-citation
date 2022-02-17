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
    `description`       MEDIUMTEXT,
    `creation_time`     LONG,
    `modification_time` LONG,
    FOREIGN KEY (`author`) REFERENCES `users` (`id`),
    UNIQUE (`author`, `title`)
);
