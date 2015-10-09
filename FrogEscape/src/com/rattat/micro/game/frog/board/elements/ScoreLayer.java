/**
 * Copyright 2006 William Robertson (www.rattat.com)
 */

package com.rattat.micro.game.frog.board.elements;

import com.rattat.micro.graphics.Layer;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * Used to create the part of the frogger board that displays the score
 * an high score 
 *
 * @author william@rattat.com
 */
public class ScoreLayer extends Layer /* implements HomeListener */ {

    /**
     * Score to display
     */
    private int score = 0;
    
    /**
     * Highscore to display
     */
    private int highScore = 0;
    
    /**
     * Width of one cell (fictional cell used in determining where to position scores)
     */
    private int cellWidth = 8;
    
    /**
     * Height of one cell (fictional cell used in determining where to position scores)
     */
    private int cellHeight = 7;
    
    /**
     * The background colour of the component
     */ 
    private int backgroundColor = 0x00002A; // dark blue
    
    /**
     * width of the whole component - used in conjunction with cellWidth
     */ 
    private int NUM_CELLS_WIDE = 30;
    
    /**
     * width of the whole component - used in conjunction with cellHeight
     */ 
    private int NUM_CELLS_HIGH = 3;
    
    /**
     * How many digits long is the score
     */
    private int NUM_DIGITS_SCORE = 5;
    
    /**
     * Path representing image number files - * wildcard inicated digit (0,1 etc)
     */
    private String numberImageFile = "/*.png";
    
    /**
     * Holds each of the digits image files
     */
    private Image[] numberImages = new Image[10];
    
    /**
     * Page to image file saying "1-up"
     */
    private String oneUpImageFile = "/__1_up.png";
    
    /**
     * Image  saying 1-up
     */
    private Image oneUpImage;
    
    /**
     * Page to image file saying "high score"
     */
    private String highScoreImageFile = "/__high_score.png";
    
    /**
     * Image saying "high score"
     */
    private Image highScoreImage;
    
    /**
     * Y offset of the actual score digits
     */
    private int scoreOffsetY = cellHeight;
    
    /**
     * X offset of the player score and 1-up image
     */
    private int oneUpOffsetX = 4 * cellWidth;
    
    /**
     * X offset of the high score
     */
    private int highScoreOffsetX = 12 * cellWidth;
    
    /**
     * X offset of the image saying "high score"
     */
    private int highScoreImageOffsetX = 11 * cellWidth;
    
    /** Creates a new instance of ScoreLayer */
    public ScoreLayer () {
        init();
    }
    
    /**
     * Load all images and get tge ScoreLayer ready for use
     */
    private void init() {
        setWidth( cellWidth * NUM_CELLS_WIDE );
        setHeight( cellWidth * NUM_CELLS_HIGH );    // yes, cellWidth, not cellHeight
        
        try {
            oneUpImage = Image.createImage(oneUpImageFile);
        } catch (Exception e) {
            System.err.println("Error loading image: " + e);
        }
        
        try {
            highScoreImage = Image.createImage(highScoreImageFile);
        } catch (Exception e) {
            System.err.println("Error loading image: " + e);
        }
        
        // load the number images
        
        for (int i=0 ; i<10 ; i++) {
            char[] chars = new char[1];
            Integer.toString(i).getChars(0,1, chars, 0);
            String file = numberImageFile.replace( '*', chars[0] );
            try {
                numberImages[i] = Image.createImage(file);
            } catch (Exception e) {
                System.err.println("Error loading image: " + e);
            }
        }
    }

    /**
     * Paint the ScoreLayer on a graphics object
     *
     * @param Graphics g
     * 
     */
    public void paint(Graphics g) {
        // clear the rectangle
        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());

        // paint the 1-up image
        g.drawImage(oneUpImage, oneUpOffsetX, 0, Graphics.LEFT|Graphics.TOP);
        
        // paint the 1-up score
        paintScore(g, oneUpOffsetX, scoreOffsetY, getScore());

        // paint the high score image
        g.drawImage(highScoreImage, highScoreImageOffsetX, 0, Graphics.LEFT|Graphics.TOP);
        
        // paint the high score
        paintScore(g, highScoreOffsetX, scoreOffsetY, getHighScore());
    }
    
    /**
     * Paint a score in a certain position on the screen
     * 
     * Works by looping through the score digits and painting the image
     * representing each digit in the appropriate place 
     *
     * @param Graphics g
     * @param int x
     * @param int y
     * @param int score
     */
    private void paintScore(Graphics g, int x, int y, int score) {

        //char[] scoreCharsTest = {'0'};
        char[] scoreChars = padScore( getNumberChars(score) );
        //char[] scoreChars = getNumberChars(score);
        
        for (int i=0 ; i<scoreChars.length ; i++) {
            int digit = Integer.parseInt( String.valueOf(scoreChars[i]) );
            
            g.drawImage(numberImages[digit], (i*numberImages[digit].getWidth()) + x, y, Graphics.LEFT|Graphics.TOP);
        }
    }
    
    /**
     * Padd an array of score characters with 0 chars
     *
     * @param char[] score
     */
    private char[] padScore(char[] score) {
        return leftPadChars(score, '0', NUM_DIGITS_SCORE);
    }
    
    /**
     * Convert a number into a character array consisting of the digits of the
     * number in a character array
     *
     * @param int number
     * @return char[]
     */
    private char[] getNumberChars(int number) {
        String numberString =  Integer.toString(number);
        
        int numChars = numberString.length();
        char[] chars = new char[numChars];
        numberString.getChars(0,numberString.length(), chars, 0);
        
        return chars;
    }
    
    /**
     * Create an array left padded with a character based on an existing array
     * and the length that the new arary should be
     *
     * @param char[] toPad
     * @param char padding
     * @param int length
     */
    private char[] leftPadChars(char[] toPad, char padding, int length) {
        if (length - toPad.length<=0) {
            return toPad;
        }
        
        char[] padded = new char[length];

        for (int i=0 ; i<toPad.length ; i++) {
            padded[length-1-i] = toPad[toPad.length-1-i];
        }

        for (int i=0 ; i<length-toPad.length ; i++) {
            int position = length -1 - toPad.length - i;
            padded[position] = padding;
        }
        
        return padded;
    }

    /**
     * Get the player's score
     */
    public int getScore () {
        return score;
    }

    /**
     * Set the player's score
     *
     * @param int score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Get the high score
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Set the high score
     *
     * @param int highScore
     */
    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}
