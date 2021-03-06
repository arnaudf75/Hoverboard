-- MySQL Script generated by MySQL Workbench
-- 05/05/15 16:46:10
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema hoverboard_esgi
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema hoverboard_esgi
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hoverboard_esgi` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `hoverboard_esgi` ;

-- -----------------------------------------------------
-- Table `hoverboard_esgi`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hoverboard_esgi`.`users` (
  `idUser` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `login` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `isAdmin` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`idUser`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hoverboard_esgi`.`plugins`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hoverboard_esgi`.`plugins` (
  `idPlugin` INT NOT NULL AUTO_INCREMENT,
  `namePlugin` VARCHAR(60) NOT NULL,
  `descriptionPlugin` VARCHAR(100) NOT NULL,
  `dateRelease` DATE NOT NULL,
  `isValid` TINYINT(1) NOT NULL DEFAULT 0,
  `idEditeur` INT NOT NULL,
  PRIMARY KEY (`idPlugin`),
  INDEX `fk_plugins_users1_idx` (`idEditeur` ASC),
  CONSTRAINT `fk_plugins_users1`
    FOREIGN KEY (`idEditeur`)
    REFERENCES `hoverboard_esgi`.`users` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hoverboard_esgi`.`version`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hoverboard_esgi`.`version` (
  `idVersion` INT NOT NULL AUTO_INCREMENT,
  `dateUpdate` DATE NOT NULL,
  `numVersion` VARCHAR(60) NOT NULL,
  `changelog` VARCHAR(200) NOT NULL,
  `pathToVersion` VARCHAR(100) NOT NULL,
  `idPlugin` INT NULL,
  PRIMARY KEY (`idVersion`),
  INDEX `fk_version_plugins1_idx` (`idPlugin` ASC),
  CONSTRAINT `fk_version_plugins1`
    FOREIGN KEY (`idPlugin`)
    REFERENCES `hoverboard_esgi`.`plugins` (`idPlugin`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hoverboard_esgi`.`dashboard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hoverboard_esgi`.`dashboard` (
  `idDashboard` INT NOT NULL AUTO_INCREMENT,
  `titleDashboard` VARCHAR(45) NOT NULL,
  `descriptionDashboard` VARCHAR(200) NULL,
  PRIMARY KEY (`idDashboard`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hoverboard_esgi`.`widgets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hoverboard_esgi`.`widgets` (
  `idWidget` INT NOT NULL AUTO_INCREMENT,
  `nameWidget` VARCHAR(45) NOT NULL,
  `contentWidget` VARCHAR(1000) NULL,
  `positionX` INT NOT NULL,
  `positionY` INT NOT NULL,
  `longueur` INT NOT NULL,
  `largeur` INT NOT NULL,
  `typeWidget` VARCHAR(45) NULL,
  `isDeleted` TINYINT(1) NOT NULL DEFAULT 0,
  `idDashboard` INT NOT NULL,
  `idPlugin` INT NULL,
  PRIMARY KEY (`idWidget`),
  INDEX `fk_widgets_dashboard1_idx` (`idDashboard` ASC),
  INDEX `fk_widgets_plugins1_idx` (`idPlugin` ASC),
  CONSTRAINT `fk_widgets_dashboard1`
    FOREIGN KEY (`idDashboard`)
    REFERENCES `hoverboard_esgi`.`dashboard` (`idDashboard`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_widgets_plugins1`
    FOREIGN KEY (`idPlugin`)
    REFERENCES `hoverboard_esgi`.`plugins` (`idPlugin`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hoverboard_esgi`.`utilise`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hoverboard_esgi`.`utilise` (
  `idUser` INT NOT NULL,
  `idDashboard` INT NOT NULL,
  `isDashboardAdmin` TINYINT(1) NULL,
  PRIMARY KEY (`idUser`, `idDashboard`),
  INDEX `fk_Users_has_Dashboard_Dashboard1_idx` (`idDashboard` ASC),
  INDEX `fk_Users_has_Dashboard_Users_idx` (`idUser` ASC),
  CONSTRAINT `fk_Users_has_Dashboard_Users`
    FOREIGN KEY (`idUser`)
    REFERENCES `hoverboard_esgi`.`users` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Users_has_Dashboard_Dashboard1`
    FOREIGN KEY (`idDashboard`)
    REFERENCES `hoverboard_esgi`.`dashboard` (`idDashboard`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hoverboard_esgi`.`installe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hoverboard_esgi`.`installe` (
  `idPlugin` INT NOT NULL,
  `idUser` INT NOT NULL,
  PRIMARY KEY (`idPlugin`, `idUser`),
  INDEX `fk_version_has_users_users1_idx` (`idUser` ASC),
  INDEX `fk_installe_plugins1_idx` (`idPlugin` ASC),
  CONSTRAINT `fk_version_has_users_users1`
    FOREIGN KEY (`idUser`)
    REFERENCES `hoverboard_esgi`.`users` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_installe_plugins1`
    FOREIGN KEY (`idPlugin`)
    REFERENCES `hoverboard_esgi`.`plugins` (`idPlugin`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `hoverboard_esgi`.`logs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hoverboard_esgi`.`logs` (
  `idLog` INT NOT NULL AUTO_INCREMENT,
  `idUser` INT NOT NULL,
  `idWidget` INT NOT NULL,
  `contentWidget` VARCHAR(1000) NULL,
  `modifDate` DATETIME NOT NULL,
  PRIMARY KEY (`idLog`, `idUser`, `idWidget`),
  INDEX `fk_users_has_widgets_widgets1_idx` (`idWidget` ASC),
  INDEX `fk_users_has_widgets_users1_idx` (`idUser` ASC),
  CONSTRAINT `fk_users_has_widgets_users1`
    FOREIGN KEY (`idUser`)
    REFERENCES `hoverboard_esgi`.`users` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_widgets_widgets1`
    FOREIGN KEY (`idWidget`)
    REFERENCES `hoverboard_esgi`.`widgets` (`idWidget`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
