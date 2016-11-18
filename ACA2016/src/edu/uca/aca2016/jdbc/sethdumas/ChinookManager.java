/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.aca2016.jdbc.sethdumas;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sethd
 */
public class ChinookManager {

    Connection con;
    private static final Logger logger = Logger.getLogger(ChinookManager.class.getName());

    public void ChinookManager() {
        try {
            Path inpath = Paths.get("resources", "config", "sethdumas", "ChinookManager.properties");
            Properties props;
            try (FileInputStream in = new FileInputStream(inpath.toFile())) {
                props = new Properties();
                props.load(in);
            }

            con = DriverManager.getConnection(props.getProperty("db.connection"));

        } catch (SQLException | IOException ex) {
            Logger.getLogger(ChinookManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //addArtist has a single parameter for the artist’s name. 
    //This method should insert a row into the Artist database table 
    //in order to make a new Artist record.

    public void addArtist(String addArtist) throws SQLException{
        //Connection con = null;
        PreparedStatement ps = null;
        
        try{
            //con = DriverManager.getConnection(props.getProperty("db.connection"));
            String sql = "INSERT INTO Artist (Name)" + "VALUES (?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, addArtist);
            ps.executeUpdate();
                                    
        } catch (SQLException ex) {
            Logger.getLogger(ChinookManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            if (ps != null) {
                ps.close();
            }
        
    }
}
    // getArtist takes a single parameter for the artist’s name. 
    //The method needs to query the Artist table and attempt to find the 
    //artist the user requested. This needs to be a case insensitive search. 
    //The method should return the ID of the artist if one is found. 
    //If there is no match or there are multiple rows returned the method should return -1.

//    public void getArtist() throws SQLException{
//        
//    } 
}
//get artist -row count - you have to know how many rows to come back.
//