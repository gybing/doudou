USE mayaya;
DROP TABLE IF EXISTS Todo;
CREATE TABLE Todo
(
	todoID				 INT UNSIGNED NOT NULL AUTO_INCREMENT,
    userID               INT UNSIGNED NOT NULL, 
    todoType			 enum('Friend', 'Comment', 'PicTag', 'EvtTag','Announcement') NOT NULL DEFAULT 'Comment',
    contentId			 INT UNSIGNED NOT NULL DEFAULT 0,
    status				 enum('Done','UnDone') NOT NULL DEFAULT 'UnDone',
    
    PRIMARY KEY (todoID),
    index idx_Todo_userID(userID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
