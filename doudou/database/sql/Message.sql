USE doudou;
DROP TABLE IF EXISTS Message;
CREATE TABLE Message
(
	id					 INT UNSIGNED NOT NULL AUTO_INCREMENT,
	title				 VARCHAR(30) NOT NULL DEFAULT '',
	content				 TEXT NOT NULL,
	messageTypeId		 INT UNSIGNED NOT NULL, 
	userId               INT UNSIGNED NOT NULL, 
    atChildList			 TEXT NOT NULL,
	publishTime			 DATETIME DEFAULT '0000-00-00 00:00:00' NOT NULL,
	publishLevel		 enum('School', 'Class') NOT NULL DEFAULT 'School',
	mustFeedBack		 BOOL NOT NULL DEFAULT FALSE,
	available			 BOOL NOT NULL DEFAULT TRUE,
	
    PRIMARY KEY (id),
    index idx_message_userId(userId)
    
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
