/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBA;

import Classes.Match;
import Classes.Player;
import DBA.config.DatabaseConnection;
import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author usuario
 */
public class MatchDao {
    
    private Properties sqlQueries = new Properties();
    
    private Connection connection;
    
    public MatchDao(){
        connection = DatabaseConnection.getInstance();
        try {
            sqlQueries.load(new FileInputStream("../config/SqlQueries.properties"));
        } catch (Exception ex) {
            System.out.println("Error al cargar las propiedades en MatchDao: " + ex.getMessage());
        }
    }
    /**
     * Saves the match and returns the id.
     * @param match 
     */
    public int saveMatch(Match match){
        int id = 0;
        String query = sqlQueries.getProperty("sp_saveMatch");
        
        try {
            CallableStatement procedure = connection.prepareCall(query);
            
            procedure.setInt(1, match.getCuantityOfPlayers());
            procedure.setString(2, match.getWinner().getNickName());
            procedure.setInt(3, match.getWinner().getPoints());
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return id;
    }
    // Link information: http://programandoointentandolo.com/2013/11/como-ejecutar-un-procedimiento-almacenado-desde-java-con-jdbc.html
    public void saveResultOfPlayer(Player player, int points, int matchId){
        String query = sqlQueries.getProperty("storedProc.saveMatchResult");
        try {
            
            CallableStatement procedure = connection.prepareCall(query);
            
            procedure.setString(1, player.getNickName());
            procedure.setInt(2, points);
            procedure.setInt(3,matchId);
            
            if(!procedure.execute())
                System.out.println("Error al ejecutar el stored procedure");
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
}
