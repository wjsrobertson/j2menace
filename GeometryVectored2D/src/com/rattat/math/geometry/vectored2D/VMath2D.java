/*
 * Vector2D.java
 */

package com.rattat.math.geometry.vectored2D;

/**
 * <p>Class to do vector maths in 2D</p>
 *
 * <p>
 * 
 * </p>
 *
 * @author william@rattat.com
 */
public final class VMath2D {
   
    private static final Vector2D SIDE  = new Vector2D(1, 0);
    
    private static final Vector2D UP    = new Vector2D(0, -1);
    
    private static final Vector2D ZERO  = new Vector2D(0, 0);
    
    private static final Vector2D EMPTY = new Vector2D(Double.NaN, Double.NaN);
    
    public static final double EPSILON   = 0.001;
    
    /**
     * Private constructor to stop class from being instantiated
     */
    private VMath2D() {
    }

    /**
     * Sum two vectors together and return the result as a new Vector2D instance
     * 
     * @param u First vector to add
     * @param v Second vector to add
     * @return Sum of the two vectors
     */
    public final static Vector2D sum(final Vector2D u, final Vector2D v) {
        Vector2D response = new Vector2D();
        toSum(u, v, response);
        return response;
    }

    /**
     * Sum two vectors together and update <code>out</code> to contain the
     * result. 
     *
     * @param u     First vector to add
     * @param v     Second vector to add
     * @param out   Vector to update with sum of the two vectors <code>u</code> 
     *              and <code>v</code>
     */
    public final static void toSum(final Vector2D u, final Vector2D v, Vector2D out) {
        out.setXY( u.getX()+v.getX(), u.getY()+v.getY() );
    }

    /**
     * Calculate the difference of two vectors and return the result
     *
     * @param u First vector
     * @param v Vector to subtract from first vector
     *
     * @return Sum of the two vectors <code>u</code> and <code>v</code>
     */
    public final static Vector2D difference(final Vector2D u, final Vector2D v) {
        Vector2D response = new Vector2D();
        toDifference(u, v, response);
        return response;
    }

    /**
     * Calculate the difference of two vectors and update <code>out</code> to contain the
     * result. 
     *
     * @param u     First vector
     * @param v     Vector to subtract from <code>u</code>
     * @param out   Vector to update with the difference of <code>u</code> 
     *              and <code>v</code>
     */
    public final static void toDifference(final Vector2D u, final Vector2D v, Vector2D out) {
        out.setXY( u.getX()-v.getX(), u.getY()-v.getY() );
    }

    /**
     * Calculate the product of two vectors and return the result
     *
     * @param v Vector to be multiplied
     * @param d Number to multiply <code>u</code> by
     *
     * @return Product of the two vectors
     */
    public final static Vector2D product(final Vector2D v, double d) {
        Vector2D response = new Vector2D();
        toProduct(v, d, response);
        return response;
    }

    /**
     * Calculate the product of two vectors and update <code>out</code> to contain the
     * result. 
     *
     * @param v Vector to be multiplied
     * @param d Number to multiply <code>u</code> by
     * @param out Vector to hold the product of <code>u</code> and <code>v</code>
     */
    public final static void toProduct(final Vector2D v, double d, Vector2D out) {
        out.setXY( d * v.getX(), d * v.getY() );
    }

    /**
     * Calculate the quotient of two vectors and return the result
     *
     * @param v Vector to be devided
     * @param d Number to divide <code>v</code> by
     *
     * @return Quotient of <code>v</code> and <code>d</code>
     */
    public final static Vector2D quotient(final Vector2D v, double d) {
        Vector2D response = new Vector2D();
        toQuotient(v, d, response);
        return response;
    }

    /**
     * Calculate the quotient of two vectors and update <code>out</code> to contain the
     * result. 
     *
     * @param v Vector to be devided
     * @param d Number to divide <code>u</code> by
     * @param out Vector to hold the quotient of <code>v</code> and <code>d</code>
     */
    public final static void toQuotient(final Vector2D v, double d, Vector2D out) {
        out.setXY( v.getX() / d, v.getY() / d );
    }

