-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.22-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.2.0.4947
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for hoang_modeldb
DROP DATABASE IF EXISTS `hoang_modeldb`;
CREATE DATABASE IF NOT EXISTS `hoang_modeldb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `hoang_modeldb`;


-- Dumping structure for table hoang_modeldb.subscriptions
DROP TABLE IF EXISTS `subscriptions`;
CREATE TABLE IF NOT EXISTS `subscriptions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `hostname` varchar(50) NOT NULL DEFAULT '0',
  `user_id` int(11) NOT NULL,
  `subscription_key` varchar(50) NOT NULL,
  `status` varchar(50) NOT NULL,
  `billing_status` varchar(50) DEFAULT NULL,
  `next_billing_date` varchar(50) DEFAULT NULL,
  `last_billing_date` varchar(50) DEFAULT NULL,
  `trial_end_date` varchar(50) DEFAULT NULL,
  `created_at` varchar(50) NOT NULL,
  `updated_at` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table hoang_modeldb.subscriptions: ~2 rows (approximately)
/*!40000 ALTER TABLE `subscriptions` DISABLE KEYS */;
REPLACE INTO `subscriptions` (`id`, `name`, `hostname`, `user_id`, `subscription_key`, `status`, `billing_status`, `next_billing_date`, `last_billing_date`, `trial_end_date`, `created_at`, `updated_at`) VALUES
	(1, 'MySubscription', 'hoang.com', 1, '5475dd13-f4a0-4f7a-9a75-2542a6ad8b13', 'active', NULL, NULL, NULL, NULL, '2016-10-27 10:57:57', '2016-10-27 10:57:57'),
	(2, 'MySubscription1', 'hoang.com', 1, '0181d6db-dbb4-4f3a-9420-396a803e1870', 'active', NULL, NULL, NULL, NULL, '2016-10-27 10:59:00', '2016-10-27 10:59:00');
/*!40000 ALTER TABLE `subscriptions` ENABLE KEYS */;


-- Dumping structure for table hoang_modeldb.users
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `level` int(11) NOT NULL,
  `status` varchar(50) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table hoang_modeldb.users: ~1 rows (approximately)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
REPLACE INTO `users` (`id`, `email`, `name`, `password`, `level`, `status`, `created_at`, `updated_at`) VALUES
	(1, 'hoang.com', 'Hoang', 'testpw', 1, 'active', '2016-10-27 09:35:16', '2016-10-27 09:35:16');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
