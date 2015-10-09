/*
 * GameController.java
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
import java.util.Random;
import java.util.Vector;

import com.rattat.math.geometry.vectored2D.VMath2D;
import com.rattat.math.geometry.vectored2D.Vector2D;
import com.rattat.micro.game.aster.elements.Asteroid;
import com.rattat.micro.game.aster.elements.ExplodingPolygon;
import com.rattat.micro.game.aster.elements.FloatingObject;
import com.rattat.micro.game.aster.elements.FlyingSaucer;
import com.rattat.micro.game.aster.elements.Missile;
import com.rattat.micro.game.aster.elements.SpaceShip;
import com.rattat.micro.game.aster.elements.Star;

/**
 * <p>
 * This class contains most of the actual logic that controls 
 * and updates the model.
 * </p>
 * 
 * <p>
 * The {@code tick()} method is the method which is called externally
 * to update the game state, incrementing elapsed time by one game
 * tick.
 * </p>
 * 
 * <p>
 * This class broadcasts events which can be observed
 * by clients who implement the {@code GameListener} interface
 * after they have been added with {@code addListener()}.
 * </p>
 * 
 * @author william@rattat.com
 */
public class GameController {
	
	/**
	 * Random number generator
	 */
    Random random = new Random( (long) System.currentTimeMillis() );
    

    /**
     * Minimum X position - 0
     */
    private int gameMinX;
    
    /**
     * Minimum Y position - 0
     */
    private int gameMinY;
    
    /**
     * Maximum X position in the game
     */
    private int gameMaxX;
    
    /**
     * Maximum Y position in the game
     */
    private int gameMaxY;
    
    /**
     * The width of the game area
     */
    private int gameWidth;
    
    /**
     * The height of the game area
     */
    private int gameHeight;
    
    /**
     * Scale of the game area compared to screen size
     */
    private int gameScale = 10;
 
    /**
     * Number of lives a player starts with
     */
    private static final int NUM_LIVES_START = 3;

    /**
     * Wait time after player looses all lives before game ends
     */
    private static final int DEAD_GAMEOVER_WAIT = 20;
    
    /**
     * Wait time before a new ship is created after a player looses
     * a life
     */
    private static final int CREATE_SHIP_WAIT = 15;
        
    /**
     * Age a spacehip must be before it can be destroyed
     */
    private static final int MIN_KILL_AGE = 10;
    
    /**
     * Age a missile be active for after it has been fired
     * by a spaceship
     */
    private static final int MISSLE_MAX_AGE_SHIP = 9;
    
    /**
     * Age a missile can be active for after it has been fired by a flying saucer
     */
    private static final int MISSLE_MAX_AGE_SAUCER = 10;
       
	/**
	 * Score for killing an asteroid
	 */
	private static final int ASTEROID_HIT_POINTS = 10;

	/**
	 * Score for killing the flying saucer
	 */
    private static final int SAUCER_HIT_POINTS = 100;
    
    /**
     * business of saucer - smaller number means more 
     * chance of saucer appearing per gtame tick
     */
    private static final int SAUCER_ACTIVITY = 1000;
    
    /**
     * smaller number means saucer is more likely to fire 
     * missile per game tick
     */
    private static final int SAUCER_MISSILE_ACTIVITY = 10;

    /**
     * Holds {@code GameListener} instances who are to be 
     * notified of game events
     */
    private Vector listeners = new Vector(); 
	
	/**
	 * Holds the model - all of the game data
	 */
	Model model = null;
	
	/**
	 * Create a new instance of GameController
	 * 
	 * @param model	The game model
	 */
	public GameController( Model model ) {
		this.model = model;
	}
	
	/**
	 * Initialise the size of the game board
	 * 
	 * @param width
	 * @param height
	 */
    public void setGameSize(int width, int height) {
        gameMinX = 0;
        gameMinY = 0;
        gameWidth = width;
        gameHeight = height;
        gameMaxX = gameMinX + gameWidth;
        gameMaxY = gameMinY + gameHeight;
    }
    
    /**
     * Create a set of random stars scattered around the background
     * of the gameplay area
     */
    private void initStars() {
        
    	Star[] stars = new Star[20];
    	
        for (int i=0 ; i<20 ; i++) {
            double width = 3 * random.nextDouble();
            double x = gameScale * gameWidth * random.nextDouble();
            double y = gameScale * gameHeight * random.nextDouble();
            stars[i] = new Star(new Vector2D(x, y), width);
        }
        
        model.setStars( stars );
    }

