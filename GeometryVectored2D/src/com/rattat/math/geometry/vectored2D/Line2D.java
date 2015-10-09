/**
 * Line2d.java
 *
 * Subject to the apache license v. 2.0
 *
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 * @author william@rattat.com
 */

package com.rattat.math.geometry.vectored2D;

/**
 * A class representing a line in 2D
 *
 * @author william@rattat.com
 *
 * @todo - tidy up positionModified / recalculate() / getBounds()
 */
public class Line2D {

    /**
     * The position vector of the start od the line (start point)
     */
    private Vector2D start = new Vector2D();
    
    /**
     * The position vector of the end of line (end point)
     */
    private Vector2D end = new Vector2D();
    
    /**
     * Will hold the mid point of the line. This gets updated
     * when required
     */
    private Vector2D midPoint = null;

    /**
     * Gradient of the line
     */
    double gradient = 0;
    
    /**
     * c of y=mx+c fame
     */
    double yOffset  = 0;
    
    /**
     * True if end points have moved - used to save unneeded calculations
     *
     * @todo - use this properly
     */
    private boolean positionModified = true;
    
    /**
     * True if the mid point has changed. Forces recalculation
     * of mid point.
     *
     * @todo - use this properly
     */
    private boolean midPointChanged = true;
    
    /**
     * Bounding rectangle
     */
    private BoundingRectangle2D bounds = null;

    /**
     * Creates a new instance of Line2D
     */
    public Line2D() {
    }
    
    /**
     * Creates a new instance of Line2D
     * 
     * @param xStart x position of start vector
     * @param yStart y position of start vector
     * @param xEnd   x position of end vector
     * @param yEnd   y position of end vector
     */
    public Line2D(double xStart, double yStart, double xEnd, double yEnd) {
        setStart(new Vector2D(xStart, yStart));
        setEnd(new Vector2D(xEnd, yEnd));
    }

    /**
     * Creates a new instance of Line2D with start and end points set to the same 
     * values as the values of the {@code start} and {@code end} parameters.
     * 
     * @param start
     * @param end
     */
    public Line2D(Vector2D start, Vector2D end) {
        setStart(start);
        setEnd(end);
    }
    
    /**
     * Creates a new instance of Line2D with values for the start and end 
     * points copied from those of the {@code line} parameter.
     * 
     * @param line
     */
    public Line2D(Line2D line) {
        setStart( new Vector2D(line.getStart()) );
        setEnd( new Vector2D(line.getEnd()) );
    }

    /**
     * Set the start vector - the point where the line starts.
     * 
     * @param v
     */
    public void setStart(Vector2D v) {
        start.set(v);
        positionModified = true;
    }

    /**
     * Set the end vector - the point where the line ends
     * 
     * @param v
     */
    public void setEnd(Vector2D v) {
        end.set(v);
        positionModified = true;
    }

    /**
     * <p>Get the start vector - the point where the line starts.</p>
     *
     * <p>
     * Note that the returned vector is not a copy, so changing 
     * the properties of the returned vector would change the properties 
     * of the start vector of the line. This is prohibited - users must not
     * alter the returned value.
     * </p>
     * 
     * @return Start vector
     */
    public Vector2D getStart() {
        return start;
    }

    /**
     * <p>Get the end vector - the point where the line ends.</p>
     *
     * <p>
     * Note that the returned vector is not a copy, so changing 
     * the properties of the returned vector will change the properties 
     * of the start vector of the line. This is prohibited - users must not
     * alter the returned value.
     * </p>
     * 
     * @return End vector
     */
    public Vector2D getEnd() {
        return end;
    }
    
    /**
     * Get the gradient of the line. The m in y = mx + c.
     *
     * @return gradient of the line
     */
    public double getGradient() {
        recalculate();
        
        return gradient;
    }
    
    /**
     * The Y offset of this line. The c in y = mx + c.
     *
     * @return y offset of the line
     */
    public double getYOffset() {
        recalculate();
        
        return yOffset;
    }
    
    /**
     * Get the mid point of the line
     *
     * @return A vector representing the mid point of the line
     * 
     * @todo - add this to unit tests
     */
    public Vector2D midPoint() {
    	if (midPointChanged) {
    		if ( midPoint == null ) {
    			midPoint = new Vector2D();
    		}
    		    		
    		midPoint.set(start);
            midPoint.add(end);
            midPoint.scaleBy(0.5);
    	}
        
        return midPoint;
    }

    /**
     * Check if a point (represented by a position vector) lies on this line
     * 
     * @param v position vector to check
     *
     * @return true if contained, false othrewise
     */
    public boolean contains(Vector2D v) {
         recalculate();

        if (! getBounds().contains(v)) {
            return false;
        }

        if ( gradient == Double.POSITIVE_INFINITY  ) {
            if (v.getX() == start.getX()) {
                return true;
            }
        } else {
            double yIntersect = gradient * v.getX() + yOffset;

            return (   v.getY() > yIntersect - VMath2D.EPSILON 
                    && v.getY() < yIntersect + VMath2D.EPSILON );
        }

        return false;
    }

