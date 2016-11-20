/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.aca2016.jdbc.jeffbanksz4l;

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
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Setup for connecting to the database.
 *
 * @author jeffb
 */
public class ChinookManager {

    Connection con;
    private static final Logger logger = Logger.getLogger(ChinookManager.class.getName());

    /**
     * Setup to look for the default Properties.
     *
     * @throws java.sql.SQLException
     */
    public ChinookManager() throws SQLException {
        try {
            Path inpath = Paths.get("resources", "config", "jeffbanksz4l", "ChinookManager.properties");
            FileInputStream in = new FileInputStream(inpath.toFile());
            Properties cmProperties = new Properties();
            cmProperties.load(in);
            in.close();

            logger.info("Connecting to database: " + cmProperties.getProperty("db.connection"));

            con = DriverManager.getConnection(cmProperties.getProperty("db.connection"));

        } catch (FileNotFoundException ex) {
            logger.severe("FileNotFoundException: " + ex.getMessage());
        } catch (IOException ex) {
            logger.severe("IO Exception: " + ex.getMessage());
        }
    }

    /**
     * Method to add an Artist.
     *
     * @param artistName
     * @throws SQLException
     */
    public void addArtist(String artistName) throws SQLException {

        PreparedStatement ps = null;

        try {
            String sql = "INSERT INTO Artist (Name) VALUES (?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, artistName);
            ps.executeUpdate();
            logger.info("Added Artist '" + artistName + "' to the database");
        } catch (SQLException ex) {
            logger.severe("SQL exception: " + ex.getMessage());
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    /**
     * Retrieve the Artist ID
     *
     * @param artistName
     * @return
     * @throws SQLException
     */
    public int getArtist(String artistName) throws SQLException {

        PreparedStatement ps = null;
        int ArtistId = -1;
        String sql = "SELECT * FROM Artist WHERE upper(Name) = ?";
//        ResultSet rs;
//        Statement stmt = con.createStatement();
//        int rowcount = 0;
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, artistName.toUpperCase());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ArtistId = rs.getInt("ArtistId");
            }
            if (rs.next()) {
                ArtistId = -1;
            }

        } catch (SQLException ex) {
            logger.severe("SQL Exception: " + ex.getMessage());
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
        logger.info("Return ArtistId: " + ArtistId);
        return ArtistId;
    }

    /**
     *
     * @param ArtistId
     * @param newArtistName
     * @return
     * @throws java.sql.SQLException
     */
    public boolean updateArtist(int ArtistId, String newArtistName) throws SQLException {

        PreparedStatement ps = null;
        int update = 1;
        boolean updateArtist = false;
        String sql = "UPDATE Artist SET Name = ? WHERE ArtistId = ?";

        try {

            ps = con.prepareStatement(sql);
            ps.setString(1, newArtistName);
            ps.setInt(2, ArtistId);
            ps.executeUpdate();

        } catch (SQLException ex) {
            logger.severe("SQL Exception: " + ex.getMessage());
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
        logger.info("Updated Artist: " + newArtistName);

        if (update != 1) {
            return true;
        } else {
            return false;
        }
//        logger.info("Boolean: " + updateArtist);
    }

    /**
     *
     * @param ArtistId
     * @return
     * @throws java.sql.SQLException
     */
    public boolean deleteArtist(int ArtistId) throws SQLException {

        PreparedStatement ps = null;
        String sql = "DELETE FROM Artist WHERE ArtistId = ?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, ArtistId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            logger.severe("SQL Exception: " + ex.getMessage());
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
    
    public void countRecords(String Artist) throws SQLException {
        
        ResultSet rs;
        Statement stmt = con.createStatement();
        int rowcount = 0;
        
        rs = stmt.executeQuery("SELECT COUNT(*) AS ArtistCount FROM Artist WHERE ArtistID > 275");
        if (rs.next()) {
            rowcount = rs.getInt("ArtistCount");
        }
        
//        System.out.println("There are " + rowcount + " rows");
        
        rs = stmt.executeQuery("SELECT * FROM Artist WHERE ArtistID > 275");
        while (rs.next()) {
//            System.out.format("Artist: %d\t%-30.30s%n", rs.getInt("ArtistID"), rs.getString("Name"));
        }

        stmt.close();
    }
}
