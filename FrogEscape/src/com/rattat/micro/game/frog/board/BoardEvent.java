/**
 * Copyright 2006 William Robertson (www.rattat.com)
 */

package com.rattat.micro.game.frog.board;

/**
 * Very simple class - just holds public static final ints that 
 * are used to notify BoardListeners of events. In most Observer pattern
 * implementations an instance of the Event class would be passed around
 * not notify of events, but creating all these objects is not a great thing in 
 * MIDP. Passing around ints insatead is plenty effective. 
 *
 * @author william@rattat.com
 */
public class BoardEvent {
    
    /**
     * User has lost a life
     */
    public static final int FROG_DEATH = 1;
    
    /**
     * User has completed a level
     */
    public static final int LEVEL_COMPLETE = 2;
    
    /**
     * User has won the game
     */
    public static final int GAME_WON = 3;
    
    /**
     * User has lost the game
     */
    public static final int GAME_LOST = 4;
    
    /**
     * User has started a new level
     */
    public static final int LEVEL_START = 5;
    
    /**
     * User has started a new game
     */
    public static final int GAME_START = 6;
    
    /**
     * User has moved the frog
     */
    public static final int FROG_MOVE = 7;
    
    /**
     * User has managed to get the frog into one of the home spaces
     * at the top of the screen
     */
    public static final int FROG_HOME = 8;
    
    /** 
     * Private constructor - this class should never be instanciated. Just 
     * used to get at the static ints
     */
    private BoardEvent () {
    }
    
}
