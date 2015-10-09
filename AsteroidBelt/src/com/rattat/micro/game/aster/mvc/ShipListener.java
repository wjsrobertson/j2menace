/*
 * ShipListener.java
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
 * ship events must implement. 
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
public interface ShipListener {
	
	/**
	 * Thruster on the ship has been fired
	 */
	public static final int EVENT_THRUST_START = 1;
	
	/**
	 * Thruster on the ship has been stopped
	 */
	public static final int EVENT_THRUST_STOP = 2;
	
	/**
	 * A missile has been fired by the ship
	 */
	public static final int EVENT_MISSILE_FIRED = 3;
	
    /**
     * <p>
     * Objects who wish to observe game events fired by the ShipController
     * class must implement this interface 
     * </p>
     * 
     * <p>
     * Objects can see and alter the state of the game by 
     * performing operations on the {@code model} parameter.
     * </p>
     * 
     * @param event		The event that has been fired
     * @param model		The model of the game
     * 
     * @see GameController.addListener(GameListener listener)
     */
	public void shipEvent(int event, Model model);
}
