CREATE DATABASE  IF NOT EXISTS `analityc`;
USE `analityc`;

DROP TABLE IF EXISTS `statistic`;

CREATE TABLE `statistic` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name_products` varchar(45) NOT NULL,
  `count_visits` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

LOCK TABLES `statistic` WRITE;

INSERT INTO `statistic` VALUES (1,'Orange',28),
                               (2,'Tomato',21),
                               (3,'Melon',21),
                               (4,'Apple',31),
                               (5,'Pasta',25),
                               (6,'Laser Pencil',13),
                               (7,'Note Book',2),
                               (8,'Telephone',5),
                               (9,'CPU',7);

UNLOCK TABLES;