    /**
     * Initialise the space ship, reset it and
     * place it in the starting position 
     */
    private void initSpaceShip() {
    	SpaceShip ship = model.getSpaceShip();
    	
    	ship.reset();
    	ship.setActive(true);
    	ship.setPosition(gameMaxX/2 * gameScale, gameMaxY/2 * gameScale);
    }

    /**
     * <p>
     * Create {@code numAsteroids} asteroids with random 
     * location, angular velocity and velocity. 
     * </p>
     * 
     * <p>
     * This class uses the object pooling capabilities of the 
     * {@code Model} to re-use inactive asteroids if they
     * are available.
     * </p>
     * 
     * @param numAsteroids	Num asteroids to create
     * 
     * @todo Improve random speed / position
     */
    private void initAsteroids(int numAsteroids) {

        for (int i=0 ; i<numAsteroids ; i++) {
        	double xV = 2 * random.nextDouble() + 1;
        	if ( random.nextInt(1) == 0 ) {
        		xV = -xV;
        	}
        	double yV = 2 * random.nextDouble() + 1;
        	if ( random.nextInt(1) == 0 ) {
        		yV = -yV;
        	}
        	double xP = random.nextInt( (gameMaxX - gameMinX) * gameScale  );	// todo * scale
        	double yP = random.nextInt( (gameMaxY - gameMinY) * gameScale );	// todo * scale
        	
        	Asteroid asteroid = model.nextFreeAsteroid();
        	asteroid.set( new Vector2D(xP, yP), new Vector2D(xV, yV), 3 );
        }
    }
    
    /**
     * Place the flying saucer into gameplay at a random position 
     * on either side of the screen
     * 
     * <p>
	 * Fires a {@code GameListener.EVENT_SAUCER_APPEAR} event 
     * </p>
     */
    private void initSaucer() {
        FlyingSaucer saucer = model.getSaucer(); 
        
        int type = random.nextInt(2);
        int yPosition = (int) ((gameMaxY-gameMinY) * gameScale * random.nextDouble());
              
        
        saucer.setType(type);
        saucer.reset();
        saucer.updateForces();
        saucer.setActive(true);
        
        if (type==FlyingSaucer.LEFT) {
            saucer.setPosition(1, yPosition );
        } else if (type==FlyingSaucer.RIGHT) {
            saucer.setPosition(gameMaxX * gameScale -1, yPosition );
        }
        
        notifyListeners( GameListener.EVENT_SAUCER_APPEAR );
    }

    /**
     * <p>
     * Update the game - increase by one unit of time
     * </p>
     */
    public void tick() {
    	updateFloatingObjects();

        collisionCheck();

        nextLevelCheck();
        
        saucerBehaviour();
        
        gameOverCheck();
    }
    
    /**
     * <p>
     * Preform the logic required during gameplay
     * to update the positions and inactive / active
     * status of all {@code FloatingObject} objects 
     * that are in play
     * </p>
     * 
     * <p>
	 * Fires a {@code GameListener.EVENT_SHIP_MORTAL} event 
	 * if the spaceship has just reached the age where it 
	 * can be killed
     * </p>
     */
    private void updateFloatingObjects() {

        // missile
    	
    	Missile missile = model.getMissile();
    	
        if ( FloatingObject.isActive(missile) ) {
            missile.update();  
            correctPosition(missile);
            
            if (missile.getAge() > MISSLE_MAX_AGE_SHIP) {
                missile.setActive(false);
            }
        }
        
        // saucer missile
        
    	Missile saucerMissile = model.getSaucerMissile();
    	
        if ( FloatingObject.isActive( saucerMissile ) ) {
            saucerMissile.update();  

            if (saucerMissile.getAge() > MISSLE_MAX_AGE_SAUCER) {
            	saucerMissile.setActive(false);
            }
        }

        // asteroids
        
        Enumeration e = null;
        Vector asteroids = model.getAsteroids();
        
        for ( e = asteroids.elements() ; e.hasMoreElements() ; ) {
            Asteroid asteroid = (Asteroid) e.nextElement();
            asteroid.update();  
            asteroid.rotate();
            correctPosition(asteroid);
        }

        // spaceship
        
        SpaceShip spaceShip = model.getSpaceShip();

        if ( FloatingObject.isActive( spaceShip) ) {
            spaceShip.update();
            correctPosition(spaceShip);
            if ( spaceShip.getAge() == SpaceShip.MORTAL_AGE ) {
            	notifyListeners(GameListener.EVENT_SHIP_MORTAL);
            }
        } else {
            model.incCreateShipTimer();
            if (model.getCreateShipTimer() > CREATE_SHIP_WAIT && model.getLives() > 0) {
                initSpaceShip();
            }
        }
        
        // explosions
        
        Vector explosions = model.getExplosions();

        for (e = explosions.elements() ; e.hasMoreElements() ; ) {
            ExplodingPolygon explosion = (ExplodingPolygon) e.nextElement();
            explosion.update();  
            if (explosion.getAge() > ExplodingPolygon.MAX_AGE) {
            	explosion.setActive(false);
            }
            correctPosition(explosion);
        }
        
        FlyingSaucer saucer = model.getSaucer();

        // flying saucer
        
        if ( FloatingObject.isActive(saucer) ) {
            // update saucer position
            saucer.update();

            if ( saucer.getPosition().getX() / gameScale > gameMaxX ) {
                saucer.setActive(false);
            } else if ( saucer.getPosition().getX() / gameScale < gameMinX ) {
                saucer.setActive(false);
            }

            correctPosition(saucer);
        }
    }
    
