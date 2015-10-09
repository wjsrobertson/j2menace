/**
 * Copyright 2006 William Robertson (www.rattat.com)
 */

package com.rattat.micro.game.frog.board.elements;

import com.rattat.micro.game.frog.board.elements.Home;

/**
 * HomeListener interface should be implemented by objects wishing to be 
 * notified of home events. e.g. player lands on cell, insect gets eaten etc.
 *
 * @author william@rattat.com
 */
public interface HomeListener {
    
    /**
     * Called when a HomeEvent happens
     *
     * @param int event
     * @param Home home
     */
    public void homeEvent(int event, Home home);
}
