/*
 * ShipController.java
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

import java.util.Enumeration;
import java.util.Vector;

import com.rattat.math.geometry.vectored2D.VMath2D;
import com.rattat.math.geometry.vectored2D.Vector2D;
import com.rattat.micro.game.aster.elements.Missile;
import com.rattat.micro.game.aster.elements.SpaceShip;

/**
 * Class for controlling the SpaceShip
 * 
 * @author william@rattat.com
 */
public class ShipController {
	
	/**
	 * The anular velocity for rotations
	 */
	private static final double ANGULAR_VELOCITY = 0.2;
	
	/**
	 * Holds the game model
	 */
	private Model model;
	
	/**
	 * Holds ShipListener instances - ship event observers
	 */
    private Vector listeners = new Vector(); 
	
    /**
     * Create a new instance of ShipController
     * 
     * @param model
     */
	public ShipController( Model model ) {
		this.model = model;
	}
    
    /**
     * Rotate the ship clockwise
     */
    public void rotateShipClockwise() {
    	if (model.getSpaceShip() != null) {
    		model.getSpaceShip().rotate(-ANGULAR_VELOCITY);
    	}
    }
    
    /**
     * Rotate the ship anticlockwise
     */
    public void rotateShipAntiClockwise() {
    	if (model.getSpaceShip() != null) {
            model.getSpaceShip().rotate(ANGULAR_VELOCITY);
    	}
    }
    
    /**
     * Apply impulse force to the ship in forward direction
     */
    public void forwardThrustShip() {
    	if (model.getSpaceShip() != null) {
    		if ( ! model.isThrusterOn() ) {
    	    	notifyListeners(ShipListener.EVENT_THRUST_START);
    		}
	        model.setThrusterOn(true);
	        model.getSpaceShip().thrust();
    	}
    }
    
    /**
     * Apply impulse force to the ship in reverse direction
     */
    public void reverseThrustShip() {
    	if (model.getSpaceShip() != null) {
    		if ( ! model.isThrusterOn() ) {
    	    	notifyListeners(ShipListener.EVENT_THRUST_START);
    		}
	        model.setThrusterOn(true);
	        model.getSpaceShip().reverseThrust();
    	}
    }
    
    /**
     * Stop the ship's thruster
     */
    public void clearThruster() {
    	model.setThrusterOn(false);
    	notifyListeners(ShipListener.EVENT_THRUST_STOP);
    }
    
    /**
     * Fire a missile, of allowed
     */
    public void lanunchMissile() {
    	SpaceShip ship  = model.getSpaceShip();
    	Missile missile = model.getMissile();
    	
    	if ( ship.isActive() && ship.isMortal() && ! model.getMissile().isActive() ) {
            missile.setPosition(new Vector2D( ship.getPosition() ));
            missile.setVelocity( VMath2D.product( ship.getDirection(), missile.getMaxSpeed()) );
            missile.setAge(0);
        	missile.setActive(true);
        	
        	notifyListeners(ShipListener.EVENT_MISSILE_FIRED);
        }
    }
    
    /**
     * Add a new listener to observe ship events
     * 
     * @param listener
     */
    public void addListener(ShipListener listener) {
    	if ( ! listeners.contains(listener) ) {
    		listeners.addElement(listener);
    	}
    }
    
    /**
     * Stop a ship listener form receiving ship events
     * 
     * @param listener
     */
    public void removeListener(ShipListener listener) {
    	if ( listeners.contains(listener) ) {
    		listeners.removeElement(listener);
    	}
    }
    
    /**
     * Notify ship listeners of ship events
     * 
     * @param event
     */
    private void notifyListeners(int event) {
    	for ( Enumeration e = listeners.elements() ; e.hasMoreElements() ; ) {
    		ShipListener listener = (ShipListener) e.nextElement();
    		listener.shipEvent(event, model);
    	}
    }
   	
}
