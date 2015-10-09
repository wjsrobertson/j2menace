/**
 * Copyright 2006 William Robertson (www.rattat.com)
 */

package com.rattat.micro.game.frog.board.listeners;

import com.rattat.micro.game.frog.board.*;
import com.rattat.micro.game.frog.sound.SoundManager;

/**
 * Listens for BoardEvents and plays sounds at the appropriate times
 *
 * @author william@rattat.com
 */
public class BoardSoundListener implements BoardListener {

    /**
     * The SoundManager object used to play frogger sounds
     */
    private SoundManager soundPlayer;

    /**
     * Creates a new instance of BoardSoundListener
     */
    public BoardSoundListener () {
        soundPlayer = SoundManager.getInstance();
    }

    /**
     * BoardListener interface method
     *
     * Listen for BoardEvent.LEVEL_START, BoardEvent.FROG_HOME, BoardEvent.FROG_DEATH
     * and pay the appropriate dounds when these event happen
     */
    public void boardEvent(int event, BoardModel board) {
        switch (event) {
            case BoardEvent.GAME_START:
                //soundPlayer.play(SoundManager.SOUND_NEW_GAME);
                break;
                
            case BoardEvent.LEVEL_START:
                soundPlayer.play(SoundManager.SOUND_NEW_LEVEL);
                break;

            case BoardEvent.FROG_HOME:
                soundPlayer.play(SoundManager.SOUND_FROG_HOME);
                break;
                
            case BoardEvent.FROG_DEATH:
                soundPlayer.play(SoundManager.SOUND_FROG_DEATH);
                break;
                                
        }
    }
}
