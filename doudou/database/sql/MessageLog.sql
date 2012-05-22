USE doudou;
DROP TABLE IF EXISTS MessageLog;
CREATE TABLE MessageLog
(
	logId				 INT UNSIGNED NOT NULL AUTO_INCREMENT,
	id					 INT UNSIGNED NOT NULL,
    publishTime			 DATETIME DEFAULT '0000-00-00 00:00:00' NOT NULL,
	userId               INT UNSIGNED NOT NULL, 
	userName			 VARCHAR(100) NOT NULL DEFAULT '',
    title				 VARCHAR(30) NOT NULL DEFAULT '',
	content				 TEXT NOT NULL,
	messageType			 VARCHAR(30) NOT NULL DEFAULT '',
    toWhoList			 TEXT NOT NULL,
	messageLevel		 enum('School', 'Class') NOT NULL DEFAULT 'School',
	mustFeedBack		 BOOL NOT NULL DEFAULT FALSE,
    operationType		 enum('Add','Update','Delete') NOT NULL DEFAULT 'Add',
    
    PRIMARY KEY (logId),
    key idx_pictureLog_query(publishTime,userId,userName)
    
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
