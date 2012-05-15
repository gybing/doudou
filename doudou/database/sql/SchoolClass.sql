USE doudou;
DROP TABLE IF EXISTS SchoolClass;
CREATE TABLE SchoolClass
(
	id				 	INT UNSIGNED NOT NULL AUTO_INCREMENT,
    schoolClassName     VARCHAR(30) NOT NULL DEFAULT '',
    description			VARCHAR(100) NOT NULL DEFAULT '',
    schoolId			INT UNSIGNED NOT NULL DEFAULT 0,
    available			BOOL NOT NULL DEFAULT TRUE,
    
    PRIMARY KEY (id),
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
