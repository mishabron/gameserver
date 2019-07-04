-- MySQL dump 10.16  Distrib 10.1.39-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: gameEngine
-- ------------------------------------------------------
-- Server version	10.1.39-MariaDB

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
  CONSTRAINT `fk_BonusPin_Batch1` FOREIGN KEY (`Batch_id`) REFERENCES `CardBatch` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BonusPin`
--

LOCK TABLES `BonusPin` WRITE;
/*!40000 ALTER TABLE `BonusPin` DISABLE KEYS */;
INSERT INTO `BonusPin` VALUES (1,'0925',NULL,'2019-05-22 03:10:10',1,0),(1,'1009',NULL,'2019-05-22 03:10:10',1,0),(1,'1043',NULL,'2019-05-22 03:10:10',1,0),(1,'1394',NULL,'2019-05-22 03:10:10',1,0),(1,'3984',NULL,'2019-05-22 03:10:10',1,0),(1,'4693',NULL,'2019-05-22 03:10:10',1,0),(1,'4802',NULL,'2019-05-22 03:10:10',1,0),(1,'5002',NULL,'2019-05-22 03:10:10',1,0),(1,'5293',NULL,'2019-05-22 03:10:10',1,0),(1,'5340',NULL,'2019-05-22 03:10:10',1,0),(1,'6104',NULL,'2019-05-22 03:10:10',1,0),(1,'6163',NULL,'2019-05-22 03:10:10',1,0),(1,'6695',NULL,'2019-05-22 03:10:10',1,0),(1,'7359',NULL,'2019-05-22 03:10:10',1,0),(1,'7776',NULL,'2019-05-22 03:10:10',1,0),(1,'7832',NULL,'2019-05-22 03:10:10',1,0),(1,'7956',NULL,'2019-05-22 03:10:10',1,0),(1,'8376',NULL,'2019-05-22 03:10:10',1,0),(1,'8462',NULL,'2019-05-22 03:10:10',1,0),(1,'9439',NULL,'2019-05-22 03:10:10',1,0);
/*!40000 ALTER TABLE `BonusPin` ENABLE KEYS */;
UNLOCK TABLES;

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
  CONSTRAINT `fk_BonusPin_Batch11` FOREIGN KEY (`Batch_id`) REFERENCES `CardBatch` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BoosterPin`
--

LOCK TABLES `BoosterPin` WRITE;
/*!40000 ALTER TABLE `BoosterPin` DISABLE KEYS */;
INSERT INTO `BoosterPin` VALUES (1,'0038',NULL,'2019-05-22 03:10:10',1,0),(1,'0397',NULL,'2019-05-22 03:10:10',1,0),(1,'0792',NULL,'2019-05-22 03:10:10',1,0),(1,'1258',NULL,'2019-05-22 03:10:10',1,0),(1,'2282',NULL,'2019-05-22 03:10:10',1,0),(1,'2514',NULL,'2019-05-22 03:10:10',1,0),(1,'3026',NULL,'2019-05-22 03:10:10',1,0),(1,'3458',NULL,'2019-05-22 03:10:10',1,0),(1,'4186',NULL,'2019-05-22 03:10:10',1,0),(1,'4331',NULL,'2019-05-22 03:10:10',1,0),(1,'6174',NULL,'2019-05-22 03:10:10',1,0),(1,'6562',NULL,'2019-05-22 03:10:10',1,0),(1,'7142',NULL,'2019-05-22 03:10:10',1,0),(1,'7229',NULL,'2019-05-22 03:10:10',1,0),(1,'8285',NULL,'2019-05-22 03:10:10',1,0);
/*!40000 ALTER TABLE `BoosterPin` ENABLE KEYS */;
UNLOCK TABLES;

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
  `CardsInBatch` int(4) NOT NULL,
  `CardPrice` decimal(5,2) DEFAULT NULL,
  `Payout1` decimal(10,2) DEFAULT NULL,
  `Payout2` decimal(10,2) DEFAULT NULL,
  `Payout3` decimal(10,2) DEFAULT NULL,
  `FreeGame` tinyint(4) DEFAULT NULL,
  `UpdateBy` varchar(10) DEFAULT NULL,
  `UpdateTime` timestamp NULL DEFAULT NULL,
  `Userid` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `barcode_UNIQUE` (`Barcode`),
  KEY `fk_Batch_1` (`Game_id`),
  KEY `fk_Batch_2_idx` (`Userid`),
  CONSTRAINT `fk_Batch_1` FOREIGN KEY (`Game_id`) REFERENCES `Game` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Batch_2` FOREIGN KEY (`Userid`) REFERENCES `Users` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CardBatch`
--

