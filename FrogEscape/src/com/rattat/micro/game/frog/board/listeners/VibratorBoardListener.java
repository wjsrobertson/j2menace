/**
 * Copyright 2006 William Robertson (www.rattat.com)
 */

package com.rattat.micro.game.frog.board.listeners;

import com.rattat.micro.game.frog.board.*;
import com.rattat.micro.ui.vibrator.Vibrator;

/**
 * vibrate the handset when the user looses a life
 *
 * implements BoardListener to know when to play the sound
 *
 * @author william@rattat.com
 */
public class VibratorBoardListener implements BoardListener {
    
    /**
     * Length of time to vibrate for when the player looses a life
     */
    private static final int DEATH_VIBRATION = 100;
    
    /**
     * Creates a new instance of VibratorBoardListener
     */
    public VibratorBoardListener () {
    }
    
    /**
     * BoardListener interface method
     *
     * Listen for the BoardEvent.FROG_DEATH event and vibrate when it happens
     *
     * @param int event
     * @param BoardModel model
     */
    public void boardEvent(int event, BoardModel model) {
        switch (event) {
            case BoardEvent.FROG_DEATH:
                Vibrator.vibrate(DEATH_VIBRATION);
                break;
        }
    }
    
}
