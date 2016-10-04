DROP TABLE Pictures CASCADE;
DROP TABLE Users CASCADE;
DROP TABLE Albums CASCADE;
DROP TABLE Friends CASCADE;
DROP TABLE Comments CASCADE;
DROP TABLE Tags CASCADE;
DROP TABLE AlbumContains CASCADE;

DROP SEQUENCE Pictures_picture_id_seq;
DROP SEQUENCE Users_user_id_seq;
DROP SEQUENCE Tags_tag_id_seq;
DROP SEQUENCE Comments_comment_id_seq;
DROP SEQUENCE Albums_album_id_seq;

CREATE SEQUENCE Users_user_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 14
  CACHE 1;

CREATE TABLE Users
(
  user_id int4 NOT NULL DEFAULT nextval('Users_user_id_seq'),
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  role_name varchar(255) NOT NULL DEFAULT 'RegisteredUser',
  first varchar(255) NOT NULL,
  last varchar(255) NOT NULL,
  dob char(8) NOT NULL,
  gender char(1) DEFAULT NULL,
  hometown varchar(255) DEFAULT NULL,
  CONSTRAINT users_pk PRIMARY KEY (user_id)
);

#INSERT INTO Users (email, password, firstname, lastname, dob) VALUES ('test@bu.edu', 'test', 'test', 'test', 'testtest');

CREATE SEQUENCE Albums_albumid_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 14
  CACHE 1;

CREATE TABLE Albums
(
	album_id int4 NOT NULL DEFAULT nextval('Albums_album_id_seq'),
	name varchar(255) NOT NULL,
  owner int4 REFERENCES Users(user_id),
	date_created date NOT NULL,
	CONSTRAINT album_pk PRIMARY KEY (album_id)
);


CREATE SEQUENCE Pictures_picture_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 14
  CACHE 1;

CREATE TABLE Pictures
(
  picture_id int4 NOT NULL DEFAULT nextval('Pictures_picture_id_seq'),
  caption varchar(255) NOT NULL,
  imgdata bytea NOT NULL,
  size int4 NOT NULL,
  content_type varchar(255) NOT NULL,
  thumbdata bytea NOT NULL,
  owner int4 REFERENCES Users(user_id),
  CONSTRAINT pictures_pk PRIMARY KEY (picture_id)
); 

CREATE SEQUENCE Comments_comment_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 14
  CACHE 1;

CREATE TABLE Comments (
  comment_id int4 NOT NULL DEFAULT nextval('Comments_comment_id_seq'),
  owner int4 REFERENCES Users(user_id),
  picture int4 REFERENCES Pictures(picture_id),
  text varchar(255),
  dateofcomment date NOT NULL,
  CONSTRAINT commentid_pk PRIMARY KEY (comment_id)
);

CREATE SEQUENCE Tags_tag_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 14
  CACHE 1;

CREATE TABLE Tags (
  tag_id int4 NOT NULL DEFAULT nextval('Tags_tag_id_seq'),
  tag varchar(255),
  picture int4 REFERENCES Pictures(picture_id),
  CONSTRAINT tags_pk PRIMARY KEY (tag_id)
);

CREATE TABLE PicAndAlbum (
  album_id int4 REFERENCES Albums,
  picture_id int4 REFERENCES Pictures,
  CONSTRAINT picAndAlbum_pk PRIMARY KEY (album_id, picture_id)
);

CREATE TABLE Friends
(
	user1 int4 NOT NULL,
	user2 int4 NOT NULL,
	CONSTRAINT friendship_pk PRIMARY KEY (user1, user2),
	CONSTRAINT user1_fk FOREIGN KEY (user1) REFERENCES Users(user_id),
	CONSTRAINT user2_fk FOREIGN KEY (user2) REFERENCES Users(user_id)
);