    /**
     * Check if a line intersects this line
     * 
     * @param l Line to check
     *
     * @return true if intersection, false othrewise
     */
    public boolean intersects(Line2D l) {
        recalculate();

        if (! l.getBounds().intersects(getBounds())) {
            return false;
        }

        /*
         procedure - work out the gradients & intersection point of the 
         extended lines then see if the intersection point is within the
         bounds of the lines. 
         Paralell lines and vertical lines handled as special cases
         */
  
        // determine the max and min points for each line - used later

        double xTestMin = Math.min(start.getX(), end.getX());
        double xTestMax = Math.max(start.getX(), end.getX());
        double yTestMin = Math.min(start.getY(), end.getY());
        double yTestMax = Math.max(start.getY(), end.getY());
        double xMin = Math.min(l.getStart().getX(), l.getEnd().getX());
        double xMax = Math.max(l.getStart().getX(), l.getEnd().getX());
        double yMin = Math.min(l.getStart().getY(), l.getEnd().getY());
        double yMax = Math.max(l.getStart().getY(), l.getEnd().getY());

        // intersection point

        double x;
        double y;

        // determine the gradient & y offset for test line

        double testGradient = l.getGradient();
        double testYOffset = l.getYOffset();

        // special case 1) both lines are vertical

        if (gradient == Double.POSITIVE_INFINITY 
            && testGradient == Double.POSITIVE_INFINITY) {
            if ( start.getX() == l.getStart().getX() ) {
                if (yMin == yTestMin) {
                    return true;
                } else if (yMin <= yTestMin) {
                    return (yMax >= yTestMin);
                } else {  // yTestMin > yMin
                    return (yTestMax >= yMin);
                }
            } else {
                // the lines are parallel and never meet
                return false;
            }
        }

        // special case 2) this line paralell to test line
        
        if (gradient == testGradient) {
            if (yOffset == testYOffset) {
                return true;
            } else {
                return false;
            }
        }

        // For the remaining cases, calculate the intersection point then
        // see if it's within the bounds of the lines
        
        if (gradient == Double.POSITIVE_INFINITY) {
            // special case 3) this line has infinite gradient, test line doesn't           
            x = start.getX();
            y = testGradient * x + testYOffset;
        } else if (testGradient == Double.POSITIVE_INFINITY) {
            // special case 4) test line has infinite gradient, this line doesn't           
            x = l.getStart().getX();
            y = (gradient * x) + yOffset;
        } else {
            // normal case
            // *************************************************************************************
            x = (testYOffset - yOffset) / (gradient - testGradient); // X = (c2 - c1) / (m1 - m2) 
            y = (gradient * x) + yOffset; // Y = c1 + m1 * X
            //System.out.println("C");
        }
        
        return (
            (y >= yTestMin && y <= yTestMax)
            && (y >= yMin && y <= yMax)
            && (x >= xTestMin && x <= xTestMax)
            && (x >= xMin && x <= xMax)
        );
    }

    /**
     * Get the bounding rectangle
     * 
     * @return BoundingRectangle2D minimum bounding rectangle
     */
    public BoundingRectangle2D getBounds() {
        recalculate();

        return bounds;
    }
    
    /**
     * Rotate the line clockwise about a point by an arbritray angle.
     *
     * @param v     Point vector to rotate about
     * @param angle Angle in radians
     */
    public void rotate(Vector2D v, double angle) {
        rotate(v.getX(), v.getY(), angle);
    }
    
    /**
     * Rotate the line clockwise about a point by an arbritray angle.
     *
     * @param x     X ordinate of point to rotate about
     * @param y     Y ordinate of point to rotate about
     * @param angle Angle in radians
     */
    public void rotate(double x, double y, double angle) {

        double theta = -angle;
        
        /*
         start point first
         */

        // translate to origin
        double tmpX = start.getX() - x;
        double tmpY = start.getY() - y;
        // rotate
        double sin = Math.sin(theta);
        double cos = Math.cos(theta);
        double newX = tmpX * cos - tmpY * sin;
        double newY = tmpX * sin + tmpY * cos;
        // translate back to old location
        newX += x;
        newY += y;
        
        // set the point to be where we calculated it should be
        start.setXY(newX,newY);

        /*
         now do the end point
         */

        // translate to origin
        tmpX = end.getX() - x;
        tmpY = end.getY() - y;
        // rotate
        sin = Math.sin(theta);
        cos = Math.cos(theta);
        newX = tmpX * cos - tmpY * sin;
        newY = tmpX * sin + tmpY * cos;
        // translate back to old location
        newX += x;
        newY += y;
        
        // set the point to be where we calculated it should be
        end.setXY(newX,newY);
        
        positionModified = true;
    }
    
    /**
     * <p>Recalculate gradient and offset.<p>
     *
     * <p>Does nothing if start/end points have not changed.</p>
     */
    private void recalculate() {
        
        if (! positionModified) {
            return;
        }
        
        if (bounds == null) {
            bounds = new BoundingRectangle2D();
        }
        
        /*
         Recalculate gradient and yOffset
         */
        
        if ( start != null && end != null ) {
            if (start.getX() == end.getX()) {
                gradient = Double.POSITIVE_INFINITY;
                yOffset  = 0;
            } else {
                gradient = (start.getY()-end.getY()) / (start.getX()-end.getX());
                yOffset  = start.getY() - gradient * start.getX();
            }
        }
        
        /*
         Recalculate bounding rectangle
         */
        
        double minX = Math.min(start.getX(), end.getX());
        double maxX = Math.max(start.getX(), end.getX());
        double minY = Math.min(start.getY(), end.getY());
        double maxY = Math.max(start.getY(), end.getY());

        bounds.set(minX, minY, Math.abs(maxX-minX), Math.abs(maxY-minY));
        
        positionModified = false;
    }

    /**
     * 
     * 
     * @return String with information about the Line2D;
     */
    public String toString() {
        
        StringBuffer buffer = new StringBuffer();
        buffer.append("(");
        buffer.append(start.getX());
        buffer.append("," );
        buffer.append(start.getY());
        buffer.append(") -> (");
        buffer.append(end.getX());
        buffer.append(",");
        buffer.append(end.getY());
        buffer.append(")");
        
        return buffer.toString();
    }
}
