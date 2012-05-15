USE doudou;
DROP TABLE IF EXISTS Teacher;
DROP TABLE IF EXISTS Parents;
DROP TABLE IF EXISTS User;
CREATE TABLE User
(
    id	                 INT UNSIGNED NOT NULL AUTO_INCREMENT, 
    login                VARCHAR(30) NOT NULL DEFAULT '',
    passWd               VARCHAR(60) NOT NULL DEFAULT '',
    firstName			 VARCHAR(30) NOT NULL DEFAULT '',
    lastName			 VARCHAR(30) NOT NULL DEFAULT '',
    gender				 VARCHAR(10) NOT NULL DEFAULT '',
    
    userType             VARCHAR(30) NOT NULL DEFAULT '',   
    lastLoginTime        DATETIME DEFAULT '0000-00-00 00:00:00' NOT NULL ,
    available			 BOOL NOT NULL DEFAULT TRUE,
    
    PRIMARY KEY (id),
    UNIQUE key idx_user_login(login)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
