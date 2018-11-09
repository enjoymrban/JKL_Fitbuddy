# Tour schema
# --- !Ups

create table category (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  title VARCHAR(40)
);

create table fitUser (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  description VARCHAR(200),
  firstName VARCHAR(20),
  lastName VARCHAR(20),
  fullName VARCHAR(40),
  email VARCHAR(30),
  avatarUrl VARCHAR(100)
);

 create table event (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  category_id BIGINT,
  creator_id BIGINT,
  description VARCHAR(100),
  date VARCHAR(64),
  nrOfPlayers VARCHAR(20),
  coordinateX NUMERIC(19,16),
  coordinateY NUMERIC(19,16),
  -- coordinateX VARCHAR(20),
  -- coordinateY VARCHAR(20),
  FOREIGN KEY (creator_id) REFERENCES fitUser (id),
  FOREIGN KEY (category_id) REFERENCES category (id)
);


create table Favorite_Categories (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  fitUser_id BIGINT,
  category_id BIGINT,
  FOREIGN KEY (fitUser_id) REFERENCES fitUser (id),
  FOREIGN KEY (category_id) REFERENCES category (id)
);

create table Buddies (
id BIGSERIAL NOT NULL PRIMARY KEY,
fitUser_id BIGINT,
buddy_id BIGINT,
FOREIGN KEY (fitUser_id) REFERENCES fitUser (id),
FOREIGN KEY (buddy_id) REFERENCES fitUser (id)
);

create table Interested (
id BIGSERIAL NOT NULL PRIMARY KEY,
event_id BIGINT,
fitUser_id BIGINT,
FOREIGN KEY (event_id) REFERENCES event (id),
FOREIGN KEY (fitUser_id) REFERENCES fitUser (id)
);

create table Participants (
id BIGSERIAL NOT NULL PRIMARY KEY,
event_id BIGINT,
fitUser_id BIGINT,
FOREIGN KEY (event_id) REFERENCES event (id),
FOREIGN KEY (fitUser_id) REFERENCES fitUser (id)
);

INSERT INTO category (title) VALUES ('Fussball');
INSERT INTO category (title) VALUES ('Basketball');
INSERT INTO category (title) VALUES ('Tennis');
INSERT INTO category (title) VALUES ('Jogging');
INSERT INTO category (title) VALUES ('Sonstiges');

INSERT INTO fitUser (description, firstName, lastName, fullName, email, avatarUrl) VALUES ('Ehemalige Miss Ostschweiz', 'Silvio', 'Jaeger', 'Silvio Jäger', 'silvio.jaeger@ntb.ch', 'hallihallo/katze.png');
INSERT INTO fitUser (description, firstName, lastName, fullName, email, avatarUrl) VALUES ('Bester Basketballer unserer Zeit', 'LeBron', 'James', 'LeBron James', 'lbj@lakers23.nba', 'getdunked.png');
INSERT INTO fitUser (description, firstName, lastName, fullName, email, avatarUrl) VALUES ('Bester Basler Export', 'Roger', 'Federer', 'Roger Federer', 'fedi@ch', 'racketrocket.png');
INSERT INTO fitUser (description, firstName, lastName, fullName, email, avatarUrl) VALUES ('Fussballer', 'Mo', 'Salah', 'Mo Salah', 'fasdf@ch', 'rertzaocket.png');


INSERT INTO event (category_id, creator_id, description, date, nrOfPlayers, coordinateX, coordinateY) VALUES (1, 3, 'Fussballspielen mit Profis', '12.09.2018', '22', '12.3456789', '98.7654321');
INSERT INTO event (category_id, creator_id, description, date, nrOfPlayers, coordinateX, coordinateY) VALUES (5, 1, 'Schwimmen', '22.09.2018', '4', '47.73473737373', '9.4243542345');
INSERT INTO event (category_id, creator_id, description, date, nrOfPlayers, coordinateX, coordinateY) VALUES (4, 2, 'Runden', '16.09.2018', '4', '47.547878787', '9.05354545');
INSERT INTO event (category_id, creator_id, description, date, nrOfPlayers, coordinateX, coordinateY) VALUES (3, 3, 'Runden', '16.09.2018', '4', '47.147878787', '9.05354545');
INSERT INTO event (category_id, creator_id, description, date, nrOfPlayers, coordinateX, coordinateY) VALUES (2, 2, 'Runden', '16.09.2018', '4', '47.845876787', '9.65364545');
INSERT INTO event (category_id, creator_id, description, date, nrOfPlayers, coordinateX, coordinateY) VALUES (1, 4, 'Runden', '16.09.2018', '4', '47.645878787', '9.216545455');
INSERT INTO event (category_id, creator_id, description, date, nrOfPlayers, coordinateX, coordinateY) VALUES (4, 2, 'Runden', '16.09.2018', '4', '47.597818787', '9.55334545');

INSERT INTO Favorite_Categories (fitUser_id, category_id) VALUES (1,1);
INSERT INTO Favorite_Categories (fitUser_id, category_id) VALUES (2,2);
INSERT INTO Favorite_Categories (fitUser_id, category_id) VALUES (3,3);
INSERT INTO Favorite_Categories (fitUser_id, category_id) VALUES (1,4);
INSERT INTO Favorite_Categories (fitUser_id, category_id) VALUES (3,5);
INSERT INTO Favorite_Categories (fitUser_id, category_id) VALUES (4,1);
INSERT INTO Favorite_Categories (fitUser_id, category_id) VALUES (4,3);

INSERT INTO Buddies (fitUser_id, buddy_id) VALUES (2,1);
INSERT INTO Buddies (fitUser_id, buddy_id) VALUES (1,2);
INSERT INTO Buddies (fitUser_id, buddy_id) VALUES (1,3);
INSERT INTO Buddies (fitUser_id, buddy_id) VALUES (3,2);
INSERT INTO Buddies (fitUser_id, buddy_id) VALUES (2,3);
INSERT INTO Buddies (fitUser_id, buddy_id) VALUES (4,2);
INSERT INTO Buddies (fitUser_id, buddy_id) VALUES (4,1);
INSERT INTO Buddies (fitUser_id, buddy_id) VALUES (2,4);

INSERT INTO Interested (event_id, fitUser_id) VALUES (1,1);

INSERT INTO Participants (event_id, fitUser_id) VALUES (2,2);

# --- !Downs
  drop table event cascade;
  --drop table event;
  drop table category cascade;
  --drop table category;
  drop table fitUser cascade;
  --drop table fitUser;
  drop table Favorite_Categories cascade;
 -- drop table Buddies;
  drop table Buddies cascade;
  drop table Interested cascade;
  drop table Participants cascade;