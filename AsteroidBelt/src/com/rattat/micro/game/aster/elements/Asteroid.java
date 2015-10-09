/*
 * Asteroid.java
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

import java.util.Random;

import com.rattat.math.geometry.vectored2D.Line2D;
import com.rattat.math.geometry.vectored2D.Polygon2D;
import com.rattat.math.geometry.vectored2D.Vector2D;

/**
 * A class representing an asteroid
 *
 * @author william@rattat.com
 */
public class Asteroid extends FloatingObject {
	
	/**
	 * Random number generator
	 */
	private static Random random = new Random( (long) System.currentTimeMillis() );

	/**
	 * The polygon representing the boundary (edge) of the asteroid
	 */
    private Polygon2D polygon = new Polygon2D();

    /**
     * The velocity at which the asteroid will rotate, in radians
     */
    public double angularVelocity = 0;

    /**
     * The size of the asteroid - 3 largest, 1 smallest
     */
    private int size = 3;

    /** Creates a new instance of Asteroid */
    public Asteroid() {
    }
    
    /**
     * <p>
     * Create an asteroid at a specific posision, with a specific
     * velocity and of a specific size. 
     * </p>
     * 
     * <p>
     * Note that the position and
     * velocity vectors have their values copied - i.e. The internal
     * position and velocity Vector2D objects will not refer to the same
     * Vector2D objects passed in as parameters.
     * </p>
     * 
     * @param position
     * @param velocity
     * @param size
     */
    public Asteroid(Vector2D position, Vector2D velocity, int size) {
    	set(position, velocity, size);
    }
    
    /**
     * <p>
     * Set the posision, velocity size of hte asteroid. 
     * </p>
     * 
     * <p>
     * Note that the position and
     * velocity vectors have their values copied - i.e. The internal
     * position and velocity Vector2D objects will not refer to the same
     * Vector2D objects passed in as parameters.
     * </p>
     * 
     * @param position
     * @param velocity
     * @param size
     */
    public void set(Vector2D position, Vector2D velocity, int size) {
    	polygon.clearVertices();
    	
        int width;

        this.size = size;
        setPosition(position);
        setVelocity(velocity);

        switch (size) {
            case 1:
                width = 60;
                break;
            case 2:
                width = 130;
                break;
            case 3:
            default:
                width = 180;
                break;
        }

        switch (random.nextInt(3)) {
            case 2:
                addVertex(0,width/2);
                addVertex(width/4,width/4);
                addVertex(width,0);
                addVertex(width/4,-1*width/4);
                addVertex(0,-1*width);
                addVertex(-1*width,0);
                break;

            case 1:
                addVertex(0,width);
                addVertex(0,width/2);
                addVertex(width,0);
                addVertex(0,-1*width);
                addVertex(0,-1*width/2);
                addVertex(-1*width, 0);
                addVertex(-1*width/4, width/4);
                break;
                
            default:
            case 0:
                addVertex(0,width/2);
                addVertex(width/2,width/2);
                addVertex(width/2,0);
                addVertex(0,-width/2);
                addVertex(-1*width,0);
                addVertex(-1*width,width/2);
                addVertex(-1*width/2,width);
                break;
        }

        angularVelocity = (0.6*random.nextDouble()) - 0.3;
    }

    /**
     * Add a vertex to the bounding Polygon
     * 
     * @param x
     * @param y
     */
    private void addVertex(int x, int y) {
        polygon.addVertex(new Vector2D(x, y));
    }

    /**
     * Rotate once according to the angular velocity of the
     * asteroid
     */
    public void rotate() {
        //return;

        Vector2D midPoint = polygon.midPoint();
        polygon.rotate(midPoint.getX(), midPoint.getY(), angularVelocity);
    }

    /**
     * <p>
     * Get the bounding Polygon of the asteroid. 
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
     * Get the size of the asteroid - 1 smallest, 3 largest
     * 
     * @return
     */
    public int getsize() {
        return size;
    }

    /**
     * Check if the asteroid is colliding with a Missile
     * 
     * @param missile
     * 
     * @return
     */
    public boolean isColliding(Missile missile) {
        Polygon2D testPolygon = new Polygon2D(polygon);
        testPolygon.translate(getPosition());
        Line2D l = new Line2D(missile.getPosition(), missile.getLastPosition());
        
        return testPolygon.intersects(/*m.getPosition()*/ l);
    }
}

