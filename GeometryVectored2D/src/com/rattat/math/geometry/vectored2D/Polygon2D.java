/**
 * @author william@rattat.com
 */

package com.rattat.math.geometry.vectored2D;

import java.util.*;

/**
 * A polygon in two dimensions
 *
 * @author william@rattat.com
 */
public class Polygon2D {

    /**
     * Vertices of this polygon
     */
    private Vector vertices = new Vector();
    
    /**
     * Triangles that this polygon is composed of
     */
    private Vector triangles = new Vector();
    
    /**
     * Edges of this polygon
     */
    private Vector edges = new Vector();
    
    /**
     * Midpoint of this polygon
     */
    private Vector2D midPoint = new Vector2D();

    /**
     * Flags when the mid point has changed. Used to stop unneeded calculations
     */
    private boolean isMidPointChanged = true;
    
    /**
     * Flags when the trinagulation has changed. Used to stop unneeded calculations.
     *
     * Triangulation is splitting the polygon into triangles
     */
    private boolean isTriangulationChanged = true;
    
    /**
     * Flags when the edges have changed. Used to stop unneeded calculations.
     */
    private boolean haveEdgesChanged = true;
    
    /**
     * Flags when the bounds have changed. Used to stop unneeded calculations.
     */
    private boolean boundsChanged = true;
    
    /**
     * Rectangle containing the bounds for the polygon
     */
    private BoundingRectangle2D bounds = new BoundingRectangle2D();
    
    /**
     * Creates a new instance of Polygon2D
     */
    public Polygon2D() {
    }   
    
    /**
     * Creates a new instance of Polygon2D with vertices with the same values 
     * as the {@code p} parameter.
     * 
     * @param polygon   The polygon to copy
     */
    public Polygon2D(Polygon2D polygon) {
        for (Enumeration e = polygon.vertices() ; e.hasMoreElements() ; ) {
            addVertex(new Vector2D((Vector2D) e.nextElement()));
        }
    }

    /**
     * <p>Get an enumeration for looping through the vertices of this polygon.</p>
     *
     * <p>Please note code calling this method must not alter the values of the
     * Enumuration and should treat it as read only.</p>
     *
     * @return  enumeration for looping through vertices
     */
    public Enumeration vertices() {
        return vertices.elements();
    }
    
    /**
     * Remove all versices form the object
     */
    public void clearVertices() {
    	vertices.removeAllElements();
    }
    
    /**
     * <p>Get an enumeration for looping through the edges of this polygon.</p>
     *
     * <p>Please note code calling this method must not alter the values of the
     * Enumuration and should treat it as read only.</p>
     *
     * @return  enumeration for looping through edges
     */
    public Enumeration edges() {
        generateEdges();
        return edges.elements();
    }
    
    /**
     * <p>Get an enumeration for looping through the triangles this polygon is 
     * composed of.</p>
     *
     * <p>Please note code calling this method must not alter the values of the
     * Enumuration and should treat it as read only.</p>
     *
     * @return  enumeration for looping through triangles
     */
    public Enumeration triangles() {
        triangulate();
        return triangles.elements();
    }
    
    /**
     * Translate the polygon by a vector offset
     *
     * @param offset
     */
    public void translate(Vector2D offset) {
        Enumeration e = vertices();
        while (e.hasMoreElements()) {
            Vector2D v = (Vector2D) e.nextElement();
            v.add(offset);
        }
        
        flagModified();
    }

    /**
     * <p>Add a vertex to the polygon.</p>
     * 
     * <p>Creates a new vertex and copies the values from {@code point}</p>
     *
     * @param point
     */
    public void addVertex(Vector2D point) {
        vertices.addElement(point);
        
        flagModified();
    }

    /**
     * Check if a point is contained within this polygon
     *
     * @param point
     *
     * @return  true if contained, false otherwise
     */
    public boolean contains(Vector2D point) {

        if (! getBounds().contains(point)) {
            System.out.println("AAA: " + point);
            return false;
        }

        Line2D verticalRay = new Line2D(point, new Vector2D(point.getX(), Double.POSITIVE_INFINITY));
        int intersectionCount = 0;       
        boolean contains = false;
        
        generateEdges();
                
        for (Enumeration e = edges.elements() ; e.hasMoreElements() ; ) {
            Line2D testLine = (Line2D) e.nextElement();
            double minY = Math.min(testLine.getStart().getY(), testLine.getEnd().getY());
            double maxY = Math.max(testLine.getStart().getY(), testLine.getEnd().getY());
            
            if (testLine.getGradient() == Double.POSITIVE_INFINITY) {
                if ( point.getX() == testLine.getStart().getX()
                    && point.getY() >= minY 
                    && point.getY() <= maxY ) {
                    contains = true;
                }
            } else {
                if (verticalRay.intersects(testLine)) {
                    intersectionCount++;
                }
            }
        }

        return (contains || (intersectionCount % 2 == 1));
    }

    /**
     * Check if a line intersects with this polygon
     *
     * @param line
     *
     * @return  True if intersects, false otherwise
     */
    public boolean intersects(Line2D line) {
        
        if (! line.getBounds().intersects(getBounds())) {
            return false;
        }

        generateEdges();
        
        for ( Enumeration e = edges.elements() ; e.hasMoreElements() ; ) {
            Line2D current = (Line2D) e.nextElement();
            if (current.intersects(line)) {
                return true;
            }
        }

        return contains(line.getStart()) || contains(line.getEnd());
    }

