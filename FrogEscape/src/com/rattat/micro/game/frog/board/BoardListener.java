/**
 * Copyright 2006 William Robertson (www.rattat.com)
 */

package com.rattat.micro.game.frog.board;

import com.rattat.micro.game.frog.board.BoardModel;

/**
 * BoardListener interface should be implemented by objects wishing to be 
 * notified of board events. e.g. new game, lost life etc.
 *
 * @author william@rattat.com
 */
public interface BoardListener {
    
    /**
     * Called when a BoardEvent happens
     * 
     * @param int event The releveant BoardEvent static int representing
     *                  the event
     * @param BoardModel model  The model the event happened on
     */
    public void boardEvent(int event, BoardModel model);
}
