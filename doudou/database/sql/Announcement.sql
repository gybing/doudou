USE mayaya;
DROP TABLE IF EXISTS Announcement;
CREATE TABLE Announcement
(
	announcementId		 INT UNSIGNED NOT NULL AUTO_INCREMENT,
	userId               INT UNSIGNED NOT NULL, 
	content				 TEXT NOT NULL,
    atChildList			 TEXT NOT NULL,
	publishTime			 DATETIME DEFAULT '0000-00-00 00:00:00' NOT NULL,
	
    PRIMARY KEY (announcementId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
