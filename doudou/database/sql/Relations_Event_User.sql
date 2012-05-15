USE mayaya;
DROP TABLE IF EXISTS Relations_Event_User;
CREATE TABLE Relations_Event_User
(
	relationID			 INT UNSIGNED NOT NULL AUTO_INCREMENT,
    eventID            	 INT UNSIGNED NOT NULL, 
    toChildID			 INT UNSIGNED NOT NULL,
    self				 BOOL NOT NULL DEFAULT FALSE,
    
    PRIMARY KEY (relationID),
    index idx_peu_toUserID(toChildId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
