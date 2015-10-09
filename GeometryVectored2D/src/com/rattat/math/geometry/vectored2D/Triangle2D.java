/**
 * Triangle2d.java
 *
 * Subject to the apache license v. 2.0
 *
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 * @author william@rattat.com
 */

package com.rattat.math.geometry.vectored2D;

/**
 * <p>A class representing a triangle in 2D</p>
 *
 * @author william@rattat.com
 */
public class Triangle2D {
    
    // Attributes

    /**
     * Vertex A
     */
    private Vector2D a;
    
    /**
     * Vertex B
     */
    private Vector2D b;
    
    /**
     * Vertex C
     */
    private Vector2D c;
    
    /**
     * Flag to denote that the points have moved and the bounds rectangle 
     * is out of date. Used to determine if the bounds rectangle needs to 
     * be recalculated.
     */
    private boolean boundsChanged = true;
    
    /**
     * The bounding rectangle of the triangle
     */
    private BoundingRectangle2D bounds = new BoundingRectangle2D();
    
    // Constructors

    /**
     * Creates a new instance of Triangle2D
     */
    public Triangle2D() {
    }

    /**
     * Creates a new instance of Triangle2D with the values of the three 
     * vertices equal to the values of the paramters
     * 
     * @param a first vertex
     * @param b second vertex
     * @param c third vertex
     */
    public Triangle2D(Vector2D a, Vector2D b, Vector2D c) {
        this.a = new Vector2D(a);
        this.b = new Vector2D(b);
        this.c = new Vector2D(c);
    }
    
    /**
     * <p>Get the first vertex</p>
     * 
     * <p>Note that the returned Vector2D is a reference to the actual vertex, not
     * a copy. So, users must not modify the returned instance.
     */
    public Vector2D getA() {
        return a;
    }
    
    /**
     * <p>Get the second vertex</p>
     * 
     * <p>Note that the returned Vector2D is a reference to the actual vertex, not
     * a copy. So, users must not modify the returned instance.
     */
    public Vector2D getB() {
        return b;
    }

    /**
     * <p>Get the third vertex</p>
     * 
     * <p>Note that the returned Vector2D is a reference to the actual vertex, not
     * a copy. So, users must not modify the returned instance.
     */
    public Vector2D getC() {
        return c;
    }

    /**
     * Check if a point is contained within this triangle
     * 
     * @param point Point to check
     *
     * @return true if containted, false otherwise
     */
    public boolean contains(Vector2D point) {
        if (! getBounds().contains(point)) {
            return false;
        }
        
        return false;  
    }

    /**
     * Check if a line intersects this triangle
     * 
     * @param line Line to check
     *
     * @return true if intersects, false otherwise
     */
    public boolean intersects(Line2D line) {
        if (! line.getBounds().intersects(getBounds())) {
            return false;
        }

        return false;    
    }

    /**
     * Check if this triangle intersects another 
     * 
     * @param triangle Triangle to check
     *
     * @return true if intersects, false otherwise
     */
    public boolean intersects(Triangle2D triangle) {
        if (! triangle.getBounds().intersects(getBounds())) {
            return false;
        }
        
        return false;
    }

    /**
     * Get the bounding rectangle
     * 
     * @return Minimum bounding rectangle
     */
    public BoundingRectangle2D getBounds() {
        if (boundsChanged) {
            double minX = Math.min(a.getX(), b.getX());
            minX = Math.min(minX, c.getX());
            double maxX = Math.max(a.getX(), b.getX());
            maxX = Math.max(minX, c.getX());
            double minY = Math.min(a.getY(), b.getY());
            minY = Math.min(minY, c.getY());
            double maxY = Math.max(a.getY(), b.getY());
            maxY = Math.max(minY, c.getY());

            bounds.set(new Vector2D(minX, minY), Math.abs(maxX-minX), Math.abs(maxY-minY));
        }

        return bounds;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();       

        buffer.append("{");
        buffer.append(a);
        buffer.append(", ");
        buffer.append(b);
        buffer.append(", ");
        buffer.append(c);
        buffer.append("}");

        return buffer.toString();
    }
}

