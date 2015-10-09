/*
 * AsteroidsGameCanvas.java
 * 
 * Copyright 2007 William Robertson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.rattat.micro.game.mb.geometry;

/**
 * A class representing a point consisting of integer 
 * x and y coordinates 
 * 
 * @author william@rattat.com
 */
public class Point {

    /**
     * X ordinate
     */
    private int x;

    /**
     * Y ordinate
     */
    private int y;

    /**
     * Creates a new instance of IntegerPoint2d 
     *
     * @param x X ordinate
     * @param y Y ordinate
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the X ordinate
     *
     * @return 
     */
    public int getX() {
        return x;
    }

    /**
     * Set the X ordinate
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Get the Y ordinate
     *
     * @return 
     */
    public int getY() {
        return y;
    }

    /**
     * Set the Y ordinate
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }
}