    /**
     * Calculate the maginitude of a vector
     *
     * @param v Vector to calculate the magnitude of
     */
    public static final double magnitude(final Vector2D v) {
        return Math.sqrt(v.getX()*v.getX() + v.getY()*v.getY());
    }

    /**
     * Calculate the maginitude of a vector
     *
     * @param u First vector
     * @param v Second vector
     */
    public final static double dotProduct(final Vector2D u, final Vector2D v) {
        return u.getX() * v.getX() + u.getY() * v.getY();
    }

    /**
     * Calculate a truncated vector
     *
     * @param v vector
     * @param maxLength Length to truncate to
     *
     * @return  <code>v</code> truncated to have length <code>maxLength</code> 
     *          or less.
     */
    public final static Vector2D truncate(final Vector2D v, double maxLength) {
        Vector2D response = new Vector2D();
        toTruncate(v, maxLength, response);
        return response;
    }

    /**
     * Calculate a truncated vector and assign the result to <code>out</code>
     *
     * @param v vector
     * @param maxLength Length to truncate to
     * @param out Vector to set with result
     */
    public final static void toTruncate(final Vector2D v, double maxLength, Vector2D out) {
        double length = magnitude(v);
        
        if (length > maxLength) {
            toProduct(v, maxLength/length, out);
        } else {
            out.set(v);
        }
    }

    /**
     * Calculate a normalized vector and assign the result to <code>out</code>
     *
     * @param v Vector to normalize
     *
     * @return Calculate truncated version of <code>v</code>
     */
    public final static Vector2D normalize(final Vector2D v) {
        Vector2D response = new Vector2D();
        toNormalize(v, response);
        return response;
    }

    /**
     * Calculate a normalized vector and assign the result to <code>out</code>
     *
     * @param v Vector to calculate normal of
     * @param out Vector to set with result
     */
    public final static void toNormalize(final Vector2D v, Vector2D out) {
        double magnitude = magnitude(v);
        
        if (magnitude != 0) {
            toProduct(v, 1/magnitude, out);
        } else {
            out.set(VMath2D.ZERO);
        }
    }

    /**
     * Create a new vector which is the interpolated value between two vectors. 
     * If {@code ratio} is 0 then the response is equal to {@code u} and if ratio
     * is 1 then the repsonse is equal to {@code v}. Values between 0 and 1 return
     * a value between {@code u} and {@code v}.
     *
     * @param ratio Ratio
     * @param u 
     * @param v 
     *
     * @return Interplated value
     */
    public final static Vector2D interpolate(double ratio, Vector2D u, Vector2D v) {
        Vector2D response = new Vector2D();
        toInterpolate(ratio, u, v, response);
        return response;
    }
    
    /**
     * Set a vector to be the interpolation of a ratio along two vectors 
     *
     * @param ratio Ratio
     * @param u 
     * @param v 
     * @param out    Interplated value
     *
     * @see interpolate(double ratio, Vector2D u, Vector2D v)
     */
    public final static void toInterpolate(double ratio, Vector2D u, Vector2D v, Vector2D out) {
        double x = u.getX() + ratio * (v.getX() - u.getX());
        double y = u.getY() + ratio * (v.getY() - u.getY());
        
        out.setXY(x,y);
    }
    
    /**
     * Get a copy of the SIDE vector
     */
    public static final Vector2D getSide() {
        return new Vector2D(SIDE);
    }
    
    /**
     * Get a copy of the UP vector
     */
    public static final Vector2D getUp() {
        return new Vector2D(UP);
    }
    
    /**
     * Get a copy of the ZERO vector
     */
    public static final Vector2D getZero() {
        return new Vector2D(ZERO);
    }
    
    /**
     * Get a copy of the EMPTY vector
     */
    public static final Vector2D getEmpty() {
        return new Vector2D(EMPTY);
    }
    

}
