/**
 *
 *
 */

package com.rattat.micro.game.frog.sound;

import com.rattat.micro.sound.SimplePlayer;

/**
 * Singleton class for playing Asteroids sounds 
 * 
 * Basically a wrapper around a SimplePlayer with hard-wired sound names
 * 
 * Access with:
 * 
 * <code>
 * SoundManager sound = SoundManager.getInstance();
 * sound.play(SoundManager.SOUND_MENU);
 * </code>
 *  
 * @author william@rattat.com
 */
public class SoundManager {
    
    private static SoundManager instance = null;
    
    private boolean on = true;
    
    public static final String SOUND_MENU            = "menu";
    public static final String SOUND_NEW_GAME        = "new_game";
    public static final String SOUND_FROG_HOME       = "frog_home";
    public static final String SOUND_NEW_LEVEL       = "new_level";
    public static final String SOUND_FROG_DEATH      = "frog_death";
    
    private SimplePlayer sounds = new SimplePlayer();
    
    /** 
     * Private constructor - singleton
     *
     * @see getInstance()
     */
    private SoundManager () {
        init();
    }
    
    /**
     * Fetch the instance of the SoundManager class
     * 
     * @return SoundManager
     */
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
            instance.init();
        }
        
        return instance;
    }

    /**
     * Initialise the sounds, ready to play
     */
    private void init() {
        addSound(SOUND_MENU,       "/menu.mid",  "audio/midi"); 
        addSound(SOUND_NEW_GAME,   "/start.wav", "audio/x-wav");
        addSound(SOUND_FROG_HOME,  "/home.wav",  "audio/x-wav");
        addSound(SOUND_FROG_DEATH, "/splat.wav", "audio/x-wav");
        addSound(SOUND_NEW_LEVEL,  "/frog.wav",  "audio/x-wav");
    }

    /**
     * Add a sound
     */
    private void addSound(String name, String path, String mimeType) {  
        try {
            sounds.add(name, path, mimeType); 
        } catch (Exception e) {
            System.err.print("Error loading sound (" + name +  ") -" + e);
        }
    }

    /**
     * Play a sound once
     *
     * @param String the name of the sound to play
     */
    public void play(String sound) {
        if (on) {
            play(sound, 1);
        }
    }

    /**
     * Play a sound, looping the required number of times
     *
     * @param String sound  The name of the sound to play
     * @param int loop      The number of times to loop the sound
     */
    public void play(String sound, int loop) {
        if (on) {
            sounds.play(sound, loop);
        }
    }

    /**
     * Stop playing a sound
     *
     * @param String the name of the sound to stop playing
     */
    public void stop(String sound) {
        sounds.stop(sound);
    }

    /**
     * Stop playing all sounds
     */
    public void stop() {
        sounds.stop();
    }
    
    /**
     * Turn on sound
     */
    public void on() {
        on = true;
    }
    
    /**
     * turn off sound - all calls to play() will no nothing
     */
    public void off() {
        on = false;
        stop();
    }
    
    /**
     * Check if sounds are turned on or off
     *
     * @return boolean
     */
    public boolean isOn() {
        return on;
    }
    
}
