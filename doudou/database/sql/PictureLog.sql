USE doudou;
DROP TABLE IF EXISTS PictureLog;
CREATE TABLE PictureLog
(
	logId				 INT UNSIGNED NOT NULL AUTO_INCREMENT,
	id					 INT UNSIGNED NOT NULL,
    publishTime			 DATETIME DEFAULT '0000-00-00 00:00:00' NOT NULL,
	userId               INT UNSIGNED NOT NULL, 
	userName			 VARCHAR(100) NOT NULL DEFAULT '',
    pictureURL   		 VARCHAR(255) NOT NULL DEFAULT '',
    description			 VARCHAR(255) NOT NULL DEFAULT '',
    messageLevel		 enum('School', 'Class') NOT NULL DEFAULT 'School',
    atChildList			 TEXT NOT NULL,
    operationType		 enum('Add','Update','Delete') NOT NULL DEFAULT 'Add',
    
    PRIMARY KEY (logId),
    key idx_pictureLog_query(publishTime,userId,userName)
    
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
