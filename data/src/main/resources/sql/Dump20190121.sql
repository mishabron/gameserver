CREATE DATABASE  IF NOT EXISTS `gameEngine` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `gameEngine`;
-- MySQL dump 10.16  Distrib 10.1.37-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: gameEngine
-- ------------------------------------------------------
-- Server version	10.1.37-MariaDB

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
-- Table structure for table `BonusPin`
--

DROP TABLE IF EXISTS `BonusPin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BonusPin` (
  `Batch_id` int(8) NOT NULL,
  `Pin` varchar(4) NOT NULL,
  `UpdateBy` varchar(10) DEFAULT NULL,
  `UpdateTime` timestamp NULL DEFAULT NULL,
  `Active` tinyint(1) DEFAULT NULL,
  `Used` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`Batch_id`,`Pin`),
  UNIQUE KEY `Pin_UNIQUE` (`Pin`),
  KEY `fk_BonusPin_Batch1_idx` (`Batch_id`),
  KEY `index3` (`Pin`),
  CONSTRAINT `fk_BonusPin_Batch1` FOREIGN KEY (`Batch_id`) REFERENCES `CardBatch` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BoosterPin`
--

DROP TABLE IF EXISTS `BoosterPin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BoosterPin` (
  `Batch_id` int(8) NOT NULL,
  `Pin` varchar(4) NOT NULL,
  `UpdateBy` varchar(10) DEFAULT NULL,
  `UpdateTime` timestamp NULL DEFAULT NULL,
  `Active` tinyint(1) DEFAULT NULL,
  `Used` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`Batch_id`,`Pin`),
  UNIQUE KEY `Pin_UNIQUE` (`Pin`),
  KEY `fk_BonusPin_Batch1_idx` (`Batch_id`),
  KEY `index3` (`Pin`),
  CONSTRAINT `fk_BonusPin_Batch11` FOREIGN KEY (`Batch_id`) REFERENCES `CardBatch` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CardBatch`
--

DROP TABLE IF EXISTS `CardBatch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CardBatch` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `Batchdate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `Barcode` varchar(7) NOT NULL,
  `Game_id` int(2) NOT NULL,
  `CardsInBatch` int(4) DEFAULT NULL,
  `CardPrice` decimal(5,2) DEFAULT NULL,
  `Payout1` decimal(10,2) DEFAULT NULL,
  `Payout2` decimal(10,2) DEFAULT NULL,
  `Payout3` decimal(10,2) DEFAULT NULL,
  `FreeGame` tinyint(4) DEFAULT NULL,
  `UpdateBy` varchar(10) DEFAULT NULL,
  `UpdateTime` timestamp NULL DEFAULT NULL,
  `Userid` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `gameID_UNIQUE` (`Game_id`),
  UNIQUE KEY `barcode_UNIQUE` (`Barcode`),
  KEY `fk_Batch_2_idx` (`Userid`),
  CONSTRAINT `fk_Batch_1` FOREIGN KEY (`Game_id`) REFERENCES `Game` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Batch_2` FOREIGN KEY (`Userid`) REFERENCES `Users` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Cards`
--

DROP TABLE IF EXISTS `Cards`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Cards` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `card_number` varchar(12) NOT NULL,
  `Barcode` varchar(7) NOT NULL,
  `Game_id` int(2) NOT NULL,
  `Batch_id` int(8) NOT NULL,
  `WinPin` varchar(4) DEFAULT NULL,
  `Price` decimal(10,2) DEFAULT NULL,
  `DeviceId` varchar(20) DEFAULT NULL,
  `Active` tinyint(4) DEFAULT NULL,
  `ActivateBy` varchar(45) DEFAULT NULL,
  `ActivateDate` timestamp NULL DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Played` tinyint(4) DEFAULT NULL,
  `NumberOfHits` int(1) DEFAULT NULL,
  `Paid` tinyint(4) DEFAULT NULL,
  `UpdateBy` varchar(10) DEFAULT NULL,
  `UpdateTime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `barcode_UNIQUE` (`Barcode`),
  UNIQUE KEY `card_number_UNIQUE` (`card_number`),
  KEY `fk_Cards_1_idx` (`Game_id`),
  KEY `fk_Cards_2_idx` (`Batch_id`),
  CONSTRAINT `fk_Cards_1` FOREIGN KEY (`Game_id`) REFERENCES `Game` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cards_2` FOREIGN KEY (`Batch_id`) REFERENCES `CardBatch` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Contact`
--

DROP TABLE IF EXISTS `Contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Contact` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `Distributor_id` int(10) DEFAULT NULL,
  `First_name` varchar(45) DEFAULT NULL,
  `Last_name` varchar(45) DEFAULT NULL,
  `Telephone` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `UpdateTime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `index2` (`Distributor_id`),
  CONSTRAINT `fk_Contact_Distibutor1` FOREIGN KEY (`Distributor_id`) REFERENCES `Distributor` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Distributor`
--

DROP TABLE IF EXISTS `Distributor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Distributor` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) DEFAULT NULL,
  `Address` varchar(45) DEFAULT NULL,
  `City` varchar(45) DEFAULT NULL,
  `State` varchar(45) DEFAULT NULL,
  `Zip` varchar(45) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `Telephone` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Notes` varchar(45) DEFAULT NULL,
  `UpdateTime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Game`
--

DROP TABLE IF EXISTS `Game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Game` (
  `id` int(2) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Hit`
--

DROP TABLE IF EXISTS `Hit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Hit` (
  `Sequence` int(1) NOT NULL AUTO_INCREMENT,
  `Card_id` int(12) NOT NULL,
  `Number_1` int(1) DEFAULT NULL,
  `Number_2` int(1) DEFAULT NULL,
  `Number_3` int(1) DEFAULT NULL,
  `Number_4` int(1) DEFAULT NULL,
  `BonusHit` tinyint(1) DEFAULT NULL,
  `HtTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`Sequence`,`Card_id`),
  KEY `fk_Hit_1_idx` (`Card_id`),
  CONSTRAINT `fk_Hit_1` FOREIGN KEY (`Card_id`) REFERENCES `Cards` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SuperPin`
--

DROP TABLE IF EXISTS `SuperPin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SuperPin` (
  `Batch_id` int(8) NOT NULL,
  `Pin` varchar(4) NOT NULL,
  `UpdateBy` varchar(10) DEFAULT NULL,
  `UpdateTime` timestamp NULL DEFAULT NULL,
  `Active` tinyint(1) DEFAULT NULL,
  `Used` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`Batch_id`,`Pin`),
  UNIQUE KEY `Pin_UNIQUE` (`Pin`),
  KEY `index3` (`Pin`),
  CONSTRAINT `fk_SuperPin_1` FOREIGN KEY (`Batch_id`) REFERENCES `CardBatch` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users` (
  `userId` varchar(10) NOT NULL,
  `Password` varchar(15) NOT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Role` varchar(20) NOT NULL,
  `UpdateBy` varchar(10) DEFAULT NULL,
  `UpdateTime` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Vendor`
--

DROP TABLE IF EXISTS `Vendor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Vendor` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `Distributor_id` int(10) NOT NULL,
  `First_name` varchar(45) DEFAULT NULL,
  `Last_name` varchar(45) DEFAULT NULL,
  `Telephone` varchar(45) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `UpdateTime` timestamp NULL DEFAULT NULL,
  `UpdateBy` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Vendor_Distributor1_idx` (`Distributor_id`),
  CONSTRAINT `fk_Vendor_Distributor1` FOREIGN KEY (`Distributor_id`) REFERENCES `Distributor` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-21 21:51:26
