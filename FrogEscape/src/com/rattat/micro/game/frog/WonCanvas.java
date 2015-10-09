/**
 * Copyright 2006 William Robertson (www.rattat.com)
 */

package com.rattat.micro.game.frog;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Font;

/**
 * A simple class to display a page saying congrats for winning the game
 *
 * @author william@rattat.com
 */
public class WonCanvas extends Canvas {

    /**
     * title text
     */
    String title = "Well Done - you win!";
    
    /**
     * little bit of congratulatory text so the user doesn't feel like 
     * playing the game for so long was a complete waste of time.
     */
    String text = "The frogs love you :-)";

    /**
     * Left hand side of a bit of text telling the user to press a key to continue
     * in the middle of this and continueTextRight will be the name of the button 
     * they have to press to continue
     */
    String continueTextLeft = "press ";
    
    /**
     * Right hand side of a bit of text telling the user to press a key to continue
     * in the middle of this and continueTextLeft will be the name of the button 
     * they have to press to continue
     */
    String continueTextRight = " to continue";

    /**
     * Colour of the text
     */
    int textColor = 0x00AA55;

    /**
     * Background colour of the screen
     */
    int backgroundColor = 0x000000;
    
    /**
     * Holds the Font object returned from the Graphics object - needed
     * so that we can determine its height for spacing when painting on the 
     * screen
     */
    Font font;
    
    /**
     * Used to switch between different screens - needed to display the 
     * main menu when the user continues from this screen
     */
    private ScreenChooser screenChooser;

    /**
     * Creates a new instance of WonCanvas
     */
    public WonCanvas (ScreenChooser screenChooser) {
        setFullScreenMode(true);
        this.screenChooser = screenChooser;
    }

    /**
     * Canvas concrete method
     *
     * paint the congrats to the screen. 
     *
     * @param Graphics g
     */
    public void paint(Graphics g) {
        // background
        
        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(textColor);

        font = g.getFont();
        
        int yPos = getHeight() / 2 - font.getHeight()*2;
        int xPos = getWidth() / 2;
        g.drawString(title, xPos, yPos, Graphics.TOP|Graphics.HCENTER);
        
        yPos = getHeight() / 2;
        xPos = getWidth() / 2;
        g.drawString(text, xPos, yPos, Graphics.TOP|Graphics.HCENTER);
        
        String continueText = continueTextLeft
                              + getKeyName( getKeyCode(Canvas.FIRE) ) 
                              + continueTextRight;
        
        yPos = getHeight() / 2 + font.getHeight()*2;
        xPos = getWidth() / 2;
        g.drawString(continueText, xPos, yPos, Graphics.TOP|Graphics.HCENTER);
    }
    
    /**
     * Canvas method
     * 
     * Listen for the continue keypress - the Canvas.FIRE keypress
     *
     * @param int keyCode
     */
    public void keyPressed(int keyCode) {
        if (getGameAction(keyCode) == Canvas.FIRE) {
            try {
                screenChooser.displayScreen(ScreenChooser.SCREEN_MAIN_MENU);
            } catch (Exception e) {
            }
        }
        
    }
}
