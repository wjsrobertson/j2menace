/**
 * BoundingRectangle2d.java
 *
 * Subject to the apache license v. 2.0
 *
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 * @author william@rattat.com
 */

package com.rattat.math.geometry.vectored2D;

/**
 * <p>Class to represent a bounding recangle in two 2D.</p>
 *
 * <p>
 * The rectangle will be at right angles to the x and y axes. It is useful
 * for fail-fast calculations for collision detection between vectored shapes.
 * If the bounding rectangles of two shapes do not intersect then the shapes 
 * themselves do not intersect.
 * </p>
 *
 * <p>
 * The position vector represeents the top left of the rectangle.
 * <p>
 *
 * @author william robertson <william@rattat.com>
 *
 * @todo - setters
 */
public class BoundingRectangle2D {

    /**
     * Position of the top-left of the rectangle
     */
    private Vector2D position = new Vector2D();

    /**
     * Width of the rectangle
     */
    private double width = 0;

    /**
     * Height of the rectangle
     */
    private double height = 0;

    /**
     * Creates a new instance of BoundingRectangle2D with position (0,0), width 0
     * and height 0.
     */
    public BoundingRectangle2D() {
    }

    /**
     * Creates a new instance of BoundingRectangle2D with position and
     * dimensions as specified in the parameters.
     * 
     * @param position  Position vector
     * @param width     Width of rectangle
     * @param height    Height of rectangle
     */
    public BoundingRectangle2D(final Vector2D position, double width, double height) {
        set(position, width, height);
    }
    
    /**
     * Set the position and dimensions of the rectangle
     *
     * @param position  Position vector
     * @param width     Width of rectangle
     * @param height    Height of rectangle
     */
    public void set(final Vector2D position, double width, double height) {
        this.position.set(position);
        this.width = width;
        this.height = height;
    }
    
    /**
     * Set the position and dimensions of the rectangle
     *
     * @param x         X ordinate of position
     * @param y         Y ordinate of position
     * @param width     Width of rectangle
     * @param height    Height of rectangle
     */
    public void set(double x, double y, double width, double height) {
        position.setXY(x,y);
        this.width = width;
        this.height = height;
    }

    /**
     * <p>Get the position vector</p>
     *
     * <p>
     * Please note that this method does not return a copy - it returns a 
     * refernce to the actual position vector. Therefore, altering the 
     * returned vector will alter the position of this rectangle.
     * </p>
     * 
     * @return Position vector
     */
    public Vector2D getPosition() {
        return position;
    }

    /**
     * Get the width of the rectangle
     *
     * @return width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Get the height of the rectangle
     *
     * @return height
     */
    public double getHeight() {
        return height;
    }

    /**
     * <p>
     * Check if a point (represented by a position vector) is contianed within
     * this rectangle
     * </p>
     *
     * <p>
     * This method does not consider a point on the edge of the rectangle to be
     * contained within it.
     * </p>
     * 
     * @param vector    Point to check
     *
     * @return true if vector is contained in the rectangle, false otherwise
     */
    public boolean contains(final Vector2D vector) {
        
        System.out.println( "position: " + position );
        System.out.println( "width: " + width );
        System.out.println( "height: " + height );
        
        System.out.println( ":1: " + (vector.getX() > position.getX()) );
        System.out.println( ":2: " + (vector.getY() > position.getY()) );
        System.out.println( ":3: " + (vector.getX() < (position.getX() + width)) );
        System.out.println( ":4: " + (vector.getY() < (position.getY() + height)) );
        
        return (vector.getX() > position.getX()
                && vector.getY() > position.getY()
                && vector.getX() < (position.getX() + width) 
                && vector.getY() < (position.getY() + height) );
    }

    /**
     * <p>Check if the rectangle intersects with another</p>
     *
     * <p>
     * If two rectangles meet only at an edge or vertex then it will not count
     * as an intersection. 
     * </p>
     *
     * <p>
     * i.e. the square from (1,1) to (2,2) does NOT intersect
     * with the square from (2,2) to (3,3) according to this method.
     * </p>
     *
     * @param rectangle Rectangle to check
     *
     * @return true if rectangles intersect, false otherwise
     */
    public boolean intersects(final BoundingRectangle2D rectangle) {      

         if ( position.getX() >= rectangle.getPosition().getX() + rectangle.getWidth()
             || rectangle.getPosition().getX() >= position.getX() + width
             || position.getY() >= rectangle.getPosition().getY() + rectangle.getHeight()
             || rectangle.getPosition().getY() >= position.getY() + height ) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns a string representation of the rectangle
     *
     * @return String with details of the rectangle
     */
    public String toString() {
        return "{" + position + " " + width + "x" + height + "}";
    }
}