    /**
     * <p>
     * Check for collisions between 
     * astroids / missiles / space ship and the flying saucer
     * </p>
     * 
     * <p>
     * If a collision is detected then call the appropriate 
     * methods to kiuck off explosions etc.
     * </p>
     * 
     * <p>
	 * Fires a {@code GameListener.EVENT_MISSILE_BLOCKED} event 
	 * if a flying saucer missile gets blocked by an asteroid
     * </p>
     */
    private void collisionCheck() {

        Vector asteroids      = model.getAsteroids();
        SpaceShip spaceShip   = model.getSpaceShip();
    	Missile missile       = model.getMissile();
    	Missile saucerMissile = model.getSaucerMissile();
        FlyingSaucer saucer   = model.getSaucer();
        
        // spaceship / asteroid intersection
        
        Enumeration e = null;

        if ( FloatingObject.isActive( spaceShip ) ) {
            for ( e = asteroids.elements() ; e.hasMoreElements() ; ) {
                Asteroid asteroid = (Asteroid) e.nextElement();
                if ( FloatingObject.isActive( asteroid ) && spaceShip.isColliding( asteroid ) ) {
                    shipDeath();
                    break;
                }
            }
        }

        // spaceship / missile intersection

        if ( FloatingObject.isActive( spaceShip ) ) {
            if ( FloatingObject.isActive( saucerMissile ) ) {
                if (spaceShip.isMortal() && spaceShip.isColliding(saucerMissile)) {
                    shipDeath();
                }
            }
        }
   
        // missile / asteroid intersection
        
        for ( e = asteroids.elements(); e.hasMoreElements() ; ) {
            Asteroid asteroid = (Asteroid) e.nextElement();
            if ( FloatingObject.isActive(missile) && FloatingObject.isActive(asteroid) && asteroid.isColliding(missile)) {
                model.incScore( ASTEROID_HIT_POINTS );
                asteroidDeath(asteroid);
                missile.setActive(false);
            } 
            
            // saucer can't shoot through asteroids
            
            if ( FloatingObject.isActive(saucerMissile) && FloatingObject.isActive(asteroid) && asteroid.isColliding(saucerMissile)) {
                saucerMissile.setActive(false);
                notifyListeners(GameListener.EVENT_MISSILE_BLOCKED);
            }
        }
        
        // flying saucer / missile intersection
        
        if ( FloatingObject.isActive(saucer) ) {
            if ( FloatingObject.isActive(missile) ) {
                if ( saucer.isColliding(missile) ) {
                	saucerDeath();
                    missile.setActive(false);
                }
            }
        }
        
        // spaceship / flying saucerintersection

        if ( FloatingObject.isActive(spaceShip) ) {
            if ( FloatingObject.isActive(saucer) ) {
                if (spaceShip.isMortal() && spaceShip.isColliding(saucer)) {
                    shipDeath();
                }
            }
        }       
    }
    
