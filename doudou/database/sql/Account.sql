USE mayaya;
DROP TABLE IF EXISTS User;
CREATE TABLE User
(
    id	                 INT UNSIGNED NOT NULL AUTO_INCREMENT, 
    login                VARCHAR(30) NOT NULL DEFAULT '',
    passwd               VARCHAR(60) NOT NULL DEFAULT '',
    firstName			 VARCHAR(30) NOT NULL DEFAULT '',
    lastName			 VARCHAR(30) NOT NULL DEFAULT '',
    
    userType             enum('Teacher', 'Parents','SchoolAdmin','SuperAdmin') NOT NULL DEFAULT 'Parents',   
    lastLoginTime        DATETIME DEFAULT '0000-00-00 00:00:00' NOT NULL ,
    
    userPicUrl			 VARCHAR(100) NOT NULL DEFAULT '',
    
    userDescription		 VARCHAR(128) NOT NULL DEFAULT '',
    
    telephone			 VARCHAR(20) NOT NULL DEFAULT '',
    email				 VARCHAR(60) NOT NULL DEFAULT '',
    gender				 VARCHAR(20) NOT NULL DEFAULT '',
    
    PRIMARY KEY (id),
    UNIQUE key idx_user_login(login)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
