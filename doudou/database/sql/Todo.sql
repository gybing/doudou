USE doudou;
DROP TABLE IF EXISTS Todo;
CREATE TABLE Todo
(
	id					 INT UNSIGNED NOT NULL AUTO_INCREMENT,
    userId               INT UNSIGNED NOT NULL, 
    todoType			 enum('Friend', 'Comment', 'Picture', 'Event','Message','Modified') NOT NULL DEFAULT 'Comment',
    contentId			 INT UNSIGNED NOT NULL DEFAULT 0,
    todoStatus		     enum('Done','UnDone') NOT NULL DEFAULT 'UnDone',
    
    PRIMARY KEY (id),
    index idx_Todo_userID(userId)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
