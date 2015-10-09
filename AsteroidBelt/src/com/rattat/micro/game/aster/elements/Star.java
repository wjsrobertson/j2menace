/*
 * Star.java
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

import com.rattat.math.geometry.vectored2D.Vector2D;

/**
 * A class representing a star
 *
 * @author william@rattat.com
 */
public class Star {
    
	/**
	 * Radius of the star
	 */
    double radius;
    
    /**
     * Position of the star
     */
    Vector2D position = new Vector2D();

    /** Creates a new instance of Star */
    public Star() {
    }

    /**
     * Creates a new instance of Star
     * 
     * @param position
     * @param radius
     */
    public Star(Vector2D position, double radius) {
        setPosition(position);
        setRadius(radius);
    }

    /**
     * <p>
     * Get the bounding Polygon of the asteroid. 
     * </p>
     * 
     * <p>
     * This method returns a reference to the internal Vector2D
     * object. Users should treat the object as read only and
     * should not modify it. See package notes for more 
     * information. 
     * </p>
     * 
     * @return
     */
    public Vector2D getPosition() {
        return position;
    }

    /**
     * <p>
     * Set the position of the Star.
     * </p>
     * 
     * <p>
     * Properties of the {@code position} parameter
     * are copied to into the internal position vector.
     * </p>
     * 
     * @param position
     */
    public void setPosition(Vector2D position) {
        this.position.set(position);
    }

    /**
     * Get the radius of the Star
     * 
     * @return
     */
    public double getRadius() {
        return radius;
    }
    
    /**
     * Set the radius of the Star
     * 
     * @param radius
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }
}
