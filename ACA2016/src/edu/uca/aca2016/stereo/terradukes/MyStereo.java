/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.aca2016.stereo.terradukes;
import edu.uca.aca2016.interfaces.Stereo;
import edu.uca.aca2016.interfaces.StereoExtended;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;

/**
 * Terra D Dukes
 * My Stereo
 * 10/21/16
 *
 * @author DueTe
 */

    
//my

public class MyStereo implements StereoExtended{
    //implement list
    //stereo status
    //is usb loaded
    
    

	ArrayList<Track> tracks;
	int current;
	Status status;
	boolean shufflePlay;
	USB usb;
	boolean usbLoaded;
	
	public MyStereo(){
		this.tracks = new ArrayList<>();
	}
        
        //load usb scan for files 0-1000
        //tracks added to list
        
	@Override
	public void loadUSB() {
            	if(this.tracks.size()>0){
                    
          return ;
        //generate random number 0-999
		}
		int t = (int) Math.random();
		for(int i = 0 - 1000; i < t; i++){
		tracks.add(new Track());
		}
        //if loaded boolean true
   
	    this.usbLoaded = true;
	}
	@Override
	public boolean isUSBLoaded() {

	   return this.usbLoaded;
	}

        @Override
	
        public void unloadUSB() {
        //unload USB-reset
		this.tracks = new ArrayList<>();
		this.current =0;
		this.usbLoaded = false;
                
	}
        //number of tracks
	@Override
	public int totalTrackCount() {
		return tracks.size();
	}
        
        //what number is the current track
	@Override
	public int currentTrackNumber() {
		return this.current;
	}
        
        //1-1000 straight play
	@Override
	public void enableStraightPlayMode() {
		this.shufflePlay = false;
	}
        
        //1-1000 shuffle play
	@Override
	public void enableShufflePlayMode() {
		this.shufflePlay = false;
	}
        //stop or pause
	@Override
	public void stop() {
		this.status = Status.STOPPED;
	}
        
        //stop or pause
	@Override
	public void pause() {
		this.status = Status.PAUSED;
                
	}
        //if forward if pressed advanced to next track- not 0-restart
	@Override
	public void nextTrack() {
		if(this.current< tracks.size()-1)
			this.current++;
		else 
                    this.current =0; 
	}
       //if rewind is pressed reverse to previous track
	@Override
	public void previousTrack() {
		if(this.current> 0)
			this.current--;
		else 
	//if 0 start from end
                    this.current =tracks.size()-1; 
		
	}
        
        //playing
	@Override
	public boolean isPlaying() {
	return status == Status.PLAYING;
	}
        
        //paused
	@Override
	public boolean isPaused() {
	   return status == Status.PAUSED;
	}
 //*
        
        

    @Override
    public void loadTrackList(File trackListSource) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void play() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> getTrackList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCurrentTrackFileName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
    //status
 enum Status {
	PLAYING, PAUSED, STOPPED
}


class USB {

}


class Track {

}

