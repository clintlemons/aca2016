/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.aca2016.jdbc.Calhoun512;

/**
 *
 * @author calho
 */

import java.io.BufferedReader;
import java.sql.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 *
 * @author calho
 */
public class ChinookManager{
    
    Connection con;

    private Properties chinook = new Properties();
    
    /**
     *
     * @throws IOException
     * @throws SQLException
     */
    public ChinookManager() throws IOException, SQLException {
                 
        FileInputStream in = null;      
        try {
    
            Path inpath = Paths.get("resources", "config", "Calhoun512", "ChinookManager.properties");
            in = new FileInputStream(inpath.toFile());
            this.chinook.load(in);
            con = DriverManager.getConnection(chinook.getProperty("db.connection"));
                    
        }finally{
            
            if (in != null){
                in.close();
            }
            
        }
        
    }
    /**
     *
     * @param Name
     * @throws SQLException
     */
    
    // This method inputs a row and value into the database.
        public void addArtist(String Name) throws SQLException {
            
            PreparedStatement ps = null;
            
            try{
                
                String sql = "INSERT INTO Artist (Name) VALUES (?)";
                ps = con.prepareStatement(sql);
                ps.setString(1, Name);
                ps.executeUpdate();
            }
            catch(SQLException ex){
                Logger.getLogger(ChinookManager.class.getName());
                
            }
            finally {
                if (ps != null){
                ps.close();
            }
            }
        }
        
    /**
     *
     * @param Name
     * @throws SQLException
     * @return 
     */
        
     // This method searches the database one-by-one to find a given value.
    public int getArtist (String Name) throws SQLException {
            
            PreparedStatement ps = null;
            int ArtistId = -1;
            String sql = "SELECT * FROM Artist WHERE UPPER (Name) = ?";
            try{
                ps = con.prepareStatement(sql);
                ps.setString(1, Name.toUpperCase());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    ArtistId = rs.getInt("ArtistId");
                }
                if (rs.next()) {
                    ArtistId = -1;
            }
                
           } catch (SQLException ex) {
               Logger.getLogger(ChinookManager.class.getName()).log(Level.SEVERE, null, ex);
           } finally{
                if (ps != null) {
                    ps.close();
                }
           }
            //Logger.log(Level.INFO, "Return Artist Id:" + ArtistId);
            return ArtistId;
        }
    
    /**
     *
     * @param Id
     * @param Name
     * @return
     * @throws SQLException
     */
    
    // This method finds a particular row and updates the corresponding value.
    public boolean updateArtist (int Id, String Name) throws SQLException {
            PreparedStatement ps = null;
            boolean x = false;
         
            try{
                String sql = "UPDATE Artist SET Name = ? WHERE ArtistId = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, Name);
                ps.setInt(2, Id);
                int rs = ps.executeUpdate();
                
                if (rs == 1) {
                    x = true;
                }
                      
            }catch (SQLException ex) {
                Logger.getLogger(ChinookManager.class.getName()).log(Level.SEVERE, null, ex);
            
            }finally {
                if (ps != null) {
                    ps.close();
                }
            }
            return x;
    }
        
    /**
     *
     * @param ArtistId
     * @return
     * @throws SQLException
     */
    // This method deletes the selected value from the SQL database
    public boolean deleteArtist (int ArtistId) throws SQLException{
            PreparedStatement ps = null;
            boolean q = false;
            
            
            try {
                String sql = "DELETE FROM Artist WHERE ArtistId = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, ArtistId);
                int rs = ps.executeUpdate();
                
                if (rs == 1) {
                    q = true;
                }
                
            }catch (SQLException ex) {
                Logger.getLogger(ChinookManager.class.getName()).log(Level.SEVERE, null, ex);
                
            }finally {
                if (ps != null) {
                    ps.close();
                }
            }
                return q;
        }
    
        public void loadBatchArtist(File f, int column) throws FileNotFoundException, SQLException{
            PreparedStatement ps = null;
            String line = "";
            String SplitBy = ",";
            
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String sql = "INSERT INTO Artist WHERE Values = (?)";
                ps = con.prepareStatement(sql);
                
            
            
                 }

}
        
}