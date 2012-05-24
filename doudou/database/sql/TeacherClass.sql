USE doudou;
DROP TABLE IF EXISTS TeacherClass;
CREATE TABLE TeacherClass
(
	id				 	INT UNSIGNED NOT NULL AUTO_INCREMENT,
    teacherId           INT UNSIGNED NOT NULL DEFAULT 0,
    classId             INT UNSIGNED NOT NULL DEFAULT 0,
    teacherTypeId		INT UNSIGNED NOT NULL DEFAULT 0,	
    available			BOOL NOT NULL DEFAULT TRUE,
    
    PRIMARY KEY (id),
    index idx_tc_classId(classId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
