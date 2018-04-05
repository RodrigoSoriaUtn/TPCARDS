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
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class MatchDao {
    
    private Properties sqlQueries = new Properties();
    
    private Connection connection;
    
    private MatchDao(){
        connection = DatabaseConnection.getInstance();
        try {
            sqlQueries.load(new FileInputStream("../config/SqlQueries.properties"));
        } catch (Exception ex) {
            System.out.println("Error al cargar las propiedades en MatchDao: " + ex.getMessage());
        }
    }
    
    public void saveMatch(Match match){
        
    }
    // Link information: http://programandoointentandolo.com/2013/11/como-ejecutar-un-procedimiento-almacenado-desde-java-con-jdbc.html
    public void saveResultOfPlayer(Player player, int points, Match match){
        String query = sqlQueries.getProperty("storedProc.saveMatchResult");
        try {
            
            CallableStatement procedure = connection.prepareCall(query);
            
        } catch (SQLException ex) {
            
        }
    }
    
}
