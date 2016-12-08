/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.aca2016.chinook.coopecor;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Logger;

/**
 *
 * @author Cory's HP Pavilion
 */
public class ChinookGenreManager {
    Connection con = null;
    private static final Logger logger = Logger.getLogger(ChinookGenreManager.class.getName());
    
    /**
     * Creates a connection to an instance of the Chinook database. 
     * 
     * The connection URL is obtained by reading a properties file located on
     * the classpath at resources/config/coopecor/ChinookGenreManager.properties
     */
public ChinookGenreManager(){
        try{
            // attempt to read a "known" properties file that is on the classpath
            Enumeration<URL> url = ChinookGenreManager.class.getClassLoader().getResources("config/coopecor/ChinookManager.properties");
            InputStream stream = new FileInputStream(url.nextElement().getPath());
            Properties props = new Properties();
            props.load(stream);
            stream.close();
            
            logger.info("Connecting to database: " + props.getProperty("jbdc.connection"));
            
            // the properties file loaded, attempt to connect using the value of the "jdbc.connection" property
            Class.forName("org.sqlite.JDBC");
            if (this.con == null) {
                con = DriverManager.getConnection(props.getProperty("jdbc.connection"));
            }
        }
        catch(FileNotFoundException ex){
            logger.severe("File Not Found: " + ex.getMessage());
        }
        catch(IOException ex){
            logger.severe("IO Exception: " + ex.getMessage());
        }
        catch(SQLException ex){
            logger.severe("SQL Issue: " + ex.getMessage());
        }
        catch(ClassNotFoundException ex){
            logger.severe("Class not found: " + ex.getMessage());
        }
}   

    public static void main(String[] args)
    {
        ChinookGenreManager jdbc = new ChinookGenreManager();
    }
}

