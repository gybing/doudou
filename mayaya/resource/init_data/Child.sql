USE mayaya;
TRUNCATE TABLE Child ;

insert into Child (childID,lastName,age,userStatus,photoURL,classes,lastLoginTime,gender,birthDate,description,nationality,address,enterDate,program,firstName)values
(1,'Children',0,'Valid','user/Head-1.png','TFLH-Reception','0000-00-00 00:00:00','Male','2006-10-03 00:00:00','All Children in Class TFLH-Reception','China','FLH','2000-01-01 00:00:00','FULL','ALL'),
(2,'Chen',0,'Valid','user/Head-2.png','TFLH-Reception','0000-00-00 00:00:00','Male','2006-10-03 00:00:00','','Hongkong','Hat 28A Block E1 Gemdole Int 1 Gdn 91 Janguo 11 Chaoyang,Beijing','2009-02-12 00:00:00', 'HALF','Alpha'),
(3,'Sayer',0,'Valid','user/Head-3.png','TFLH-Reception','0000-00-00 00:00:00','Female','2006-06-09 00:00:00','','Austrilian','#4505, Fortune Heights Apartment 北京财富中心','2010-01-04 00:00:00','HALF','Amy'),
(4,'Tsoi',0,'Valid','user/Head-4.png','TFLH-Reception','0000-00-00 00:00:00','Male','2006-04-11 00:00:00','','Hongkong','万科青青家园132-1-102', '2010-09-01 00:00:00' ,'FULL','Andre'),
(5,'Wang',0,'Valid','user/Head-5.png','TFLH-Reception','0000-00-00 00:00:00','Female','2007-03-09 00:00:00','','China','朝阳区万科东地1-2405','2009-05-01 00:00:00','FULL','Annie'),
(6,'He',0,'Valid','user/Head-6.png','TFLH-Reception','0000-00-00 00:00:00', 'Male','2006-06-30 00:00:00','','China','朝阳南十里居28号院6号2门1101','2009-04-06 00:00:00','HALF','Badi'),
(7,'Zhang',0,'Valid','user/Head-7.png','TFLH-Reception','0000-00-00 00:00:00', 'Male','2005-12-19 00:00:00','','China','朝阳区光驱门外大街31# 合生国际花园','2009-09-01 00:00:00','FULL','Bojian'),
(8,'Yang',0,'Valid','user/Head-8.png','TFLH-Reception','0000-00-00 00:00:00', 'Male','2007-01-12 00:00:00','','Hongkong','','2009-11-10 00:00:00', 'HALF','Canice'),
(9,'Wan',0,'Valid','user/Head-9.png','TFLH-Reception','0000-00-00 00:00:00', 'Male','2006-02-24 00:00:00','','China','增光路44号院6号楼3单元606','2010-09-01 00:00:00','FULL','Jason'),
(10,'MinSuh',0,'Valid','user/Head-10.png','TFLH-Reception','0000-00-00 00:00:00','Female','2005-12-27 00:00:00','','Korea','东三环中路16号世桥公寓2-605','2011-05-01 00:00:00','FULL','Joan'),
(11,'Leininger',0,'Valid','user/Head-11.png','TFLH-Reception','0000-00-00 00:00:00', 'Male', '2006-04-08 00:00:00','','USA','北京弘燕路山水文园1期4-6-902','2008-09-08 00:00:00','FULL','Jacob'),
(12,'Bao',0,'Valid','user/Head-12.png','TFLH-Reception','0000-00-00 00:00:00','Female','2006-06-23 00:00:00','','Canada','亦庄境界小区39楼801室','2009-04-06 00:00:00','FULL','Mya'),
(13,'Li',0,'Valid','user/Head-13.png','TFLH-Reception','0000-00-00 00:00:00', 'Female','2006-09-08 00:00:00','','China','北京西城区','2009-05-25 00:00:00','FULL','Luai'),
(14,'Wang',0,'Valid','user/Head-14.png','TFLH-Reception','0000-00-00 00:00:00', 'Male','2006-07-07 00:00:00','','China','北京方庄芳城花园一区9#-1604，马驹桥小周易村9号院','2008-03-10 00:00:00','FULL','Oddy'),
(15,'Yang',0,'Valid','user/Head-15.png','TFLH-Reception','0000-00-00 00:00:00', 'Female','2006-09-20 00:00:00','','Canada','朝阳区世桥国贸2号楼2503室','2009-06-01 00:00:00','FULL','Sophie'),
(16,'Jin',0,'Valid','user/Head-16.png','TFLH-Reception','0000-00-00 00:00:00', 'Male','2008-02-07 00:00:00','','USA','Green Lake Place, Bld.7, Unit 2, Apt.2605, 88 Dongsihuan Beilu','2010-09-08 00:00:00','FULL','Vinson'),
(17,'Schaffarceyk',0,'Valid','user/Head-17.png','TFLH-Reception','0000-00-00 00:00:00', 'Male','2006-05-28 00:00:00','','German','Pingod building 8B, app. 1502, Baizhewanlu 32','2008-03-10	00:00:00','FULL','Victor'),
(18,'Zhou',0,'Valid','user/Head-18.png','TFLH-Reception','0000-00-00 00:00:00','Female','2006-03-28 00:00:00','','China','朝阳区弘燕东路山水文园三期5号楼4单元402','2009-09-01	 00:00:00','FULL','Xiaohan'),
(19,'Siow',0,'Valid','user/Head-19.png','TFLH-Reception','0000-00-00 00:00:00', 'Male', '2006-08-21 00:00:00','','Singapore','Park 18T2, Building 3, Apt. 2004, No.218, 八里庄北里','2009-12-15 00:00:00','FULL','Jared'),
(20,'Wang',0,'Valid','user/Head-20.png','TFLH-Reception','0000-00-00 00:00:00', 'Female','2006-02-17 00:00:00','','America','金地国际花园D2座18A','2009-09-01 00:00:00','FULL','Jennifer'),
(21,'Tan',0,'Valid','user/Head-21.png','TFLH-Reception','0000-00-00 00:00:00','Male','2006-07-11 00:00:00','','China','北京市朝阳区东四环中路远洋天地69#2206','2009-04-09 00:00:00','FULL','Jerry'),
(22,'Johansen',0,'Valid','user/Head-22.png','TFLH-Reception','0000-00-00 00:00:00','Female',	'2006-11-19 00:00:00','','Danish','No.9 JianGuoMenDaJie, QiJiaYuan Compound, Apt 11-1-45','2009-04-02 00:00:00','FULL','Maya'),
(23,'Samuel',0,'Valid','user/Head-23.png','TFLH-Reception','0000-00-00 00:00:00', 'Male','2006-07-01 00:00:00','','China','朝阳区武圣东里9号楼2门201','2009-09-01 00:00:00','FULL','Samuel'),
(24,'Zhang',0,'Valid','user/Head-24.png','TFLH-Reception','0000-00-00 00:00:00','Female','2006-06-16 00:00:00','','China','北京市朝阳区西大望路59号5-6-302','2009-09-01 00:00:00','FULL','Xiaoyu'),
(25,'Vitton',0,'Valid','user/Head-25.png','TFLH-Reception','0000-00-00 00:00:00','Male','2006-06-22 00:00:00','','USA/France','朝阳建国门外大街#91金地国际花园D1-2601','2008-01-07 00:00:00','FULL','Taeho');

