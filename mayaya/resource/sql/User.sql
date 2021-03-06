USE mayaya;
DROP TABLE IF EXISTS User;
CREATE TABLE User
(
    userId               INT UNSIGNED NOT NULL AUTO_INCREMENT, 
    login                VARCHAR(32) NOT NULL DEFAULT '',
    passwd               VARCHAR(64) NOT NULL DEFAULT '',
    userStatus           enum('Invalid', 'Valid') NOT NULL DEFAULT 'Valid',   
    userType             enum('Teacher', 'Parents','Follower') NOT NULL DEFAULT 'Parents',   
    lastLoginTime        DATETIME DEFAULT '0000-00-00 00:00:00' NOT NULL ,
    userPicUrl			 VARCHAR(255) NOT NULL DEFAULT '',
    firstName			 VARCHAR(32) NOT NULL DEFAULT '',
    lastName			 VARCHAR(32) NOT NULL DEFAULT '',
    userDescription		 VARCHAR(128) NOT NULL DEFAULT '',
    
    telephone			 VARCHAR(20) NOT NULL DEFAULT '',
    email				 VARCHAR(60) NOT NULL DEFAULT '',
    gender				 VARCHAR(20) NOT NULL DEFAULT '',
    
    PRIMARY KEY (userId),
    UNIQUE key idx_user_login(login)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
