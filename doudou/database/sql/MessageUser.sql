USE doudou;
DROP TABLE IF EXISTS MessageUser;
CREATE TABLE MessageUser
(
	id		 			 INT UNSIGNED NOT NULL AUTO_INCREMENT,
    messageId            INT UNSIGNED NOT NULL, 
    toChildId			 INT UNSIGNED NOT NULL,
	readStatus			 BOOLEAN NOT NULL DEFAULT FALSE,
	readerName			 VARCHAR(100) NOT NULL DEFAULT '',
	feedbackContent		 VARCHAR(100) NOT NULL DEFAULT '',
	feedbackUser		 VARCHAR(20) NOT NULL DEFAULT '',
	childClassId		 INT UNSIGNED NOT NULL, 
	
    PRIMARY KEY (id),
    index idx_mu_toChildId(toChildId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
