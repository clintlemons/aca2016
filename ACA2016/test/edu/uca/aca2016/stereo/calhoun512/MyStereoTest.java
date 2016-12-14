/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.aca2016.stereo.calhoun512;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class MyStereoTest{
    private MyStereo stereo;

    public MyStereoTest(){
    }

    @BeforeClass
    public static void setUpClass(){
    }

    @AfterClass
    public static void tearDownClass(){
    }

    @Before
    public void setUp(){
        this.stereo = new MyStereo();
    }

    @After
    public void tearDown(){
    }

    /**
     * Test of loadUSB method, of class MyStereo.
     */
    @Test
    public void testLoadUSB(){
        this.stereo.loadUSB();

        // http://www.vogella.com/tutorials/JUnit/article.html#usingjunit_asserts
        assertTrue("The USB drive should report it is loaded",this.stereo.isUSBLoaded());
        assertTrue("There should be more than one track",this.stereo.totalTrackCount() > 0);
        assertTrue("There should be 1000 or fewer tracks",this.stereo.totalTrackCount() <= 1000);
        assertTrue("The player should be 'playing'",this.stereo.isPlaying());

        assertFalse("The player should not be paused after a load",this.stereo.isPaused());
    }

    /**
     * Test of isUSBLoaded method, of class MyStereo.
     */
    @Test
    public void testIsUSBLoaded(){
        assertFalse("The USB should not be loaded when the object is new",this.stereo.isUSBLoaded());

        this.stereo.loadUSB();
        assertTrue("The USB should be loaded now.",this.stereo.isUSBLoaded());

        this.stereo.unloadUSB();
        assertFalse("The USB should not be loaded when the object is new",this.stereo.isUSBLoaded());
    }

    /**
     * Test of unloadUSB method, of class MyStereo.
     */
    @Test
    public void testUnloadUSB(){
        this.stereo.loadUSB();
        this.stereo.unloadUSB();

        assertFalse("The USB drive status should be unloaded",this.stereo.isUSBLoaded());
        assertFalse("There is no USB drive, the stereo should not be 'playing'",this.stereo.isPlaying());
    }

    /**
     * Test of currentTrackNumber method, of class MyStereo.
     */
    @Test
    public void testCurrentTrackNumber(){
        assertEquals(this.stereo.currentTrackNumber(),0);

        this.stereo.loadUSB();

        assertEquals(this.stereo.currentTrackNumber(),1);
    }

    /**
     * Test of totalTrackCount method, of class MyStereo.
     */
    @Test
    public void testTotalTrackCount(){
        this.stereo.loadUSB();

        assertTrue("There should be 1 or more tracks",this.stereo.totalTrackCount() >= 1);
        assertTrue("There should be 1000 or fewer tracks",this.stereo.totalTrackCount() <= 1000);
    }

    /**
     * Test of enableStraightPlayMode method, of class MyStereo.
     */
    @Test
    public void testEnableStraightPlayMode(){

    }

    /**
     * Test of enableShufflePlayMode method, of class MyStereo.
     */
    @Test
    public void testEnableShufflePlayMode(){

    }

    /**
     * Test of stop method, of class MyStereo.
     */
    @Test
    public void testStop(){
        assertFalse(this.stereo.isPlaying());

        this.stereo.loadUSB();
        assertTrue(this.stereo.isPlaying());

        this.stereo.stop();

        assertFalse(this.stereo.isPlaying());
    }

    /**
     * Test of pause method, of class MyStereo.
     */
    @Test
    public void testPause(){
        assertFalse(this.stereo.isPaused());

        this.stereo.loadUSB();
        this.stereo.pause();

        assertFalse(this.stereo.isPlaying());
        assertTrue(this.stereo.isPaused());
    }

    /**
     * Test of nextTrack method in straight play mode, of class MyStereo.
     */
    @Test
    public void testNextTrack_StraightPlay(){
        this.stereo.loadUSB();

        assertEquals(this.stereo.currentTrackNumber(),1);
        assertTrue("There should be 1000 or fewer tracks",this.stereo.totalTrackCount() <= 1000);

        // force the player to advance to the last track.
        for(int i = 1; i < this.stereo.totalTrackCount(); i++){
            this.stereo.nextTrack();
        }

        assertEquals(this.stereo.currentTrackNumber(),this.stereo.totalTrackCount());

        // advance past the last track
        this.stereo.nextTrack();

        assertEquals(this.stereo.currentTrackNumber(),1);
    }

    /**
     * Test of nextTrack method in shuffle play mode, of class MyStereo.
     */
    @Test
    public void testNextTrack_ShufflePlay(){
        this.stereo.loadUSB();

        assertEquals(this.stereo.currentTrackNumber(),1);
        assertTrue("There should be 1000 or fewer tracks",this.stereo.totalTrackCount() <= 1000);

        // for the rest of this test to work, we need at least 15 tracks.
        // we should unload and reload until we get there.
        while(this.stereo.totalTrackCount() < 15){
            this.stereo.unloadUSB();
            this.stereo.loadUSB();
        }

        // enable shuffle play
        this.stereo.enableShufflePlayMode();

        // we are going to attempt to store 15 random tracks in a set
        Set<Integer> s = new HashSet<>();

        for(int i = 1; i < 15; i++){
            this.stereo.nextTrack();

            s.add(this.stereo.currentTrackNumber());
        }

        // the idea is we *should* always get more than 5 random tracks out of 15 attempts.
        assertTrue("There should be at least 5 random tracks in 15 attempts",s.size() >= 5);
    }

    /**
     * Test of previousTrack method, of class MyStereo.
     */
    @Test
    public void testPreviousTrack_StraightPlay(){
        this.stereo.loadUSB();

        assertEquals(this.stereo.currentTrackNumber(),1);
        assertTrue("There should be 1000 or fewer tracks",this.stereo.totalTrackCount() <= 1000);

        this.stereo.previousTrack();
        
        assertEquals(this.stereo.currentTrackNumber(),this.stereo.totalTrackCount());
    }
    
    @Test
    public void testPreviousTrack_ShufflePlay(){
        this.stereo.loadUSB();

        assertEquals(this.stereo.currentTrackNumber(),1);
        assertTrue("There should be 1000 or fewer tracks",this.stereo.totalTrackCount() <= 1000);

        // for the rest of this test to work, we need at least 15 tracks.
        // we should unload and reload until we get there.
        while(this.stereo.totalTrackCount() < 15){
            this.stereo.unloadUSB();
            this.stereo.loadUSB();
        }

        // enable shuffle play
        this.stereo.enableShufflePlayMode();

        // we are going to attempt to store 15 random tracks in a set
        Set<Integer> s = new HashSet<>();

        for(int i = 1; i < 15; i++){
            this.stereo.previousTrack();

            s.add(this.stereo.currentTrackNumber());
        }

        // the idea is we *should* always get more than 5 random tracks out of 15 attempts.
        assertTrue("There should be at least 5 random tracks in 15 attempts",s.size() >= 5);
    }

    /**
     * Test of loadTrackList method, of class MyStereo.
     *
     * @throws java.io.IOException
     */
    @Test(expected = IOException.class)
    public void testLoadTrackList_NullFile() throws IOException{
        File trackListSource = null;

        this.stereo.loadTrackList(trackListSource);
        
        trackListSource = new File("/tmp/no-file");
    }
    
    /**
     * Test of loadTrackList method, of class MyStereo.
     *
     * @throws java.io.IOException
     */
    @Test(expected = IOException.class)
    public void testLoadTrackList_NoFile() throws IOException{
        File trackListSource = new File("/tmp/no-file");
        
        this.stereo.loadTrackList(trackListSource);
    }

    /**
     * Test of loadTrackList method, of class MyStereo.
     *
     * @throws java.io.IOException
     */
    @Test
    public void testLoadTrackList() throws IOException{
        File trackListSource = File.createTempFile("temp-track-file", ".tmp");
        trackListSource.deleteOnExit();
        
        PrintWriter outputStream = new PrintWriter(new FileWriter(trackListSource));
        outputStream.write("Track 1.mp3\n");
        outputStream.write("Track 2.mp3\n");
        outputStream.write("Track 3.mp3\n");
        
        outputStream.close();
        
        this.stereo.loadTrackList(trackListSource);
        
        assertEquals(3, this.stereo.totalTrackCount());
    }

    /**
     * Test of play method, of class MyStereo.
     */
    @Test
    public void testPlay(){
        assertFalse(this.stereo.isPlaying());
        
        this.stereo.loadUSB();
        
        this.stereo.play();
        assertTrue(this.stereo.isPlaying());

        this.stereo.stop();
        assertFalse(this.stereo.isPlaying());
        
        this.stereo.play();
        assertTrue(this.stereo.isPlaying());
        
        this.stereo.pause();
        assertFalse(this.stereo.isPlaying());
        
        this.stereo.play();
        assertTrue(this.stereo.isPlaying());
    }

    /**
     * Test of getTrackList method, of class MyStereo.
     */
    @Test
    public void testGetTrackList() throws IOException{
        File trackListSource = File.createTempFile("temp-track-file", ".tmp");
        trackListSource.deleteOnExit();
        
        PrintWriter outputStream = new PrintWriter(new FileWriter(trackListSource));
        outputStream.write("Track 1.mp3\n");
        outputStream.write("Track 2.mp3\n");
        outputStream.write("Track 3.mp3\n");
        
        outputStream.close();
        
        this.stereo.loadTrackList(trackListSource);
        
        assertTrue(this.stereo.getTrackList().size() == 3);
        assertEquals(this.stereo.getTrackList().get(1), "Track 2.mp3");
    }

    /**
     * Test of getCurrentTrackFileName method, of class MyStereo.
     */
    @Test
    public void testGetCurrentTrackFileName() throws IOException{
        File trackListSource = File.createTempFile("temp-track-file", ".tmp");
        trackListSource.deleteOnExit();
        
        PrintWriter outputStream = new PrintWriter(new FileWriter(trackListSource));
        outputStream.write("Track 1.mp3\n");
        outputStream.write("Track 2.mp3\n");
        outputStream.write("Track 3.mp3\n");
        
        outputStream.close();
        
        this.stereo.loadTrackList(trackListSource);
        
        assertTrue(this.stereo.getTrackList().size() == 3);
        assertEquals("Track 1.mp3", this.stereo.getCurrentTrackFileName());
        
        this.stereo.nextTrack();
        
        assertEquals(this.stereo.getCurrentTrackFileName(), "Track 2.mp3");
    }
}
