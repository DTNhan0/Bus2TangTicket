CREATE DATABASE IF NOT EXISTS `bus2tangticket`;

USE bus2tangticket;

CREATE TABLE `information` (
  `IdInfo` int NOT NULL AUTO_INCREMENT,
  `Cic` varchar(12) NOT NULL,
  `FirstName` varchar(100) NOT NULL,
  `MiddleName` varchar(100) NOT NULL,
  `LastName` varchar(100) NOT NULL,
  `DateOfBirth` date NOT NULL,
  `Sex` tinyint(1) DEFAULT '0' NOT NULL,
  `PermanentAddress` text NOT NULL,
  `PhoneNumber` varchar(10) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `UpdateAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`IdInfo`),
  UNIQUE KEY `Cic` (`Cic`),
  UNIQUE KEY `PhoneNumber` (`PhoneNumber`),
  UNIQUE KEY `Email` (`Email`)
);

CREATE TABLE `function` (
  `IdFunction` int NOT NULL AUTO_INCREMENT,
  `FunctionName` varchar(100) NOT NULL,
  PRIMARY KEY (`IdFunction`),
  UNIQUE KEY `FunctionName` (`FunctionName`)
);

CREATE TABLE `permissiongroup` (
  `IdPerGroup` int NOT NULL AUTO_INCREMENT,
  `PermissionName` varchar(100) NOT NULL,
  PRIMARY KEY (`IdPerGroup`),
  UNIQUE KEY `PermissionName` (`PermissionName`)
);

CREATE TABLE `permissionfunction` (
  `IdPerFunc` int NOT NULL AUTO_INCREMENT,
  `IdPerGroup` int NOT NULL,
  `IdFunction` int NOT NULL,
  `ActionName` varchar(100) NOT NULL,
  PRIMARY KEY (`IdPerFunc`),
  UNIQUE KEY `PermissionFunction` (`IdPerGroup`, `IdFunction`, `ActionName`),
  CONSTRAINT `permissionfunction_ibfk_1` FOREIGN KEY (`IdPerGroup`) REFERENCES `permissiongroup` (`IdPerGroup`),
  CONSTRAINT `permissionfunction_ibfk_2` FOREIGN KEY (`IdFunction`) REFERENCES `function` (`IdFunction`)
);

CREATE TABLE `account` (
  `IdAccount` int NOT NULL AUTO_INCREMENT,
  `IdInfo` int NOT NULL,
  `AccountName` varchar(50) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `AccessToken` text NOT NULL,
  `RefreshToken` text NOT NULL,
  `TokenExpired` datetime NOT NULL,
  `IsLocked` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`IdAccount`),
  UNIQUE KEY `AccountName` (`AccountName`),
  CONSTRAINT `authority_ibfk_1` FOREIGN KEY (`IdInfo`) REFERENCES `information` (`IdInfo`)
);

CREATE TABLE `permission` (
  `IdPermission` int NOT NULL AUTO_INCREMENT,
  `IdAccount` int NOT NULL,
  `IdPerGroup` int NOT NULL,
  PRIMARY KEY (`IdPermission`),
  UNIQUE KEY `unique_permission` (`IdPerGroup`,`IdAccount`),
  CONSTRAINT `permission_ibfk_1` FOREIGN KEY (`IdPerGroup`) REFERENCES `permissiongroup` (`IdPerGroup`),
  CONSTRAINT `permission_ibfk_2` FOREIGN KEY (`IdAccount`) REFERENCES `account` (`IdAccount`)
);

CREATE TABLE `history` (
  `IdHistory` int NOT NULL AUTO_INCREMENT,
  `IdPermission` int NOT NULL,
  `DateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Description` text NOT NULL,
  PRIMARY KEY (`IdHistory`),
  KEY `IdPermission` (`IdPermission`),
  CONSTRAINT `history_ibfk_1` FOREIGN KEY (`IdPermission`) REFERENCES `permission` (`IdPermission`)
);

CREATE TABLE `busroute` (
  `IdBusRoute` INT NOT NULL AUTO_INCREMENT,
  `IdParent` INT DEFAULT NULL,
  `BusRouteName` VARCHAR(255) NOT NULL UNIQUE,
  `Overview` TEXT,
  `Description` TEXT,
  `Highlights` TEXT,
  `Included` TEXT,
  `Excluded` TEXT,
  `WhatToBring` TEXT,
  `BeforeYouGo` TEXT,
  `UpdateAt` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `IsAvailable` TINYINT(1) DEFAULT '0',
  PRIMARY KEY (`IdBusRoute`),
  CONSTRAINT `busroute_ibfk_1` FOREIGN KEY (`IdParent`) REFERENCES `busroute` (`IdBusRoute`)
);

CREATE TABLE `ticketprice` (
  `IdTicketPrice` int NOT NULL AUTO_INCREMENT,
  `IdBusRoute` int NOT NULL,
  `ParentPrice` decimal(10,2) NOT NULL,
  `ChildPrice` decimal(10,2) NOT NULL,
  `TicketType` enum('24h','48h') NOT NULL,
  `UpdateAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Status` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`IdTicketPrice`),
  KEY `IdBusRoute` (`IdBusRoute`),
  CONSTRAINT `ticketprice_ibfk_1` FOREIGN KEY (`IdBusRoute`) REFERENCES `busroute` (`IdBusRoute`)
);

CREATE TABLE `assignment` (
  `IdAssignment` INT NOT NULL AUTO_INCREMENT,
  `IdBusRoute` INT NOT NULL,
  `IdAccount` INT NOT NULL,
  `UpdateAt` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`IdAssignment`),
  UNIQUE KEY `unique_assignment` (`IdBusRoute`, `IdAccount`),
  KEY `IdAccount` (`IdAccount`),
  CONSTRAINT `assignment_ibfk_1` FOREIGN KEY (`IdBusRoute`) REFERENCES `busroute` (`IdBusRoute`),
  CONSTRAINT `assignment_ibfk_2` FOREIGN KEY (`IdAccount`) REFERENCES `account` (`IdAccount`)
);

CREATE TABLE `busstop` (
  `IdBusStop` INT NOT NULL AUTO_INCREMENT,
  `IdParent` INT DEFAULT NULL,
  `IdBusRoute` INT DEFAULT NULL,
  `BusStopName` VARCHAR(255) NOT NULL UNIQUE,
  `Introduction` TEXT,
  `Address` VARCHAR(255) DEFAULT NULL,
  `StopOrder` INT DEFAULT '-1' NOT NULL,
  `UpdateAt` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `IsAvailable` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`IdBusStop`),
  KEY `IdBusRoute` (`IdBusRoute`),
  CONSTRAINT `busstop_ibfk_1` FOREIGN KEY (`IdBusRoute`) REFERENCES `busroute` (`IdBusRoute`),
  CONSTRAINT `busstop_ibfk_2` FOREIGN KEY (`IdParent`) REFERENCES `busstop` (`IdBusStop`),
  CONSTRAINT `busstop_chk_1` CHECK ((`StopOrder` >= -(1)))
);

CREATE TABLE `routedeparturedate` (
  `IdRouteDepartureDate` int NOT NULL AUTO_INCREMENT,
  `Date` date NOT NULL,
  `IdBusRoute` int NOT NULL,
  `NumberOfSeats` int NOT NULL,
  `Status` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`IdRouteDepartureDate`),
  UNIQUE KEY `unique_route_departure` (`IdBusRoute`,`Date`),
  CONSTRAINT `routedeparturedate_ibfk_1` FOREIGN KEY (`IdBusRoute`) REFERENCES `busroute` (`IdBusRoute`),
  CONSTRAINT `routedeparturedate_chk_1` CHECK ((`NumberOfSeats` > 0))
);

CREATE TABLE `busstopschedule` (
  `IdDepartureTime` int NOT NULL AUTO_INCREMENT,
  `IdBusStop` int NOT NULL,
  `OrderTime` int NOT NULL,
  `Time` time DEFAULT NULL,
  PRIMARY KEY (`IdDepartureTime`),
  UNIQUE KEY `unique_busstopschedule` (`IdBusStop`),
  KEY `IdBusStop` (`IdBusStop`),
  CONSTRAINT `busstopschedule_ibfk_2` FOREIGN KEY (`IdBusStop`) REFERENCES `busstop` (`IdBusStop`),
  CONSTRAINT `busstopschedule_chk_1` CHECK ((`OrderTime` > 0))
);

CREATE TABLE  `mediafile` (
  `IdMediaFile` INT NOT NULL AUTO_INCREMENT,
  `IdBusStop` INT NULL,
  `IdBusRoute` INT NULL,
  `FileName` VARCHAR(50) NOT NULL UNIQUE,
  `FileData` LONGBLOB NOT NULL,
  `FileType` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`IdMediaFile`),
  CONSTRAINT `mediafile_ibfk_1` FOREIGN KEY (`IdBusRoute`) REFERENCES `bus2tangticket`.`busroute` (`IdBusRoute`),
  CONSTRAINT `mediafile_ibfk_2` FOREIGN KEY (`IdBusStop`) REFERENCES `bus2tangticket`.`busstop` (`IdBusStop`)
);

CREATE TABLE `userbook` (
  `IdUserBook` int NOT NULL AUTO_INCREMENT,
  `FullName` varchar(255) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `PhoneNumber` varchar(20) NOT NULL,
  `UpdateAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Region` varchar(100) NOT NULL,
  PRIMARY KEY (`IdUserBook`),
  UNIQUE KEY `PhoneNumber` (`PhoneNumber`)
);

CREATE TABLE `voucher` (
  `VoucherCode` varchar(50) NOT NULL,
  `Percent` int NOT NULL DEFAULT '0',
  `Content` varchar(150) NOT NULL,
  `CreateDateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Expired` datetime NOT NULL,
  `Count` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`VoucherCode`),
  UNIQUE KEY `Content` (`Content`)
);

CREATE TABLE `invoice` (
  `IdInvoice` int NOT NULL AUTO_INCREMENT,
  `PaidDateTime` datetime NOT NULL,
  `PaymentMethod` tinyint(1) NOT NULL,
  `PaymentVia` varchar(50) NOT NULL,
  PRIMARY KEY (`IdInvoice`)
);

CREATE TABLE `invoicedetail` (
  `IdInvoiceDetail` int NOT NULL AUTO_INCREMENT,
  `IdUserBook` int NOT NULL,
  `IdRouteDepartureDate` int NOT NULL,
  `IdInvoice` int NOT NULL,
  `IdTicketPrice` int NOT NULL,
  `VoucherCode` varchar(50) DEFAULT NULL,
  `Price` decimal(10,2) NOT NULL,
  `ChildCount` int NOT NULL DEFAULT '0',
  `ParentCount` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`IdInvoiceDetail`),
  KEY `IdUserBook` (`IdUserBook`),
  KEY `IdRouteDepartureDate` (`IdRouteDepartureDate`),
  KEY `IdInvoice` (`IdInvoice`),
  KEY `VoucherCode` (`VoucherCode`),
  KEY `IdTicketPrice` (`IdTicketPrice`),
  CONSTRAINT `ticketorder_ibfk_1` FOREIGN KEY (`IdUserBook`) REFERENCES `userbook` (`IdUserBook`),
  CONSTRAINT `ticketorder_ibfk_2` FOREIGN KEY (`IdRouteDepartureDate`) REFERENCES `routedeparturedate` (`IdRouteDepartureDate`),
  CONSTRAINT `ticketorder_ibfk_3` FOREIGN KEY (`IdInvoice`) REFERENCES `invoice` (`IdInvoice`),
  CONSTRAINT `ticketorder_ibfk_4` FOREIGN KEY (`IdTicketPrice`) REFERENCES `ticketprice` (`IdTicketPrice`),
  CONSTRAINT `ticketorder_ibfk_5` FOREIGN KEY (`VoucherCode`) REFERENCES `voucher` (`VoucherCode`)
);

CREATE TABLE `blog` (
  `IdBlog` int NOT NULL AUTO_INCREMENT,
  `IdAccount` int NOT NULL,
  `NameBlog` varchar(255) NOT NULL,
  `Header` text,
  `Description` text,
  `Content` text,
  `Date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`IdBlog`),
  UNIQUE KEY `NameBlog` (`NameBlog`),
  KEY `IdAccount` (`IdAccount`),
  CONSTRAINT `blog_ibfk_1` FOREIGN KEY (`IdAccount`) REFERENCES `account` (`IdAccount`)
);

CREATE TABLE `review` (
  `IdReview` int NOT NULL AUTO_INCREMENT,
  `IdInvoiceDetail` int NOT NULL,
  `Rate` int NOT NULL,
  `Date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`IdReview`),
  KEY `IdInvoiceDetail` (`IdInvoiceDetail`),
  CONSTRAINT `review_ibfk_1` FOREIGN KEY (`IdInvoiceDetail`) REFERENCES `invoicedetail` (`IdInvoiceDetail`),
  CONSTRAINT `review_chk_1` CHECK ((`Rate` between 0 and 5))
);