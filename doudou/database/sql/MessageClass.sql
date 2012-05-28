USE doudou;
DROP TABLE IF EXISTS MessageClass;
CREATE TABLE MessageClass
(
	id					 INT UNSIGNED NOT NULL AUTO_INCREMENT,
	messageId			 INT UNSIGNED NOT NULL DEFAULT 0,
	classId				 INT UNSIGNED NOT NULL DEFAULT 0,
	schoolId			 INT UNSIGNED NOT NULL DEFAULT 0,
	available			 BOOL NOT NULL DEFAULT TRUE,
	
    PRIMARY KEY (id),
    index idx_messageclass_messageId(messageId)
    
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
