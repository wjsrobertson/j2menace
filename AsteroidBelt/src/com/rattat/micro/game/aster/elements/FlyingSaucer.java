/*
 * FlyingSaucer.java
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
 * A class representing a flying saucer
 *
 * @author william@rattat.com
 */
public class FlyingSaucer extends FloatingObject {

	/**
	 * The bounding polygon
	 */
    private Polygon2D polygon = new Polygon2D();

    /**
     * Age of the flying sauer - doesn't fire missiles
     * if it's just appeared
     */
    private int age=0;
    
    /**
     * Saucer appears on the left and moves right
     */
    public static final int LEFT = 0;
    
    /**
     * Saucer appears on the right and moves left
     */
    public static final int RIGHT = 1;
    
    /**
     * The type of the asteorid {@code LEFT} or {@code RIGHT} 
     */
    private int type = 0;
    
    /**
     * Driving force acting on the asteroid
     */
    private Vector2D force = new Vector2D();
    
    /**
     * Random number generator - used for random forces
     */
    private static Random random = new Random( System.currentTimeMillis() );
    
    /**
     * Used in movement forces
     */
    private static final int FORCE_X = 80;
    
    /**
     * Used in movement forces
     */
    private static final int FORCE_Y = 20;
    
    /**
     * Used in movement forces
     */
    private static final int FORCE_CHANGE_MAX_WAIT = 20;

    /** 
     * Creates a new instance of FlyingSaucer 
     */
    public FlyingSaucer() {
    }

    /**
     * Creates a new instance of FlyingSaucer  - 
     * 
     * @param type	{@code LEFT} or {@code RIGHT} 
     * @param scale 
     * 
     * @todo Move scale into view 
     */
    public FlyingSaucer(int type, double scale) {
        this.type = type;
        
        updateForces();

        setMaxSpeed( 40 );
        polygon.addVertex(new Vector2D(0, -2*scale));   // tip
        polygon.addVertex(new Vector2D(-1*scale, -1*scale));  
        polygon.addVertex(new Vector2D(-2*scale, -1*scale));   // base right
        polygon.addVertex(new Vector2D(-3*scale, 0));   // base right
        polygon.addVertex(new Vector2D(-2*scale, scale));   // base right
        polygon.addVertex(new Vector2D(0, scale));   // base right
        polygon.addVertex(new Vector2D(2*scale, scale));   // base right
        polygon.addVertex(new Vector2D(3*scale, 0));   // base right
        polygon.addVertex(new Vector2D(2*scale, -1*scale));   // base right
        polygon.addVertex(new Vector2D(scale, -1*scale));  
    }

    /**
     * Update the movement forces to reflect the FlyingSaucer type
     */
    public void updateForces() {
        switch(type) {
	        case RIGHT:
	            force.setXY(-FORCE_X*random.nextDouble() - FORCE_X / 2, FORCE_Y*random.nextDouble()-1);
	            break;
	
	        default:
	        case LEFT:
	            force.setXY(FORCE_X*random.nextDouble() + FORCE_X / 2, FORCE_Y*random.nextDouble()-1);
	            break;
        }
        
        constantForce(force);
    }
    
    /**
     * <p>
     * Get the bounding Polygon of the flying saucer. 
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
     * Check if a missile is colliding with the flying saucer
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
     * Get the age of the asteroid
     * 
     * @return
     */
    public int getAge() {
        return age;
    }

    /**
     * Get the type of the asteroid
     * 
     * @return
     */
    public int getType() {
        return type;
    }
    
    /**
     * Set the type of the asteroid
     * 
     * @param type
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * <p>
     * Called by the superclass when the FlyingSaucer is updated
     * </p>
     * 
     * <p>
     * Updates the age and forces
     * </p>
     */
    public void onUpdate() {
        age++;
        
        if ( random.nextInt(FORCE_CHANGE_MAX_WAIT) > (FORCE_CHANGE_MAX_WAIT-1) ) {
        	int direction = (1 -random.nextInt(2));
        	
            force.setY( direction * random.nextInt(FORCE_Y) );
            
            constantForce(force);
        }
    }
}
