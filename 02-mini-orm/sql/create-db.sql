DROP DATABASE IF EXISTS `fsd`;
CREATE DATABASE `fsd` CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
USE `fsd`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `password` varchar(80) NOT NULL,
  `age` int(8) DEFAULT NULL,
  `registration_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
