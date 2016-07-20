CREATE DATABASE statsmart;

CREATE TABLE statsmart.`host` (
  `ip` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`name`),
  UNIQUE KEY `ip_UNIQUE` (`ip`));

CREATE TABLE `cpu` (
  `dateTime` datetime NOT NULL,
  `host_name` varchar(45) NOT NULL,
  `per_usr` double DEFAULT NULL,
  `per_nice` double DEFAULT NULL,
  `per_sys` double DEFAULT NULL,
  `per_io_wait` double DEFAULT NULL,
  `cpu` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`dateTime`,`host_name`),
  KEY `host_name_idx` (`host_name`),
  CONSTRAINT `host_name` FOREIGN KEY (`host_name`) REFERENCES `host` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `memory` (
  `dateTime` datetime NOT NULL,
  `total` double DEFAULT NULL,
  `used` double DEFAULT NULL,
  `free` double DEFAULT NULL,
  `host_name` varchar(45) NOT NULL,
  PRIMARY KEY (`dateTime`,`host_name`),
  KEY `host_name_idx` (`host_name`),
  KEY `memory_host_name_idx` (`host_name`),
  CONSTRAINT `memory_host_name` FOREIGN KEY (`host_name`) REFERENCES `host` (`name`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




