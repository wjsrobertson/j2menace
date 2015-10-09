/*
 * ExplodingPolygon.java
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

import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;

import com.rattat.math.geometry.vectored2D.Line2D;
import com.rattat.math.geometry.vectored2D.Polygon2D;
import com.rattat.math.geometry.vectored2D.VMath2D;
import com.rattat.math.geometry.vectored2D.Vector2D;

/**
 * An object representing an explosion.
 *
 * @author william@rattat.com
 */
public class ExplodingPolygon extends FloatingObject {

	/**
	 * The duration of the explosion
	 */
    public static final int MAX_AGE = 14;

    /**
     * The ago of the explosion
     */
    private int age = 0;
    
    /**
     * Set of Line2D objects that compose the explosion
     */
    private Vector lines = new Vector();
    
    /**
     * Random number generator
     */
    private Random random = new Random( (long) System.currentTimeMillis() );

    /** 
     * Creates a new instance of ExplodingPolygon 
     */
    public ExplodingPolygon() {
    }
    
    /**
     * Creates a new instance of ExplodingPolygon copying the
     * position and shape from a FloatingObject and Polygon2D
     * 
     * @param vehicle
     * @param polygon
     * 
     * @see set(FloatingObject vehicle, Polygon2D polygon)
     */
    public ExplodingPolygon(FloatingObject vehicle, Polygon2D polygon) {
    	set(vehicle, polygon);
    }

    /**
     * Set the initial Polygon2D shape and position from
     * FloatingObject and Polygon2D instances.
     * 
     * @param vehicle
     * @param polygon
     */
    public void set(FloatingObject vehicle, Polygon2D polygon) {
    	
    	lines.removeAllElements();
    	
        // copy the required vehicle properties

        setVelocity( VMath2D.getZero() );
        setPosition( vehicle.getPosition() );
        setMass(vehicle.getMass());
        setAcceleration( VMath2D.getZero() );
        setForces( VMath2D.getZero() );

        // copy the polygon properties

        Vector2D polyMidPoint = polygon.midPoint();
        for ( Enumeration e = polygon.edges() ; e.hasMoreElements() ; ) {
        	Line2D line = (Line2D) e.nextElement();
        	Vector2D velocity = VMath2D.difference(line.midPoint(), polyMidPoint).scaleBy(2);
        	velocity.normalize();
        	velocity.scaleBy(15);

            lines.addElement( 
              new RotatingLine( 
            	line, (random.nextDouble() / 2), velocity  
              ) 
            );
        }
    }

    /**
     * Get the age of the polygon
     * 
     * @return
     */
    public int getAge() {
        return age;
    }
    
    /**
     * Reset the ExplodingPolygon so that it can 
     * safely be re-used
     */
    public void reset() {
    	age = 0;
    }

    /**
     * <p>
     * Called by the FloatingObject superclass when 
     * the FloatingObject is updated.
     * </p>
     * 
     * <p>
     * Increments the age and rotates the lines
     * </p>
     */
    public void onUpdate() {
        age++;

        if (age <= MAX_AGE) {
            Enumeration e = lines.elements();
            while (e.hasMoreElements()) {
                RotatingLine line = (RotatingLine) e.nextElement();
                line.update();
            }
        }
    }

    /**
     * A class representing a rotating line. That is, a Line2D 
     * with angular velocity and the capability to be updated -
     * rotated.
     * 
     * @author poobar
     */
    private class RotatingLine extends Line2D {
    	
        private double angularVelocity = 0;
        
        private Vector2D velocity = null;

        public RotatingLine(Line2D l, double angularVelocity, Vector2D velocity) {
            setStart(l.getStart());
            setEnd(l.getEnd());
            this.angularVelocity = angularVelocity;
            this.velocity = velocity;
        }

        public void update() {
        	// rotate line
            rotate(midPoint(), angularVelocity);

            // move line in direction of original vehicle
            setStart( VMath2D.sum( getStart(), velocity ) );
            setEnd( VMath2D.sum( getEnd(), velocity ) );

        }
    }

    /**
     * Get the lines that the ExplodingPolygon is
     * composed of
     * 
     * @return
     */
	public Vector getLines() {
		return lines;
	}
}
