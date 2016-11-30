/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.aca2016.jdbc.clintlemons;

import java.io.BufferedReader;
import java.io.File;
import static java.io.FileDescriptor.out;
import java.util.Properties;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileInputStream;
import java.io.IOException;
import static java.lang.System.in;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.sqlite.*;
import java.util.Dictionary;
import java.util.logging.*;
import java.util.logging.Logger;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.ResourceBundle;
//import static javax.swing.text.html.HTML.Tag.SELECT;
import jdk.nashorn.internal.runtime.ScriptRuntime;
//import static org.apache.commons.net.imap.IMAPClient.SEARCH_CRITERIA.FROM;
import org.sqlite.SQLiteConnection;
import org.sqlite.jdbc4.JDBC4PreparedStatement;
/**
 *
 * @author Username
 */
public class ChinookManager {
    Connection con;
    private final Properties defaultProperties =new Properties();
    int ArtistId = -1;
    Statement stmt = null;
    ResultSet rs = null;
    
    /**
     *loads properties file
     * @param dbconnection
     * @throws java.sql.SQLException
     * 
     */
    public void ChinookManager (){
        FileInputStream in;
        try{
            stmt = con.createStatement(
            ResultSet.TYPE_SCROLL_INSENSITIVE,
            ResultSet.CONCUR_UPDATABLE);
            Path inpath = Paths.get("resources","config","clintlemons","ChinookManager.properties");
            in = new FileInputStream(inpath.toFile());
            this.defaultProperties.load(in);
            defaultProperties.getProperty("ChinookManager.properties");
           con=DriverManager.getConnection(defaultProperties.getProperty("db.connection"));
           in.close();
        } catch (FileNotFoundException Properties_Not_Found) {
            Logger.getLogger(ChinookManager.class.getName()).log(Level.WARNING, null, Properties_Not_Found);
        } catch (IOException Properties_IO_Error) {
            Logger.getLogger(ChinookManager.class.getName()).log(Level.WARNING, null, Properties_IO_Error);
        } catch (SQLException ex) {
            Logger.getLogger(ChinookManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    /**
     *Add Artist Name
     * @param Name
     */
    public void addArtist(String Name){
       PreparedStatement ps =null;
        try {con=DriverManager.getConnection(defaultProperties.getProperty("db.connection"));
        }
        catch (SQLException ex) {
            Logger.getLogger(ChinookManager.class.getName()).log(Level.SEVERE, null, ex);
        }       
           try {
               String query= ("INSERT INTO ARTIST (Name)VALUES(?,?)");
               con.createStatement();
               ps.setString(1, Name);
               ps.executeUpdate(query);
               in.close();
           } catch (SQLException ex) {
               Logger.getLogger(ChinookManager.class.getName()).log(Level.WARNING, null, ex);
           } catch (IOException ex) {
            Logger.getLogger(ChinookManager.class.getName()).log(Level.WARNING, null, ex);
        }
    }
    /**
     *Get Artist Name
     * @param  Name
     * @return
     * @throws SQLException
     */
    public int getArtist(String Name) throws SQLException{
        int ArtistId = -1;
        PreparedStatement ps = null;
        rs = stmt.executeQuery("SELECT*FROM Artist WHERE UPPER (Name)=(?)"); //LIKE %NaMe% = Name");
            if (!con.isValid(30)) {
            } else {
                    con.rollback();
                    con.close();
                    Logger.getLogger("connection time-out");
                }
            try {
            String query= ("INSERT INTO ARTIST (Name)VALUES(?,?)");
            con.prepareStatement("String Name %NaMe%");
            ps.setString(1, Name.toUpperCase());
            rs = ps.executeQuery(query);
            if (rs.next()){
               rs.getInt("ArtistId");
            }
            if (rs.next()){
               return ArtistId=-1;
            }
            }catch (SQLException ex) {
               Logger.getLogger(ChinookManager.class.getName()).log(Level.WARNING, null, ex);
            }
            finally {
                if (ps !=null);
                ps.close();
            }
            return this.ArtistId;
    }   
    
    public boolean updateArtist(int ArtistId, String Name) throws SQLException{
            PreparedStatement ps = null;
            boolean update = false;
            rs = stmt.executeQuery("SELECT*FROM Artist WHERE UPPER (Name)=(?)");
            if (!con.isValid(30)) {
            } else {
                    con.rollback();
                    con.close();
                    Logger.getLogger("connection time-out");
                }
            try {
            String query= ("UPDATE ARTIST (Name)VALUES(?,?)");
            con.prepareStatement("UPDATE ARTIST WHERE ARTIST ");
            ps.setString(1, Name.toUpperCase());
            ps.setInt(2, ArtistId);
            ps.executeUpdate(query);
            if(ArtistId == 1){
                update = true;
            }        
            } catch (SQLException ex) {
            Logger.getLogger(ChinookManager.class.getName()).log(Level.SEVERE, null, ex);
        }       finally {
                if (ps !=null);
                ps.close();
            }
            return update;
    }
    public boolean deleteArtist(int ArtistId) throws SQLException{
           PreparedStatement ps = null;
            boolean update = false;
            rs = stmt.executeQuery("DELETE FROM Artist"+ "WHERE ArtistId =(?)");
            if (!con.isValid(30)) {
            } else {
                    con.rollback();
                    con.close();
                    Logger.getLogger("connection time-out");
                }
            try {
            con.prepareStatement("String Name %NaMe%");
            ps.setInt(1, ArtistId);
            int Id = ps.executeUpdate();
            if(ArtistId == 1){
                update = true;
            }        
            } catch (SQLException ex) {
            Logger.getLogger(ChinookManager.class.getName()).log(Level.SEVERE, null, ex);
        }       finally {
                if (ps !=null);
                ps.close();
            }return update;    
    }
    /**
     *BatchLoadArtist
     * @param f
     * @param Col
     */
    public void BatchLoadArtist(File f, int Col){
        //for col (param) 0,2 --ex of the key valued pairs being passed to you to be read by csv reader.
        //String[] Artists = line.split(",");
        //artists[collection]
        //           ^index 0
        f = new File("Artist.csv");
        //BufferedReader br = new BufferedReader(BatchLoadArtist("Artist.csv",int));
        HashMap csv = new HashMap(defaultProperties);
        
    }
}
    


//       logger.info("Added artist'"+"'Leonard Cohen"+"' to the database");
       //PreparedStatement.

//        public int getArtist(String name){
//            
//        }

//                logger.info("not implemented");
//        return (-1:2);
    

//        private Properties prop=new Properties(){
//                String Connection = prop.getProperty("ChinookManager.properties");
//                System.out.println("Connection Successful");
                
//        try {
//            prop.load(in);
//        } catch (IOException ex) {
//            Logger.getLogger(ChinookManager.class.getName()).log(Level.WARNING, null, ex);
//        }
//        try {
//            in.close();
//        } catch (IOException ex) {
//            Logger.getLogger(ChinookManager.class.getName()).log(Level.INFO, null, ex);
//     Connection con;
//    public ChinookManager() throws SQLException {
//        this.con = DriverManager.getConnection("ChinookManager.properties");   
//    }
//    public void addArtist(String Artist, int ArtistID){
//        
//    }}
//} public void insertRow(String coffeeName, int supplierID,
//                      float price, int sales, int total)
//    throws SQLException {
//
//    Statement stmt = null;
//    try {
//        stmt = con.createStatement(
//            ResultSet.TYPE_SCROLL_SENSITIVE
//            ResultSet.CONCUR_UPDATABLE);
//
//        ResultSet uprs = stmt.executeQuery(
//            "SELECT * FROM " + dbName +
//            ".COFFEES");
//
//        uprs.moveToInsertRow();
//        uprs.updateString("COF_NAME", coffeeName);
//        uprs.updateInt("SUP_ID", supplierID);
//        uprs.updateFloat("PRICE", price);
//        uprs.updateInt("SALES", sales);
//        uprs.updateInt("TOTAL", total);
//
//        uprs.insertRow();
//        uprs.beforeFirst();
//    } catch (SQLException e ) {
//        JDBCTutorialUtilities.printSQLException(e);
//    } finally {
//        if (stmt != null) { stmt.close(); }
//    }
//}
       //SQL ex here:
       // INSERT INTO Album (AlbumId, Title, ArtistId) 
       //VALUES (1, 'Waiting for the miracle', 2)
       //
       //1,2,3 (?,?,?)        
    //public void ConnectAndLoadDefaultProperties("Chinook_db") {
       // try {
           // String db = "C:\\Users\\Username\\Documents\\Chinook_db\\Chinook_Sqlite.sql";  
     //  Connection con = DriverManager.getConnection("Chinook_db");
      // public void DefaultProperties(){
            //String  == ("C:\Users\Username\Documents\NetBeansProjects\aca2016\ACA2016\resources\config\clintlemons");
           // defaultProperties = ("ChinookManager.properties");
           // Path file = file.get("C:\Users\Username\Documents\NetBeansProjects\aca2016\ACA2016\resources\config\clintlemons\ChinookManager.properties");
 
            //ResultSet rs = stmt.executeQuery("SELECT * FROM Album");
          //  Statement stmt = con.createStatement("Album");
            // in = new FileInputStream(inpath.toFile());
            //  this.defaultProperties.load();
            // in.close();
            // } catch (SQLException ex) {
            //  Logger.getLogger(ChinookManager.class.getName()).log(Level.SEVERE, null, ex);     
//        default.FileInputStream = ("ChinookManager.properties") {
//        this.toString();
//        default.Properties = 
//        
//    }
//  
//            
 // @cpfiles ex---public void addArtist(String name){
        //         logger.info("Added artist'"+name+"' to the database");
    //}
   // @cpfiles ex ---public int getArtist(String name)
   //                logger.info("not implemented");
   //                  return -1:
    //}
//    //@cpfiles ex was on or around line 31 
//       private static final Logger logger = Logger.getLogger(ChinookManager.class.getName());


    //this.con = load.DriverManager
   // public void main connectChinookManager() {
        //this.con = DriverManager.getConnection((jdbc:sqlite:)) + load.ChinookManager.properties);
//        public void ChinookManager(){