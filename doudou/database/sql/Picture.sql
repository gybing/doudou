USE mayaya;
DROP TABLE IF EXISTS Picture;
CREATE TABLE Picture
(
	id					 INT UNSIGNED NOT NULL AUTO_INCREMENT,
    publishTime			 DATETIME DEFAULT '0000-00-00 00:00:00' NOT NULL,
	userId               INT UNSIGNED NOT NULL, 
    pictureURL   		 VARCHAR(255) NOT NULL DEFAULT '',
    description			 VARCHAR(255) NOT NULL DEFAULT '',
    publishLevel		 enum('School', 'Class') NOT NULL DEFAULT 'School',
    atChildList			 TEXT NOT NULL,
    
    PRIMARY KEY (id),
    key idx_picture_query(publishTime,userId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
