/*
 * Model.java
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

import com.rattat.micro.game.aster.elements.Asteroid;
import com.rattat.micro.game.aster.elements.ExplodingPolygon;
import com.rattat.micro.game.aster.elements.FlyingSaucer;
import com.rattat.micro.game.aster.elements.Missile;
import com.rattat.micro.game.aster.elements.SpaceShip;
import com.rattat.micro.game.aster.elements.Star;

/**
 * <p>
 * Defines the model which contains all inforamtion about
 * the state of the game. FloatingObjects, score and anything
 * else required for gameplay is held here.
 * </p>
 * 
 * @author william@rattat.com
 */
public class Model {

	/**
	 * The spaceship that the user colntrols
	 */
    private SpaceShip spaceShip = new SpaceShip(25);

    /**
     * All asteroids in the game, active and inactive
     */
    private Vector asteroids  = new Vector();
    
    /**
     * All explosions in the game, active and inactive
     */
    private Vector explosions = new Vector();
    
    /**
     * The missile that can be fired by the ship
     */
    private Missile missile = new Missile();
    
    /**
     * The maximum speed of the saucer missile
     */
    private double shipMissileMaxSpeed = 200;

    /**
     * The number of lives the user has
     */
    private int lives = 0;
    
    /**
     * The current score the player has achieved
     */
    private int score = 0;  

    /**
     * The time since hte ship was created
     * 
     * @todo - this shouldn't be required - use SpaceShip age
     */
    private int createShipTimer = 0;
      
    /**
     * The curent level of gameplay - the number of asteroids at the start
     */
    private int currentLevel = 1;

    /**
     * The flying saucer who appears and tries to kill the player
     */
    private FlyingSaucer saucer = new FlyingSaucer(FlyingSaucer.LEFT, 20);
    
    /**
     * The Missile that is fired by the flying saucer
     */
    private Missile saucerMissile = new Missile();
    
    /**
     * The maximum speed of the saucer missile
     */
    private double saucerMissileMaxSpeed = 170;
    
    /**
     * The number of game ticks since the player died
     */
    private int deadTimer = -1;
        
    /**
     * Flag - ship thruster on or off
     */
    private boolean thrusterOn = false;
    
    /**
     * The stars that form the background
     */
    Star stars[] = new Star[20];
    
    /**
     * Create a new instance of Model
     */
    public Model() {
    	saucerMissile.setMaxSpeed(saucerMissileMaxSpeed);
    	missile.setMaxSpeed(shipMissileMaxSpeed);
    }

    /**
     * Get the collection of Asteroid objects
     * 
     * @return
     */
	public Vector getAsteroids() {
		return asteroids;
	}

