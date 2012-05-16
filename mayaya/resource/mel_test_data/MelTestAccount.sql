USE mayaya;

insert into User (login, passwd,firstName,lastName,userDescription,telephone,email,userType,gender) values

('jette.ebbe@gmail.com',MD5('888888'),'Ebbe','Johansen','','19820413001','jette.ebbe@gmail.com','Follower','Male'),
('cblj@hillerod.dk',MD5('888888'),'Christian','Johansen','','19820413002','cblj@hillerod.dk','Follower','Male'),
('evelyn_huangfu@yahoo.com',MD5('888888'),'Evelyn','Huangfu','','19820413003','evelyn_huangfu@yahoo.com','Follower','Female'),
('Jean.Kam@gs.com',MD5('888888'),'Jean','Kam','','19820413004','Jean.Kam@gs.com','Follower','Female'),
('Amy.Lee@gs.com',MD5('888888'),'Amy','Lee','','19820413005','Amy.Lee@gs.com','Follower','Female'),
('shanyankoder@me.com',MD5('888888'),'Shanyan','Fok','','19820413006','shanyankoder@me.com','Follower','Male'),
('JDu@mofo.com',MD5('888888'),'June','Du','','19820413007','JDu@mofo.com','Follower','Male');

insert into Relations_Child_User values

(0,22,97,'Follower'),
(0,48,97,'Follower'),

(0,22,98,'Follower'),
(0,48,98,'Follower'),

(0,22,99,'Follower'),
(0,48,99,'Follower'),

(0,22,100,'Follower'),
(0,48,100,'Follower'),

(0,22,101,'Follower'),
(0,48,101,'Follower'),

(0,22,102,'Follower'),
(0,48,102,'Follower'),

(0,22,103,'Follower'),
(0,48,103,'Follower');
