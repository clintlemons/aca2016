/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.aca2016.jdbc.shanamusiq;

import edu.uca.aca2016.config.PropertiesExample;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Shana
 */
public class ChinookManager{
    private Properties defaultProperties = new Properties();
    private String dbURL;
    private Connection con;
    /*
    Create a class constructor that takes zero parameters. This constructor needs to connect to the Chinook database we have been using in class. The database connection object should be a class level field. The configuration for the database (the path to the sqlite database) should come from a Java properties file. The file should be located in resources/config/<github username>/ and should be named ChinookManager.properties.
    */
    public ChinookManager() throws SQLException{
        this.loadDefaultProperties();
        dbURL = this.defaultProperties.getProperty("ChinookDatabase.connection");
        con = DriverManager.getConnection(dbURL);
        System.out.println(dbURL);
    }
    
    public static void main(String[] args) throws SQLException {
        ChinookManager cm = new ChinookManager();
        //cm.addArtist("Shana");
    }
    
    private void loadDefaultProperties() {
        FileInputStream in = null;
        
        try{
     
            Path inpath = Paths.get("resources","config","shanamusiq","ChinookManager.properties");
            in = new FileInputStream(inpath.toFile());
            this.defaultProperties.load(in);
        }
        catch(FileNotFoundException ex){
            Logger.getLogger(PropertiesExample.class.getName()).log(Level.SEVERE,"Properties file was not found",ex);
        }
        catch(IOException ex){
            Logger.getLogger(PropertiesExample.class.getName()).log(Level.SEVERE,"Exception reading properties file",ex);
        }
        finally{
            try{
                if (in != null) {
                    in.close();
                }
            }
            catch(IOException ex){
                Logger.getLogger(PropertiesExample.class.getName()).log(Level.SEVERE,null,ex);
            }
        }
    }

    /*Create a method named addArtist that has a single parameter for the artist’s name. 
    This method should insert a row into the Artist database table in order to make a new Artist record.
    */
 

    /*
    Create a method named getArtist that takes a single parameter for the artist’s name. This method needs to query the Artist table and attempt to find the artist the user requested. This needs to be a case insensitive search. The method should return the ID of the artist if one is found. If there is no match or there are multiple rows returned the method should return null.
    */
    
    /*
    Create a method named updateArtist that takes two parameters: 
    int id The id of the artist to be updated
    String name The new name of the artist.
    The method should find the artist record with the id provided, then update the name of the artist with the name provided. The method should return a boolean; true if the update was successful, false otherwise. 
    */
}