LOCK TABLES `CardBatch` WRITE;
/*!40000 ALTER TABLE `CardBatch` DISABLE KEYS */;
INSERT INTO `CardBatch` VALUES (1,'2019-05-22 03:10:10','UmQ10Yf',1,100,10.00,50.00,30.00,20.00,1,'mishabron','2019-05-22 03:10:10',NULL);
/*!40000 ALTER TABLE `CardBatch` ENABLE KEYS */;
UNLOCK TABLES;

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
  `DeviceId` varchar(15) DEFAULT NULL,
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
  CONSTRAINT `fk_Cards_2` FOREIGN KEY (`Batch_id`) REFERENCES `CardBatch` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cards`
--

LOCK TABLES `Cards` WRITE;
/*!40000 ALTER TABLE `Cards` DISABLE KEYS */;
INSERT INTO `Cards` VALUES (1,'690353957489','hcxjw6K',1,1,'3944',50.00,'123',1,'mishabron','2019-05-22 03:14:26',NULL,0,1,0,'mishabron','2019-05-22 03:10:10'),(2,'866325442678','2zC5sP7',1,1,'3708',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(3,'833726753230','sCqHjhZ',1,1,'2796',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(4,'947886642799','kkJSEA3',1,1,'1263',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(5,'407391364523','vhh281K',1,1,'4579',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(6,'915352384962','FrTzK7s',1,1,'9080',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(7,'994086046096','Abx8cX2',1,1,'8398',50.00,NULL,1,'mishabron','2019-05-22 03:14:25',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(8,'883709836549','iLoY309',1,1,'3092',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(9,'496288574223','AkskZrW',1,1,'5219',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(10,'229304243072','kz87sdh',1,1,'7663',50.00,NULL,1,'mishabron','2019-05-22 03:14:25',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(11,'255725264801','CAP22Cu',1,1,'4166',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(12,'190940526571','oATkxPY',1,1,'6239',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(13,'114690009022','W1rKf7I',1,1,'3169',50.00,NULL,1,'mishabron','2019-05-22 03:14:25',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(14,'600075306845','al34PV5',1,1,'8572',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(15,'559843726471','BaReww7',1,1,'4960',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(16,'146254967799','8Ee3DCR',1,1,'5842',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(17,'891054425798','fhv0rac',1,1,'1087',50.00,NULL,1,'mishabron','2019-05-22 03:14:25',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(18,'761545215161','pqRRgmc',1,1,'5654',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(19,'856160478161','Ta4xaWS',1,1,'3710',50.00,NULL,1,'mishabron','2019-05-22 03:14:25',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(20,'151057317108','u4TrSjR',1,1,'8769',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(21,'368857263805','Ky3ki5h',1,1,'7253',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(22,'584016430136','NgGbDZB',1,1,'4715',50.00,NULL,1,'mishabron','2019-05-22 03:14:25',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(23,'691251101510','0Mq1vlE',1,1,'7189',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(24,'510462490398','9KaKX3B',1,1,'9485',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(25,'723300848014','LWECG1l',1,1,'9005',50.00,NULL,1,'mishabron','2019-05-22 03:14:25',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(26,'246781483974','QN35m4c',1,1,'6588',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(27,'945632554273','qG19Czm',1,1,'0490',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(28,'379910700959','2jHtwFV',1,1,'0145',50.00,NULL,1,'mishabron','2019-05-22 03:14:25',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(29,'691964559856','J7K4S52',1,1,'6643',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(30,'992995797360','DA20AnN',1,1,'4546',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(31,'826705010745','MOaJrjM',1,1,'3278',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(32,'719984990229','yeOKqng',1,1,'1341',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(33,'752918616447','CegfMA5',1,1,'2445',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(34,'821743682519','ut0QZxt',1,1,'8673',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(35,'658866447798','GnwXUEG',1,1,'7350',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(36,'613480206767','KjO6PBL',1,1,'9868',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(37,'361855458984','6IM8NqU',1,1,'2192',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(38,'185723494352','D0DE9RJ',1,1,'5184',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(39,'264012195037','2iVWY94',1,1,'1370',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(40,'786646703397','NO1J3G6',1,1,'8227',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(41,'873820838293','XuRXTvL',1,1,'8383',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(42,'987454968779','TTKl6mc',1,1,'5118',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(43,'192684538856','YWIPGrG',1,1,'4076',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(44,'483093635268','5OLeCiA',1,1,'4939',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(45,'414402192667','4Mtuxa1',1,1,'0820',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(46,'390976172923','bZvghTU',1,1,'6809',50.00,NULL,1,'mishabron','2019-05-22 03:14:25',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(47,'246591868204','Z7tpNGn',1,1,'8147',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(48,'983580183515','wjdmTev',1,1,'8827',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(49,'445392956895','eKJzo44',1,1,'3708',50.00,NULL,1,'mishabron','2019-05-22 03:14:25',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(50,'295649137748','cGq8dxA',1,1,'1456',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(51,'812671260038','i8xHx6F',1,1,'6806',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(52,'566842830431','TSpjZiK',1,1,'3080',50.00,NULL,1,'mishabron','2019-05-22 03:14:25',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(53,'666927883728','BZPQQOJ',1,1,'3699',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(54,'197032743116','m6hfHIr',1,1,'7040',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(55,'911416295813','eTJSCgJ',1,1,'8258',50.00,NULL,1,'mishabron','2019-05-22 03:14:25',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(56,'483102751735','DjZWpBo',1,1,'7120',50.00,NULL,1,'mishabron','2019-05-22 03:14:25',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(57,'291779693463','XWPm6Og',1,1,'8070',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(58,'466510006375','96jL2VQ',1,1,'6579',50.00,NULL,1,'mishabron','2019-05-22 03:14:25',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(59,'790607874168','Hydj8CB',1,1,'1032',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(60,'447040232333','emuizhy',1,1,'9796',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(61,'196728692037','l3tfWBB',1,1,'1397',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(62,'875512855851','tYZToVl',1,1,'0065',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(63,'331924499222','cVLpERB',1,1,'5248',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(64,'326182277324','AxAOYJ9',1,1,'6392',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(65,'645698750863','aqwyuZe',1,1,'3773',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(66,'324489722962','DoRletN',1,1,'0713',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(67,'841767686555','IoFCq9E',1,1,'3381',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(68,'173229684312','CnXR1c1',1,1,'8560',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(69,'187154603584','BHaSerA',1,1,'2259',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(70,'657224302097','PrYLk7K',1,1,'6585',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(71,'244793207968','bcchPEw',1,1,'4857',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(72,'531274279296','WHBsFDk',1,1,'4145',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(73,'884087386020','EHj3WMy',1,1,'3432',50.00,NULL,1,'mishabron','2019-05-22 03:14:25',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(74,'664618947328','iU1LpXS',1,1,'2140',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(75,'501722595114','dvTbMt0',1,1,'2398',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(76,'607370764450','wtI8dqM',1,1,'6352',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(77,'118093252180','74Cw3ZJ',1,1,'3894',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(78,'772536867568','59rL8HS',1,1,'1775',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(79,'353675054151','B9fl85v',1,1,'9268',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(80,'655886842948','wdmNTTd',1,1,'6626',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(81,'703560526244','yPdMIQA',1,1,'7057',50.00,NULL,1,'mishabron','2019-05-22 03:14:25',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(82,'852865578351','8GPBs98',1,1,'5621',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(83,'961329145294','9CRDYgX',1,1,'2294',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(84,'959673032140','kzBsoen',1,1,'8958',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(85,'967296865500','MLXnPtc',1,1,'5556',50.00,NULL,1,'mishabron','2019-05-22 03:14:25',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(86,'594501844896','HefXSzo',1,1,'9397',50.00,NULL,1,'mishabron','2019-05-22 03:14:25',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(87,'283908744799','xNHtquj',1,1,'2224',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(88,'935536574247','pgABXdP',1,1,'4438',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(89,'249437204561','Vw9jLqE',1,1,'2624',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(90,'530495498338','JFahCIr',1,1,'9119',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(91,'895805967808','gXEv9qd',1,1,'4738',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(92,'346041471447','dRGwfc5',1,1,'7026',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(93,'966563555815','1RI9XaF',1,1,'4136',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(94,'734191110311','yzqt5Iw',1,1,'4760',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(95,'132444550865','bFDi70p',1,1,'2254',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(96,'567128661662','7spXDD8',1,1,'3108',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(97,'814807249346','HnTyH3G',1,1,'0750',50.00,NULL,1,'mishabron','2019-05-22 03:14:25',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(98,'742308687937','HinHrwo',1,1,'5595',50.00,NULL,1,'mishabron','2019-05-22 03:14:25',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(99,'108164947490','10N2gRj',1,1,'2641',50.00,NULL,1,'mishabron','2019-05-22 03:14:25',NULL,0,0,0,'mishabron','2019-05-22 03:10:10'),(100,'815556338069','l5pyNYi',1,1,'5703',50.00,NULL,1,'mishabron','2019-05-22 03:14:26',NULL,0,0,0,'mishabron','2019-05-22 03:10:10');
/*!40000 ALTER TABLE `Cards` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `Contact`
--

LOCK TABLES `Contact` WRITE;
/*!40000 ALTER TABLE `Contact` DISABLE KEYS */;
INSERT INTO `Contact` VALUES (32,15,'Dick','Tracy','222-111-3333','at@myemail.com',NULL),(33,15,'Big','Boss','111-111-1111','new@email.com','2018-02-15 03:48:14'),(34,16,'James','Bond','007-007-7777','my@email.com','2018-02-15 04:29:20'),(35,16,'Kirk','Duglas','444-444-4444','Kirk@email.com','2018-02-15 04:50:07'),(36,16,'Kirk','Duglas','444-444-4444','Kirk@email.com','2018-02-15 04:50:07'),(37,15,'James','Bond','111-222-3333','007@jamesbond.com','2018-02-19 02:13:47'),(38,16,'Bob','Hope','444-33-5555','rrr@jjj.com','2018-02-20 02:35:01'),(39,16,'Secret','Test','213-563-6342','secret@test.com','2018-02-27 04:06:35'),(40,16,'Secret','Test','213-563-6342','secret@test.com','2018-02-27 04:14:41');
/*!40000 ALTER TABLE `Contact` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `Distributor`
--

LOCK TABLES `Distributor` WRITE;
/*!40000 ALTER TABLE `Distributor` DISABLE KEYS */;
INSERT INTO `Distributor` VALUES (15,'Distributors IBC','123 Main St','South Park','CO','12345','USA','111-222-3333','my@email.com','',NULL),(16,'Big Brother','123 Main Street','Denver','CO','12345','USA','111-222-3333','eeee@rrrr.com','this is test','2018-02-14 03:22:02');
/*!40000 ALTER TABLE `Distributor` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `Game`
--

LOCK TABLES `Game` WRITE;
/*!40000 ALTER TABLE `Game` DISABLE KEYS */;
INSERT INTO `Game` VALUES (1,'Pingo');
/*!40000 ALTER TABLE `Game` ENABLE KEYS */;
UNLOCK TABLES;

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
  CONSTRAINT `fk_Hit_1` FOREIGN KEY (`Card_id`) REFERENCES `Cards` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Hit`
