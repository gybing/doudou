USE doudou;
DROP TABLE IF EXISTS PictureUser;
CREATE TABLE PictureUser
(
	id		 			 INT UNSIGNED NOT NULL AUTO_INCREMENT,
    pictureId            INT UNSIGNED NOT NULL, 
    toChildId			 INT UNSIGNED NOT NULL,
    
    PRIMARY KEY (id),
    index idx_pu_toChildId(toChildId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
