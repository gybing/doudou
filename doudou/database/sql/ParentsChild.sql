USE doudou;
DROP TABLE IF EXISTS ParentsChild;
CREATE TABLE ParentsChild
(
    id	         		 INT UNSIGNED NOT NULL , 
    parentId             INT UNSIGNED NOT NULL DEFAULT 0,
    childId              INT UNSIGNED NOT NULL DEFAULT 0,
    relation			 enum('Father', 'Mother', 'Guardian') NOT NULL DEFAULT 'Father',
    available			 BOOL NOT NULL DEFAULT TRUE,
    
    chiefGuardian		 BOOL NOT NULL DEFAULT FALSE,
    
    PRIMARY KEY (id),
    index idx_tc_parentId(parentId),
    index idx_tc_childId(childId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

