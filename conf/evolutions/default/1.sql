# Tour schema
# --- !Ups
 create table event (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  description VARCHAR(100),
  date VARCHAR(64),
  nrOfPlayers VARCHAR(20),
  coordinateX VARCHAR(20),
  coordinateY VARCHAR(20)
);

INSERT INTO event (description, date, nrOfPlayers, coordinateX, coordinateY) VALUES ('Fussballspielen mit Profis', '12.09.2018', '22', '123456789', '987654321');
INSERT INTO event (description, date, nrOfPlayers, coordinateX, coordinateY) VALUES ('Schwimmen', '22.09.2018', '4', '12345745', '22765411');


# --- !Downs
 drop table event;