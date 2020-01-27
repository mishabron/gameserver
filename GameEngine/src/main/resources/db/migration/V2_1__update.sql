ALTER TABLE `gameEngine`.`Hit`
CHARACTER SET = big5 , COLLATE = big5_bin ,
CHANGE COLUMN `BonusHit` `BonusHit` VARCHAR(10) NULL DEFAULT NULL AFTER `Number_4`,
DROP PRIMARY KEY,
ADD PRIMARY KEY (`Sequence`, `Card_id`, `Play_no`),
ADD INDEX `fk_Hit_1_idx` (`Card_id` ASC, `Play_no` ASC),
ADD INDEX `fk_Hit_1_idx1` (`Batch_id` ASC),
DROP INDEX `FKblr6jgevh4oyt49t2d3ocinpr` ;