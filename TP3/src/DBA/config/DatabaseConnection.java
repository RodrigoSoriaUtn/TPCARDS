/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBA.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author usuario
 */
public class DatabaseConnection {
    
    private static Connection connection = null;
    private static Properties connProp;
    
    public static Connection getInstance(){
        if(connection == null){
            try {
                obtainProperties();
                
                //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/TPCARDS", "root", "");
                connection = DriverManager.getConnection(
                                connProp.getProperty("connection")+connProp.getProperty("host")+connProp.getProperty("database") ,
                                  connProp.getProperty("user"), connProp.getProperty("password"));
            } catch (SQLException ex) {
                System.out.println("Error en la conexion con la base de datos: " + ex.getMessage());
            }
        }
        return connection;
    }
    
    private DatabaseConnection(){ }
    
    private static void obtainProperties(){
        String rutaActual = System.getProperty("user.dir");
        rutaActual += "\\config\\DatabaseProperties.properties";
        connProp = new Properties();
        try{
            connProp.load(new FileInputStream(rutaActual));
        }catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al cargar las propiedades de la bse de datos: " + ex.getMessage());
        }
    }
    
}