    /**
     * Check if another polygon intersects with this polygon
     *
     * @param polygon
     *
     * @return true if intersects, false otherwise
     */
    public boolean intersects(Polygon2D polygon) {

        if (! polygon.getBounds().intersects(getBounds())) {
            return false;
        }

        for (Enumeration e1 = edges() ; e1.hasMoreElements() ; ) {
            Line2D current = (Line2D) e1.nextElement();
            for (Enumeration e2 = polygon.edges() ; e2.hasMoreElements() ; ) {
                Line2D test = (Line2D) e2.nextElement();
                if (current.intersects(test)) {
                    return true;
                }
            }
        }
        
        return false;
    }   

    /**
     * Split the polygon into triangles for computations
     */
    private void triangulate() {
        if (isTriangulationChanged) {
            triangles.removeAllElements();

            Vector2D midPoint = midPoint();
            Vector2D first = null;
            Vector2D lastChecked = null;
            
            for (Enumeration e = vertices.elements() ; e.hasMoreElements() ; ) {
                Vector2D current = (Vector2D) e.nextElement();
                if (lastChecked == null) {
                    first = current;
                    lastChecked = first;
                } else {
                    triangles.addElement( new Triangle2D(lastChecked, midPoint, current) );
                    lastChecked = current;
                }
            }
            if (first != null) {
                triangles.addElement( new Triangle2D(lastChecked, midPoint, first) );
            }
        }

        isTriangulationChanged = false;
    }

    /**
     * Get the midpoint of this polygon
     *
     * @return Midpoint
     */
    public Vector2D midPoint() {
        if (isMidPointChanged==true) {
            double x=0;
            double y=0;

            for (Enumeration e = vertices.elements() ; e.hasMoreElements() ; ) {
                Vector2D vertex = (Vector2D) e.nextElement();
                x += vertex.getX();
                y += vertex.getY();
            }

            midPoint = new Vector2D(x/vertices.size(), y/vertices.size());
            isMidPointChanged = false;
            
            return midPoint;          
        } else {
            return midPoint;
        }
    }

    /**
     * Generate the edges of this polygon
     */
    private void generateEdges() {
        if (haveEdgesChanged==true) {
            edges.removeAllElements();
            Vector2D first = null;
            Vector2D lastChecked = null;
            double x=0;
            double y=0;
            
            for (Enumeration e = vertices.elements() ; e.hasMoreElements() ; ) {
                Vector2D vertex = (Vector2D) e.nextElement();
                x += vertex.getX();
                y += vertex.getY();

                if (lastChecked == null) {
                    first = vertex;
                    lastChecked = first;
                } else {
                    edges.addElement( new Line2D(lastChecked, vertex) );
                    lastChecked = vertex;
                }
            }
            if (first != null) {
                edges.addElement( new Line2D(lastChecked, first) );
            }
        }
        
        haveEdgesChanged = false;
    }

    /**
     * Get the bounding rectangle
     *
     * @return minimum bounding rectangle
     */
    public BoundingRectangle2D getBounds() {
        if (boundsChanged==true) {
            boolean first = true;
            double minX=0;
            double maxX=0;
            double minY=0;
            double maxY=0;
            
            for (Enumeration e = vertices.elements() ; e.hasMoreElements() ; ) {
                Vector2D vertex = (Vector2D) e.nextElement();
                if (first) {
                    minX = vertex.getX();
                    maxX = vertex.getX();
                    minY = vertex.getY();
                    maxY = vertex.getY();
                    first = false;
                } else {
                    minX = Math.min(minX, vertex.getX());
                    maxX = Math.max(maxX, vertex.getX());
                    minY = Math.min(minY, vertex.getY());
                    maxY = Math.max(maxY, vertex.getY());
                }
            }
            
            bounds.set(minX, minY, Math.abs(maxX-minX), Math.abs(maxY-minY));
            
            boundsChanged = false;
        }

        return bounds;
    }
    
    /**
     * Rotate the polygon clockwise about a point by an arbritray angle.
     *
     * @param v     Point vector to rotate about
     * @param angle Angle in radians
     */
    public void rotate(Vector2D v, double angle) {
        rotate(v.getX(), v.getY(), angle);
    }

    /**
     * Rotate the polygon clockwise about a point by an arbritray angle.
     *
     * @param x     X ordinate of point to rotate about
     * @param y     Y ordinate of point to rotate about
     * @param angle Angle in radians
     */
    public void rotate(double x, double y, double angle) {
        double theta = -angle;
        
        for (Enumeration e = vertices() ; e.hasMoreElements() ; ) {
            Vector2D vertex = (Vector2D) e.nextElement();

            // translate to origin
            double tmpX = vertex.getX() - x;
            double tmpY = vertex.getY() - y;

            // rotate
            double sin = Math.sin(theta);
            double cos = Math.cos(theta);
            
            double newX = tmpX * cos - tmpY * sin;
            double newY = tmpX * sin + tmpY * cos;

            // translate back to old location
            newX += x;
            newY += y;

            // set teh point to be where we calculated it should be
            vertex.setXY(newX, newY);
        }
        
        flagModified();
    }

    /**
     * Flag that the polygon points have changed
     *
     * @todo Just need one flag for this
     */
    private void flagModified() {
        isMidPointChanged      = true;
        isTriangulationChanged = true;
        haveEdgesChanged       = true;
        boundsChanged          = true;
    }

    /**
     * Return a string representation of the polygon
     *
     * @return A string representation of the polygon
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        
        for (Enumeration e = vertices() ; e.hasMoreElements() ; ) {
            Vector2D vertex = (Vector2D) e.nextElement();
            if ( e.hasMoreElements() ) {
                buffer.append(", " + vertex);
            }
        }
        
        return buffer.toString();
    }
    
}
