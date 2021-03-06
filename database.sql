drop database tpcards;

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

CREATE TABLE if NOT EXISTS typeOfCards(
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
	foreign key (fk_idTypeOfCard) references typeOfCards(id_typeOfCard),
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
    pk_idMatchResult int not null AUTO_INCREMENT,
    fk_idMatch INT NOT NULL,
    fk_idPlayer INT NOT NULL,
    points INT NOT NULL,
    FOREIGN KEY (fk_idMatch) REFERENCES matchs (id_match),
    FOREIGN KEY (fk_idPlayer) REFERENCES players (id_player),
    PRIMARY KEY (pk_idMatchResult)
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
    call sp_savePlayerAndGetIdOrJustId (_nickName, @_idPlayer);
    
	if @_idPlayer != 0 then 
		insert into winners(fk_idPlayer, points) 
			values (@_idPlayer, _points);
		set _winnerId = LAST_INSERT_ID();
	else
		set _winnerId = 0; -- 0 represents no winner and will throw an error.
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
	
    call sp_saveTypeOfDeckAndGetIdOrJustId(_typeOfDeck, @_deckId);
    
	if exists ( select id_typeOfCard from typeOfCards
					where typeOfCard like _cardTypeName) then
                    
		set _cardTypeId =  (select id_typeOfCard from typeOfCards
								where typeOfCard like _cardTypeName
								limit 1);
	else
		insert into typeOfCards(typeOfCard, fk_idtypeOfDeck) values (_cardTypeName, @_deckId);
        set _cardTypeId = LAST_INSERT_ID();
    END IF;
END;
&&


DROP PROCEDURE IF EXISTS sp_saveCard;
DELIMITER &&
CREATE PROCEDURE sp_saveCard(in _cardValue int, in _cardType varchar(30), in _deckName varchar(30))
BEGIN
    
    call sp_saveTypeOfCardAndGetIdOrJustId(_cardType, _deckName, @_typeOfCardId);
    
    IF NOT EXISTS ( SELECT id_card FROM cards
					WHERE valueOfCard = _cardValue AND fk_idTypeOfCard = @_typeOfCardId) THEN
		INSERT INTO cards(valueOfCard, fk_idTypeOfCard) VALUES(_cardValue, @_typeOfCardId);
    END IF;
	
END;
&&


DROP PROCEDURE IF EXISTS sp_saveMatch;
DELIMITER //
CREATE PROCEDURE sp_saveMatch(in _cuantityOfPlayers int, in _winnerNickName varchar(30), in _winnerPoints int, out _matchId int)
BEGIN
    call sp_saveWinnerAndGetId(_winnerNickName, _winnerPoints, @_winnerId);
	
    insert into matchs(cuantityOfPlayers, fk_idWinner) values (_cuantityOfPlayers, @_winnerId);
    set _matchId = last_insert_id();
    
END;
//

DROP PROCEDURE IF EXISTS sp_saveMatchResult;
DELIMITER //
CREATE PROCEDURE sp_saveMatchResult(in _nickName varchar(30), in _points int, in _idMatch int)
BEGIN
    call sp_savePlayerAndGetIdOrJustId(_nickName, @_playerId);
    
    insert into matchResults(fk_idMatch, fk_idPlayer, points)
		values(_idMatch, @_playerId, _points);
END;
//

DROP FUNCTION IF EXISTS fc_getWinnerIdByNameAndMatch;
DELIMITER $$ 
CREATE FUNCTION fc_getWinnerIdByNameAndMatch(_matchId int, _winnerNickName varchar(30)) RETURNS INT
-- RETURNS the id of the winner or 0 if there is no winner.
BEGIN
	
    DECLARE _idWinner int;
    SET _idWinner = 0;
    if exists ( select fk_idWinner, id_match from matchs
					where id_match = _matchId
					and fk_idWinner in (select id_winner from winners
											inner join players p on p.nickName like _winnerNickName) 
			   ) then
		set _idWinner = (select fk_idWinner from matchs
							where id_match = _matchId
                            limit 1);
    END IF;
    RETURN _idWinner;
END;
$$

DROP FUNCTION IF EXISTS fc_getCardIdOrZero;
DELIMITER $$ 
CREATE FUNCTION fc_getCardIdOrZero(_cardValue int, _cardTypeName varchar(30)) RETURNS int
BEGIN
	DECLARE _cardId int;
    SET _cardId = 0;
	if exists( select id_card from cards
					inner join typeOfCards typeOfC on fk_idTypeOfCard = typeOfC.id_typeOfCard
					where valueOfCard = _cardValue and typeOfC.typeOfCard like _cardTypeName
			) then
		SET _cardId = (select id_card from cards
						inner join typeOfCards typeOfC on fk_idTypeOfCard = typeOfC.id_typeOfCard
						where valueOfCard = _cardValue and typeOfC.typeOfCard like _cardTypeName
                        );
	END IF;
    RETURN _cardId;
END;
$$


DROP PROCEDURE IF EXISTS sp_saveCardsOfWinnerPerMatch;
DELIMITER &&
CREATE PROCEDURE sp_saveCardsOfWinnerPerMatch(in _cardValue int, in _cardTypeName varchar(30), in _deckName varchar(30),
										in _winnerNickName varchar(30), in _matchId int)
BEGIN
    DECLARE _winnerId int;
    DECLARE _cardId int;
    SET _winnerId = fc_getWinnerIdByNameAndMatch(_matchId, _winnerNickName);
    #Guarda la carta si no existe.
    call sp_saveCard(_cardValue, _cardTypeName, _deckName);
    SET _cardId = fc_getCardIdOrZero(_cardValue, _cardTypeName);
    
	insert into cardsOfWinnerPerMatch(fk_idCard, fk_idWinner, fk_idMatch) values (_cardId, _winnerId, _matchId);
    
END;
&&

