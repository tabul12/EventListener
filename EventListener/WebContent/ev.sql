
drop database if exists  ev;
create database ev;

use ev;

create table User(
	ID int auto_increment not null,
	FirstName varchar(30),
	LastName varchar(30),
	UserName varchar(30) unique,
	Password varchar(30),
	Mail varchar(30),
	MobileNumber varchar(30),
	Image varchar(30),
	PRIMARY KEY (ID)
);
/*  */

create table Admin(
	ID int auto_increment not null,
	primary key(ID),
	UserID int,
	FOREIGN KEY (UserID) REFERENCES User(ID)	
);

create table Punished(
	ID int auto_increment not null,
	primary key(ID),
	UserID int,
	FOREIGN KEY (UserID) REFERENCES User(ID)		
);

create table Band(
	ID int auto_increment not null,
	primary key(ID),
	UserID int,
	Name varchar(30) unique,
	About text,
	Mail text,
	FOREIGN KEY (UserID) REFERENCES User(ID)	
);
 
create table Place(
	ID int auto_increment not null,
	primary key(ID),
	UserID int,
	Name varchar(30) unique,
	Adress varchar(30),
	About text,
	foreign key(UserID) references User(ID)
);

create table Event(
	ID int auto_increment not null,
	primary key(ID),
	UserID int,
	Name varchar(30) unique,
	PlaceID int,
	Time text,
	About text,
	Price varchar(10),
	Image varchar(30),
	foreign key(UserID) references User(ID),
	foreign key(PlaceID) references Place(ID)	
);

create table Genre(
	ID int auto_increment not null,
	primary key(ID),
	Name varchar(30) unique
);

/*  ==============  makavshirebeli cxrilebi  =================   */


create table User_Band_Wishlist(
	ID int auto_increment not null,
	primary key(ID),
	UserID int,
	BandID int,
	foreign key(UserID) references User(ID),
	foreign key(BandID) references Band(ID)
);

create table User_Going_Event(
	ID int auto_increment not null,
	primary key(ID),
	UserID int,
	EventID int,
	foreign key(UserID) references User(ID),
	foreign key(EventID) references Event(ID)
);
 

create table Band_On_Event(
	ID int auto_increment not null,
	primary key(ID),
	EventID int,
	BandID int,
	foreign key(EventID) references Event(ID),
	foreign key(BandID) references Band(ID)
);


create table Band_Plays_Genre(
	ID  int auto_increment not null,
	primary key(ID),
	BandID int,
	GenreID int,
	foreign key(BandID) references Band(ID),
	foreign key(GenreID) references Genre(ID)
);

create table Band_Image(
	ID  int auto_increment not null,
	primary key(ID),
	Name varchar(30) unique,
	BandID int,
	foreign key(BandID) references Band(ID)
);
/*
	select * from Band_Image
*/

create table Band_Profile_Image(
	ID  int auto_increment not null,
	primary key(ID),
	Band_ImageID int,
	BandID int,
	foreign key(BandID) references Band(ID),
	foreign key(Band_ImageID) references Band_Image(ID)
);


create table Music(
	ID int auto_increment not null,
	primary key(ID),
	BandID int,
	Name varchar(50) unique,
	foreign key(BandID) references Band(ID)	
);
/*insert into Music(BandID,Name) values(1,'radiohead5.mp3')
select * from Music  */
create table Video(
	ID int auto_increment not null,
	Primary key(ID),
	BandID int,
	Name varchar(50) unique,
	foreign key(BandID) references Band(ID)	
);
/* insert into Video(BandID,Name) values(1,'Maybe.mp4')    
 select * from Video
*/


create table Place_Image(
	ID int auto_increment not null,
	primary key(ID),
	Name varchar(50) unique,
	PlaceID int,
	foreign key(PlaceID) references Place(ID)
);


create table Place_Profile_Image(
	ID int auto_increment not null,
	primary key(ID),
	Place_ImageID int,
	PlaceID int,
	foreign key(PlaceID) references Place(ID),
	foreign key(Place_ImageID) references Place_Image(ID)
);


create table Band_Rating(
	ID int auto_increment not null,
	primary key(ID),
	UserID int,
	BandID int,
	EventID int,
	Rating int,
	foreign key(UserID) references User(ID),
	foreign key(BandID) references Band(ID),
	foreign key(EventID) references Event(ID)
);


create table Place_Rating(
	ID int auto_increment not null,
	primary key(ID),
	UserID int,
	PlaceID int,
	EventID int,
	Rating int,
	foreign key(UserID) references User(ID),
	foreign key(PlaceID) references Place(ID),
	foreign key(EventID) references Event(ID)
);


 

 
DELIMITER $$
CREATE FUNCTION placeAverageRating(ID int)
  RETURNS double
BEGIN
  DECLARE num double;
  SELECT avg(Rating) INTO num FROM Place_Rating WHERE PlaceID = ID;
return num;
END;
$$
DELIMITER ;




DELIMITER $$
CREATE FUNCTION bandAverageRating(ID int)
  RETURNS double
BEGIN
  DECLARE num double;
  SELECT avg(Rating) INTO num FROM Band_Rating WHERE BandID = ID;
return num;
END;
$$
DELIMITER ; 

  
 
 

/*


select count(ID) from Event;



/*select * from Event where ID=1;
    
/*
insert into User(FirstName,LastName,UserName,Password,Mail,MobileNumber,Image) 
	values('mamuka','sakhelashvili','bolean','ramtamtam','msakh12@edu.ge','+995598465565','selfshot.jpg');
		 
insert into User(FirstName,LastName,UserName,Password,Mail,MobileNumber,Image)
	values('misha','maghriani','cuncula','qurdtanvijeqi','mmagh2@edu.ge','+995123456','bijoo.jpg');
		 
insert into User(FirstName,LastName,UserName,Password,Mail,MobileNumber,Image)
	values('tornike','abuladze','choki','zxc','tabul@edu.ge','+995598735770','gogodakera.jpg');
             


/*select * from Place_Profile_Image;
/*

insert into Place(UserID,Name,Adress,About)
	values(2,'ss','zastava','xinkali'); 


insert into Place(UserID,Name,Adress,About)
	values(3,'jimi','vake','xinkali'); 

insert into Place_Image(Name,PlaceID)
	values('magaria.jpg',1);

insert into Band_Image(Name,BandID)
	values('radiohead2.jpg',1);


insert into Place_Profile_Image(Place_ImageID,PlaceID)
	values(2,1);


insert into Place_Profile_Image(Place_ImageID,PlaceID)
	values(1,2);

update Place_Profile_Image
set Place_ImageID=1 where PlaceID=1;



		 


/*select * from Place_Image;
 
 
/*
delete from User 
where ID = 2;



SELECT * FROM Band 
where ID > 3 
order BY ID desc  LIMIT 2,2;*/


  