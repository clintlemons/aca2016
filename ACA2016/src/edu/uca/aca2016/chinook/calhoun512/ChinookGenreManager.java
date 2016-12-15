/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.aca2016.chinook.calhoun512;


import edu.uca.aca2016.jdbc.cpfiles.ChinookManager;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author calho
 */
public class ChinookGenreManager {

        Connection con = null;
        private static final Logger logger = Logger.getLogger(ChinookGenreManager.class.getName());
        
    public ChinookGenreManager(){
    
        try{
            
            // attempt to read a "known" properties file that is on the classpath
        Enumeration<URL> url = ChinookGenreManager.class.getClassLoader().getResources("config/Calhoun512/ChinookManager.properties");
        InputStream stream = new FileInputStream(url.nextElement().getPath());
        Properties props = new Properties();
        props.load(stream);
        stream.close();

        logger.info("Connecting to database: " + props.getProperty("db.connection"));

        // the properties file loaded, attempt to connect using the value of the "db.connection" property
        Class.forName("org.sqlite.JDBC");
        if (this.con == null) {
            con = DriverManager.getConnection(props.getProperty("db.connection"));
        }

    }
    catch(FileNotFoundException ex){
        logger.severe("File Not Found: " + ex.getMessage());

   
    }catch(IOException ex){
        logger.severe("IO Exception: " + ex.getMessage());
    
    }catch(SQLException ex){
        logger.severe("SQL Issue: " + ex.getMessage());
   
    }catch(ClassNotFoundException ex){
        logger.severe("Class not found: " + ex.getMessage());
    }
    }
public HashMap<Integer, String> getGenres() throws SQLException {
    HashMap<Integer, String> genres = new HashMap<>();
    
    try{
        Statement st = this.con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM Genre");
        
        while (rs.next()) {
            genres.put(rs.getInt("GenreId"), rs.getString("Name"));
        }
        
    }
    catch(SQLException ex){
        logger.severe("SQL Issue:" + ex.getMessage());
    }
    
    return genres;
}

public boolean addGenre(String name) throws SQLException{
    boolean ret = false;

    try{
        PreparedStatement ps = this.con.prepareStatement("INSERT INTO Genre (Name) VALUES (?)");
        ps.setString(1, name);
        int ra = ps.executeUpdate();
        if (ra == 1){
            ret = true;
            logger.log(Level.INFO, "Added Genre" + name);
        }else {
            logger.log(Level.WARNING, "Genre Add failed" + name + "not added");
        }
    }
    catch (SQLException ex){
        logger.severe("Issue adding Genre" + ex.getMessage());
    }
    return ret;
}

public String getGenreName (int id) throws SQLException {
    String ret = null;
    
    try {
        PreparedStatement ps = this.con.prepareStatement("SELECT * FROM Genre WHERE GenreId = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            ret = rs.getString("Name");
            logger.info("Search for Genre" + id + "returned:" + ret);
            
        } else {
                logger.info("Search for Genre " + id + "yielded no results");
                }
        
        }catch(SQLException ex) {
            logger.severe("Error retrieving Genre:" + ex.getMessage());
        
    }
    return ret;
}

public boolean updateGenre(int id, String name) throws SQLException{
    boolean ret = false;
    
    try {
        PreparedStatement ps = this.con.prepareStatement("UPDATE Genre SET NAME = ? Where GenreId = ?");
        ps.setString(1,name);
        ps.setInt(2, id);
        int ra = ps.executeUpdate();
        
        if (ra == 1){
        ret = true;
        logger.info("Updated Genre with id " + id + "set name to '" + name + "'");
        }
        else {
            logger.warning("Genre update of" + id + " had an unexpected result and changed " + ra + " records");
    }
    } catch(SQLException ex) {
         logger.severe("Error updating genre: " + ex.getMessage());
}

        return ret;
}

public boolean deleteGenre(int id) throws SQLException{
    boolean ret = false;
    
    try{
        PreparedStatement ps = this.con.prepareStatement("DELETE FROM Genre WHERE GenreId = ?");
        ps.setInt(1, id);
        int ra = ps.executeUpdate();
        ps.close();
        
        if (ra == 1){
            ret = true;
            logger.info("Deleted Genre with ID " + id);
        }else {
            logger.warning("Failed to delete Genre " + id + " changed " + ra + " records");
        }
    }catch(SQLException ex) {
        logger.severe("Issue deleting Genre: " + ex.getMessage());
    }
    return ret;
}

    public void close() {
        if (this.con != null) {
            try{
                this.con.close();
            }
            catch(SQLException ex){
                logger.warning(ex.getMessage());
            }




}

