USE doudou;
DROP TABLE IF EXISTS EventUser;
CREATE TABLE EventUser
(
	id			 		 INT UNSIGNED NOT NULL AUTO_INCREMENT,
    eventId            	 INT UNSIGNED NOT NULL, 
    toChildId			 INT UNSIGNED NOT NULL,
    
    PRIMARY KEY (id),
    index idx_eu_toChildId(toChildId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
