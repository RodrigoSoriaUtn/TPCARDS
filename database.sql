CREATE DATABASE IF NOT EXISTS TPCARDS;

use TPCARDS;

CREATE TABLE if NOT EXISTS players(
	id_player int not null AUTO_INCREMENT,
	nickName varchar(30) not null,
	primary key (id_player)
);

CREATE TABLE if NOT EXISTS typeOfDecks(
	id_typeOfDeck int not null AUTO_INCREMENT,
	typeOfDeck varchar(30) not null,
	primary key (id_typeOfDeck)
);

CREATE TABLE if NOT EXISTS typesOfCards(
	id_typeOfCard int not null AUTO_INCREMENT,
	typeOfCard varchar(30) not null,
	fk_idtypeOfDeck int not null,
	foreign key (fk_idtypeOfDeck) references typeOfDecks(id_typeOfDeck),
	primary key (id_typeOfCard)
);

CREATE TABLE if NOT EXISTS cards(
	id_card int not null AUTO_INCREMENT,
	valueOfCard int not null default 0,
	fk_idTypeOfCard int not null,
	foreign key (fk_idTypeOfCard) references typesOfCards(id_typeOfCard),
	primary key (id_card)
);

CREATE TABLE IF NOT EXISTS winners(
	id_winner int not null AUTO_INCREMENT,
	fk_idPlayer int not null,
	points int not null,
	foreign key (fk_idPlayer) references players(id_player),
	primary key (id_winner)
);

CREATE TABLE IF NOT EXISTS matchs(
	id_match int not null AUTO_INCREMENT,
	cuantityOfPlayers int not null,
	fk_idWinner int not null default 0, -- 0 if no winner (draw game).
	foreign key (fk_idWinner) references winners(id_winner),
	primary key (id_match)
);

CREATE TABLE IF NOT EXISTS matchResults (
    fpk_idMatch INT NOT NULL,
    fk_idPlayer INT NOT NULL,
    points INT NOT NULL,
    FOREIGN KEY (fpk_idMatch) REFERENCES matchs (id_match),
    FOREIGN KEY (fk_idPlayer) REFERENCES players (id_player),
    PRIMARY KEY (fpk_idMatch)
);

CREATE TABLE IF NOT EXISTS cardsOfWinnerPerMatch(
	id_cardsOfWinnerPerMatch int not null AUTO_INCREMENT,
	fk_idCard int not null,
	fk_idWinner int not null,
	fk_idMatch int not null,
	foreign key (fk_idWinner) references winners(id_winner),
	foreign key (fk_idCard) references cards(id_card),
	foreign key (fk_idMatch) references matchs(id_match),
	primary key (id_cardsOfWinnerPerMatch)
);

DROP PROCEDURE IF EXISTS sp_savePlayerAndGetIdOrJustId;
DELIMITER $$
CREATE PROCEDURE sp_savePlayerAndGetIdOrJustId (in _nickName varchar(30))
BEGIN
	DECLARE _idPlayer int;
    -- SI existe solo retorna el id. Sino existe lo agrega y retorna el ID.
    if exists ( select id_player from players
					where nickName like _nickName) then
		
        set _idPlayer =  (select id_player from players 
							where nickname like _nickName
							limit 1);
	else
		insert into players(nickName) values (_nickName);
        set _idPlayer = LAST_INSERT_ID();
    END IF;
    
    select _idPlayer;
END;
$$


DROP PROCEDURE IF EXISTS sp_saveWinner;
DELIMITER $$
CREATE PROCEDURE sp_saveWinner (in _nickName varchar(30), in _points int)
BEGIN
	DECLARE _idPlayer int;
    set _idPlayer = sp_savePlayerAndGetIdOrJustId (_nickName);
    
	if _idPlayer != 0 then 
		insert into winners(fk_idPlayers, points) 
			values (_idPlayer, _points);
	END IF;
END;
$$






