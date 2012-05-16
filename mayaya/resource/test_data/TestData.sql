USE mayaya;

insert into User (login, passwd,firstName,lastName,userDescription,telephone,email,userType,gender) values

('ChengSong.Sun',MD5('888888'),'成松','孙','兜兜测试账号的妈妈孙','15201167602','ChengSong.Sun@gmail.com','Parents','Female'),
('Yanjian',MD5('888888'),'健','yan','兜兜测试账号的爸爸健','15201157160','yanjian@gmail.com','Parents','Male'),
('Wangshuqiu',MD5('888888'),'TFLH','王','TFLH','18610641503','Vernon@gmail.com','Teacher','Male');

insert into Relations_Child_User values

(0,26,55,'Parents'),
(0,26,56,'Parents'),

(0,2,57,'Teacher'),
(0,3,57,'Teacher'),
(0,4,57,'Teacher'),
(0,5,57,'Teacher'),
(0,6,57,'Teacher'),
(0,7,57,'Teacher'),
(0,8,57,'Teacher'),
(0,9,57,'Teacher'),
(0,10,57,'Teacher'),
(0,11,57,'Teacher'),
(0,12,57,'Teacher'),
(0,13,57,'Teacher'),
(0,14,57,'Teacher'),
(0,15,57,'Teacher'),
(0,16,57,'Teacher'),
(0,17,57,'Teacher'),
(0,18,57,'Teacher'),
(0,19,57,'Teacher'),
(0,20,57,'Teacher'),
(0,21,57,'Teacher'),
(0,22,57,'Teacher'),
(0,23,57,'Teacher'),
(0,24,57,'Teacher'),
(0,25,57,'Teacher'),
(0,26,57,'Teacher');

insert into Child (childID,lastName,age,userStatus,photoURL,classes,lastLoginTime,gender,birthDate,description,nationality,address,enterDate,program,firstName)values
(26,'Child',0,'Valid','user/Head-26.png','Reception','0000-00-00 00:00:00','Male','2006-06-22 00:00:00','','USA/France','海淀大街海龙大厦','2008-01-07 00:00:00','FULL','Doudou');

insert into Child (childID,lastName,age,userStatus,photoURL,classes,lastLoginTime,gender,birthDate,description,nationality,address,enterDate,program,firstName)values
(27,'Yan',0,'Valid','user/Head-27.png','Mayaya-Primary','0000-00-00 00:00:00','Female','2008-04-13 00:00:00','','USA/France','Hailong','2008-01-07 00:00:00','FULL','Amy'),
(28,'Yan',0,'Valid','user/Head-28.png','Mayaya-Primary','0000-00-00 00:00:00','Male','2009-04-29 00:00:00','','USA/France','Hailong','2008-01-07 00:00:00','FULL','Bob'),
(29,'Yan',0,'Valid','user/Head-29.png','Mayaya-Toddle','0000-00-00 00:00:00','Female','2010-09-07 00:00:00','','USA/France','Hailong','2008-01-07 00:00:00','FULL','Susan'),
(30,'Yan',0,'Valid','user/Head-30.png','Mayaya-Toddle','0000-00-00 00:00:00','Male','2011-12-05 00:00:00','','USA/France','Hailong','2008-01-07 00:00:00','FULL','Qiuqiu');

insert into User (login, passwd,firstName,lastName,userDescription,telephone,email,userType,gender) values
('YanYangtian',MD5('888888'),'Yangtian','Yan','I am Yangtian','13810992393','vernon.wangshq@gmail.com','Parents','Male'),
('NaiCha',MD5('888888'),'NaiCha','Zhang','I am NaiCha','15116992926','chaogao@doudoumobile.com','Parents','Female');

insert into Relations_Child_User values

(0,27,57,'Teacher'),
(0,28,57,'Teacher'),
(0,29,57,'Teacher'),
(0,30,57,'Teacher');

insert into User (login, passwd,firstName,lastName,userDescription,telephone,email,userType,gender) values
('ADadTest',MD5('888888'),'A-Dad','Test','I am Test Dad','20120223001','chaogao@doudoumobile.com','Parents','Male'),
('AMomTest',MD5('888888'),'A-Mom','Test','I am Test Mom','20120223002','vernon@doudoumobile.com','Parents','Female');

insert into Relations_Child_User values

(0,27,60,'Parents'),
(0,27,61,'Parents'),

(0,28,60,'Parents'),
(0,28,61,'Parents'),

(0,29,60,'Parents'),
(0,29,61,'Parents'),

(0,30,60,'Parents'),
(0,30,61,'Parents');