
drop table channeldata;
drop table categorydata;
drop table userdata;


CREATE TABLE USERDATA (
subscriberId integer primary key,
username varchar(50),
usersurname varchar(50),
password varchar(20),
address varchar(100),
city varchar(50),
state varchar(50),
country varchar(50),
mobileNo integer,
email varchar(50));


CREATE TABLE CATEGORYDATA(
categoryid integer primary key,
name varchar(50),
serviceProvider varchar(30),
imgurl varchar(200),
language varchar(20),
genre varchar(20),
quality varchar(10));


CREATE TABLE CHANNELDATA(
channelId integer primary key,
name varchar(50),
categoryid integer,
imgurl varchar(50),
genre varchar(20),
hd varchar(5));

CREATE TABLE SHOWSDATA(
showId integer primary key,
showName varchar(50),
channelId integer,
timing varchar(50));



insert into userdata values(99999, 'bookreader', 'brusername', 'br123', 'Hebbal', 'Mysore','Karnataka','India','8971533637','bookreader@abc.com');

insert into categorydata values(10001, 'Movies', 'Three Cheers Cable', '/icons/blazer', 'English', 'Entertainment','480p');
insert into categorydata values(10002, 'Music', 'Three Cheers Cable', '/icons/mavrik', 'English', 'Entertainment','1080p');
insert into categorydata values(10003, 'Sports', 'Three Cheers Cable', '/icons/nayan', 'Hindi', 'Sports','720p');
insert into categorydata values(10004, 'Entertainment', 'Three Cheers Cable', '/icons/sigma', 'Spanish', 'Regional','1080p');
insert into categorydata values(10005, 'Kids', 'Three Cheers Cable', '/icons/spark', 'English','Education','720p');
insert into categorydata values(10006, 'Lifestyle', 'Three Cheers Cable', '/icons/edrace', 'English', 'Entertainment','720p');


insert into channeldata values(3243542,'3C Drama',10001, '','Entertainment','yes'); 
insert into channeldata values(3243543,'3C Comedy',10001, '','Entertainment','yes'); 
insert into channeldata values(3243544,'3C Music',10002, '','Entertainment','yes'); 
insert into channeldata values(3243545,'3C Melody',10002, '','Entertainment','yes'); 
insert into channeldata values(3243546,'3C Rock',10002, '','Entertainment','yes'); 
insert into channeldata values(3243547,'3C Tunes',10002, '','Entertainment','yes'); 
insert into channeldata values(3243548,'3C Hockey',10003, '','Entertainment','yes'); 
insert into channeldata values(3243549,'3C Cricket',10003, '','Entertainment','yes'); 
insert into channeldata values(3243550,'3C Football',10003, '','Entertainment','yes'); 
insert into channeldata values(3243551,'3C Cinema',10004, '','Regional','yes'); 
insert into channeldata values(3243552,'3C Theatre',10004, '','Regional','yes'); 
insert into channeldata values(3243553,'3C Action',10004, '','Regional','yes'); 
insert into channeldata values(3243554,'3C Cartoons',10005, '','Education','yes'); 
insert into channeldata values(3243555,'3C Animation',10005, '','Education','yes'); 
insert into channeldata values(3243556,'3C Interior',10006, '','Entertainment','yes');
insert into channeldata values(3243557,'3C Shows ',10006, '','Entertainment','yes');

insert into showsdata values(121,'Hollywood Movie',3243542,'8:00 - 12:00');
insert into showsdata values(122,'Bollywood Movie',3243542,'12:00 - 15:00');
insert into showsdata values(123,'Chinese Movie',3243542,'15:00 - 18:00');


insert into showsdata values(124,'Tamil Movie',3243543,'8:00 - 12:00');
insert into showsdata values(125,'Telugu Movie',3243543,'12:00 - 15:00');
insert into showsdata values(126,'Malayalam Movie',3243543,'15:00 - 18:00');

insert into showsdata values(127,'Tamil Movie',3243544,'8:00 - 12:00');
insert into showsdata values(128,'Telugu Movie',3243544,'12:00 - 15:00');
insert into showsdata values(129,'Malayalam Movie',3243544,'15:00 - 18:00');

