/*
 * AsteroidsRecords.java
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

import javax.microedition.rms.RecordStoreException;

import com.rattat.micro.db.SimpleStore;

/**
 * Simple class for loading and saving persistent 
 * data - high score and sound / vibrate options
 *
 * @author william@rattat.com
 */
public class AsteroidsRecords {
	
	/**
	 * The game high score
	 */
	private int highScore = 0;
	
	/**
	 * Flag - sound on / off
	 */
	private boolean soundOn = true;
	
	/**
	 * Flag - vibrate on / off
	 */
	private boolean vibrateOn = true;
	
	/**
	 * Helper objext for saving / retreiving data
	 */
	private SimpleStore store = null;
	
	/**
	 * Key for the sound record
	 */
	private static String SOUND = "sound";
	
	/**
	 * Key for the vibrate record
	 */
	private static String VIBRATE = "vibrate";
	
	/**
	 * Key for the high score record
	 */
	private static String SCORE = "highScore";
	
	/**
	 * Create a new instance of AsteroidsRecords and
	 * load the records
	 */
	public AsteroidsRecords() {
		try {
			store = new SimpleStore("asteroids");
			
			String dbScore = store.get(SCORE);
			if ( dbScore != null ) {
				highScore = Integer.valueOf(dbScore).intValue();
			} 
			
			String dbSoundOn = store.get(SOUND);
			if ( dbSoundOn != null ) {
				if ( dbSoundOn.equals("true") ) {
					soundOn = true;
				} else {
					soundOn = false;
				}
			}
			
			String dbVibrateOn = store.get(VIBRATE);
			if ( dbVibrateOn != null ) {
				if ( dbVibrateOn.equals("true") ) {
					vibrateOn = true;
				}else {
					vibrateOn = false;
				}
			}
			
		} catch (  RecordStoreException e ) {
			System.err.println(e);
			// never mind - the defaults are pretty safe
		}
	}
	
	/**
	 * Get the game high score
	 * 
	 * @return
	 */
	public int getHighScore() {
		return highScore;
	}
	
	/**
	 * Set the game high score and store it
	 * 
	 * @return
	 */
	public void setHighScore(int score) {
		store.put(SCORE, String.valueOf(score));
		this.highScore = score;
	}
	
	/**
	 * Is sound on or off
	 * 
	 * @return
	 */
	public boolean isSoundOn() {
		return soundOn;
	}
	
	/**
	 * Set the sound option and store it
	 * 
	 * @return
	 */
	public void setSoundOn(boolean soundOn) {
		store.put(SOUND, String.valueOf(soundOn));
		this.soundOn = soundOn;
	}
	
	/**
	 * Is vibrate on or off
	 * 
	 * @return
	 */
	public boolean isVibrateOn() {
		return vibrateOn;
	}
	
	/**
	 * Set the vibrate option and store it
	 * 
	 * @return
	 */
	public void setVibrateOn(boolean vibrateOn) {
		store.put(VIBRATE, String.valueOf(vibrateOn));
		this.vibrateOn = vibrateOn;
	}
}
