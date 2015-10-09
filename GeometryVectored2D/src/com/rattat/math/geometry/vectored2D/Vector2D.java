/*
 * Vector2D.java
 */

package com.rattat.math.geometry.vectored2D;

/**
 * Class to represent 2D vectors and provide functionality for performing 
 * vector operations on the vector.
 *
 * @author william
 */
public class Vector2D {

    /**
     * The X Component of the vector
     */
    private double x = 0;
    
    /**
     * The Y Component of the vector
     */
    private double y = 0;

    /**
     * Creates a new instance of Vector2D with x=0 and y=0
     */
    public Vector2D() {
    }
    
    /**
     * Creates a new instance of Vector2D with x and y components the same
     * as those of {@code v}
     * 
     * @param v Vector to base the new inatance on
     */
    public Vector2D(final Vector2D v) {
        set(v);
    }

    /**
     * Creates a new instance of Vector2D with x and y components defined
     * by the parameters {@code x} and {@code y}
     * 
     * @param x X component
     * @param y Y component
     */
    public Vector2D(double x, double y) {
        setXY(x, y);
    }
    
    /**
     * Get the X component
     *
     * @return X component
     */
    public double getX() {
        return x;
    }

    /**
     * Get the Y component
     *
     * @return Y component
     */
    public double getY() {
        return y;
    }

    /**
     * Set the Y component using a double value
     *
     * @param x X component
     */
    public Vector2D setX(double x) {
        this.x = x;
        return this;
    }

    /**
     * Set the Y component using a double value
     *
     * @param y Y component
     */
    public Vector2D setY(double y) {
        this.y = y;
        return this;
    }

    /**
     * Set the x and y components
     *
     * @param x X component
     * @param y Y component
     */
    public Vector2D setXY(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /**
     * Set the x and y components to be the same as those of {@code v}
     *
     * @param v Vector to base the x and y components on
     */
    public Vector2D set(final Vector2D v) {
        x = v.getX();
        y = v.getY();
        return this;
    }

    /**
     * Scale the vector by {@code s}
     *
     * @param s Value to scale by
     */
    public Vector2D scaleBy(double s) {
        this.x *= s;
        this.y *= s;
        return this;
    }

    /**
     * Set the x and y components to zero
     */
    public Vector2D zero() {
        x=0;
        y=0;
        return this;
    }

    /**
     * Add a vector to this one
     *
     * @param v Vector to add 
     */
    public Vector2D add(final Vector2D v) {
        this.x += v.getX();
        this.y += v.getY();
        return this;
    }
    
    /**
     * Add x and y components to this vector
     *
     * @param x Value to add to X component
     * @param y Value to add to Y component
     */
    public Vector2D add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    /**
     * Subtract a vector from this one
     *
     * @param v Vector to subtract 
     */
    public Vector2D subtract(final Vector2D v) {
        this.x -= v.getX();
        this.y -= v.getY();
        return this;
    }
    
    /**
     * Subtract x and y components from this vector
     *
     * @param x Value to subtract from X component
     * @param y Value to subtract from Y component
     */
    public Vector2D subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }
    
    /**
     * Truncate the vector to {@code length}
     */
    public Vector2D truncate(double length) {
        set( VMath2D.truncate(this, length) );
        return this;
    }
    
    /**
     * <p>Normalise the vector</p>
     *
     * <p>i.e. Keep direction the same, but with length of 1<p> 
     */
    public Vector2D normalize() {
        set( VMath2D.normalize(this) );
        return this;
    }

    /**
     * Set the vector to the sum of two vectors
     *
     * @param u First vector
     * @param v Second vector 
     */
    public Vector2D setToSum(final Vector2D u, final Vector2D v) {
        set( VMath2D.sum(u, v) );
        return this;
    }
    
    /**
     * Set the vector to the difference of two vectors
     *
     * @param u First vector
     * @param v Second vector - subtracted from first
     */
    public Vector2D setToDifference(final Vector2D u, final Vector2D v) {
        VMath2D.toDifference(u, v, this);
        return this;
    }
    
    /**
     * Set the vector to the dot product of another vector and a scaler
     *
     * @param v Vector
     * @param p Scaler to multiply vector by
     */
    public Vector2D setToProduct(final Vector2D v, double p) {
        VMath2D.toProduct(v, p, this);
        return this;
    }
    
    /**
     * Set the vector to the dot product of another vector and 1 / a scaler
     *
     * @param v Vector
     * @param q Scaler to divide vector by
     */
    public Vector2D setToQuotient(final Vector2D v, double q) {
        VMath2D.toQuotient(v, q, this);
        return this;
    }

    /**
     * Set the vector to the truncation of another vector to length {@code length}
     *
     * @param v Vector
     * @param length Length 
     */
    public Vector2D setToTruncate(final Vector2D v, double length) {
        VMath2D.toTruncate(v, length, this);
        return this;
    }

    /**
     * Set the vector to the normalization of another vector
     *
     * @param v Vector
     */
    public Vector2D setToNormalize(final Vector2D v) {
        VMath2D.toNormalize(v, this);
        return this;
    }

    /**
     * <p>Calculate the length (magnitude) of the vector</p>
     * 
     * <p>The same as {@link Vector2D#magnitude()}</p>
     * 
     * @return Length of the vector
     * @see Vector2D#magnitude()
     */
    public double length() {
        return magnitude();
    }

    /**
     * Calculate the magnitude of the vector
     * 
     * <p>The same as {@link Vector2D#length()}</p>
     * 
     * @return Magnitude of the vector
     * @see Vector2D#length()
     */
    public double magnitude() {
        return Math.sqrt(x*x + y*y);
    }
    
    /**
     * Get the magnitude^2 of the vector. This may be useful for computations
     * and is signifigantly faster than {@link Vector2D#magnitude()} since it
     * does not need a square root calculation.
     * 
     * @return Square of the magintude of the vector
     * @see Vector2D#magnitude()
     */
    public double magnitudeSquared() {
        return x*x + y*y;
    }

    /**
     * Calculate the distance to another vector
     *
     * @param v Vector to check distance to
     *
     * @return Distance to vector {@code v}
     */
    public double distance(final Vector2D v) {
        Vector2D u = VMath2D.difference(this, v);
        return u.magnitude();
    }
    
    /**
     * Check if the vector is the zero vector
     *
     * @return True if zero, false otherwise
     */
    public boolean isZero() {
        return this.equals(VMath2D.getZero());
    }
 
    /**
     * Check if the vector is the empty vector
     *
     * @return True if empty, false otherwise
     */
    public boolean isEmpty() {
        return this.equals(VMath2D.getEmpty());
    }
    
    /**
     * Check if the vector is equal to another
     *
     * @return True if equals to {@code v}, false otherwise
     */
    public boolean equals(final Object o) {
    	if ( o instanceof Vector2D ) {
    		Vector2D v = (Vector2D) o;
            //return v.getX() == this.x && v.getY() == this.y && v.getZ() == this.z;
            return (new Double(v.getX())).equals(new Double(this.x))
                   && (new Double(v.getY())).equals(new Double(this.y));
    	}
    	
    	return false;
    }

    /**
     * Get a string representation of the vector
     *
     * @return String containing X and Y components. 
     */
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }  
}
