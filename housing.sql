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

INSERT INTO building(id_building, name, gender, rooms, slots) values('COW', 'Cowart Hall', 'F', 60, 120);
INSERT INTO building(id_building, name, gender, rooms, slots) values('HAM', 'Hamil Hall', 'F', 74, 147);
/*Two for Four Residents, three for two residents, two for single.*/
INSERT INTO building(id_building, name, gender, rooms, slots) values('PAD', 'Paden Wellness House', 'F', 7, 16);
INSERT INTO building(id_building, name, gender, rooms, slots) values('GAR', 'Gardner Hall', 'M', 32, 63);
/*Same with Paden Wellness*/
INSERT INTO building(id_building, name, gender, rooms, slots) values('HIL', 'Hilcrest House', 'M', 7, 16);
/*20 for single, 30 for double, 36 for four residents*/
INSERT INTO building(id_building, name, gender, rooms, slots) values('CLE', 'Clements Hall', 'C', 86, 224);
INSERT INTO building(id_building, name, gender, rooms, slots) values('HON', 'Honors Cottage', 'C', 10, 10);
/*90% are single and 10% are double*/
INSERT INTO building(id_building, name, gender, rooms, slots) values('NRH', 'New Residence Hall', 'C', 100, 110);
/*80 for four person and 25 for two person, 6 for single*/
INSERT INTO building(id_building, name, gender, rooms, slots) values('NEW', 'Newman Center','C', 100, 376);
/*10 for single, 20 for double, 30 for four residents*/
INSERT INTO building(id_building, name, gender, rooms, slots) values('PAC', 'Pace Hall', 'C', 60, 170);
INSERT INTO building(id_building, name, gender, rooms, slots) values('SHA', 'Shackerlford Hall', 'C', 60, 170);
/*20 for double and 20 for four residents*/
INSERT INTO building(id_building, name, gender, rooms, slots) values('TV1', 'Trojan Villiage', 'C', 40, 120);
INSERT INTO building(id_building, name, gender, rooms, slots) values('TV2', 'Trojan Villiage', 'C', 40, 120);
INSERT INTO building(id_building, name, gender, rooms, slots) values('TV3', 'Trojan Villiage', 'C', 40, 120);
INSERT INTO building(id_building, name, gender, rooms, slots) values('TV4', 'Trojan Villiage', 'C', 40, 120);
