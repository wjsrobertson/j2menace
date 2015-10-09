/*
 * FLoatingObject.java
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

import com.rattat.math.geometry.vectored2D.VMath2D;
import com.rattat.math.geometry.vectored2D.Vector2D;

/**
 * <p>
 * A base class providing the functionality for
 * and object which can float in space and have forces applied
 * to it. Acceleration, velocity and position are updated when
 * the {@code update()} method is called.
 * </p>
 * 
 * <p>
 * Subclasses may override {@code onUpdate()} to be notified
 * when an update has taken place.
 * </p>
 *
 * @author william@rattat.com
 */
public class FloatingObject {
    
	private boolean active = false;
	
    /**
     * Position of the FloatingObject in space
     */
    protected Vector2D position = new Vector2D();

    /**
     * Last position of the FloatingObject, or current position
     * if the FloatingObject has not moved
     */
    private Vector2D lastPosition  = null;
    
    /**
     * Velocity of the FloatingObject
     */
    private Vector2D velocity      = new Vector2D();
    
    /**
     * Acceleration of the FloatingObject
     */
    private Vector2D acceleration  = new Vector2D();
    
    /**
     * Sum of all forces on the FloatingObject
     */
    private Vector2D forces        = new Vector2D();

    /**
     * Maximum resultant force of the FloatingObject. The 
     * total force will be truncated to this lngth after 
     * updates.
     */
    private double maxForce = 10;
   
    /**
     * Maximum speed of the FloatingObject. The velocity will be 
     * truncated to this length after updates.
     */
    private double maxSpeed = 50;
    
    /**
     * Mass of the FloatingObject
     */
    private double mass = 10;

    /**
     * <p>
     * Update the position of the FloatingObject based on forces
     * </p>
     * 
     * <p>
     * Apply resultant force, update 
     * acceleration, speed and position.
     * </p>
     * 
     * <p>
     * Calls onUpdate() after forces, acceleration, velocity and position have
     * been updated. This method can be used by subclasses to perform actions 
     * after the update.
     * </p>
     *
     * @see onUpdate()
     */
    public void update() {
    	
    	updateLastPosition();
    	
        forces.truncate(maxForce);
        acceleration.setToQuotient(forces, mass);
        velocity.add(acceleration).truncate(maxSpeed);
        position.add(velocity);

        onUpdate();
    }
    
    /**
     * Update the last position of the FloatingObject to be
     * the current position.
     */
    private void updateLastPosition() {
        if (lastPosition == null) {
            lastPosition = new Vector2D();
        }
    	
        lastPosition.setXY( getPosition().getX(), getPosition().getY()  );
    }
    
    /**
     * <p>
     * Apply an impulse force to the object
     * </p>
     * 
     * <p>
     * That is, not a constant force, but a one off "push" like
     * a person jumping off the ground
     * </p>
     * 
     * @param impulse
     * 
     * @see constantForce( Vector2D force )
     */
    public void impulseForce( Vector2D impulse ) {
        acceleration.setToQuotient(impulse, mass);
        velocity.add(acceleration);
    }
    
    /**
     * <p>
     * Apply a constant force to the object
     * </p>
     * 
     * <p>
     * That is, not an impulse force, but a constant force, like
     * the thruster on a rocket.
     * </p>
     * 
     * @param impulse
     * 
     * @see constantForce( Vector2D force )
     */
    public void constantForce( Vector2D force ) {
    	forces.add(force);
    }

    /**
     * Get the last position of the FloatingObject
     * 
     * <p>
     * This method returns a reference to the internal Vector2D
     * object. Users should treat the object as read only and
     * should not modify it. See package notes for more 
     * information. 
     * </p>
     * 
     * @return Last position of the FloatingObject
     */
    public Vector2D getLastPosition() {
        if (lastPosition == null) {
            lastPosition = new Vector2D(getPosition());
        }
        
        return lastPosition;
    }

