USE doudou;
DROP TABLE IF EXISTS PictureComment;
CREATE TABLE PictureComment
(
	id					 INT UNSIGNED NOT NULL AUTO_INCREMENT,
	content				 VARCHAR(255) NOT NULL DEFAULT '',
    publishTime			 DATETIME DEFAULT '0000-00-00 00:00:00' NOT NULL,
	userId               INT UNSIGNED NOT NULL, 
    pictureId	 		 INT UNSIGNED NOT NULL,
    
    PRIMARY KEY (id),
    index idx_picComment_pictureId(pictureId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
