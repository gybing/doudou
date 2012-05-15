USE doudou;
DROP TABLE IF EXISTS Teacher;
CREATE TABLE Teacher
(
    teacherId	         INT UNSIGNED NOT NULL , 
    description		 	 VARCHAR(128) NOT NULL DEFAULT '',
    
    telephone			 VARCHAR(20) NOT NULL DEFAULT '',
    mobile				 VARCHAR(20) NOT NULL DEFAULT '',
    workEmail			 VARCHAR(20) NOT NULL DEFAULT '',
    privateEmail		 VARCHAR(20) NOT NULL DEFAULT '',
    contact1			 VARCHAR(20) NOT NULL DEFAULT '',
    contact2			 VARCHAR(20) NOT NULL DEFAULT '',
    otherInfo			 VARCHAR(40) NOT NULL DEFAULT '',
    
    PRIMARY KEY (teacherId),
    CONSTRAINT teacher_ibfk_1 FOREIGN KEY (teacherId) REFERENCES User (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
