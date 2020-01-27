-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema gameEngine
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `gameEngine` ;

-- -----------------------------------------------------
-- Schema gameEngine
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `gameEngine` DEFAULT CHARACTER SET utf8 ;
USE `gameEngine` ;

-- -----------------------------------------------------
-- Table `gameEngine`.`Distributor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gameEngine`.`Distributor` ;

CREATE TABLE IF NOT EXISTS `gameEngine`.`Distributor` (
  `id` INT(10) NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NULL DEFAULT NULL,
  `Address` VARCHAR(45) NULL DEFAULT NULL,
  `City` VARCHAR(45) NULL DEFAULT NULL,
  `State` VARCHAR(45) NULL DEFAULT NULL,
  `Zip` VARCHAR(45) NULL DEFAULT NULL,
  `country` VARCHAR(45) NULL DEFAULT NULL,
  `Telephone` VARCHAR(45) NULL DEFAULT NULL,
  `Email` VARCHAR(45) NULL DEFAULT NULL,
  `Notes` VARCHAR(45) NULL DEFAULT NULL,
  `UpdateTime` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 17
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `gameEngine`.`Contact`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gameEngine`.`Contact` ;

CREATE TABLE IF NOT EXISTS `gameEngine`.`Contact` (
  `id` INT(10) NOT NULL AUTO_INCREMENT,
  `Distributor_id` INT(10) NULL DEFAULT NULL,
  `First_name` VARCHAR(45) NULL DEFAULT NULL,
  `Last_name` VARCHAR(45) NULL DEFAULT NULL,
  `Telephone` VARCHAR(45) NULL DEFAULT NULL,
  `Email` VARCHAR(45) NULL DEFAULT NULL,
  `UpdateTime` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Contact_Distibutor1`
    FOREIGN KEY (`Distributor_id`)
    REFERENCES `gameEngine`.`Distributor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 41
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `index2` ON `gameEngine`.`Contact` (`Distributor_id` ASC);


-- -----------------------------------------------------
-- Table `gameEngine`.`Users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gameEngine`.`Users` ;

CREATE TABLE IF NOT EXISTS `gameEngine`.`Users` (
  `userId` VARCHAR(10) NOT NULL,
  `Password` VARCHAR(15) NOT NULL,
  `Email` VARCHAR(45) NULL DEFAULT NULL,
  `Role` VARCHAR(20) NOT NULL,
  `UpdateBy` VARCHAR(10) NULL DEFAULT NULL,
  `UpdateTime` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`userId`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `gameEngine`.`Vendor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gameEngine`.`Vendor` ;

CREATE TABLE IF NOT EXISTS `gameEngine`.`Vendor` (
  `id` INT(10) NOT NULL AUTO_INCREMENT,
  `Distributor_id` INT(10) NOT NULL,
  `First_name` VARCHAR(45) NULL DEFAULT NULL,
  `Last_name` VARCHAR(45) NULL DEFAULT NULL,
  `Telephone` VARCHAR(45) NULL DEFAULT NULL,
  `Email` VARCHAR(45) NULL DEFAULT NULL,
  `UpdateTime` TIMESTAMP NULL DEFAULT NULL,
  `UpdateBy` VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Vendor_Distributor1`
    FOREIGN KEY (`Distributor_id`)
    REFERENCES `gameEngine`.`Distributor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 63
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_Vendor_Distributor1_idx` ON `gameEngine`.`Vendor` (`Distributor_id` ASC);


-- -----------------------------------------------------
-- Table `gameEngine`.`Game`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gameEngine`.`Game` ;

CREATE TABLE IF NOT EXISTS `gameEngine`.`Game` (
  `id` INT(2) NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gameEngine`.`CardBatch`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gameEngine`.`CardBatch` ;

CREATE TABLE IF NOT EXISTS `gameEngine`.`CardBatch` (
  `id` INT(8) NOT NULL AUTO_INCREMENT,
  `Batchdate` TIMESTAMP NOT NULL,
  `Barcode` VARCHAR(7) NOT NULL,
  `Game_id` INT(2) NOT NULL,
  `CardsInBatch` INT(4) NOT NULL,
  `CardPrice` DECIMAL(5,2) NULL,
  `Payout1` DECIMAL(10,2) NULL,
  `Payout2` DECIMAL(10,2) NULL,
  `Payout3` DECIMAL(10,2) NULL,
  `FreeGame` TINYINT NULL,
  `UpdateBy` VARCHAR(10) NULL,
  `UpdateTime` TIMESTAMP NULL,
  `Userid` VARCHAR(10) NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Batch_1`
    FOREIGN KEY (`Game_id`)
    REFERENCES `gameEngine`.`Game` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Batch_2`
    FOREIGN KEY (`Userid`)
    REFERENCES `gameEngine`.`Users` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Batch_2_idx` ON `gameEngine`.`CardBatch` (`Userid` ASC);

CREATE UNIQUE INDEX `barcode_UNIQUE` ON `gameEngine`.`CardBatch` (`Barcode` ASC);


-- -----------------------------------------------------
-- Table `gameEngine`.`Cards`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gameEngine`.`Cards` ;

CREATE TABLE IF NOT EXISTS `gameEngine`.`Cards` (
  `id` INT(8) NOT NULL AUTO_INCREMENT,
  `card_number` VARCHAR(12) NOT NULL,
  `Barcode` VARCHAR(7) NOT NULL,
  `Game_id` INT(2) NOT NULL,
  `Batch_id` INT(8) NOT NULL,
  `Price` DECIMAL(10,2) NULL,
  `DeviceId` VARCHAR(15) NULL,
  `Active` TINYINT NULL,
  `ActivateBy` VARCHAR(45) NULL,
  `ActivateDate` TIMESTAMP NULL,
  `Email` VARCHAR(45) NULL,
  `Played` TINYINT NULL,
  `NumberOfHits` INT(1) NULL,
  `Paid` TINYINT NULL,
  `CurrentPlay` INT(1) NULL,
  `UpdateBy` VARCHAR(10) NULL,
  `UpdateTime` TIMESTAMP NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_Cards_1`
    FOREIGN KEY (`Game_id`)
    REFERENCES `gameEngine`.`Game` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Cards_2`
    FOREIGN KEY (`Batch_id`)
    REFERENCES `gameEngine`.`CardBatch` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Cards_1_idx` ON `gameEngine`.`Cards` (`Game_id` ASC);

CREATE INDEX `fk_Cards_2_idx` ON `gameEngine`.`Cards` (`Batch_id` ASC);

CREATE UNIQUE INDEX `barcode_UNIQUE` ON `gameEngine`.`Cards` (`Barcode` ASC);

CREATE UNIQUE INDEX `card_number_UNIQUE` ON `gameEngine`.`Cards` (`card_number` ASC);


-- -----------------------------------------------------
-- Table `gameEngine`.`BonusPin`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gameEngine`.`BonusPin` ;

CREATE TABLE IF NOT EXISTS `gameEngine`.`BonusPin` (
  `Batch_id` INT(8) NOT NULL,
  `Position` INT(4) NOT NULL,
  `Sequence` INT(1) NOT NULL,
  `card_number` VARCHAR(12) NULL,
  `Active` TINYINT(1) NULL,
  `Used` TINYINT(1) NULL,
  `UpdateTime` TIMESTAMP NULL,
  `UpdateBy` VARCHAR(10) NULL,
  PRIMARY KEY (`Batch_id`, `Position`, `Sequence`),
  CONSTRAINT `fk_BonusPin_Batch1`
    FOREIGN KEY (`Batch_id`)
    REFERENCES `gameEngine`.`CardBatch` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_BonusPin_Batch1_idx` ON `gameEngine`.`BonusPin` (`Batch_id` ASC);

CREATE INDEX `index3` ON `gameEngine`.`BonusPin` (`Position` ASC);


-- -----------------------------------------------------
-- Table `gameEngine`.`SuperPin`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gameEngine`.`SuperPin` ;

CREATE TABLE IF NOT EXISTS `gameEngine`.`SuperPin` (
  `Batch_id` INT(8) NOT NULL,
  `Position` INT(4) NOT NULL,
  `Sequence` INT(1) NOT NULL,
  `card_number` VARCHAR(12) NULL,
  `Active` TINYINT(1) NULL,
  `Used` TINYINT(1) NULL,
  `UpdateTime` TIMESTAMP NULL,
  `UpdateBy` VARCHAR(10) NULL,
  PRIMARY KEY (`Batch_id`, `Position`, `Sequence`),
  CONSTRAINT `fk_SuperPin_1`
    FOREIGN KEY (`Batch_id`)
    REFERENCES `gameEngine`.`CardBatch` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `index3` ON `gameEngine`.`SuperPin` (`Position` ASC);


-- -----------------------------------------------------
-- Table `gameEngine`.`Play`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gameEngine`.`Play` ;

CREATE TABLE IF NOT EXISTS `gameEngine`.`Play` (
  `Play_no` INT(1) NOT NULL,
  `Card_id` INT(12) NOT NULL,
  `WinPin` VARCHAR(4) NOT NULL,
  `UpdateBy` VARCHAR(10) NULL,
  `UpdateTime` TIMESTAMP NULL,
  PRIMARY KEY (`Play_no`, `Card_id`),
  CONSTRAINT `fk_Play_1`
    FOREIGN KEY (`Card_id`)
    REFERENCES `gameEngine`.`Cards` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_Play_1_idx` ON `gameEngine`.`Play` (`Card_id` ASC);


-- -----------------------------------------------------
-- Table `gameEngine`.`Hit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `gameEngine`.`Hit` ;

CREATE TABLE IF NOT EXISTS `gameEngine`.`Hit` (
  `Sequence` INT(1) NOT NULL AUTO_INCREMENT,
  `Card_id` INT(12) NOT NULL,
  `Play_no` INT(1) NOT NULL,
  `Batch_id` INT(8) NOT NULL,
  `Number_1` INT(1) NULL,
  `Number_2` INT(1) NULL,
  `Number_3` INT(1) NULL,
  `Number_4` INT(1) NULL,
  `BonusHit` TINYINT(1) NULL,
  `FirstPlay` TINYINT(1) NULL,
  `HtTime` TIMESTAMP NOT NULL,
  PRIMARY KEY (`Sequence`, `Card_id`, `Play_no`),
  CONSTRAINT `fk_Hit_2`
    FOREIGN KEY (`Card_id` , `Play_no`)
    REFERENCES `gameEngine`.`Play` (`Card_id` , `Play_no`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Hit_1`
    FOREIGN KEY (`Batch_id`)
    REFERENCES `gameEngine`.`CardBatch` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = big5
COLLATE = big5_bin;

CREATE INDEX `fk_Hit_1_idx` ON `gameEngine`.`Hit` (`Card_id` ASC, `Play_no` ASC);

CREATE INDEX `fk_Hit_1_idx1` ON `gameEngine`.`Hit` (`Batch_id` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