    /**
     * <p>
     * Check if the player has destroyed all asteroids and 
     * reached the next level
     * </p>
     * 
     * <p>
	 * Fires a {@code GameListener.EVENT_NEW_LEVEL} event 
	 * if the next level has been reached
     * </p>
     */
    private void nextLevelCheck() {
    	
    	// check to see if all asteroids are inactive (shot)
    	
    	boolean allInactive = true;
    	for ( Enumeration en=model.getAsteroids().elements() ; en.hasMoreElements() ; ) {
    		Asteroid asteroid = (Asteroid) en.nextElement();
    		if ( FloatingObject.isActive(asteroid) ) {
    			allInactive = false;
    		}
    	}
    	
    	// initialise next level if required
    	
        if ( allInactive && model.getLives()>0) {
        	model.incCurrentLevel();
            initAsteroids( model.getCurrentLevel() );
            initSpaceShip();
            
            notifyListeners( GameListener.EVENT_NEW_LEVEL );
        }
    }
    
    /**
     * <p>
     * Handle the destruction of the flying saucer. Called when
     * the player has shot a missile at the flying saucer. Allocates
     * points, inactivates the saucer etc
     * </p>
     * 
     * <p>
	 * Fires a {@code GameListener.EVENT_SAUCER_DESTROYED} event 
     * </p>
     */
    private void saucerDeath() {
        FlyingSaucer saucer = model.getSaucer(); 
    	
        // create the explosion
        
        ExplodingPolygon explosion = model.nextFreeExplosion();
        explosion.set(saucer, saucer.getPolygon());

        saucer.setActive(false); // deactivate the saucer
        saucer.reset();

        model.incScore( SAUCER_HIT_POINTS );	// score points
        notifyListeners( GameListener.EVENT_SAUCER_DESTROYED ); // notify observers of event
    }

    /**
     * Handle the destruction of the player's space ship. 
     * Loose a life and display explosion
     * 
     * <p>
	 * Fires a {@code GameListener.EVENT_SHIP_DESTROYED} event 
     * </p>
     */
    private void shipDeath() {
        SpaceShip spaceShip   = model.getSpaceShip();
    	
        if (spaceShip!=null) {
            
            // create the explosion

            ExplodingPolygon explosion = model.nextFreeExplosion();
            explosion.set(spaceShip, spaceShip.getPolygon());
            
            model.setLives( model.getLives() -1 );	// loose a life
            model.setCreateShipTimer(0);
   
            spaceShip.setActive(false); // deactivate the ship
                        
            notifyListeners( GameListener.EVENT_SHIP_DESTROYED ); // notify observers of event
        }
    }
    
    /**
     * Handle the destruction of an asteroid. Create baby
     * asteroids if the asteroid is large enough, create 
     * explosion and allocate points.
     * 
     * <p>
	 * Fires a {@code GameListener.EVENT_ASTEROID_DESTROYED} event 
     * </p>
     * 
     * @param asteroid	Asteroid to die
     */
    private void asteroidDeath(Asteroid asteroid) {

        asteroid.setActive(false); // deactivate the asteroid
        int size = asteroid.getsize();
        
        // create baby asteroids, if required
        
        if ( size >1 ) {

        	Vector2D positiveVelocity = VMath2D.sum(
        		asteroid.getVelocity().scaleBy( 3 + random.nextDouble() * 5 ).add( 1 - random.nextDouble() * 2 , 1 - random.nextDouble() * 3), 
        		new Vector2D(5, 5)
        	);
        	Vector2D negativeVelocity = new Vector2D( -positiveVelocity.getX(), -positiveVelocity.getY() );
        	
        	Asteroid child1 = model.nextFreeAsteroid();
        	child1.set( asteroid.getPosition(), positiveVelocity, size - 1 );

        	Asteroid child2 = model.nextFreeAsteroid();
        	child2.set( asteroid.getPosition(), negativeVelocity, size - 1 );
        }
        
        // create the explosion
        
        ExplodingPolygon explosion = model.nextFreeExplosion();
        explosion.set(asteroid, asteroid.getPolygon());
               
        notifyListeners( GameListener.EVENT_ASTEROID_DESTROYED ); // notify observers of event
    }
    
    /**
     * If a game object was wandered offscreen then warp it around to 
     * the other side.
     * 
     * @param object
     */
    private void correctPosition(FloatingObject object) {
        Vector2D position = object.getPosition();

        if ( position.getX() / gameScale > gameMaxX ) {
            position.setX((double)gameMinX * (double)gameScale);
        } else if ( position.getX() / gameScale < gameMinX ) {
            position.setX((double)gameMaxX * (double)gameScale);
        }

        if ( position.getY() / gameScale > gameMaxY ) {
            position.setY((double)gameMinY * (double)gameScale);
        } else if ( position.getY() / gameScale < gameMinY ) {
            position.setY((double)gameMaxY * (double)gameScale);
        }
    }
    
