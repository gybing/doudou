USE mayaya;

insert into Child (childID,classes,firstName,lastName,gender,birthDate,photoURL) values
(31,'TFLH-Toddler One','Chloe','Cui','Female','2009-5-22','user/ToddlerOne-Chloe.png'),
(32,'TFLH-Toddler One','Bow','Hughes','Female','2009-5-24','user/ToddlerOne-Bow.png'),
(33,'TFLH-Toddler One','Ethan','Auyeung','Male','2009-5-25','user/ToddlerOne-Ethan.png'),
(34,'TFLH-Toddler One','Youxi','Cao','Male','2009-6-4','user/ToddlerOne-Caoyuxi.png'),
(35,'TFLH-Toddler One','Maia','Roark','Female','2009-6-9','user/ToddlerOne-Maia.png'),
(36,'TFLH-Toddler One','Joshua','Barker','Male','2009-6-11','user/ToddlerOne-Joshua.png'),
(37,'TFLH-Toddler One','Hana','deUirron','Female','2009-7-5','user/ToddlerOne-Hana.png'),
(38,'TFLH-Toddler One','Julian','Liu','Male','2009-7-6',''),
(39,'TFLH-Toddler One','Wing Kah','Siew','Male','2009-7-15','user/ToddlerOne-Siewwingkah.png'),
(40,'TFLH-Toddler One','Donghang','Liu','Male','2009-7-17','user/ToddlerOne-LiuDonghang.png'),
(41,'TFLH-Toddler One','Heejun','Kim','Male','2009-8-26','user/ToddlerOne-KimHeejun.png'),
(42,'TFLH-Toddler One','Thierry','Rahal','Male','2009-9-16','user/ToddlerOne-Thierry.png'),
(43,'TFLH-Toddler One','Sierra','Lee','Female','2009-10-4','user/ToddlerOne-Sierra.png'),
(44,'TFLH-Toddler One','William','Chovanec','Male','2009-10-29','user/ToddlerOne-William.png'),
(45,'TFLH-Toddler One','Sebastian','Beltrame','Male','2009-11-20','user/ToddlerOne-Sebastian.png'),
(46,'TFLH-Toddler One','Ellen','Richards','Female','2009-12-8','user/ToddlerOne-Ellen.png'),
(47,'TFLH-Toddler One','Yoonhun','Kim','Male','2010-3-3','user/ToddlerOne-KimYoonhun.png'),
(48,'TFLH-Toddler One','Max','Johansen','Male','2010-4-29','user/ToddlerOne-Max.png'),
(49,'TFLH-Toddler One','Taani','Shukla','Female','2010-5-31','');

insert into User (login, passwd,firstName,lastName,userDescription,telephone,email,userType,gender) values

('Nicole',MD5('888888'),'Nicole','','TFLH','13701206397','','Teacher','Female');


insert into Relations_Child_User values
(0,31,62,'Teacher'),
(0,32,62,'Teacher'),
(0,33,62,'Teacher'),
(0,34,62,'Teacher'),
(0,35,62,'Teacher'),
(0,36,62,'Teacher'),
(0,37,62,'Teacher'),
(0,38,62,'Teacher'),
(0,39,62,'Teacher'),
(0,40,62,'Teacher'),
(0,41,62,'Teacher'),
(0,42,62,'Teacher'),
(0,43,62,'Teacher'),
(0,44,62,'Teacher'),
(0,45,62,'Teacher'),
(0,46,62,'Teacher'),
(0,47,62,'Teacher'),
(0,48,62,'Teacher'),
(0,49,62,'Teacher');

insert into User (login,firstName,lastName,telephone,email,userType,gender) values

('daniayd512@hotmail.com','Dan','Yu','18641807512','daniayd512@hotmail.com','Parents','Female'),
('tzcui@hotmail.com','Tianzhi','Cui','18641807522','tzcui@hotmail.com','Parents','Male'),

('alexchienn@yahoo.com','Alex','Hughes','18611134413','alexchienn@yahoo.com','Parents','Female'),
('ben@hugheswho.com','Ben','Hughes','18601200934','ben@hugheswho.com','Parents','Male'),

('emilyoyuan@hotmail.com','Emily','Cheng','13910881844','emilyoyuan@hotmail.com','Parents','Female'),
('shanoyuan@gmail.com','Shan','O-Yuan','13901012827','shanoyuan@gmail.com','Parents','Male'),

('luluxiang@yahoo.cn','Lulu','Xiang','18611949335','luluxiang@yahoo.cn','Parents','Female'),
('wei.cao@warburgpincus.com','Wei','Cao','13910060335','wei.cao@warburgpincus.com','Parents','Male'),

('laura.roark@gmail.com','Laura','Roark','13522748238','laura.roark@gmail.com','Parents','Female'),

('nolabarker@gmail.com','Nola','Barker','13910794849','nolabarker@gmail.com','Parents','Female'),
('1970.cfjb@googlemail.com','James','Barker','13511080549','1970.cfjb@googlemail.com','Parents','Male'),


('weiweideng2000@gmail.com','Deng','Wei','13911402229','weiweideng2000@gmail.com','Parents','Female'),
('stevenliu@gmail.com','Steven','Liu','13911863166','stevenliu@gmail.com','Parents','Male'),

