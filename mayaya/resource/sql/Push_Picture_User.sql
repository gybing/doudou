USE mayaya;
DROP TABLE IF EXISTS Push_Picture_User;
CREATE TABLE Push_Picture_User
(
	push_Pic_UserID		 INT UNSIGNED NOT NULL AUTO_INCREMENT,
    pictureID            INT UNSIGNED NOT NULL, 
    toChildId			 INT UNSIGNED NOT NULL,
    publishTime			 DATETIME DEFAULT '0000-00-00 00:00:00' NOT NULL,
    self				 BOOL NOT NULL DEFAULT FALSE,
    
    PRIMARY KEY (push_Pic_UserID),
    index idx_ppu_toUserID(toChildId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
