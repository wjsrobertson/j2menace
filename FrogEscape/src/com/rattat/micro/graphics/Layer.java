/**
 * Copyright 2006 William Robertson (www.rattat.com)
 */

package com.rattat.micro.graphics;

import javax.microedition.lcdui.Graphics;

/**
 *
 * Since midp2 Layer can't be subclassed, this class provides similar 
 * functionality
 *
 * @todo document methods
 *
 * @author william@rattat.com
 */
public abstract class Layer {
    
    private int x=0;
    
    private int y=0;
    
    private int width=0;
    
    private int height=0;
    
    private boolean visible;
    
    /** Creates a new instance of Layer */
    public Layer () {
    }

    public int getX () {
        return x;
    }

    public void setX (int x) {
        this.x = x;
    }

    public int getY () {
        return y;
    }

    public void setY (int y) {
        this.y = y;
    }

    public int getWidth () {
        return width;
    }

    public void setWidth (int width) {
        this.width = width;
    }

    public int getHeight () {
        return height;
    }

    public void setHeight (int height) {
        this.height = height;
    }

    public boolean isVisible () {
        return visible;
    }

    public void setVisible (boolean visible) {
        this.visible = visible;
    }
    
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }
    
    public abstract void paint(Graphics g);
}
