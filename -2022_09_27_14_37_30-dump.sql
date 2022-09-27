-- MySQL dump 10.13  Distrib 8.0.23, for osx10.16 (x86_64)
--
-- Host: 127.0.0.1    Database: hotel
-- ------------------------------------------------------
-- Server version	8.0.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `booking` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `guest_id` bigint NOT NULL,
  `room_id` int NOT NULL,
  `arriving` date NOT NULL,
  `leaving` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `room_id` (`room_id`,`arriving`,`leaving`),
  UNIQUE KEY `guest_id` (`guest_id`,`arriving`,`leaving`),
  CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`guest_id`) REFERENCES `guests` (`id`) ON DELETE CASCADE,
  CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (1,1,4,'2021-12-23','2021-12-30'),(3,3,5,'2021-12-16','2021-12-19');
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `insert_booking` BEFORE INSERT ON `booking` FOR EACH ROW BEGIN
    IF (NEW.arriving >= NEW.leaving) THEN
        signal sqlstate '45000';
    end if;
    #Check, if this guest didn't book this room previously in this dates
    IF (SELECT COUNT(1)
        FROM booking
        WHERE (booking.arriving BETWEEN new.arriving and new.leaving or
               booking.leaving BETWEEN new.arriving and new.leaving)
          and booking.room_id = new.room_id) <> 0 THEN
        signal sqlstate '45000';
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Temporary view structure for view `full_booking`
--

