-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.17-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             8.0.0.4396
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table uma.clazzes
DROP TABLE IF EXISTS `clazzes`;
CREATE TABLE IF NOT EXISTS `clazzes` (
  `id` varchar(255) NOT NULL,
  `course_id` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `ended_at` datetime DEFAULT NULL,
  `room` varchar(255) DEFAULT NULL,
  `started_at` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `teacher_id` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4me0k9kach5poc785tr3uqnid` (`course_id`),
  KEY `FKmn8e9wv0mfi5cek6777eig2d0` (`teacher_id`),
  CONSTRAINT `FK4me0k9kach5poc785tr3uqnid` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`),
  CONSTRAINT `FKmn8e9wv0mfi5cek6777eig2d0` FOREIGN KEY (`teacher_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table uma.clazzes: ~0 rows (approximately)
DELETE FROM `clazzes`;
/*!40000 ALTER TABLE `clazzes` DISABLE KEYS */;
/*!40000 ALTER TABLE `clazzes` ENABLE KEYS */;


-- Dumping structure for table uma.clazzes_students
DROP TABLE IF EXISTS `clazzes_students`;
CREATE TABLE IF NOT EXISTS `clazzes_students` (
  `id` varchar(255) NOT NULL,
  `clazz_id` varchar(255) DEFAULT NULL,
  `student_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKa9c82blvuu08cu1yboekhqe0r` (`clazz_id`,`student_id`),
  KEY `FKis2euftl2jll3e9ot393b2d8p` (`student_id`),
  CONSTRAINT `FK3mivflk6durd7t9yu2el0tb9` FOREIGN KEY (`clazz_id`) REFERENCES `clazzes` (`id`),
  CONSTRAINT `FKis2euftl2jll3e9ot393b2d8p` FOREIGN KEY (`student_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table uma.clazzes_students: ~0 rows (approximately)
DELETE FROM `clazzes_students`;
/*!40000 ALTER TABLE `clazzes_students` DISABLE KEYS */;
/*!40000 ALTER TABLE `clazzes_students` ENABLE KEYS */;


-- Dumping structure for table uma.courses
DROP TABLE IF EXISTS `courses`;
CREATE TABLE IF NOT EXISTS `courses` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `department_id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `num_credits` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsv2mdywju86wq12x4did4xd78` (`department_id`),
  CONSTRAINT `FKsv2mdywju86wq12x4did4xd78` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table uma.courses: ~0 rows (approximately)
DELETE FROM `courses`;
/*!40000 ALTER TABLE `courses` DISABLE KEYS */;
/*!40000 ALTER TABLE `courses` ENABLE KEYS */;


-- Dumping structure for table uma.departments
DROP TABLE IF EXISTS `departments`;
CREATE TABLE IF NOT EXISTS `departments` (
  `id` varchar(255) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `university_id` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjn5iikgy1jbuvs7s2caxcrleb` (`university_id`),
  CONSTRAINT `FKjn5iikgy1jbuvs7s2caxcrleb` FOREIGN KEY (`university_id`) REFERENCES `universities` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table uma.departments: ~0 rows (approximately)
DELETE FROM `departments`;
/*!40000 ALTER TABLE `departments` DISABLE KEYS */;
/*!40000 ALTER TABLE `departments` ENABLE KEYS */;


-- Dumping structure for table uma.departments_teachers
DROP TABLE IF EXISTS `departments_teachers`;
CREATE TABLE IF NOT EXISTS `departments_teachers` (
  `id` varchar(255) NOT NULL,
  `department_id` varchar(255) DEFAULT NULL,
  `teacher_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKgc0sohx0us0juii9m1puu95op` (`department_id`,`teacher_id`),
  KEY `FK3s8xafap80i815iraxs9sdnti` (`teacher_id`),
  CONSTRAINT `FK3s8xafap80i815iraxs9sdnti` FOREIGN KEY (`teacher_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKinuu3yko9wrj9kruf7c4a6yh4` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table uma.departments_teachers: ~0 rows (approximately)
DELETE FROM `departments_teachers`;
/*!40000 ALTER TABLE `departments_teachers` DISABLE KEYS */;
/*!40000 ALTER TABLE `departments_teachers` ENABLE KEYS */;


-- Dumping structure for table uma.domains
DROP TABLE IF EXISTS `domains`;
CREATE TABLE IF NOT EXISTS `domains` (
  `id` varchar(255) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table uma.domains: ~3 rows (approximately)
DELETE FROM `domains`;
/*!40000 ALTER TABLE `domains` DISABLE KEYS */;
INSERT INTO `domains` (`id`, `code`, `name`, `type`) VALUES
	('1', 'VNI', 'Vietnam', 'Country'),
	('2', 'CAN', 'Canada', 'Country'),
	('3', 'USA', 'United States of America', 'Country');
/*!40000 ALTER TABLE `domains` ENABLE KEYS */;


-- Dumping structure for table uma.roles
DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table uma.roles: ~4 rows (approximately)
DELETE FROM `roles`;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (`id`, `name`) VALUES
	('1', 'ROLE_ADMIN'),
	('2', 'ROLE_USER'),
	('3', 'ROLE_STUDENT'),
	('4', 'ROLE_TEACHER');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;


-- Dumping structure for table uma.universities
DROP TABLE IF EXISTS `universities`;
CREATE TABLE IF NOT EXISTS `universities` (
  `id` varchar(255) NOT NULL,
  `country_id` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5yvtrokrvm0jumlnecjia46lr` (`country_id`),
  CONSTRAINT `FK5yvtrokrvm0jumlnecjia46lr` FOREIGN KEY (`country_id`) REFERENCES `domains` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table uma.universities: ~0 rows (approximately)
DELETE FROM `universities`;
/*!40000 ALTER TABLE `universities` DISABLE KEYS */;
/*!40000 ALTER TABLE `universities` ENABLE KEYS */;


-- Dumping structure for table uma.users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `discriminator` varchar(31) NOT NULL,
  `id` varchar(255) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `department_id` varchar(255) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `degree` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsbg59w8q63i0oo53rlgvlcnjq` (`department_id`),
  CONSTRAINT `FKsbg59w8q63i0oo53rlgvlcnjq` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table uma.users: ~1 rows (approximately)
DELETE FROM `users`;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`discriminator`, `id`, `age`, `created_at`, `email`, `name`, `password`, `token`, `updated_at`, `VERSION`, `department_id`, `year`, `degree`) VALUES
	('USER', '17fb512c-4a64-4b9d-8a13-d9c8b28c7fd3', 1, '2017-04-02 16:37:09', 'homer@3s', 'U1', '$2a$12$RwGGCqWkjZ3yFrlx4pR4qOBj17nSqozuMB9708U/47JxfdtpClnHS', NULL, '2017-04-02 16:37:09', 0, NULL, NULL, NULL),
	('USER', '81b17890-ff51-4180-a4ed-28a74cfae3cd', 66, '2017-03-04 20:47:58', 'admin@3s', 'Admin', '$2a$12$TQPs4NOccDydGr5LYPhXg.K.nXOeNqU4oUsFJi59fTAsNPhl.wcLu', NULL, '2017-04-02 16:29:42', 1, NULL, NULL, NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;


-- Dumping structure for table uma.users_roles
DROP TABLE IF EXISTS `users_roles`;
CREATE TABLE IF NOT EXISTS `users_roles` (
  `id` varchar(255) NOT NULL,
  `role_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKq3r1u8cne2rw2hkr899xuh7vj` (`user_id`,`role_id`),
  KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`),
  CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table uma.users_roles: ~1 rows (approximately)
DELETE FROM `users_roles`;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` (`id`, `role_id`, `user_id`) VALUES
	('5a2f6dee-e64a-48ce-a745-0ffc59d6be7d', '1', '17fb512c-4a64-4b9d-8a13-d9c8b28c7fd3'),
	('bf3aebbe-6956-49ff-ade1-c83286c71937', '2', '17fb512c-4a64-4b9d-8a13-d9c8b28c7fd3'),
	('141b1142-c370-4946-b786-62dfcb529d13', '1', '81b17890-ff51-4180-a4ed-28a74cfae3cd');
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
