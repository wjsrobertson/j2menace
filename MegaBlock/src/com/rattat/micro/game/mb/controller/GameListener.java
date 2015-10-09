/*
 * GameListener.java
 *
 * Subject to the apache license v. 2.0
 *
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 * @author william@rattat.com
 */

package com.rattat.micro.game.mb.controller;

/**
 *
 * @author william@rattat.com
 */
public interface GameListener {
	    
    public static final int EVENT_GAME_OVER = 0;
    
    public static final int EVENT_LINE_CLEARED = 1;
	
    public void gameEvent(int event);
}
