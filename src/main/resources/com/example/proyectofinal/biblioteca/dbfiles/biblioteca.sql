-- MySQL Script generated by MySQL Workbench
-- Sat Jun  1 10:35:44 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mydb` ;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Autor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Autor` (
  `idAutor` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL,
  PRIMARY KEY (`idAutor`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Libro`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Libro` (
  `ISBN` VARCHAR(13) NOT NULL,
  `titulo` VARCHAR(100) NOT NULL,
  `idAutor` INT NULL,
  `anyo` INT NULL,
  PRIMARY KEY (`ISBN`),
  INDEX `fk_Libro_Autor_idx` (`idAutor` ASC) VISIBLE,
  CONSTRAINT `fk_Libro_Autor`
    FOREIGN KEY (`idAutor`)
    REFERENCES `mydb`.`Autor` (`idAutor`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Genero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Genero` (
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`nombre`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`EjemplaresDisponibles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`EjemplaresDisponibles` (
  `idEjemplar` INT NOT NULL AUTO_INCREMENT,
  `Libro_ISBN` VARCHAR(13) NULL,
  `estado` VARCHAR(45) NULL,
  PRIMARY KEY (`idEjemplar`),
  INDEX `fk_EjemplaresDisponibles_Libro1_idx` (`Libro_ISBN` ASC) VISIBLE,
  CONSTRAINT `fk_EjemplaresDisponibles_Libro1`
    FOREIGN KEY (`Libro_ISBN`)
    REFERENCES `mydb`.`Libro` (`ISBN`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Socio`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Socio` (
  `idSocio` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `apellidos` VARCHAR(45) NULL,
  `telefono` VARCHAR(9) NULL,
  `email` VARCHAR(45) NULL,
  PRIMARY KEY (`idSocio`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Prestamo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Prestamo` (
  `idPrestamo` INT NOT NULL AUTO_INCREMENT,
  `idEjemplar` INT NULL,
  `idSocio` INT NULL,
  `fecha_inicio` DATE NOT NULL,
  `fecha_fin` DATE NULL,
  `isDevuelto` TINYINT NULL,
  PRIMARY KEY (`idPrestamo`),
  INDEX `fk_EjemplaresDisponibles_has_Socio_Socio1_idx` (`idSocio` ASC) VISIBLE,
  INDEX `fk_EjemplaresDisponibles_has_Socio_EjemplaresDisponibles1_idx` (`idEjemplar` ASC) VISIBLE,
  CONSTRAINT `fk_EjemplaresDisponibles_has_Socio_EjemplaresDisponibles1`
    FOREIGN KEY (`idEjemplar`)
    REFERENCES `mydb`.`EjemplaresDisponibles` (`idEjemplar`)
    ON DELETE SET NULL
    ON UPDATE CASCADE,
  CONSTRAINT `fk_EjemplaresDisponibles_has_Socio_Socio1`
    FOREIGN KEY (`idSocio`)
    REFERENCES `mydb`.`Socio` (`idSocio`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Libro_has_Genero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Libro_has_Genero` (
  `Libro_ISBN` VARCHAR(13) NOT NULL,
  `Genero_nombre` VARCHAR(45) NULL,
  PRIMARY KEY (`Libro_ISBN`),
  INDEX `fk_Libro_has_Genero_Genero1_idx` (`Genero_nombre` ASC) VISIBLE,
  INDEX `fk_Libro_has_Genero_Libro1_idx` (`Libro_ISBN` ASC) VISIBLE,
  CONSTRAINT `fk_Libro_has_Genero_Libro1`
    FOREIGN KEY (`Libro_ISBN`)
    REFERENCES `mydb`.`Libro` (`ISBN`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Libro_has_Genero_Genero1`
    FOREIGN KEY (`Genero_nombre`)
    REFERENCES `mydb`.`Genero` (`nombre`)
    ON DELETE SET NULL
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
