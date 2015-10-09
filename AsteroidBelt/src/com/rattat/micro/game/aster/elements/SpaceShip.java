/*
 * SpaceShip.java
 * 
 * Copyright 2007 William Robertson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.rattat.micro.game.aster.elements;

import com.rattat.math.geometry.vectored2D.Line2D;
import com.rattat.math.geometry.vectored2D.Polygon2D;
import com.rattat.math.geometry.vectored2D.Vector2D;

/**
 * A class representing an space ship
 *
 * @author william@rattat.com
 */
public class SpaceShip extends FloatingObject {

	/**
	 * The forward thrust force magnitude
	 */
    private double thrustMagnitude = 60;
    
	/**
	 * The polygon representing the boundary (edge) of the asteroid
	 */
    private Polygon2D polygon = new Polygon2D();
    
    /**
     * Initial rotation of the spaceship
     */
    public double rotation = Math.PI;

    /**
     * Thrust operating on the SpaceShip
     */
    private Vector2D thrust = new Vector2D();
    
    /**
     * The age of the SpaceShip
     */
    private int age;
    
    /**
     * Direction the SpaceShip is facing
     */
    private Vector2D direction = null;
    
    /**
     * Scale of the SpaceShip
     */
    private double scale = 0;
    
    /**
     * Age after which time the SpaceShip can be destroyed
     */
    public static final int MORTAL_AGE = 20;

    /** 
     * Creates a new instance of SpaceShip
     * 
     * @param scale
     */
    public SpaceShip(double scale) {
    	this.scale = scale;
        setMaxSpeed( 140 );
        reset();
    }

    /**
     * Get the age of the SpaceShip
     * 
     * @return
     */
    public int getAge() {
        return age;
    }

    /**
     * <p>
     * Rotate the SpaceShip by an angle
     * </p>
     *  
     * <p>
     * Angle is measured in radians
     * </p>
     * 
     * @param angle
     */
    public void rotate(double angle) {
        rotation -= angle;
        Vector2D midPoint = polygon.midPoint();
        polygon.rotate(midPoint.getX(), midPoint.getY(), angle);
    }

    /**
     * Preform forward thrust on the SpaceShip
     */
    public void thrust() {
        thrust.setXY( -1 * thrustMagnitude * Math.sin(rotation), 
                   thrustMagnitude * Math.cos(rotation) );
        impulseForce(thrust);
    }
    
    /**
     * Preform reverse thrust on the SpaceShip
     */    
    public void reverseThrust() {
        thrust.setXY( 0.3 * thrustMagnitude * Math.sin(rotation), 
                    -0.3 * thrustMagnitude * Math.cos(rotation) );
        impulseForce(thrust);
    }

    /**
     * <p>
     * Get the bounding Polygon of the SpaceShip 
     * </p>
     * 
     * <p>
     * This method returns a reference to the internal bounding Polygon2D
     * object. Users should treat the object as read only and
     * should not modify it. See package notes for more 
     * information. 
     * </p>
     * 
     * @return
     */
    public Polygon2D getPolygon() {
        return polygon;
    }

    /**
     * Check if the SpaceShip is colliding with an Asteroid
     * 
     * @param asteroid
     * 
     * @return	True if colliding, false otherwise
     */
    public boolean isColliding(Asteroid asteroid) {
        if ( isMortal() ) {
            Polygon2D shipPolygon = new Polygon2D(polygon);
            shipPolygon.translate(getPosition());

            Polygon2D asteroidPolygon = new Polygon2D(asteroid.getPolygon());
            asteroidPolygon.translate(asteroid.getPosition());

            return (shipPolygon.intersects(asteroidPolygon));
        } else {
            return false;
        }
    }

    /**
     * Check if the SpaceShip is colliding with a Missile
     * 
     * @param missile
     * 
     * @return	True if colliding, false otherwise
     */
    public boolean isColliding(Missile missile) {
        Polygon2D testPolygon = new Polygon2D(polygon);
        testPolygon.translate(getPosition());
        Line2D l = new Line2D(missile.getPosition(), missile.getLastPosition());
        return testPolygon.intersects(/*m.getPosition()*/ l);
    }
    
    /**
     * Check if the SpaceShip is colliding with a FlyingSaucer
     * 
     * @param missile
     * 
     * @return	True if colliding, false otherwise
     */
    public boolean isColliding(FlyingSaucer saucer) {
        if ( isMortal() ) {
            Polygon2D shipPolygon = new Polygon2D(polygon);
            shipPolygon.translate(getPosition());

            Polygon2D saucerPolygon = new Polygon2D(saucer.getPolygon());
            saucerPolygon.translate(saucer.getPosition());

            return (shipPolygon.intersects(saucerPolygon));
        } else {
            return false;
        }
    }

    /**
     * Reset the age, rotation and FloatingObject properties (
     * velocity, position etc.)
     */
    public void reset() {
    	super.reset();
    	
    	polygon.clearVertices();
        polygon.addVertex(new Vector2D(0, -2*scale));   // nose
        polygon.addVertex(new Vector2D(-scale, 2*scale));   // base left
        polygon.addVertex(new Vector2D(0, 1.5*scale));   // booster
        polygon.addVertex(new Vector2D(scale, 2*scale));   // base right
    	
        age = 0;
        rotation = Math.PI;
    }
    
    /**
     * Check if the SpaceShip is old enough to be
     * killed.
     * 
     * @return
     */
    public boolean isMortal() {
        return (age >= MORTAL_AGE);
    }

    /**
     * Called by the superclass when position / velocity
     * gets updated. Just increments the age of the SpaceShip.
     */
    public void onUpdate() {
        age++;
    }

    /**
     * Get the current rotation of the SpaceShip
     * 
     * @return
     */
	public double getRotation() {
		return rotation;
	}
	
    /**
     * <p>
     * Get the direction the SpaceShip is facing
     * </p>
     * 
     * <p>
     * This method returns a reference to the internal bounding Vector2D
     * object. Users should treat the object as read only and
     * should not modify it. See package notes for more 
     * information. 
     * </p>
     * 
     * @return
     */
	public Vector2D getDirection() {
		if ( direction == null ) {
			direction = new Vector2D();
		}
		
		direction.setXY(-1 * Math.sin(rotation), Math.cos(rotation));
		return direction.normalize();
	}
}
