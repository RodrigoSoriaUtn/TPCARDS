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
CREATE PROCEDURE sp_savePlayerAndGetIdOrJustId (in _nickName varchar(30), out _idOfPlayer int)
BEGIN
    -- SI existe solo retorna el id. Sino existe lo agrega y retorna el ID.
    if exists ( select id_player from players
					where nickName like _nickName) then
		
        set _idOfPlayer =  (select id_player from players 
							where nickname like _nickName
							limit 1);
	else
		insert into players(nickName) values (_nickName);
        set _idOfPlayer = LAST_INSERT_ID();
    END IF;
    
END;
$$

 
DROP PROCEDURE IF EXISTS sp_saveWinnerAndGetId;
DELIMITER $$
CREATE PROCEDURE sp_saveWinnerAndGetId (in _nickName varchar(30), in _points int, out _winnerId int)
BEGIN
	DECLARE _idPlayer int;
    call sp_savePlayerAndGetIdOrJustId (_nickName, @idPlayer);
    
	if _idPlayer != 0 then 
		insert into winners(fk_idPlayers, points) 
			values (_idPlayer, _points);
		set _winnerId = LAST_INSERT_ID();
	else
		set _winnerId = 0; -- 0 represents no winner.
	END IF;
END;
$$

DROP PROCEDURE IF EXISTS sp_saveTypeOfDeckAndGetIdOrJustId;
DELIMITER &&
CREATE PROCEDURE sp_saveTypeOfDeckAndGetIdOrJustId(in _deckTypeName varchar(30), out _deckId int)
BEGIN

	if exists ( select id_typeOfDeck from typeOfDecks
					where typeOfDeck like _deckTypeName) then
                    
		set _deckId =  (select id_typeOfDeck from typeOfDecks 
							where typeOfDeck like _deckTypeName
							limit 1);
	else
		insert into typeOfDecks(typeOfDeck) values (_deckTypeName);
        set _deckId = LAST_INSERT_ID();
    END IF;
END;
&&

DROP PROCEDURE IF EXISTS sp_saveTypeOfCardAndGetIdOrJustId;
DELIMITER &&
CREATE PROCEDURE sp_saveTypeOfCardAndGetIdOrJustId(in _cardTypeName varchar(30), in _typeOfDeck varchar(30),
												   out _cardTypeId int)
BEGIN
	DECLARE _deckId int;
    call sp_saveTypeOfDeckAndGetIdOrJustId(_typeOfDeck, @_deckId);
    
	if exists ( select id_typeOfCard from typeOfCards
					where typeOfCard like _cardTypeName) then
                    
		set _cardTypeId =  (select id_typeOfCard from typeOfCards
								where typeOfCard like _cardTypeName
								limit 1);
	else
		insert into typeOfCards(typeOfCard, fk_idtypeOfDeck) values (_cardTypeName, _deckId);
        set _cardTypeId = LAST_INSERT_ID();
    END IF;
END;
&&


DROP PROCEDURE IF EXISTS sp_saveCard;
DELIMITER &&
CREATE PROCEDURE sp_saveCard(in _cardValue int, in _cardType varchar(30), in _deckName varchar(30))
BEGIN
	DECLARE _typeOfDeckId int;
    DECLARE _typeOfCardId int;
    
    call sp_saveTypeOfCardAndGetIdOrJustId(_cardType, _deckName, @_typeOfCardId);
    
    IF NOT EXISTS ( SELECT id_card FROM cards
					WHERE valueOfCard = _cardValue AND fk_idTypeOfCard = _typeOfCardId) THEN
		INSERT INTO cards(valueOfCard, fk_idTypeOfCard) VALUES(_cardValue, _typeOfCardId);
    END IF;
	
END;
&&


DROP PROCEDURE IF EXISTS saveMatch;
DELIMITER //
CREATE PROCEDURE saveMatch(in _cuantityOfPlayers int, in _winnerNickName varchar(30), in _winnerPoints int)
BEGIN
	DECLARE _winnerId int;
	DECLARE _matchId int;
    call sp_saveWinnerAndGetId(_winnerNickName, _winnerPoints, @_winnerId);
	
    insert into matchs(cuantityOfPlayers, fk_idWinner) values (_cuantityOfPlayers, _winnerId);
END;
//

DROP PROCEDURE IF EXISTS saveMatchResult;
DELIMITER //
CREATE PROCEDURE saveMatchResult(in _nickName varchar(30), in _points int, in _idMatch int)
BEGIN
	DECLARE _playerId int;
    set _playerId = sp_savePlayerAndGetIdOrJustId(_nickName, @_playerId);
    
    insert into matchResults(fpk_idMatch, fk_idPlayer, points)
		values(_idMatch, _playerId, _points);
END;
//


CREATE TABLE IF NOT EXISTS matchResults (
    fpk_idMatch INT NOT NULL,
    fk_idPlayer INT NOT NULL,
    points INT NOT NULL,
    FOREIGN KEY (fpk_idMatch) REFERENCES matchs (id_match),
    FOREIGN KEY (fk_idPlayer) REFERENCES players (id_player),
    PRIMARY KEY (fpk_idMatch)
);





