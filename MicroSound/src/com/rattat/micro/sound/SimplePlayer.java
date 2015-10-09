/**
 * @author william@rattat.com
 */

package com.rattat.micro.sound;

import java.util.Enumeration;
import java.util.Hashtable;
import java.io.*;
import javax.microedition.io.*;
import javax.microedition.media.*;

/**
 * Facade class for playing sounds
 *
 * @author william@rattat.com
 */
public class SimplePlayer implements PlayerListener {  
    
    Hashtable sounds = new Hashtable();
    
    private static SimplePlayer instance = null;
    
    /** 
     * private constructor - use {@code getInstance()} 
     * 
     * @see getInstance()
     */
    private SimplePlayer () {
    }
    
    /**
     * Get the singleton instance of SimplePlayer
     */
    public static SimplePlayer getInstance() {
    	if ( instance == null ) {
    		instance = new SimplePlayer();
    	}
    	
    	return instance;
    }
    
    /**
     * Add a sound to teh list of available sounds
     *
     * @param name Name of the sound to add
     * @param file file to the sound
     * @param format Format of file
     * 
     * @return true if added, false otherwise
     */
    public boolean add(String name, String file, String format) {
    	try {
	        InputStream is = getClass().getResourceAsStream(file);
	        Player player = Manager.createPlayer(is, format);
	        player.realize();
	        player.prefetch();
	        sounds.put(name, player);
	        return true;
    	} catch (Exception e) {
    		return false;
    	}
    }
    
    /**
     * Play a sound a number of times
     * 
     * @param name   Name of the sound to play
     * @param loop      Number of times to loop the sound
     * 
     * @return true if played, false otherwise
     */
    public synchronized boolean play(String name, int loop) {
        try {
            Player player = (Player) sounds.get(name);
            if (player != null ) {
            	if ( player.getState() == Player.STARTED) {
                    player.stop();
            	}
            	if ( player.getState() == Player.PREFETCHED) {
                    player.setLoopCount(loop);
                    player.start();
            	}
            }
            return true;
        } catch (Exception e) {
        	return false;
        }
    }
    
    /**
     * Play a sound once
     *
     * @param name   Name of the sound to play
     */
    public synchronized boolean play(String name) {
    	return play(name, 1);
    }
    
    /**
     * Remove a sound form the list of avaiale sounds
     *
     * @param name   Name of the sound to remove
     */
    public void remove(String name) {
        sounds.remove(name);
    }
    
    /**
     * Stop all sounds
     */ 
    public synchronized void stop() {
        Enumeration enumeration = sounds.elements ();
        
        while (enumeration.hasMoreElements ()) {
            Player player = (Player) enumeration.nextElement();
            try {
                player.stop();
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
    
    /**
     * Stop all sounds
     */ 
    public synchronized void stop(String name) {
        try {
            Player player = (Player) sounds.get(name);
            if (player != null) {
                player.stop();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    /**
     * Not used
     */
    public void playerUpdate(Player player, String event, Object eventData) {
        
    }
}
