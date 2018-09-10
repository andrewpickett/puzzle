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
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `puzzle`.`puzzle`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `puzzle`.`puzzle` ;

CREATE TABLE IF NOT EXISTS `puzzle`.`puzzle` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `sequence_num` DECIMAL(8,2) NOT NULL,
  `title` VARCHAR(255) NOT NULL,
  `description` VARCHAR(2000) NOT NULL,
  `answer` VARCHAR(45) NOT NULL,
  `complete_ind` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  INDEX `fk_puzzle_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_puzzle_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `puzzle`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `puzzle`.`hint`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `puzzle`.`hint` ;

CREATE TABLE IF NOT EXISTS `puzzle`.`hint` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `puzzle_id` INT NOT NULL,
  `description` VARCHAR(2000) NOT NULL,
  `available_ind` TINYINT NOT NULL,
  `hint_time` TIMESTAMP NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_hint_puzzle_idx` (`puzzle_id` ASC),
  CONSTRAINT `fk_hint_puzzle`
    FOREIGN KEY (`puzzle_id`)
    REFERENCES `puzzle`.`puzzle` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
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
  PRIMARY KEY (`id`),
  INDEX `fk_answer_guess_puzzle1_idx` (`puzzle_id` ASC),
  CONSTRAINT `fk_answer_guess_puzzle1`
    FOREIGN KEY (`puzzle_id`)
    REFERENCES `puzzle`.`puzzle` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
