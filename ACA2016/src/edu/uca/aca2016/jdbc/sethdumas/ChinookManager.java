/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.aca2016.jdbc.sethdumas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public ChinookManager() {
        try {
            Path inpath = Paths.get("resources", "config", "sethdumas", "ChinookManager.properties");
            Properties props;

            FileInputStream in = new FileInputStream(inpath.toFile());
            props = new Properties();
            props.load(in);

            con = DriverManager.getConnection(props.getProperty("db.connection"));
            System.out.println(props.getProperty("db.connection"));

        } catch (SQLException | IOException ex) {
            Logger.getLogger(ChinookManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //addArtist has a single parameter for the artist’s name. 
    //This method should insert a row into the Artist database table 
    //in order to make a new Artist record.
    /**
     * @param ArtistName
     * @throws SQLException
     */
    public void addArtist(String ArtistName) throws SQLException {
        //Connection con = null;
        PreparedStatement ps = null;

        try {
            //con = DriverManager.getConnection(props.getProperty("db.connection"));
            String sql = "INSERT INTO Artist (Name) VALUES (?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, ArtistName);
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ChinookManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
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

    /**
     * @param ArtistName
     * @return
     * @throws SQLException
     */
    public int getArtist(String ArtistName) throws SQLException {
        PreparedStatement ps = null;
        int ArtistId = -1;
        String sql = "SELECT * FROM Artist WHERE UPPER (Name) = (?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, ArtistName.toUpperCase());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ArtistId = rs.getInt("ArtistId");
            }
            if (rs.next()) {
                ArtistId = -1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChinookManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
        return ArtistId;
    }
    //find the artist record with the id 
    //provided, then update the name of the 
    //artist with the name provided. The method 
    //should return a boolean; true if the 
    //update was successful, false otherwise. 

    /**
     * @param ArtistId
     * @param ArtistName
     * @return
     * @throws java.sql.SQLException
     */
    public boolean upadteArtist(int ArtistId, String ArtistName) throws SQLException {
        PreparedStatement ps = null;
        boolean update = false;
        String sql = "UPDATE Artist SET Name = (?) WHERE ArtistId = (?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, ArtistName);
            ps.setInt(2, ArtistId);
            int Id = ps.executeUpdate();

            if (Id == 1) {
                update = true;

            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "SQL Exception: {0}", ex.getMessage());
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
        return update;
    }

    //Method to delete an Artist
    /**
     * @param ArtistId
     * @return
     * @throws java.sql.SQLException
     */
    public boolean deleteArtist(int ArtistId) throws SQLException {
        PreparedStatement ps = null;
        boolean update = false;
        String sql = "DELETE FROM Artist WHERE ArtistId = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, ArtistId);
            ps.executeUpdate();
            int Id = ps.executeUpdate();

            if (Id == 1) {
                update = true;
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "SQL Exception: {0}", ex.getMessage());
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
        return update;

    }

    //A method to read in a CSV file and update the artist column
    /**
     * @param csvFile Read csv file
     * @param Column THe column to read from csv file
     * @throws SQLException
     */
    public void batchLoadArtist(File csvFile, int Column) throws SQLException {

        PreparedStatement ps = null;
        String sql = "INSERT INTO Artist (Name) VALUES (?)";
        ps = con.prepareStatement(sql);

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] artist = line.split(",");
                logger.info("Artist Name: " + artist[Column]);
                ps.setObject(1, artist[Column]);
                ps.addBatch();
                logger.log(Level.INFO, "Added Artist: {0}", artist[Column]);
            }
            ps.executeBatch();
        } catch (IOException ex) {
            Logger.getLogger(ChinookManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
