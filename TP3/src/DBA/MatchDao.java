/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBA;

import Classes.Match;
import Classes.Player;
import DBA.config.DatabaseConnection;
import DBA.config.DatabaseProcedures;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author usuario
 */
public class MatchDao {
    
    private Connection connection;
    
    public MatchDao(){
        connection = DatabaseConnection.getInstance();
    }
    /**
     * Saves the match and returns the id.
     * @param match 
     * @return the id of the match generated.
     */
    public int saveMatch(Match match){
        int id = 0;
        String query = DatabaseProcedures.SAVEMATCH.getQuery();
        
        try {
            CallableStatement procedure = connection.prepareCall(query);
            
            procedure.setInt(1, match.getCuantityOfPlayers());
            procedure.setString(2, match.getWinner().getNickName());
            procedure.setInt(3, match.getWinner().getPoints());
            
            procedure.registerOutParameter(4, Types.INTEGER);
            procedure.execute();
            id = procedure.getInt(4);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return id;
    }
    // Link information: http://programandoointentandolo.com/2013/11/como-ejecutar-un-procedimiento-almacenado-desde-java-con-jdbc.html
    public void saveResultOfPlayer(Player player, int points, int matchId){
        String query = DatabaseProcedures.SAVEMATCHRESULT.getQuery();
        try {
            
            CallableStatement procedure = connection.prepareCall(query);
            
            procedure.setString(1, player.getNickName());
            procedure.setInt(2, points);
            procedure.setInt(3,matchId);
            
            procedure.execute();
            
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
}
