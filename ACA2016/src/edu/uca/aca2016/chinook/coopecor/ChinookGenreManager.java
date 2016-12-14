/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.aca2016.chinook.coopecor;


import java.io.File;
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
import java.util.logging.Logger;

/**
 *
 * @author Cory's HP Pavilion
 */
public class ChinookGenreManager {
    Connection con = null;
    private static final Logger logger = Logger.getLogger(ChinookGenreManager.class.getName());
    private static final String p_path = "C:\\ChinookDatabase1.4_Sqlite\\aca2016\\resources\\config\\coopecor\\ChinookManager.properties";
    /**
     * Creates a connection to an instance of the Chinook database. 
     * 
     * The connection URL is obtained by reading a properties file located on
     * the classpath at resources/config/coopecor/ChinookGenreManager.properties
     */
public ChinookGenreManager(){
        try{
            // attempt to read a "known" properties file that is on the classpath
            //Enumeration<URL> url = ChinookGenreManager.class.getClassLoader().getResources("config/coopecor/ChinookManager.properties");
            File prop = new File (p_path);
            FileInputStream(prop);
            Properties props = new Properties();
            props.load(p_path);
            prop.close();
            
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
 /**
     * Preforms a case insensitive search by name for a given genre.
     * 
     * @param name The name of the genre to search.
     * @return The database genreid of the genre if a single result is found; -1 if
     * no result or ambiguous results are found.
     */
    public int getGenres(String name) {
        // default the return value to -1
        int ret = -1;

        try{
            // attempt to find the artist by name. note we use upper() to make it case insensitive
            PreparedStatement ps = this.con.prepareStatement("SELECT * FROM Genre WHERE UPPER(Name) = UPPER(?)");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            
            // if there is a record, get the genre id to return
            if (rs.next()) {
                ret = rs.getInt("GenreID");
                logger.info("Search for genre '" + name + "' yielded an ID of " + ret);
            }
            else {
                logger.info("Search for genre '" + name + "' yielded no results");
            }
            
            // if there is a second record we have ambiguous resuts, return -1
            if (rs.next()) {
                ret = -1;
                logger.info("Search for genre yielded ambiguous results, returning -1");
            }
        }
        catch(SQLException ex){
            logger.severe("Issue searching for genre: " + ex.getMessage());
        }

        // send back either an ID or -1 for no or ambiguous results
        return ret;
    }
public HashMap<Integer, String> getGenres() {
        HashMap<Integer, String> genre = new HashMap<>();
        
        try{
            Statement s = this.con.createStatement();
            
            ResultSet rs = s.executeQuery("SELECT * FROM Genre");
            
            while (rs.next()) {
                genre.put(rs.getInt("GenreID"), rs.getString("Name"));
            }
        }
        catch(SQLException ex){
            logger.severe("SQL Issue: " + ex.getMessage());
        }
        
        return genre;
    }
public boolean addGenre(String name) {
        boolean ret = false;
         try{
            PreparedStatement ps = this.con.prepareStatement("INSERT INTO Genre (Name) VALUES(?)");
            ps.setString(1, name);
            int ra = ps.executeUpdate();
            
            if (ra == 1) {
            ret = true;
            }
            
            ps.close();
        }
        catch(SQLException ex){
            logger.severe("Issue adding genre: " + ex.getMessage());
        }
        
        logger.info("Added genre '" + name + "' to the database ");
         
        return ret;
    }
        
public String getGenreName(int id) {
        String ret = null;

        try{
            // attempt to find the genre by id
            PreparedStatement ps = this.con.prepareStatement("SELECT * FROM Genre WHERE GenreId = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            // if there is a record, get the genre id to return
            if (rs.next()) {
                ret = rs.getString("Name");
                //logger.info("Search by id for genre '" + id + "' yielded an name of " + ret);
            }
            else {
                //logger.info("Search for genre '" + id + "' yielded no results");
            }
        }
        catch(SQLException ex){
            //logger.severe("Issue searching for genre: " + ex.getMessage());
        }

        // send back null or the name
        return ret;
    }




public boolean updateGenre(int id, String name) {
        boolean ret = false;
try{
            PreparedStatement ps = this.con.prepareStatement("UPDATE Genre SET Name = ? WHERE GenreID = ?");
            ps.setString(1, name);
            ps.setInt(2, id);
            int ra = ps.executeUpdate();
            
            if (ra == 1) {
                ret = true;
                logger.info("Updated genre with ID " + id + " set name to '" + name + "'");
            }
            else {
                logger.warning("Update genre with ID " + id + " had an undesired result and changed " + ra + " records");
            }
        }
        catch(SQLException ex){
            logger.severe("Issue updating genre: " + ex.getMessage());
        }
        
        return ret;
    }
public boolean deleteGenre(int id) {
        boolean ret = false;
        
        try{
            PreparedStatement ps = this.con.prepareStatement("DELETE FROM Genre WHERE GenreID = ?");
            ps.setInt(1, id);
            int ra = ps.executeUpdate();
            
            if (ra == 1) {
                ret = true;
                logger.info("Deleted genre with ID " + id);
            }
            else {
                logger.warning("Delete genre with ID " + id + " had an undesired result and changed " + ra + " records");
            }
        }
        catch(SQLException ex){
            logger.severe("Issue deleting genre: " + ex.getMessage());
        }
        
        return ret;
    }

    public static void main(String[] args)
    {
        ChinookGenreManager jdbc = new ChinookGenreManager();
        jdbc.getGenres();
        jdbc.addGenre(p_path);
        System.out.println(jdbc.getGenreName(5));
        jdbc.updateGenre(7, p_path);
        jdbc.deleteGenre(8);
        
        
    }

    private void FileInputStream(File prop) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