	/**
	 * Get the current level of gameplay
	 * 
	 * @return
	 */
	public int getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * Set the current level of gameplay
	 * 
	 * @param currentLevel
	 */
	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}
	
	/**
	 * Increment the current level by one
	 */
	public void incCurrentLevel() {
		this.currentLevel++;
	}

	/**
	 * Get the length of time since the player died
	 * 
	 * @return
	 */
	public int getDeadTimer() {
		return deadTimer;
	}

	/**
	 * Set the length of time since the player died
	 * 
	 * @param deadTimer
	 */
	public void setDeadTimer(int deadTimer) {
		this.deadTimer = deadTimer;
	}
	
	/**
	 * Increment the length of time since hte player died
	 */
	public void incDeadTimer() {
		this.deadTimer++;
	}

	/**
	 * Get the collection of ExplodingPolygon objects
	 * 
	 * @return
	 */
	public Vector getExplosions() {
		return explosions;
	}

	/**
	 * Get the number of lives the payer has remaining
	 * 
	 * @return
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * Set the number of lives the payer has remaining
	 * 
	 * @param lives
	 */
	public void setLives(int lives) {
		this.lives = lives;
	}

	/**
	 * Get the missile that can be fired by the user
	 * 
	 * @return
	 */
	public Missile getMissile() {
		return missile;
	}

	/**
	 * Get the FlyingSaucer object
	 * 
	 * @return
	 */
	public FlyingSaucer getSaucer() {
		return saucer;
	}

	/**
	 * Get the missile that can be fired by the FlyingSaucer
	 * 
	 * @return
	 */
	public Missile getSaucerMissile() {
		return saucerMissile;
	}

	/**
	 * Get the players current score
	 * 
	 * @return
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Set the players current score
	 * 
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	/**
	 * Increment the score by {@code score}
	 * 
	 * @param score
	 */
	public void incScore(int score) {
		this.score += score;
	}

	/**
	 * Get the SpaceShip instance
	 * 
	 * @return
	 */
	public SpaceShip getSpaceShip() {
		return spaceShip;
	}

	/**
	 * Check if the thruster is on
	 * 
	 * @return	True if on, false otherwise
	 */
	public boolean isThrusterOn() {
		return thrusterOn;
	}

	/**
	 * Set if the thruster is on or not
	 * 
	 * @param thrusterOn
	 */
	public void setThrusterOn(boolean thrusterOn) {
		this.thrusterOn = thrusterOn;
	}

	/**
	 * Get the stars that form the background
	 * 
	 * @return
	 */
	public Star[] getStars() {
		return stars;
	}

	/**
	 * Set the stars that form the background
	 * 
	 * @param stars
	 */
	public void setStars(Star[] stars) {
		this.stars = stars;
	}

	/**
	 * Get the number of game ticks since the ship was created
	 * 
	 * @return
	 */
	public int getCreateShipTimer() {
		return createShipTimer;
	}
	
	/**
	 * Set the number of game ticks since the ship was created
	 * 
	 * @return
	 */
	public void setCreateShipTimer(int createShipTimer) {
		this.createShipTimer = createShipTimer;
	}
	
	/**
	 * Increment the number of game ticks since the ship was created by one
	 * 
	 * @return
	 */
	public void incCreateShipTimer() {
		createShipTimer++;
	}
	
	
	/**
	 * <p>
	 * Get an inactive Asteroid to use or create a new
	 * one if there are no inactive ones.
	 * </p>
	 * 
	 * <p>
	 * The Asteroid will be activated before it is returned
	 * </p>
	 * 
	 * <p>
	 * This is a simple form of object pooling
	 * </p>
	 * 
	 * @return
	 */
	public Asteroid nextFreeAsteroid() {
		Asteroid asteroid = null;
		
		// check if there is an inactive Asteroid to use
		
		for ( Enumeration en = asteroids.elements() ; en.hasMoreElements() ; ) {
			asteroid = (Asteroid) en.nextElement();
			if ( ! asteroid.isActive() ) {
				asteroid.setActive(true);
				return asteroid;
			}
		}
		
		// no existing asteroids found, so create a new one
		
		asteroid = new Asteroid();
    	asteroid.setActive(true);
    	if ( ! asteroids.contains(asteroid) ) {
    		asteroids.addElement(asteroid);
    	}
		
		return asteroid;
	}
	
	/**
	 * <p>
	 * Get an inactive ExplodingPolygon to use or create a new
	 * one if there are no inactive ones.
	 * </p>
	 * 
	 * <p>
	 * The ExplodingPolygon will be activated before it is returned
	 * </p>
	 * 
	 * <p>
	 * This is a simple form of object pooling
	 * </p>
	 * 
	 * @return
	 */
	public ExplodingPolygon nextFreeExplosion() {
		ExplodingPolygon explosion = null;
		
		// check if there is an inactive ExplodingPolygon to use
		
		for ( Enumeration en = explosions.elements() ; en.hasMoreElements() ; ) {
			explosion = (ExplodingPolygon) en.nextElement();
			if ( ! explosion.isActive() ) {
				explosion.reset();
				explosion.setActive(true);
				return explosion;
			}
		}
		
		// no existing explosions found, so create a new one
		
		explosion = new ExplodingPolygon();
		explosion.setActive(true);
		explosions.addElement(explosion);
		
		return explosion;
	}

}
