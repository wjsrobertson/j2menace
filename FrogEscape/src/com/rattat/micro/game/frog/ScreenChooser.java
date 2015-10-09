/**
 * Copyright 2006 William Robertson (www.rattat.com)
 */

package com.rattat.micro.game.frog;

import com.rattat.micro.game.frog.*;

/**
 * Simple class for choosing which screen to display. Rather than passing
 * around a midlet instance, pass this around instead. Safer.
 *
 * @author william@rattat.com
 */
public class ScreenChooser {
    
    /**
     * The FroggerMIDLet instance
     */
    Midlet midlet;
    
    /**
     * The main menu
     */
    public static final int SCREEN_MAIN_MENU = 1;
    
    /**
     * The game board
     */
    public static final int SCREEN_BOARD = 2;
    
    /**
     * The pause screen (main menu with extra option)
     */
    public static final int SCREEN_PAUSE = 3;
    
    /**
     * The game won screen
     */
    public static final int SCREEN_WON = 4;   

    /** 
     * Creates a new instance of ScreenChooser 
     *
     * Takes FroggerMIDLet as a parameter - used for actually
     * displaying the appropriate Displayables
     *
     * @param Midlet midlet
     */
    public ScreenChooser (Midlet midlet) {
        this.midlet = midlet;
    }

    /**
     * Display a particular screen must be one of the SCREEN_XXX variables
     *
     * @param int name  Name of screen to display
     *
     * @throws Exception if invalid parameter is passed in (i.e. undefined 
     * screen - not SCREEN_XXX) 
     */
    public void displayScreen(int name) throws Exception {
        switch (name) {
            case SCREEN_MAIN_MENU:
                midlet.displayMainMenu();
                break;
                
            case SCREEN_BOARD:
                midlet.displayBoard();
                break;
                
            case SCREEN_PAUSE:
                midlet.displayPause();
                break;
                
            case SCREEN_WON:
                midlet.displayWon();
                break;
        
            default:
                throw new Exception("Invalid screen");
        }
    }    
}
