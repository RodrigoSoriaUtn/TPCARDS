/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBA;

import Classes.Abstract.AbstractCard;
import DBA.config.DatabaseConnection;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alumno
 */
public class CardDao {
    
    private Properties sqlQueries = new Properties();
    
    private Connection connection;
    
    public CardDao(){
        connection = DatabaseConnection.getInstance();
        try {
            sqlQueries.load(new FileInputStream("../config/SqlQueries.properties"));
        } catch (IOException ex) {
            System.out.println("Error al cargar las propiedades en MatchDao: " + ex.getMessage());
        }
        
    }
    
    public void saveCard(AbstractCard card){
        
        String query = sqlQueries.getProperty("storedProc.saveCard");
        
        try {
            CallableStatement proc = connection.prepareCall(query);
            
            proc.setInt(1, card.getValue());
            proc.setString(2, card.getType().toString());
            proc.setString(3, card.getType().getTypeOfDeck());
            
            if(!proc.execute())
                System.out.println("Error al ejecutar el stored procedure");
            
        } catch (SQLException ex) {
            System.out.println("Error al procesar el procedimiento: " + ex.getMessage());
        }
        
    }
    
    public void saveCardOfWinnerPerMatch(){
        
    }
    
}
