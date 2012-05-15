USE mayaya;
DROP TABLE IF EXISTS Relations_Child_User;
CREATE TABLE Relations_Child_User
(
	relationID			 INT UNSIGNED NOT NULL AUTO_INCREMENT,
    childID              INT UNSIGNED NOT NULL, 
    userID				 INT UNSIGNED NOT NULL,
    userType			 enum('Teacher', 'Parents', 'Both', 'Follower') NOT NULL DEFAULT 'Parents',
    
    PRIMARY KEY (relationID),
    index idx_relations_childID(childID),
    index idx_relations_userID(userID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
