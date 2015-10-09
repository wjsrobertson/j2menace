/*
 * Circle2D.java
 *
 * Created on May 22, 2007, 1:46 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.rattat.math.geometry.vectored2D;

/**
 *
 * @author poobar
 */
public class Circle2D {
    
    private double radius = 0;
    
    private Vector2D center = new Vector2D();
    
    private boolean modified = true;
    
    private BoundingRectangle2D bounds = null;
    
    /** Creates a new instance of Circle2D */
    public Circle2D() {
    }
    
    /** Creates a new instance of Circle2D */
    public Circle2D(Vector2D center, double radius) {
        setCenter( new Vector2D(center) );
        setRadius(radius);
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Vector2D getCenter() {
        return center;
    }

    public void setCenter(Vector2D center) {
        this.center = center;
    }
    
    public BoundingRectangle2D getBounds() {
        if ( bounds == null ) {
            bounds = new BoundingRectangle2D();
        }
        
        double diameter = 2 * radius;
        
        bounds.set( center.getX()-radius, center.getY()-radius, diameter, diameter );
        
        return bounds;
    }
    
    /**
     * Check if a point is contained within this circle
     * 
     * @param point Point to check
     *
     * @return true if containted, false otherwise
     */
    public boolean contains(Vector2D point) {
        if (! getBounds().contains(point)) {
            return false;
        }
        
        // x*x + y*y - R*R = 0 -> equation for radius
        
        double x2 = point.getX() * point.getX();
        double y2 = point.getX() * point.getX();
        double r2 = radius * radius;
        
        return (x2 + y2 - r2) < 0;
    }

    /**
     * Check if a line intersects this circle
     * 
     * @param line Line to check
     *
     * @return true if intersects, false otherwise
     */
    public boolean intersects(Line2D line) {
        if (! line.getBounds().intersects(getBounds())) {
            return false;
        }

        return true;    
    }

    /**
     * Check if this triangle intersects another 
     * 
     * @param circle Circle to check
     *
     * @return true if intersects, false otherwise
     */
    public boolean intersects(Circle2D circle) {
        if (! circle.getBounds().intersects(getBounds())) {
            return false;
        }
        
        return (radius + circle.getRadius()) > center.distance( circle.getCenter() );
    }
    
}
