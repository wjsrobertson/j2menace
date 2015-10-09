/*
 * GameListener.java
 * 
 * Copyright 2007 William Robertson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.rattat.micro.game.aster.mvc;

/**
 * <p>
 * Defines an interface which objects wishing to observe
 * game events must implement. 
 * </p>
 * 
 * <p>
 * Events are defined using the public static integers 
 * like {@code EVENT_THRUST_START} and {@code EVENT_MISSILE_FIRED}
 * which are defined in this interface.
 * </p>
 *
 * @author william@rattat.com
 */
public interface GameListener {
	
	/**
	 * Event fired when the game has first started
	 */
    public static final int EVENT_GAME_START = 1;
    
	/**
	 * Event fired when the game ends
	 */
    public static final int EVENT_GAME_END = 2;
    
	/**
	 * Event fired when an asteroid is destroyed
	 */
    public static final int EVENT_ASTEROID_DESTROYED = 3;
    
	/**
	 * Event fired when the spaceship is destroyed
	 */
    public static final int EVENT_SHIP_DESTROYED = 4;
    
	/**
	 * Event fired when a flying saucer is destroyed
	 */
    public static final int EVENT_SAUCER_DESTROYED = 5;
    
    //public static final int EVENT_SHIP_MISSILE_FIRED = 6;
    
	/**
	 * Event fired when a flying saucer appears onscreen
	 */
    public static final int EVENT_SAUCER_APPEAR = 7;
    
	/**
	 * Event fired when a flying saucer shoots a missile
	 */
    public static final int EVENT_SAUCER_MISSILE_FIRED = 8;
    
	/**
	 * Event fired when a new level is entered
	 */
    public static final int EVENT_NEW_LEVEL = 9;
    
	/**
	 * Event fired when a missilfew hundred milisecondse fired by a flying saucer 
	 * is blocked by an asteroid
	 */
    public static final int EVENT_MISSILE_BLOCKED = 10;
    
	/**
	 * Event fired when the users space ship becomes mortal. This
	 * is a short time after gameplay starts to give the user a 
	 * chance to get into position when the l;evel first starts.
	 */
    public static final int EVENT_SHIP_MORTAL = 11;
           	
    /**
     * <p>
     * Objects who wish to observe game events fired by the GameController
     * class must implement this interface 
     * </p>
     * 
     * <p>
     * Objects can see and alter the state of the game by 
     * performing operations on the {@code model} parameter.
     * </p>
     * 
     * @param gameEvent	The event that has been fired
     * @param model		The model of the game
     * 
     * @see GameController.addListener(GameListener listener)
     */
	public void gameEvent(int gameEvent, Model model);
}
