/*
 * ScoreLayer.java
 *
 * Created on 27 September 2006, 19:51
 *
 */

package com.rattat.micro.game.frog.board.elements;

import com.rattat.micro.graphics.Layer;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author william@rattat.com
 */
public class StatusLayer extends Layer {

    /**
     * Number of lives to display
     */
    private int lives = 1;
    
    /**
     * The amount of time a player has to complete a level
     */
    private int maxTime = 100;
    
    /**
     * The remaining time a player has to complete the level
     */
    private int time = 0;
    
    /**
     * The current level that the player is on
     */
    private int level = 1;
    
    /**
     * The width of one cell of this layer. Used for rendering
     */
    int cellWidth = 16;
    
    /**
     * When the time reaches this point it will be displayed to indicate urgency
     */
    private static final int CTRITICAL_TIME_PERCENT = 0;
        
    /**
     * Width of this component in cells
     */
    private static final int NUM_CELLS_WIDE = 15;
    
    /**
     * Path to the image file saying "Time"
     */
    private String timeImageFile = "/__time.png";
    
    /**
     * Image saying "Time"
     */
    private Image timeImage = null;
    
    /**
     * Path to the image representing one player life
     */
    private String lifeImageFile = "/frog_life.png";
    
    /**
     * Image representing one player life
     */
    private Image lifeImage = null;
    
    /**
     * Path to the image representing one level
     */
    private String levelImageFile = "/level.png";
    
    /**
     * Image representing one level
     */
    private Image levelImage = null;
    
    /**
     * X offset to the time image
     */
    private int timeImageXOffset;
    
    /**
     * X offset from the RHS of the board where the first level image should
     * appear
     */
    private int levelImageXOffset;  // offset from the RHS

    /**
     * The width of the life bar when it is full
     */
    private int timeBarFullWidth = 0;
    
    /**
     * The normal colour of the time bar
     */
    private int timeBarFullColor = 0x00FF00;
    
    /**
     * Calculated from timeBarFullWidth - if the bar is any smaller
     * than this then it is critical
     */
    private int timeBarCriticalWidth = 200;
    
    /**
     * The colour that the life bar will be rendered in when time is critical
     */
    private int timeBarCriticalColor = 0xFF0000;
    
    /**
     * y padding in pixels 
     */
    private static final  int LIFE_BAR_PADDING_Y = 1;
    
    /**
     * X padding in pixels 
     */
    private static final int LIFE_BAR_PADDING_X = 1;

    /** 
     * Creates a new instance of ScoreLayer 
     */
    public StatusLayer() {
        init();
    }

    /**
     * Load all images and get the StatusLayer ready for use
     */
    private void init() {
        try {
            timeImage = Image.createImage(timeImageFile);
            timeImageXOffset = getWidth() - timeImage.getWidth();
        } catch (Exception e) {
            System.err.println("Error loading image: " + e);
        }
        
        try {
            lifeImage = Image.createImage(lifeImageFile);
        } catch (Exception e) {
            System.err.println("Error loading image: " + e);
        }
        
        try {
            levelImage = Image.createImage(levelImageFile);
            levelImageXOffset = 2 * levelImage.getWidth();
        } catch (Exception e) {
            System.err.println("Error loading image: " + e);
        }
                
        setWidth( cellWidth * NUM_CELLS_WIDE );
        setHeight( timeImage.getHeight() );
        
        timeBarFullWidth    = getWidth() - (2 * timeImage.getWidth());
        timeBarCriticalWidth = (int) ((double)timeBarFullWidth / 10);
    }
    
    /**
     * Reset back to initial state
     */
    private void reset() {
        level = 1;
        time = maxTime;
    }

    /**
     * Set the amount of time a user is allowed to play for before they loose
     * a life
     */
    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    /**
     * Set the remaining time
     *
     * @param int time
     */
    public void setTime(int time) {
        this.time = time;
    }
    
    /**
     * Get the remaining time
     */
    public int getTime() {
        return time;
    }
    
    /**
     * Set the number of lives a player has left
     *
     * @param int lives
     */
    public void setLives (int lives) {
        this.lives = lives;
    }

    /**
     * Set the current level
     *
     * @param int level
     */
    public void setLevel (int level) {
        this.level = level;
    }
    
    /**
     * Paint the StatueLayer on a Graphics object
     *
     * @param Graphics g
     */
    public void paint(Graphics g) {
        
        // clear the rectangle
        
        g.setColor(0, 0, 0); // black
        g.fillRect(getX(), getY(), getWidth(), getHeight());
        
        // paint the time image (RHS of time bar)
        
        if (timeImage != null) {
            g.drawImage(timeImage, getWidth() - timeImage.getWidth(), getY(), Graphics.TOP|Graphics.LEFT);
        }

        // draw the lives
        
        if (lifeImage != null ) {
            for (int i=0 ; i<lives-1 ; i++) {
                g.drawImage(lifeImage, i * lifeImage.getWidth(), getY() , Graphics.TOP|Graphics.LEFT);
            }
        }
        
        // paint the level indicators
        if (levelImage != null) {
            for (int i=0 ; i<level ; i++) {
                int xOffset = getWidth() - levelImageXOffset - (i * lifeImage.getWidth());
                g.drawImage(levelImage, xOffset, getY() , Graphics.TOP|Graphics.LEFT);
            }
        }
        
        // paint the time strip

        if (timeImage != null && time > 0) {
            int color = timeBarFullColor;
            
            if ( (((double)time / (double)maxTime) * 100) <= CTRITICAL_TIME_PERCENT ) {
                color = timeBarCriticalColor;
            }
            
            g.setColor(color);
            
            int barWidth  = (int) ((((double)time) / ((double) maxTime)) * timeBarFullWidth);
            int barEndX   = getX() + getWidth() - timeImage.getWidth() - LIFE_BAR_PADDING_X;
            int barStartX = barEndX - ((int) barWidth) - LIFE_BAR_PADDING_X;
            
            int barHeight = timeImage.getHeight() / 2;
            int barEndY   = getY() + getHeight() - LIFE_BAR_PADDING_Y;
            int barStartY = barEndY - barHeight - LIFE_BAR_PADDING_Y;
            
            g.fillRect( barStartX, barStartY, barWidth, barHeight );
        }
    }    
    
    /**
     * Decrement the time counter by one unit
     */
    public void tick() {
        time--;
    }
}
