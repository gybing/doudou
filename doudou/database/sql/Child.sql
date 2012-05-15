USE mayaya;
DROP TABLE IF EXISTS Child;
CREATE TABLE Child
(
	childID				 INT UNSIGNED NOT NULL AUTO_INCREMENT, 
    firstName			 VARCHAR(30) NOT NULL DEFAULT '',
    lastName			 VARCHAR(30) NOT NULL DEFAULT '',
    age					 INT NOT NULL DEFAULT 0,
    userStatus           enum('Invalid', 'Valid') NOT NULL DEFAULT 'Valid',   
    photoURL   			 VARCHAR(255) NOT NULL DEFAULT '',
    cover				 VARCHAR(255) NOT NULL DEFAULT '',
    classes				 VARCHAR(64) NOT NULL DEFAULT '',
    lastLoginTime        DATETIME DEFAULT '0000-00-00 00:00:00' NOT NULL ,
    
    gender				 VARCHAR(10) NOT NULL DEFAULT 'Male',
    birthDate			 DATETIME DEFAULT '0000-00-00 00:00:00' NOT NULL ,
    description			 VARCHAR(255) NOT NULL DEFAULT '',
    nationality			 VARCHAR(20) NOT NULL DEFAULT '',
    address				 VARCHAR(100) NOT NULL DEFAULT '',
    enterDate			 DATETIME DEFAULT '0000-00-00 00:00:00' NOT NULL ,
    program				 enum('FULL', 'HALF') NOT NULL DEFAULT 'FULL',
    
    PRIMARY KEY (childID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