    /**
     * <p>
     * Initliase the flying saucer at random intervals and 
     * fire missile from saucer at random intervals
     * </p>
     * 
     * <p>
	 * Fires a {@code GameListener.EVENT_SAUCER_MISSILE_FIRED} event if
	 * a missile is fired 
     * </p>
     */
    public void saucerBehaviour() {
    	
        FlyingSaucer saucer   = model.getSaucer();
    	
        if ( ! FloatingObject.isActive(saucer) && random.nextDouble() * SAUCER_ACTIVITY < 1 ) {	// todo 
            initSaucer();
        }
        
        if ( FloatingObject.isActive(saucer) ) {
        	Missile saucerMissile = model.getSaucerMissile();
            SpaceShip spaceShip   = model.getSpaceShip();
        	
            // fire a missile if the time is ripe
            if ( ! FloatingObject.isActive(saucerMissile) 
            		&& FloatingObject.isActive(saucer) 
            		&& FloatingObject.isActive(spaceShip)
            		&& spaceShip.isMortal() 
            		&& saucer.getAge() > MIN_KILL_AGE
            		&& random.nextInt( SAUCER_MISSILE_ACTIVITY ) < 1
            		
            ) {

            	saucerMissile.reset();
                saucerMissile.setPosition(saucer.getPosition());
            	Vector2D directionVector = VMath2D.difference(spaceShip.getPosition(), saucer.getPosition());
            	saucerMissile.setVelocity( directionVector.normalize().scaleBy( saucerMissile.getMaxSpeed() ) );
                saucerMissile.setActive(true);
                saucerMissile.setAge(0);
                
                notifyListeners( GameListener.EVENT_SAUCER_MISSILE_FIRED );
            }       
        } 
    }

    /**
     * <p>
     * Setup the model and prepare to play a new game
     * </p>
     * 
     * <p>
	 * Fire a {@code GameListener.EVENT_GAME_START} 
     * </p>
     */
    public void newGame() {
        model.setScore(0);
        model.setLives( NUM_LIVES_START );
        model.setCurrentLevel( 1 );
        model.getAsteroids().removeAllElements();
        model.getExplosions().removeAllElements();
        model.getSaucerMissile().setActive(false);
        model.getSaucer().setActive(false);
        model.getSpaceShip().setActive(true);

        initAsteroids( model.getCurrentLevel() );
        initSpaceShip();
        initStars();

        notifyListeners( GameListener.EVENT_GAME_START );
    }

    /**
     * <p>
     * Check if all asteroids have been destroyed and the
     * game is over. 
     * </p>
     * 
     * <p>
     * Fires a {@code GameListener.EVENT_GAME_END} 
     * event is it is over.
     * </p>
     */
    private void gameOverCheck() {
        if (model.getLives() <= 0) {
            model.incDeadTimer();
            if (model.getDeadTimer() > DEAD_GAMEOVER_WAIT) {
                model.setDeadTimer(-1);
                notifyListeners( GameListener.EVENT_GAME_END );
            }
        }
    }
    
    /**
     * Add a new listener to observe game events
     * 
     * @param listener
     */
    public void addListener(GameListener listener) {
    	if ( ! listeners.contains(listener) ) {
    		listeners.addElement(listener);
    	}
    }
    
    /**
     * Remove a game listener 
     * 
     * @param listener
     */
    public void removeListener(GameListener listener) {
    	if ( listeners.contains(listener) ) {
    		listeners.removeElement(listener);
    	}
    }
    
    /**
     * Notify game listeners of game events
     * 
     * @param listener
     */
    private void notifyListeners(int event) {
    	for ( Enumeration e = listeners.elements() ; e.hasMoreElements() ; ) {
    		GameListener listener = (GameListener) e.nextElement();
    		
    		listener.gameEvent(event, model);
    	}
    }

    /**
     * Get the ratio of screen size to game board size
     * 
     * @return
     * 
     * @todo Do away with this
     */
	public int getGameScale() {
		return gameScale;
	}

    /**
     * Set the ratio of screen size to game board size
     * 
     * @todo Do away with this
     * 
	 * @param gameScale
	 */
	public void setGameScale(int gameScale) {
		this.gameScale = gameScale;
	}
}