insert into showsdata values(130,'Tamil Songs',3243545,'8:00 - 12:00');
insert into showsdata values(131,'Telugu Songs',3243545,'12:00 - 15:00');
insert into showsdata values(132,'Malayalam Songs',3243545,'15:00 - 18:00');

insert into showsdata values(133,'Tamil Songs',3243546,'8:00 - 12:00');
insert into showsdata values(134,'Telugu Songs',3243546,'12:00 - 15:00');
insert into showsdata values(135,'Malayalam Songs',3243546,'15:00 - 18:00');

insert into showsdata values(136,'Hindi Songs',3243546,'8:00 - 12:00');
insert into showsdata values(137,'Arabic Songs',3243546,'12:00 - 15:00');
insert into showsdata values(138,'Bengali Songs',3243546,'15:00 - 18:00');

insert into showsdata values(139,'Tamil Songs',3243547,'8:00 - 12:00');
insert into showsdata values(140,'Telugu Songs',3243547,'12:00 - 15:00');
insert into showsdata values(141,'English Songs',3243547,'15:00 - 18:00');

insert into showsdata values(142,'AB cup',3243548,'8:00 - 12:00');
insert into showsdata values(143,'World cup',3243548,'12:00 - 15:00');
insert into showsdata values(144,'XYZ cup',3243548,'15:00 - 18:00');

insert into showsdata values(145,'PQR cup',3243548,'8:00 - 12:00');
insert into showsdata values(146,'ABC cup',3243548,'12:00 - 15:00');
insert into showsdata values(147,'Legends',3243548,'15:00 - 18:00');


insert into showsdata values(148,'RBC Cup',3243549,'8:00 - 12:00');
insert into showsdata values(149,'Olden days',3243549,'12:00 - 15:00');
insert into showsdata values(150,'XYZ legends',3243549,'15:00 - 18:00');

insert into showsdata values(151,'World cup',3243550,'8:00 - 12:00');
insert into showsdata values(152,'ABC cup',3243550,'12:00 - 15:00');
insert into showsdata values(153,'Legends',3243550,'15:00 - 18:00');

insert into showsdata values(154,'Tamil Movie',3243551,'8:00 - 12:00');
insert into showsdata values(155,'Telugu Movie',3243551,'12:00 - 15:00');
insert into showsdata values(156,'Malayalam Movie',3243551,'15:00 - 18:00');

insert into showsdata values(157,'Tamil Movie',3243552,'8:00 - 12:00');
insert into showsdata values(158,'Telugu Movie',3243552,'12:00 - 15:00');
insert into showsdata values(159,'Malayalam Movie',3243552,'15:00 - 18:00');

insert into showsdata values(160,'Tamil Movie',3243553,'8:00 - 12:00');
insert into showsdata values(161,'Telugu Movie',3243553,'12:00 - 15:00');
insert into showsdata values(162,'Malayalam Movie',3243553,'15:00 - 18:00');

insert into showsdata values(163,'Old cartoons',3243554,'8:00 - 12:00');
insert into showsdata values(164,'New cartoons',3243554,'12:00 - 15:00');
insert into showsdata values(165,'Yesteryear cartoons',3243554,'15:00 - 18:00');

insert into showsdata values(166,'Old cartoons',3243555,'8:00 - 12:00');
insert into showsdata values(167,'New cartoons',3243555,'12:00 - 15:00');
insert into showsdata values(168,'Yesteryear cartoons',3243555,'15:00 - 18:00');

insert into showsdata values(169,'Tamil Movie',3243556,'8:00 - 12:00');
insert into showsdata values(170,'Telugu Movie',3243556,'12:00 - 15:00');
insert into showsdata values(171,'Malayalam Movie',3243556,'15:00 - 18:00');

insert into showsdata values(172,'Tamil Movie',3243557,'8:00 - 12:00');
insert into showsdata values(173,'Telugu Movie',3243557,'12:00 - 15:00');
insert into showsdata values(174,'Malayalam Movie',3243557,'15:00 - 18:00');
