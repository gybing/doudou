USE mayaya;
DROP TABLE IF EXISTS Comment;
CREATE TABLE Comment
(
	commentID			 INT UNSIGNED NOT NULL AUTO_INCREMENT,
	content				 VARCHAR(255) NOT NULL DEFAULT '',
    publishTime			 DATETIME DEFAULT '0000-00-00 00:00:00' NOT NULL,
	userID               INT UNSIGNED NOT NULL, 
    pictureID	 		 INT UNSIGNED NOT NULL,
    
    PRIMARY KEY (commentID),
    index idx_comment_pictureId(pictureID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
