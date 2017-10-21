CREATE SCHEMA housing;
USE housing;

CREATE TABLE `student` (
  `id_student` INT,
  `name`VARCHAR(25),
  `surname` VARCHAR(50),
  `gpa` DECIMAL(3,2),
  `age` INT,
  `gender` CHAR(1),
  `year` VARCHAR(10),
  `athletic` BOOLEAN,
  `scholarship` BOOLEAN,
  primary key (`id_student`)
) ENGINE = InnoDB;

CREATE TABLE `building` (
  `id_building` CHAR(3),
  `name` VARCHAR(45),
  `gender` CHAR(1),
  `rooms` INT,
  `slots` INT,
  primary key (`id_building`)
) ENGINE = InnoDB;

CREATE TABLE `room` (
  `id_room` VARCHAR(10),
  `capacity` INT,
  `cost` INT,
  `BUILDING`CHAR(3),
  primary key (`id_room`)
) ENGINE = InnoDB;

ALTER TABLE `room`
ADD CONSTRAINT `BUILDING`
FOREIGN KEY (`BUILDING`)
REFERENCES `BUILDING` (`id_building`);
