USE doudou;
DROP TABLE IF EXISTS DouDouInfoType;
CREATE TABLE DouDouInfoType
(
	id				 	INT UNSIGNED NOT NULL AUTO_INCREMENT,
    infoType            enum('TeacherType', 'MessageType') NOT NULL DEFAULT 'TeacherType',
    typeName			VARCHAR(30) NOT NULL DEFAULT '',
    schoolId			INT UNSIGNED NOT NULL AUTO_INCREMENT,
    available			BOOL NOT NULL DEFAULT TRUE,
    
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
