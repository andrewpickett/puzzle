-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema puzzle
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `puzzle` ;

-- -----------------------------------------------------
-- Schema puzzle
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `puzzle` DEFAULT CHARACTER SET utf8 ;
USE `puzzle` ;

-- -----------------------------------------------------
-- Table `puzzle`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `puzzle`.`user` ;

CREATE TABLE IF NOT EXISTS `puzzle`.`user` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `puzzle`.`user` (`id` ASC);


-- -----------------------------------------------------
-- Table `puzzle`.`puzzle`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `puzzle`.`puzzle` ;

CREATE TABLE IF NOT EXISTS `puzzle`.`puzzle` (
  `id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `prev_puzzle_id` INT NULL,
  `title` VARCHAR(255) NULL,
  `description` VARCHAR(2000) NOT NULL,
  `answer` VARCHAR(45) NOT NULL,
  `normalized_ind` TINYINT NOT NULL DEFAULT 0,
  `complete_time` TIMESTAMP NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `puzzle`.`puzzle` (`id` ASC);


-- -----------------------------------------------------
-- Table `puzzle`.`hint`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `puzzle`.`hint` ;

CREATE TABLE IF NOT EXISTS `puzzle`.`hint` (
  `puzzle_id` INT NOT NULL,
  `seq_num` INT NOT NULL,
  `description` VARCHAR(2000) NOT NULL,
  `available_ind` TINYINT NOT NULL DEFAULT 1,
  `hint_time` TIMESTAMP NULL,
  PRIMARY KEY (`puzzle_id`, `seq_num`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `puzzle`.`answer_guess`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `puzzle`.`answer_guess` ;

CREATE TABLE IF NOT EXISTS `puzzle`.`answer_guess` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `puzzle_id` INT NOT NULL,
  `value` VARCHAR(45) NOT NULL,
  `guess_time` TIMESTAMP NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `puzzle`.`answer_guess` (`id` ASC);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
