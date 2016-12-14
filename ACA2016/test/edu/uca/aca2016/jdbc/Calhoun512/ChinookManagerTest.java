/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.aca2016.jdbc.Calhoun512;

import java.io.IOException;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author calho
 */
public class ChinookManagerTest {
    
    public ChinookManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addArtist method, of class ChinookManager.
     */
    @Test
    public void testAddArtist() throws Exception {
        System.out.println("addArtist");
        String artist_name = "Andrew";
        ChinookManager instance = new ChinookManager();
        instance.addArtist(artist_name);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of getArtist method, of class ChinookManager.     
     */
    @Test
     public void testGetArtist() throws IOException, SQLException {
        System.out.println("getArtist");
        ChinookManager instance = new ChinookManager();
        int x = instance.getArtist("Gerald Moore");
        assertEquals (270, x);
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
   }
     
    /**
     * Test of updateArtist method, of class ChinookManager.
     */
    @Test
    public void testUpdateArtist() throws Exception {
        System.out.println("updateArtist");
        int Id = 270;
        String Name = "Geraldine";
        ChinookManager instance = new ChinookManager();
        boolean expResult = true;
        boolean result = instance.updateArtist(Id, Name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of deleteArtist method, of class ChinookManager.
     */
    @Test
    public void testDeleteArtist() throws Exception {
        System.out.println("deleteArtist");
        int ArtistId = 0;
        ChinookManager instance = new ChinookManager();
        boolean expResult = false;
        boolean result = instance.deleteArtist(ArtistId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
