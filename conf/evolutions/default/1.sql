# Tour schema
# --- !Ups
create table event (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  description VARCHAR(100),
  date VARCHAR(64),
  nrOfPlayers VARCHAR(20),
  coordinateX VARCHAR(20),
  coordinateY VARCHAR(20),
  PRIMARY KEY (id)
);

INSERT INTO event (description, date, nrOfPlayers, coordinateX, coordinateY) VALUES ('Fussballspielen mit Profis', '12.09.2018', '22', '123456789', '987654321');

# --- !Downs

drop table event;