--

LOCK TABLES `Hit` WRITE;
/*!40000 ALTER TABLE `Hit` DISABLE KEYS */;
INSERT INTO `Hit` VALUES (1,1,3,5,7,9,0,'2019-05-22 03:20:39');
/*!40000 ALTER TABLE `Hit` ENABLE KEYS */;
UNLOCK TABLES;

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
  CONSTRAINT `fk_SuperPin_1` FOREIGN KEY (`Batch_id`) REFERENCES `CardBatch` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SuperPin`
--

LOCK TABLES `SuperPin` WRITE;
/*!40000 ALTER TABLE `SuperPin` DISABLE KEYS */;
INSERT INTO `SuperPin` VALUES (1,'0857',NULL,'2019-05-22 03:10:10',1,0),(1,'1217',NULL,'2019-05-22 03:10:10',1,0),(1,'2527',NULL,'2019-05-22 03:10:10',1,0),(1,'3032',NULL,'2019-05-22 03:10:10',1,0),(1,'4660',NULL,'2019-05-22 03:10:10',1,0),(1,'5296',NULL,'2019-05-22 03:10:10',1,0),(1,'5378',NULL,'2019-05-22 03:10:10',1,0),(1,'7936',NULL,'2019-05-22 03:10:10',1,0),(1,'8827',NULL,'2019-05-22 03:10:10',1,0),(1,'9840',NULL,'2019-05-22 03:10:10',1,0);
/*!40000 ALTER TABLE `SuperPin` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES ('bob','hope','rrr@jjj.com','DISTRIBUTOR',NULL,'2018-01-01 06:01:00'),('curly','stooge','curly@stooges.com','DISTRIBUTOR',NULL,'2018-01-01 06:01:00'),('jamesbond','007','007@jamesbond.com','DISTRIBUTOR',NULL,'2018-01-01 06:01:00'),('larry','stooge','larry@stoogies.com','DISTRIBUTOR','mishabron','2018-02-25 06:07:56'),('mishabron','samolet','','LOTOROLA_MANAGER','mishabron','2018-01-01 06:01:00'),('moe','stooge','moe@stooges.com','DISTRIBUTOR','mishabron','2018-02-26 00:12:01'),('test','secret','secret@test.com','DISTRIBUTOR','mishabron','2018-02-27 04:06:35');
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;

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

--
-- Dumping data for table `Vendor`
--

LOCK TABLES `Vendor` WRITE;
/*!40000 ALTER TABLE `Vendor` DISABLE KEYS */;
INSERT INTO `Vendor` VALUES (1,15,'Curly','Stooge','111-444-5555','curly@stooges.com','2018-02-20 04:46:44',NULL),(14,16,'Larry','Howard','222-444-5555','larry@stoogies.com','2018-02-25 06:07:40',NULL),(20,15,'Moe','Howard','333-333-3333','moe@stooges.com','2018-02-26 00:12:00',NULL),(62,15,'Test','New Test','333-333-3333','moe@stooges.com','2018-02-26 03:43:44',NULL);
/*!40000 ALTER TABLE `Vendor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-03 23:51:02
