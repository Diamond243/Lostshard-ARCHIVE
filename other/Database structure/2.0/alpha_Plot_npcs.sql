-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: jenkins.lostshard.com    Database: alpha
-- ------------------------------------------------------
-- Server version	5.1.73

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Plot_npcs`
--

DROP TABLE IF EXISTS `Plot_npcs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Plot_npcs` (
  `Plot_id` int(11) NOT NULL,
  `pitch` float NOT NULL,
  `world` varchar(255) DEFAULT NULL,
  `x` double NOT NULL,
  `y` double NOT NULL,
  `yaw` float NOT NULL,
  `z` double NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `store_id` int(11) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  KEY `FK8u6cj1cbl4f1ulx66tpo9mum3` (`store_id`),
  KEY `FKerw51ulre9849eiblnm63jpjq` (`Plot_id`),
  CONSTRAINT `FK8u6cj1cbl4f1ulx66tpo9mum3` FOREIGN KEY (`store_id`) REFERENCES `Store` (`id`),
  CONSTRAINT `FKerw51ulre9849eiblnm63jpjq` FOREIGN KEY (`Plot_id`) REFERENCES `Plot` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Plot_npcs`
--

LOCK TABLES `Plot_npcs` WRITE;
/*!40000 ALTER TABLE `Plot_npcs` DISABLE KEYS */;
INSERT INTO `Plot_npcs` VALUES (34,2.98109,'world',-694.699112115865,91,-89.9955,132.47482601415,'Osama',NULL,'BANKER','0143a7a0-1a7e-4eba-841f-f728d0e0bfe7'),(78,-2.14606,'world',1878.23310167963,91,-268.955,1982.54364696324,'Matt',NULL,'BANKER','7937b990-7d33-44b2-8062-a6514bdbd09e'),(96,25.2177,'world',-1065.48082996409,212,358.24,705.300366432086,'Inxax',NULL,'BANKER','68401be2-79ee-4940-a0d7-626007234e57'),(1,3.3292,'world',-362.710983458067,91,52.7441,48.55142720894,'Lucid',NULL,'BANKER','9fead0f5-4b0f-4533-8489-6b7b301e0555'),(1,-5.91638,'world',-364.451429964566,58,-78.2635,94.6207191304349,'Defman',NULL,'BANKER','e66c452f-605d-4dcd-b250-c967a9e858f8'),(2,0.718324,'world',76.475499892356,71,-273.449,284.39616352474,'Lucid',NULL,'BANKER','21490a8f-4819-4cc0-9db9-620840f64ae5'),(2,40.4639,'world',58.4580232800122,71,-170.411,292.648200126218,'Ayunema',NULL,'GUARD','a25fc48a-21ae-413e-8eef-e06928d62c0f'),(2,3.60001,'world',73.5982167507352,71,35.2499,281.401786913013,'2$whore',NULL,'GUARD','d605b40d-61f2-47da-8929-c048a6875758'),(2,-4.84429,'world',67.2930024138201,71,-243.961,304.368679794441,'Virus',NULL,'GUARD','3c96e307-51c8-4f40-b552-84333e9cfb44');
/*!40000 ALTER TABLE `Plot_npcs` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-17 17:12:43