

LOCK TABLES `Distributor` WRITE;
/*!40000 ALTER TABLE `Distributor` DISABLE KEYS */;
INSERT INTO `Distributor` VALUES (15,'Distributors IBC','123 Main St','South Park','CO','12345','USA','111-222-3333','my@email.com','',NULL),(16,'Big Brother','123 Main Street','Denver','CO','12345','USA','111-222-3333','eeee@rrrr.com','this is test','2018-02-14 03:22:02');
/*!40000 ALTER TABLE `Distributor` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES ('bob','hope','rrr@jjj.com','DISTRIBUTOR',NULL,'2018-01-01 06:01:00'),('curly','stooge','curly@stooges.com','DISTRIBUTOR',NULL,'2018-01-01 06:01:00'),('jamesbond','007','007@jamesbond.com','DISTRIBUTOR',NULL,'2018-01-01 06:01:00'),('larry','stooge','larry@stoogies.com','DISTRIBUTOR','mishabron','2018-02-25 06:07:56'),('mishabron','samolet','','LOTOROLA_MANAGER','mishabron','2018-01-01 06:01:00'),('moe','stooge','moe@stooges.com','DISTRIBUTOR','mishabron','2018-02-26 00:12:01'),('test','secret','secret@test.com','DISTRIBUTOR','mishabron','2018-02-27 04:06:35');
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `Vendor` WRITE;
/*!40000 ALTER TABLE `Vendor` DISABLE KEYS */;
INSERT INTO `Vendor` VALUES (1,15,'Curly','Stooge','111-444-5555','curly@stooges.com','2018-02-20 04:46:44',NULL),(14,16,'Larry','Howard','222-444-5555','larry@stoogies.com','2018-02-25 06:07:40',NULL),(20,15,'Moe','Howard','333-333-3333','moe@stooges.com','2018-02-26 00:12:00',NULL),(62,15,'Test','New Test','333-333-3333','moe@stooges.com','2018-02-26 03:43:44',NULL);
/*!40000 ALTER TABLE `Vendor` ENABLE KEYS */;

LOCK TABLES `Contact` WRITE;
/*!40000 ALTER TABLE `Contact` DISABLE KEYS */;
INSERT INTO `Contact` VALUES (32,15,'Dick','Tracy','222-111-3333','at@myemail.com',NULL),(33,15,'Big','Boss','111-111-1111','new@email.com','2018-02-15 03:48:14'),(34,16,'James','Bond','007-007-7777','my@email.com','2018-02-15 04:29:20'),(35,16,'Kirk','Duglas','444-444-4444','Kirk@email.com','2018-02-15 04:50:07'),(36,16,'Kirk','Duglas','444-444-4444','Kirk@email.com','2018-02-15 04:50:07'),(37,15,'James','Bond','111-222-3333','007@jamesbond.com','2018-02-19 02:13:47'),(38,16,'Bob','Hope','444-33-5555','rrr@jjj.com','2018-02-20 02:35:01'),(39,16,'Secret','Test','213-563-6342','secret@test.com','2018-02-27 04:06:35'),(40,16,'Secret','Test','213-563-6342','secret@test.com','2018-02-27 04:14:41');
/*!40000 ALTER TABLE `Contact` ENABLE KEYS */;
UNLOCK TABLES;

INSERT INTO `gameEngine`.`Game` (`id`,`Name`) VALUES (1, "Pingo");

UNLOCK TABLES;
