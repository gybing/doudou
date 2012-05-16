USE mayaya;
DROP TABLE IF EXISTS Friends_From_To;
CREATE TABLE Friends_From_To
(
	friendsID			 INT UNSIGNED NOT NULL AUTO_INCREMENT,
    fromUserID           INT UNSIGNED NOT NULL, 
    toChildID			 INT UNSIGNED NOT NULL,
    confirmed			 BOOL NOT NULL DEFAULT false,
    
    PRIMARY KEY (friendsID),
    index idx_friend_fromUserID(fromUserID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
