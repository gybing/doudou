USE doudou;
DROP TABLE IF EXISTS EventLog;
CREATE TABLE EventLog
(
	logId				 INT UNSIGNED NOT NULL AUTO_INCREMENT,
	id					 INT UNSIGNED NOT NULL,
	content				 TEXT NOT NULL,
	beginTime			 DATETIME DEFAULT '0000-00-00 00:00:00' NOT NULL,
    endTime 			 DATETIME DEFAULT '0000-00-00 00:00:00' NOT NULL,
	userId               INT UNSIGNED NOT NULL, 
    title				 VARCHAR(100) NOT NULL DEFAULT '',
    location			 VARCHAR(100) NOT NULL DEFAULT '',
    atChildList			 TEXT NOT NULL,
    publishTime			 DATETIME DEFAULT '0000-00-00 00:00:00' NOT NULL,
    allday				 BOOL NOT NULL DEFAULT FALSE,
    userName			 VARCHAR(100) NOT NULL DEFAULT '',
    operationType		 enum('Add','Update','Delete') NOT NULL DEFAULT 'Add',
    
    PRIMARY KEY (logId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
