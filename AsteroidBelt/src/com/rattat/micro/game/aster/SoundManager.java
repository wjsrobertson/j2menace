/*
 * SoundManager.java
 * 
 * Copyright 2007 William Robertson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.rattat.micro.game.aster;

import com.rattat.micro.game.aster.mvc.GameListener;
import com.rattat.micro.game.aster.mvc.Model;
import com.rattat.micro.game.aster.mvc.ShipListener;
import com.rattat.micro.sound.SimplePlayer;

/**
 * An instance of the SoundManager class responds to 
 * game and ship events to play sounds at the 
 * appropriate times
 *
 * @author william@rattat.com
 */
public class SoundManager implements GameListener, ShipListener {
	
	/**
	 * Flag for on or off
	 */
	private boolean on = false;
    
	/**
	 * Key for the explosion sound
	 */
    public static final String SOUND_EXPLOSION  = "explosion";
    
	/**
	 * Key for the shoot sound
	 */
    public static final String SOUND_SHOOT      = "shoot";
    
	/**
	 * Key for the starting sound
	 */
    public static final String SOUND_START      = "start";
    
    //public static final String SOUND_END        = "end";
    
	/**
	 * Key for the saucer sound
	 */
    public static final String SOUND_SAUCER     = "saucer";
    
	/**
	 * Key for the theme music played in the menu
	 */
    public static final String SOUND_MENU       = "menu";       
    
    //public static final String SOUND_EXPLOSION2 = "explosion2";
    
	/**
	 * Key for the sound when a saucer missile is bloecked by an esteroid
	 */
    public static final String SOUND_BLOCK = "block";   
	
    /**
     * The helper object for playing sounds
     */
	private SimplePlayer player = null;
	
	/**
	 * Create a new instance of SoundManager
	 */
	public SoundManager() {
		player = SimplePlayer.getInstance();
		initSounds();
	}
	
	/**
	 * Load all the sounds that will be used in the application
	 */
    private void initSounds() {
        try {
        	player.add(SOUND_MENU,      "/menu.mid",      "audio/midi"); 
        	player.add(SOUND_EXPLOSION, "/explosion.wav", "audio/x-wav");
        	player.add(SOUND_SHOOT,     "/shoot.wav",     "audio/x-wav");
        	player.add(SOUND_START,     "/start.wav",     "audio/x-wav");
        	//player.add(SOUND_END,       "/end.wav",       "audio/x-wav");
        	player.add(SOUND_SAUCER,    "/saucer.wav",    "audio/x-wav"); 
        	//player.add(SOUND_EXPLOSION2,"/explosion2.wav","audio/x-wav");
        	player.add(SOUND_BLOCK,		"/block.wav","audio/x-wav"); 
        } catch (Exception e) {
            System.err.println(e);
        }       
    }
	
	/**
	 * Respond to ship events and play sounds at appropriate times
	 * 
	 * @param event
	 * @param model
	 * 
	 * @see com.rattat.micro.game.aster.mvc.ShipListener.shipEvent(int event, Model model)
	 */
	public void shipEvent(int event, Model model) {
		if ( ! on ) {
			return;
		}
		
		switch (event) {
			case EVENT_THRUST_START:
				break;
			
			case EVENT_THRUST_STOP:
				break;
			
			case EVENT_MISSILE_FIRED:
		    	player.play(SOUND_SHOOT);
				break;
		}
	}
	
	/**
	 * Respond to game events and play sounds at appropriate times
	 * 
	 * @param gameEvent
	 * @param model
	 * 
	 * @see com.rattat.micro.game.aster.mvc.GameListener.gameEvent(int gameEvent, Model model)
	 */
	public void gameEvent(int gameEvent, Model model) {
		if ( ! on ) {
			return;
		}
		
		switch (gameEvent) {
		    case EVENT_GAME_START:
		    	break;
		    case EVENT_GAME_END:
		    	//player.play(SOUND_END);
		    	break;
		    case EVENT_ASTEROID_DESTROYED:
		    	player.play(SOUND_EXPLOSION);
		    	break;
		    case EVENT_SHIP_DESTROYED:
		    	player.play(SOUND_EXPLOSION);
		    	break;
		    case EVENT_SAUCER_DESTROYED:
		    	player.play(SOUND_EXPLOSION);
		    	break;
		    case EVENT_SAUCER_APPEAR:
		    	player.play(SOUND_SAUCER);
		    	break;
		    case EVENT_SAUCER_MISSILE_FIRED:
		    	player.play(SOUND_SHOOT);
		    	break;
		    case EVENT_NEW_LEVEL:
		    	break;
		    case EVENT_MISSILE_BLOCKED:
		    	player.play(SOUND_BLOCK);
		    	break;
		    case EVENT_SHIP_MORTAL:
		    	player.play(SOUND_START);
		    	break;
		}
	}

	/**
	 * Check if playing sounds is turned on or off 
	 * 
	 * @return	True if on, false otherwise
	 */
	public boolean isOn() {
		return on;
	}

	/**
	 * Turn sdounds on or off
	 * 
	 * @param on
	 */
	public void setOn(boolean on) {
		this.on = on;
	}
	
	/**
	 * Play a sound by name an number of times
	 * 
	 * @param sound	Name of the sound to play
	 * @param num
	 */
	public void play(String sound, int num) {
		if ( on ) {
			player.play(sound, num);
		}
	}
	
	/**
	 * Play a sound 
	 * 
	 * @param sound	Name of the sound to play
	 * @param num
	 */
	public void play(String sound) {
		if ( on ) {
			player.play(sound);
		}
	}
	
	/**
	 * Stop playing a sound
	 * 
	 * @param sound	Name of the sound to stop playing the sound off
	 */
	public void stop(String sound) {
		player.stop(sound);
	}
}
