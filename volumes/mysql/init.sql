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
