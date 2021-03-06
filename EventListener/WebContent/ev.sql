
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

create table Admin(
	ID int auto_increment not null,
	primary key(ID),
	UserID int,
	FOREIGN KEY (UserID) REFERENCES User(ID) ON DELETE CASCADE	
);

create table Punished(
	ID int auto_increment not null,
	primary key(ID),
	UserID int,
	FOREIGN KEY (UserID) REFERENCES User(ID) ON DELETE CASCADE
);

create table Band(
	ID int auto_increment not null,
	primary key(ID),
	UserID int,
	Name varchar(30) unique,
	About text,
	Mail text,
	FOREIGN KEY (UserID) REFERENCES User(ID)	 ON DELETE CASCADE
);

create table Place(
	ID int auto_increment not null,
	primary key(ID),
	UserID int,
	Name varchar(30) unique,
	Adress varchar(30),
	About text,
	foreign key(UserID) references User(ID) ON DELETE CASCADE
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
	foreign key(UserID) references User(ID) ON DELETE CASCADE,
	foreign key(PlaceID) references Place(ID)	ON DELETE CASCADE
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
	foreign key(UserID) references User(ID) ON DELETE CASCADE,
	foreign key(BandID) references Band(ID) ON DELETE CASCADE
);

create table User_Going_Event(
	ID int auto_increment not null,
	primary key(ID),
	UserID int,
	EventID int,
	foreign key(UserID) references User(ID) ON DELETE CASCADE ,
	foreign key(EventID) references Event(ID) ON DELETE CASCADE
);
 

create table Band_On_Event(
	ID int auto_increment not null,
	primary key(ID),
	EventID int,
	BandID int,
	foreign key(EventID) references Event(ID) ON DELETE CASCADE,
	foreign key(BandID) references Band(ID) ON DELETE CASCADE
);


create table Band_Plays_Genre(
	ID  int auto_increment not null,
	primary key(ID),
	BandID int,
	GenreID int,
	foreign key(BandID) references Band(ID) ON DELETE CASCADE,
	foreign key(GenreID) references Genre(ID) ON DELETE CASCADE
);

create table Band_Image(
	ID  int auto_increment not null,
	primary key(ID),
	Name varchar(30) unique,
	BandID int,
	foreign key(BandID) references Band(ID) ON DELETE CASCADE
);


create table Band_Profile_Image(
	ID  int auto_increment not null,
	primary key(ID),
	Band_ImageID int,
	BandID int,
	foreign key(BandID) references Band(ID) ON DELETE CASCADE ,
	foreign key(Band_ImageID) references Band_Image(ID) ON DELETE CASCADE
);


create table Music(
	ID int auto_increment not null,
	primary key(ID),
	BandID int,
	Name varchar(50) unique,
	foreign key(BandID) references Band(ID)	 ON DELETE CASCADE
);

create table Video(
	ID int auto_increment not null,
	Primary key(ID),
	BandID int,
	Name varchar(50) unique,
	foreign key(BandID) references Band(ID)	 ON DELETE CASCADE
);


create table Place_Image(
	ID int auto_increment not null,
	primary key(ID),
	Name varchar(50) unique,
	PlaceID int,
	foreign key(PlaceID) references Place(ID) ON DELETE CASCADE
);


create table Place_Profile_Image(
	ID int auto_increment not null,
	primary key(ID),
	Name varchar(50),
	PlaceID int unique,
	foreign key(PlaceID) references Place(ID) ON DELETE CASCADE
);


create table Band_Rating(
	ID int auto_increment not null,
	primary key(ID),
	UserID int,
	BandID int,
	EventID int,
	Rating int,
	foreign key(UserID) references User(ID) ON DELETE CASCADE,
	foreign key(BandID) references Band(ID) ON DELETE CASCADE,
	foreign key(EventID) references Event(ID) ON DELETE CASCADE
);


create table Place_Rating(
	ID int auto_increment not null,
	primary key(ID),
	UserID int,
	PlaceID int,
	EventID int,
	Rating int,
	foreign key(UserID) references User(ID) ON DELETE CASCADE,
	foreign key(PlaceID) references Place(ID) ON DELETE CASCADE,
	foreign key(EventID) references Event(ID) ON DELETE CASCADE
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
use ev;
delete  from band where ID=1;
select * from Video;
select * from Band_Image;
select * from User;
select * from Event;
select * from User_Band_Wishlist