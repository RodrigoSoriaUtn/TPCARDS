/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBA.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 *
 * @author usuario
 */
public class DatabaseConnection {
    
    private static Connection connection = null;
    
    public static Connection getInstance(){
        if(connection == null){
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/TPCARDS", "root", "");
            } catch (SQLException ex) {
                System.out.println("Error en la conexion con la base de datos: " + ex.getMessage());
            }
        }
        return connection;
    }
    
    private DatabaseConnection(){
        
    }
    
}