DROP TABLE IF EXISTS `full_booking`;
/*!50001 DROP VIEW IF EXISTS `full_booking`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `full_booking` AS SELECT 
 1 AS `id`,
 1 AS `guest_id`,
 1 AS `first_name`,
 1 AS `last_name`,
 1 AS `guest_phone`,
 1 AS `passport`,
 1 AS `email`,
 1 AS `personal_options`,
 1 AS `room_number`,
 1 AS `room_type`,
 1 AS `total_price`,
 1 AS `guest_arrive`,
 1 AS `guest_leave`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `guests`
--

DROP TABLE IF EXISTS `guests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guests` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(300) NOT NULL,
  `last_name` varchar(300) NOT NULL,
  `phone` varchar(13) NOT NULL,
  `passport` varchar(11) NOT NULL,
  `email` varchar(250) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`),
  UNIQUE KEY `passport` (`passport`),
  UNIQUE KEY `email` (`email`),
  CONSTRAINT `guests_chk_1` CHECK ((length(`first_name`) > 1)),
  CONSTRAINT `guests_chk_2` CHECK ((length(`last_name`) > 3)),
  CONSTRAINT `guests_chk_3` CHECK ((length(`phone`) > 8)),
  CONSTRAINT `guests_chk_4` CHECK ((length(`passport`) > 8))
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guests`
--

LOCK TABLES `guests` WRITE;
/*!40000 ALTER TABLE `guests` DISABLE KEYS */;
INSERT INTO `guests` VALUES (1,'Nastya','Kuzyk','+380506196870','001234634','kuzykanastasija@gmail.com'),(3,'Masha','Goriacheva','+380958919588','001234569','mary@gmail.com'),(12,'Test','Test','+380506195878','444444445','example@mail');
/*!40000 ALTER TABLE `guests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guests_personal_options`
--

DROP TABLE IF EXISTS `guests_personal_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guests_personal_options` (
  `guest_id` bigint NOT NULL,
  `option_id` int NOT NULL,
  KEY `guest_id` (`guest_id`),
  KEY `option_id` (`option_id`),
  CONSTRAINT `guests_personal_options_ibfk_1` FOREIGN KEY (`guest_id`) REFERENCES `guests` (`id`),
  CONSTRAINT `guests_personal_options_ibfk_2` FOREIGN KEY (`option_id`) REFERENCES `personal_options` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guests_personal_options`
--

LOCK TABLES `guests_personal_options` WRITE;
/*!40000 ALTER TABLE `guests_personal_options` DISABLE KEYS */;
INSERT INTO `guests_personal_options` VALUES (1,3),(1,6),(1,7),(1,8),(1,1),(3,1),(3,8),(3,3),(12,2);
/*!40000 ALTER TABLE `guests_personal_options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personal_options`
--

DROP TABLE IF EXISTS `personal_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `personal_options` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(300) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `personal_options_chk_1` CHECK ((length(`name`) < 25))
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personal_options`
--

LOCK TABLES `personal_options` WRITE;
/*!40000 ALTER TABLE `personal_options` DISABLE KEYS */;
INSERT INTO `personal_options` VALUES (1,'Smoking'),(2,'With children'),(3,'With pets'),(4,'Wi-Fi'),(5,'Disabled'),(6,'Breakfast'),(7,'Bar'),(8,'Hairdryer');
/*!40000 ALTER TABLE `personal_options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_option`
--

DROP TABLE IF EXISTS `room_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_option` (
  `room_id` int NOT NULL,
  `option_id` int NOT NULL,
  KEY `room_id` (`room_id`),
  KEY `option_id` (`option_id`),
  CONSTRAINT `room_option_ibfk_1` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`) ON DELETE CASCADE,
  CONSTRAINT `room_option_ibfk_2` FOREIGN KEY (`option_id`) REFERENCES `room_options` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_option`
--

LOCK TABLES `room_option` WRITE;
/*!40000 ALTER TABLE `room_option` DISABLE KEYS */;
INSERT INTO `room_option` VALUES (1,2),(1,5),(2,2),(2,4),(2,5),(3,1),(3,3),(3,4),(3,7),(4,1),(4,2),(4,3),(4,4),(4,7),(5,1),(5,2),(5,3),(5,4),(5,5),(5,7),(6,1),(6,2),(6,3),(6,4),(6,5),(6,6),(6,7),(6,8),(7,2);
/*!40000 ALTER TABLE `room_option` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_options`
--

DROP TABLE IF EXISTS `room_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_options` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(250) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  CONSTRAINT `room_options_chk_1` CHECK ((length(`name`) < 25))
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_options`
--

LOCK TABLES `room_options` WRITE;
/*!40000 ALTER TABLE `room_options` DISABLE KEYS */;
INSERT INTO `room_options` VALUES (8,'Balcony'),(2,'Bed for children'),(6,'Conditioner'),(5,'Fridge'),(3,'Pet friendly'),(1,'Smoke friendly'),(7,'TV'),(4,'Wi-Fi');
/*!40000 ALTER TABLE `room_options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_options_personal_options`
--

DROP TABLE IF EXISTS `room_options_personal_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_options_personal_options` (
  `room_options_id` int NOT NULL,
  `personal_option_id` int NOT NULL,
  UNIQUE KEY `room_options_id` (`room_options_id`,`personal_option_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_options_personal_options`
--

LOCK TABLES `room_options_personal_options` WRITE;
/*!40000 ALTER TABLE `room_options_personal_options` DISABLE KEYS */;
INSERT INTO `room_options_personal_options` VALUES (1,1),(2,2),(3,3),(4,4);
/*!40000 ALTER TABLE `room_options_personal_options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_types`
--

DROP TABLE IF EXISTS `room_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_types` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(80) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type` (`type`),
  CONSTRAINT `room_types_chk_1` CHECK ((length(`type`) > 2)),
  CONSTRAINT `room_types_chk_2` CHECK ((length(`description`) > 10))
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_types`
--

LOCK TABLES `room_types` WRITE;
/*!40000 ALTER TABLE `room_types` DISABLE KEYS */;
INSERT INTO `room_types` VALUES (1,'Standard','standard room'),(2,'Business','large room with office equipment (computer, fax), suitable for work'),(3,'Lux','the most luxurious hotel room, several bedrooms, an office, two or three toilets');
/*!40000 ALTER TABLE `room_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rooms`
--

DROP TABLE IF EXISTS `rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rooms` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type_id` int NOT NULL,
  `price` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `type_id` (`type_id`),
  CONSTRAINT `rooms_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `room_types` (`id`),
  CONSTRAINT `rooms_chk_1` CHECK ((`price` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rooms`
--

LOCK TABLES `rooms` WRITE;
/*!40000 ALTER TABLE `rooms` DISABLE KEYS */;
INSERT INTO `rooms` VALUES (1,1,100),(2,1,110),(3,2,206),(4,2,220),(5,3,300),(6,3,350),(7,1,300);
/*!40000 ALTER TABLE `rooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `full_booking`
--

--
-- Procedure to book a room created
--

CREATE
    definer = root@localhost procedure book_room(IN guest_id int, IN room_id int, IN arrived date, IN leaving date)
BEGIN

    #Check, if this guest didn't book this room previously in this dates
    IF (SELECT COUNT(1) FROM booking WHERE (booking.arrived BETWEEN arrived and leaving or booking.leaving BETWEEN arrived and leaving) and booking.guest_id = guest_id and booking.room_id = room_id) = 0 THEN
        insert into booking(guest_id, room_id, arrived, leaving) VALUES (guest_id, room_id, arrived, leaving);
    ELSE
        # return error
        SELECT 'User already book this dates' as error;
    END IF;

    END;



/*!50001 DROP VIEW IF EXISTS `full_booking`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `full_booking` AS select `booking`.`id` AS `id`,`g`.`id` AS `guest_id`,`g`.`first_name` AS `first_name`,`g`.`last_name` AS `last_name`,`g`.`phone` AS `guest_phone`,`g`.`passport` AS `passport`,`g`.`email` AS `email`,`g`.`personal_options` AS `personal_options`,`r`.`id` AS `room_number`,`rt`.`type` AS `room_type`,((to_days(`booking`.`leaving`) - to_days(`booking`.`arriving`)) * `r`.`price`) AS `total_price`,`booking`.`arriving` AS `guest_arrive`,`booking`.`leaving` AS `guest_leave` from (((`booking` left join (select `guests`.`first_name` AS `first_name`,`guests`.`last_name` AS `last_name`,`guests`.`phone` AS `phone`,`guests`.`passport` AS `passport`,`guests`.`email` AS `email`,`guests`.`id` AS `id`,group_concat(`po`.`name` separator ', ') AS `personal_options` from ((`guests` left join `guests_personal_options` `gpo` on((`guests`.`id` = `gpo`.`guest_id`))) left join `personal_options` `po` on((`gpo`.`option_id` = `po`.`id`))) group by `guests`.`first_name`,`guests`.`last_name`,`guests`.`phone`,`guests`.`id`) `g` on((`booking`.`guest_id` = `g`.`id`))) left join `rooms` `r` on((`booking`.`room_id` = `r`.`id`))) left join `room_types` `rt` on((`r`.`type_id` = `rt`.`id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-27 14:37:31
