/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBA;

import Classes.Abstract.AbstractCard;
import DBA.config.DatabaseConnection;
import DBA.config.DatabaseProcedures;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author alumno
 */
public class CardDao {
    
    
    private Connection connection;
    
    public CardDao(){
        connection = DatabaseConnection.getInstance();
        
    }
    
    public void saveCard(AbstractCard card){
        
        String query = DatabaseProcedures.SAVECARD.getQuery();
        
        try {
            CallableStatement proc = connection.prepareCall(query);
            
            proc.setInt(1, card.getValue());
            proc.setString(2, card.getType().toString());
            proc.setString(3, card.getType().getTypeOfDeck());
            
            proc.execute();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
    
    public void saveCardOfWinnerPerMatch(int cardValue, String cardType, String deckName,
                                            String winnerNickName, int matchId){
        
        String query = DatabaseProcedures.SAVECARDOFPLAYERPERMATCH.getQuery();
        
        try {
            CallableStatement proc = connection.prepareCall(query);
            
            proc.setInt(1, cardValue);
            proc.setString(2, cardType);
            proc.setString(3, deckName);
            proc.setString(4, winnerNickName);
            proc.setInt(5, matchId);
            
            proc.execute();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
        
    }
    
}
