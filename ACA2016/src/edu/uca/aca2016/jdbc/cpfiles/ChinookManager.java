package edu.uca.aca2016.jdbc.cpfiles;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * A class to manipulate the contents of the Chinook SQLite Database
 * 
 * More information about the Chinook database can be found at:
 * https://chinookdatabase.codeplex.com/
 * 
 * @author cfiles
 */
public class ChinookManager{
    Connection con;
    private static final Logger logger = Logger.getLogger(ChinookManager.class.getName());
    
    /**
     * Creates a connection to an instance of the Chinook database. 
     * 
     * The connection URL is obtained by reading a properties file located on
     * the classpath at resources/config/cpfiles/ChinookManager.properties
     */
    public ChinookManager(){
        try{
            // attempt to read a "known" properties file that is on the classpath
            Path inpath = Paths.get("resources","config","cpfiles", "ChinookManager.properties");
            FileInputStream in = new FileInputStream(inpath.toFile());
            Properties props = new Properties();
            props.load(in);
            in.close();
            
            logger.info("Connecting to database: " + props.getProperty("db.connection"));
            
            // the properties file loaded, attempt to connect using the value of the "db.connection" property
            con = DriverManager.getConnection(props.getProperty("db.connection"));
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
    }
    
    /**
     * Adds an artist to the Artist table of the database. The artist gets the 
     * next id according to the database.
     * 
     * @param name The name of the Artist to add
     */
    public void addArtist(String name) {
        try{
            PreparedStatement ps = this.con.prepareStatement("INSERT INTO Artist (Name) VALUES(?)");
            ps.setString(1, name);
            ps.executeUpdate();
            ps.close();
        }
        catch(SQLException ex){
            logger.severe("Issue adding artist: " + ex.getMessage());
        }
        
        logger.info("Added artist '" + name + "' to the database ");
    }
    
    /**
     * Preforms a case insensitive search by name for a given artist.
     * 
     * @param name The name of the artist to search.
     * @return The database id of the artist if a single result is found; -1 if
     * no result or ambiguous results are found.
     */
    public int getArtist(String name) {
        // default the return value to -1
        int ret = -1;

        try{
            // attempt to find the artist by name. note we use upper() to make it case insensitive
            PreparedStatement ps = this.con.prepareStatement("SELECT * FROM Artist WHERE UPPER(Name) = UPPER(?)");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            
            // if there is a record, get the artist id to return
            if (rs.next()) {
                ret = rs.getInt("ArtistID");
                logger.info("Search for artist '" + name + "' yielded an ID of " + ret);
            }
            else {
                logger.info("Search for artist '" + name + "' yielded no results");
            }
            
            // if there is a second record we have ambiguous resuts, return -1
            if (rs.next()) {
                ret = -1;
                logger.info("Search for artist yielded ambiguous results, returning -1");
            }
        }
        catch(SQLException ex){
            logger.severe("Issue searching for artist: " + ex.getMessage());
        }

        // send back either an ID or -1 for no or ambiguous results
        return ret;
    }
    
    /**
     * Updates an artist's name by the given id.
     * 
     * @param id The ID of the artist to update.
     * @param name The new name of the artist.
     * @return true when a single record is updated. false if no records or multiple
     * records are updated.
     */
    public boolean updateArtist(int id, String name) {
        boolean ret = false;
        
        try{
            PreparedStatement ps = this.con.prepareStatement("UPDATE Artist SET Name = ? WHERE ArtistID = ?");
            ps.setString(1, name);
            ps.setInt(2, id);
            int ra = ps.executeUpdate();
            
            if (ra == 1) {
                ret = true;
                logger.info("Updated artist with ID " + id + " set name to '" + name + "'");
            }
            else {
                logger.warning("Update artist with ID " + id + " had an undesired result and changed " + ra + " records");
            }
        }
        catch(SQLException ex){
            logger.severe("Issue updating artist: " + ex.getMessage());
        }
        
        return ret;
    }
    
    /**
     * Deletes an artist by the given id.
     * @param id The ID of the artist to delete.
     * @return true when a single record is deleted. false if no records or multiple
     * records are deleted.
     */
    public boolean deleteArtist(int id) {
        boolean ret = false;
        
        try{
            PreparedStatement ps = this.con.prepareStatement("DELETE FROM Artist WHERE ArtistID = ?");
            ps.setInt(1, id);
            int ra = ps.executeUpdate();
            
            if (ra == 1) {
                ret = true;
                logger.info("Deleted artist with ID " + id);
            }
            else {
                logger.warning("Delete artist with ID " + id + " had an undesired result and changed " + ra + " records");
            }
        }
        catch(SQLException ex){
            logger.severe("Issue deleting artist: " + ex.getMessage());
        }
        
        return ret;
    }
}
