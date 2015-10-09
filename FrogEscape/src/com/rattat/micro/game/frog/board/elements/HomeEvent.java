/**
 * Copyright 2006 William Robertson (www.rattat.com)
 */

package com.rattat.micro.game.frog.board.elements;

/**
 * Very simple class - just holds public static final ints that 
 * are used to notify BoardListeners of events. In most Observer pattern
 * implementations an instance of the Event class would be passed around
 * not notify of events, but creating all these objects is not a great thing in 
 * MIDP. Passing around ints insatead is plenty effective. 
 *
 * @author william@rattat.com
 */
public class HomeEvent {
    
    /**
     * Frog dies from hitting the wrong bit of the home component
     */
    public static final int FROG_DEATH = 1;
    
    /**
     * Frog has landed in an empty cell
     */
    public static final int FROG_HOME = 2;
    
    /**
     * An insect has appeared in one of the cells
     */
    public static final int INSECT_ARRIVE = 3;
    
    /**
     * An insect has left one of the cells
     */
    public static final int INSECT_LEAVE = 4;
    
    /**
     * An aligator has appeared in one of the cells
     */
    public static final int ALIGATOR_ARRIVE = 5;
    
    /**
     * An aligator has left one of the cells
     */
    public static final int ALIGATOR_LEAVE = 6;
    
    /**
     * An insect has been eaten by the player's frog
     */
    public static final int INSECT_EATEN = 7;

    /** 
     * Private constructor - should never be instanciated
     */
    private HomeEvent () {
    }
    
}
