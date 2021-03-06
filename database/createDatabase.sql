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
															 `password` VARCHAR(100) NOT NULL,
															 `admin` TINYINT NOT NULL DEFAULT 0,
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
																`description` VARCHAR(2000) NOT NULL,
																`start_time` TIMESTAMP NULL,
																`complete_time` TIMESTAMP NULL,
																`next_puzzle_id` INT NULL,
																`max_score` INT NOT NULL DEFAULT 100,
																`earned_score` INT NULL,
																`points_per_day` INT NOT NULL DEFAULT 1,
																`points_per_hint` INT NOT NULL DEFAULT 25,
																`points_per_incorrect` INT NOT NULL DEFAULT 5,
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
																		`correct_ind` TINYINT NOT NULL DEFAULT 0,
																		PRIMARY KEY (`id`))
	ENGINE = InnoDB;

CREATE UNIQUE INDEX `id_UNIQUE` ON `puzzle`.`answer_guess` (`id` ASC);


-- -----------------------------------------------------
-- Table `puzzle`.`correct_answer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `puzzle`.`correct_answer` ;

CREATE TABLE IF NOT EXISTS `puzzle`.`correct_answer` (
																		  `id` INT NOT NULL AUTO_INCREMENT,
																		  `puzzle_id` INT NOT NULL,
																		  `next_puzzle_id` INT NOT NULL,
																		  `answer` VARCHAR(45) NOT NULL,
																		  `normalized_ind` TINYINT NOT NULL DEFAULT 1,
																		  PRIMARY KEY (`id`))
	ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
