USE doudou;
DROP TABLE IF EXISTS Event;
CREATE TABLE Event
(
	id					 INT UNSIGNED NOT NULL AUTO_INCREMENT,
	content				 TEXT NOT NULL,
	beginTime			 DATETIME DEFAULT '0000-00-00 00:00:00' NOT NULL,
    endTime 			 DATETIME DEFAULT '0000-00-00 00:00:00' NOT NULL,
	userId               INT UNSIGNED NOT NULL, 
    title				 VARCHAR(100) NOT NULL DEFAULT '',
    location			 VARCHAR(100) NOT NULL DEFAULT '',
    atChildList			 TEXT NOT NULL,
    publishTime			 DATETIME DEFAULT '0000-00-00 00:00:00' NOT NULL,
    allday				 BOOL NOT NULL DEFAULT FALSE,
    publishLevel		 enum('School', 'Class') NOT NULL DEFAULT 'School',
    available			 BOOL NOT NULL DEFAULT TRUE,
    
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
