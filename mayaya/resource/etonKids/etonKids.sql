USE mayaya;

insert into User (login, passwd,firstName,lastName,userDescription,telephone,email,userType,gender) values

('jv.vallee@etonkids.com',MD5('888888'),'Jemina','Vallee','Etonkids','18600829611','jv.vallee@etonkids.com','Teacher','Female'),
('anna@zhenfund.com',MD5('888888'),'Anna','Fang','','13801075385','anna@zhenfund.com','Teacher','Female'),
('account@VC.com',MD5('888888'),'FirstName','LastName','','12345678912','','Teacher','Male'),
('account2@VC.com',MD5('888888'),'A','Teacher','','12345678913','','Teacher','Male'),
('asomani8@gmail.com',MD5('888888'),'Alok','Somani','','13621893424','asomani8@gmail.com','Teacher','Male');

insert into Relations_Child_User values

(0,27,104,'Teacher'),
(0,28,104,'Teacher'),

(0,27,105,'Teacher'),
(0,28,105,'Teacher'),

(0,22,104,'Teacher'),
(0,48,104,'Teacher'),

(0,22,105,'Teacher'),
(0,48,105,'Teacher'),

(0,27,106,'Teacher'),
(0,28,106,'Teacher'),

(0,22,106,'Teacher'),
(0,48,106,'Teacher'),

(0,27,107,'Teacher'),
(0,28,107,'Teacher'),

(0,22,107,'Teacher'),
(0,48,107,'Teacher'),

(0,27,109,'Teacher'),
(0,28,109,'Teacher'),

(0,22,109,'Teacher'),
(0,48,109,'Teacher');