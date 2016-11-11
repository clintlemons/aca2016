/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.aca2016.jdbc.jeffbanksz4l;

import edu.uca.aca2016.config.PropertiesExample;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jeffb
 */
public class ChinookManager {

    private final Properties defaultProperties = new Properties();

    public ChinookManager() {
        this.loadDefaultProperties();

        System.out.println(this.defaultProperties.getProperty("db.connection"));
    }

    private void loadDefaultProperties() {
        FileInputStream in = null;

        try {
            Path inpath = Paths.get("resources", "config", "jeffbanksz4l", "ChinookManager.properties");
            in = new FileInputStream(inpath.toFile());
            this.defaultProperties.load(in);
            in.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertiesExample.class.getName()).log(Level.SEVERE, "Properties file was not found", ex);
        } catch (IOException ex) {
            Logger.getLogger(PropertiesExample.class.getName()).log(Level.SEVERE, "Exception reading properties file", ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(PropertiesExample.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void connectToAndQueryDatabase(String url) throws SQLException {
        Connection con = DriverManager.getConnection(url);

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Customer");

        while (rs.next()) {
            String first_name = rs.getString("FirstName");
            String last_name = rs.getString("LastName");
            int id = rs.getInt("CustomerId");

            System.out.format("Customer: %d\t%-30.30s %-30.30s%n", id, first_name, last_name);
        }

        stmt.close();
        con.close();
    }
}
