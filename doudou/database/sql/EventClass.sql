USE doudou;
DROP TABLE IF EXISTS EventClass;
CREATE TABLE EventClass
(
	id					 INT UNSIGNED NOT NULL AUTO_INCREMENT,
	eventId			 	 INT UNSIGNED NOT NULL DEFAULT 0,
	classId				 INT UNSIGNED NOT NULL DEFAULT 0,
	schoolId			 INT UNSIGNED NOT NULL DEFAULT 0,
	available			 BOOL NOT NULL DEFAULT TRUE,
	
    PRIMARY KEY (id),
    index idx_eventclass_eventId(eventId)
    
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