('isvicki@hotmail.com','Weizhi','Ye','13699294601','isvicki@hotmail.com','Parents','Female'),
('Zhwenxiong@doudoumobile','Zhwenxiong','Siew','','','Parents','Male'),

('zhengrong@robartsinteriors.com','Rong','Zheng','13910314651','zhengrong@robartsinteriors.com','Parents','Female'),
('liuwuyi@crystalcg.com','Wuyi','Liu','13501007964','liuwuyi@crystalcg.com','Parents','Male'),

('Jiali@doudoumobile.com','Jiali','Yu','13901108451','simeon.todo@gmail.com','Parents','Female'),
('simeon.todo@gmail.com','Tae Youn','Kim','18611855358','simeon.todo@gmail.com','Parents','Male'),

('Wenxiao@doudoumobile','Wenxiao','Li','','','Parents','Female'),
('rony.rahal@tellmemore.cn','Rony','Rahal','18601083169','rony.rahal@tellmemore.cn','Parents','Male'),

('polly.chow@gmail.com','Polly','Chow','13601313145','polly.chow@gmail.com','Parents','Female'),
('andrewwlee@gmail.com','Andy','Lee','13501087262','andrewwlee@gmail.com','Parents','Male'),

('francesch@gmail.com','Fang','Chen','13910523060','francesch@gmail.com','Parents','Female'),
('prchovanec@gmail.com','Patrick','Chovanec','13439473204','prchovanec@gmail.com','Parents','Male'),

('federica.beltrame@me.com','Federica','Beltrame','13701137025','federica.beltrame@me.com','Parents','Female'),
('alegandro.a@mac.com','Alejandro','Augusto','13811871970','alegandro.a@mac.com','Parents','Male'),

('ednalynne.salvo@gmail.com','Edna','Lynne','13717718724','ednalynne.salvo@gmail.com','Parents','Female'),
('alunrichards@bakerhughes.com','John','Wyn','13466308223','alunrichards@bakerhughes.com','Parents','Male'),

('brandy.cho@pjinterior.com','Sooyeun','Cho','13910129443','brandy.cho@pjinterior.com','Parents','Female'),
('bokim@kingandwood.com','','Kim','13910658843','bokim@kingandwood.com','Parents','Male'),


('tanumittra@yahoo.co.in','Tanu','Shukla','15510271527','tanumittra@yahoo.co.in','Parents','Female'),
('n.shukla@shell.com','Nitin','Shukla','13810960974','n.shukla@shell.com','Parents','Male');


insert into Relations_Child_User values
(0,48,40,'Parents'),
(0,48,41,'Parents'),

(0,31,63,'Parents'),
(0,31,64,'Parents'),

(0,32,65,'Parents'),
(0,32,66,'Parents'),

(0,33,67,'Parents'),
(0,33,68,'Parents'),

(0,34,69,'Parents'),
(0,34,70,'Parents'),

(0,35,71,'Parents'),

(0,36,72,'Parents'),
(0,36,73,'Parents'),

(0,37,46,'Parents'),

(0,38,74,'Parents'),
(0,38,75,'Parents'),

(0,39,76,'Parents'),
(0,39,77,'Parents'),

(0,40,78,'Parents'),
(0,40,79,'Parents'),

(0,41,80,'Parents'),
(0,41,81,'Parents'),

(0,42,82,'Parents'),
(0,42,83,'Parents'),

(0,43,84,'Parents'),
(0,43,85,'Parents'),

(0,44,86,'Parents'),
(0,44,87,'Parents'),

(0,45,88,'Parents'),
(0,45,89,'Parents'),

(0,46,90,'Parents'),
(0,46,91,'Parents'),

(0,47,92,'Parents'),
(0,47,93,'Parents'),

(0,49,94,'Parents'),
(0,49,95,'Parents');

insert into User (login, passwd,firstName,lastName,userDescription,telephone,email,userType,gender) values

('TiffanyLiu@doudoumobile.com',MD5('888888'),'Tiffany','Liu','TFLH','15901341856','','Teacher','Female');

insert into Relations_Child_User values

(0,31,96,'Teacher'),
(0,32,96,'Teacher'),
(0,33,96,'Teacher'),
(0,34,96,'Teacher'),
(0,35,96,'Teacher'),
(0,36,96,'Teacher'),
(0,37,96,'Teacher'),
(0,38,96,'Teacher'),
(0,39,96,'Teacher'),
(0,40,96,'Teacher'),
(0,41,96,'Teacher'),
(0,42,96,'Teacher'),
(0,43,96,'Teacher'),
(0,44,96,'Teacher'),
(0,45,96,'Teacher'),
(0,46,96,'Teacher'),
(0,47,96,'Teacher'),
(0,48,96,'Teacher'),
(0,49,96,'Teacher');


insert into Relations_Child_User values

(0,31,71,'Teacher'),
(0,32,71,'Teacher'),
(0,33,71,'Teacher'),
(0,34,71,'Teacher'),
(0,36,71,'Teacher'),
(0,37,71,'Teacher'),
(0,38,71,'Teacher'),
(0,39,71,'Teacher'),
(0,40,71,'Teacher'),
(0,41,71,'Teacher'),
(0,42,71,'Teacher'),
(0,43,71,'Teacher'),
(0,44,71,'Teacher'),
(0,45,71,'Teacher'),
(0,46,71,'Teacher'),
(0,47,71,'Teacher'),
(0,48,71,'Teacher'),
(0,49,71,'Teacher');