/*
 * VibrationManager.java
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
import com.rattat.micro.ui.vibrator.Vibrator;

/**
 * An instance of the VibrationManager class responds to 
 * game and ship events to vibrate at appropriate times
 *
 * @author william@rattat.com
 */
public class VibrationManager implements GameListener, ShipListener {
	    
	/**
	 * miliseconds to vibrate when the ship explodes
	 */
    private final int SHIP_VIBRATION_DURATION = 250;
    
	/**
	 * miliseconds to vibrate when an asteroid explodes
	 */
    private final int ASTEROID_VIBRATION_DURATION = 100;
    
	/**
	 * miliseconds to vibrate when the flying saucer explodes
	 */
    private final int SAUCER_VIBRATION_DURATION = 100;
    
    /**
     * On / off flag - will not vibrate when switched off
     */
	private boolean on = false;

	/**
	 * Respond to ship events and vibrate appropriately
	 * 
	 * @param event
	 * @param model
	 * 
	 * @see com.rattat.micro.game.aster.mvc.ShipListener.shipEvent(int event, Model model)
	 */
	public void shipEvent(int event, Model model) {
	}
	
	/**
	 * Respond to game events and vibrate appropriately
	 * 
	 * @param event
	 * @param model
	 * 
	 * @see com.rattat.micro.game.aster.mvc.GameListener.gameEvent(int gameEvent, Model model)
	 */
	public void gameEvent(int gameEvent, Model model) {
		if ( ! on ) {
			return;
		}
		
		switch (gameEvent) {
	    case EVENT_ASTEROID_DESTROYED:
	    	Vibrator.vibrate(ASTEROID_VIBRATION_DURATION);
	    	break;
	    case EVENT_SHIP_DESTROYED:
	    	Vibrator.vibrate(SHIP_VIBRATION_DURATION);
	    	break;
	    case EVENT_SAUCER_DESTROYED:
	    	Vibrator.vibrate(SAUCER_VIBRATION_DURATION);
	    	break;
		}
	}
	
	/**
	 * Check if vibrations are turned on
	 * 
	 * @return	True if on, false othrwise
	 */
	public boolean isOn() {
		return on;
	}

	/**
	 * Turn vibrations on or off
	 * 
	 * @param on
	 */
	public void setOn(boolean on) {
		this.on = on;
	}
}