    /**
     * Get the predicted position of the FloatingObject in the future
     *
     * @param time Time in the future
     * @return Position in the future
     */
    public Vector2D predictFuturePosition(double time) {
        return VMath2D.sum(position, VMath2D.product(velocity, time));
    }

    /**
     * Callback for when FloatingObject forces / velocity / position is updated
     */
    public void onUpdate() {
    	
    }

    /**
     * Set the position, force, velocity and acceleration to be zero.
     */
    public void reset() {
        position.setXY(0,0);
        forces.setXY(0,0);
        velocity.setXY(0,0);
        acceleration.setXY(0,0);
    }

    /**
     * <p>
     * Get the acceleration of the FloatingObject
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
	public Vector2D getAcceleration() {
		return acceleration;
	}

	/**
	 * Set the x and y components of the acceleration vector to 
	 * be the same as the parameter's
	 * 
	 * @param acceleration
	 */
	public void setAcceleration(Vector2D acceleration) {
		this.acceleration.set(acceleration);
	}

    /**
     * <p>
     * Get the forces acting on the FloatingObject
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
	public Vector2D getForces() {
		return forces;
	}

	/**
	 * Set the x and y components of the force vector to 
	 * be the same as the parameter's
	 * 
	 * @param acceleration
	 */
	public void setForces(Vector2D forces) {
		this.forces.set(forces);
	}

	/**
	 * Get the mass of the FloatingObject
	 * 
	 * @return
	 */
	public double getMass() {
		return mass;
	}

	/**
	 * Set the mass of the FloatingObject
	 * 
	 * @param mass
	 */
	public void setMass(double mass) {
		this.mass = mass;
	}

	/**
	 * Get the total maximum force that can 
	 * act on the FloatingObject
	 * 
	 * @return
	 */
	public double getMaxForce() {
		return maxForce;
	}

	/**
	 * Set the total maximum force that can 
	 * act on the FloatingObject
	 * 
	 * @param maxForce
	 */
	public void setMaxForce(double maxForce) {
		this.maxForce = maxForce;
	}

	/**
	 * Get the maximum speed that the floating object
	 * can travel at
	 * 
	 * @return
	 */
	public double getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * Set the maximum speed that the floating object
	 * can travel at
	 * 
	 * @param maxSpeed
	 */
	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

    /**
     * <p>
     * Get the position of the FloatingObject
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
     * Set the position of the FloatingObject
     * </p>
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y) {
		position.setX(x);
		position.setY(y);
	}

	/**
     * <p>
     * Set the x and y components of the position vector
     * to be the same as those of hte parameter
     * </p>
	 * 
	 * @param position
	 */
	public void setPosition(Vector2D position) {
		this.position.set(position);
	}

    /**
     * <p>
     * Get the velocity of the FloatingObject
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
	public Vector2D getVelocity() {
		return velocity;
	}

	/**
     * <p>
     * Set the x and y components of the velocity vector
     * to be the same as those of hte parameter
     * </p>
	 * 
	 * @param position
	 */
	public void setVelocity(Vector2D velocity) {
		this.velocity.set(velocity);
	}

	/**
	 * <p>
	 * Check if a FloatingObject instance is flagged as active. 
	 * </p>
	 * 
	 * <p>
	 * This method is null-safe and will return false if the 
	 * {@code object} parameter is null.
	 * </p>
	 * 
	 * @param object
	 * 
	 * @return
	 */
	public static boolean isActive(FloatingObject object) {
		return (object != null && object.isActive());
	}

	/**
	 * <p>
	 * Check if the FloatingObject is flagged as active. 
	 * </p>
	 * 
	 * <p>
	 * This flag can be used for object pooling, or for any 
	 * other business purpose.
	 * </p>
	 * 
	 * @return
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * <p>
	 * Set whether or not the FloatingObject is flagged as active. 
	 * </p>
	 * 
	 * <p>
	 * This flag can be used for object pooling, or for any 
	 * other business purpose.
	 * </p>
	 * 
	 * @param active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

}
