/*
 * MenuCanvas.java
 * 
 * Copyright 2007 William Robertson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.rattat.micro.game.aster;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

import com.rattat.micro.ui.selectslider.Option;
import com.rattat.micro.ui.selectslider.SelectListener;
import com.rattat.micro.ui.selectslider.SelectSlider;

/**
 * Canvas object for displaying options - new game, 
 * turn sound off etc
 *
 * @author william@rattat.com
 */
public class MenuCanvas extends Canvas implements SelectListener {

	/**
	 * The slider object which handles the option slider logic
	 */
	private SelectSlider slider = null;
    
	/**
	 * The high score to display
	 */
    private int highScore = 0;
    
    /**
     * Title to display
     */
    private String title = "Asteroid Belt";
    
    /**
     * Author to display
     */
    private String author = "by William Robertson";
    
    /**
     * High score string
     */
    private String highScoreDescription = "high score: ";
    
    /**
     * Info string
     */
    private String info = "www.rattat.com";
    
    /**
     * The height of the rendered slider in pixels
     */
    private static final int SLIDER_HEIGHT = 50;
    
    /**
     * Flag indicating if the menu should display the options
     * available when paused or not (continue game)
     */
    private boolean paused = false;
    
    /**
     * Flag indication whether sound is on or off - user will
     * be given the option to turn it off/on accordingly
     */
    private boolean soundOn = false;
    
    /**
     * Flag indication whether vibrations on or off - user will
     * be given the option to turn them off/on accordingly
     */
    private boolean vibrateOn = false;
    
    /**
     * Option to continue playing a paused game
     */
    public static final Option CONTINUE    = new Option(1, "continue game");
    
    /**
     * Option to exit the application
     */
    public static final Option EXIT        = new Option(2, "exit");
    
    /**
     * Option to turn sound on
     */
    public static final Option SOUND_ON    = new Option(3, "sound off");
    
    /**
     * Option to turn sound off
     */
    public static final Option SOUND_OFF   = new Option(4, "sound on");
    
    /**
     * Option to turn vibrations on
     */
    public static final Option VIBRATE_ON  = new Option(5, "vibrate off");
    
    /**
     * Option to turn vibrations off
     */
    public static final Option VIBRATE_OFF = new Option(6, "vibrate on");
    
    /**
     * Option to start new game
     */
    public static final Option NEW_GAME    = new Option(7, "new game");
    
    /**
     * Option to display about page
     */
    public static final Option ABOUT       = new Option(8, "about");
    
    /**
     * When left or right key is held down, slide then wait 
     * for this number of miliseconds before sliding again
     */
    private static final int MOVE_WAIT_PERIOD = 500;
    
    /**
     * Used to keep track of the last slide to stop very fast
     * movement of the options
     */
    private long lastMoveTime = 0;

    /**
     * Construct a new instance of MenuCanvas
     * 
     * @param slider
     */
    public MenuCanvas( SelectSlider slider ) {
    	this.slider = slider;
        setFullScreenMode(true);
        initSlider();
    }
    
    /**
     * Initialise the slider object
     */
    private void initSlider() {
    	slider.setLooped(true);
    	
        slider.addOption(NEW_GAME);
        slider.addOption(SOUND_ON);
        slider.addOption(VIBRATE_ON);

        slider.addOption(ABOUT);
        slider.addOption(EXIT);
        slider.addSelectListener(this);
        
        slider.setCurrentOption(NEW_GAME);
    }
    
    /**
     * Canvas method
     * 
     * Listen for keypresses and move the move the slider appropriately
     *
     * @param keyCode
     */
    public void keyPressed(int keyCode) {
    	if ( System.currentTimeMillis() - lastMoveTime < MOVE_WAIT_PERIOD ) {
    		return;
    	}

    	int gameAction = getGameAction(keyCode);
    	boolean moved = false;
    	
        if (gameAction == Canvas.LEFT) { 
            slider.moveLeft();
            moved = true;
        } else if (gameAction == Canvas.RIGHT) {
            slider.moveRight();
            moved = true;
        } else if (gameAction == Canvas.FIRE) {
            slider.select();
            moved = true;
        }
        
        if ( moved ) {
        	lastMoveTime = System.currentTimeMillis();
        	repaint();
        } 
    }
    
    /**
     * Canvas method - Key is released
     */
    public void keyReleased(int keyCode) {
    	int gameAction = getGameAction(keyCode);
    	
        if (gameAction == Canvas.LEFT || gameAction == Canvas.RIGHT || gameAction == Canvas.FIRE) {
        	lastMoveTime = 0;
        }
    }
    
    /**
     * An option is selected - update options to reflect the change, if
     * required
     */
    public void optionSelected(Option option) {
        if (option == SOUND_ON) {
        	setSoundOn(true);
        } else if (option == SOUND_OFF) {
            setSoundOn(false);
        } else if (option == VIBRATE_ON) {
        	setVibrateOn(true);
        } else if (option == VIBRATE_OFF) {
            setVibrateOn(false);
        }
    }
    
    /**
     * Render the slider and information to the screen
     */
    public void paint(Graphics g) {
    	
    	// black background
    	    	
        g.setColor(0); // black
        g.fillRect(0, 0, getWidth(), getHeight());
    	
    	// Draw the text
    	
    	int sliderTop = (getHeight() - SLIDER_HEIGHT) / 2;
    	int sliderBottom = (getHeight() + SLIDER_HEIGHT) / 2;
    	int anchor = Graphics.TOP|Graphics.HCENTER;
    	Font font = g.getFont();
    	int fontHeight = font.getHeight();
        int yPos = 0;
        int xPos = getWidth() / 2;
        g.setColor(255, 255, 255); // white

        yPos = sliderTop - 3*fontHeight;
        g.drawString(title, xPos, yPos, anchor);
                
        yPos = sliderTop + -2*fontHeight;
        g.drawString(author, xPos, yPos, anchor);
        
        if (highScore>0) {
            yPos = sliderBottom + fontHeight;
            g.drawString(highScoreDescription + highScore, xPos, yPos, anchor);
        }
        
        yPos = sliderBottom + 2*fontHeight;
        g.drawString(info, xPos, yPos, anchor);
        
        // draw the slider
    	
    	g.clipRect(0, 0, getWidth(), getHeight());
     	g.clipRect(0, sliderTop, getWidth(), SLIDER_HEIGHT);
    	
        slider.paint(g);
    }

    /**
     * Alter the options to reflect a pause menu / 
     * pre-game menu. Basically, hide the "continue game"
     * option if unpaused
     *
     * @param paused
     */
	public void setPaused(boolean paused) {
		if ( paused && ! this.paused ) {	// if pausing unpaused menu
			slider.addOption(CONTINUE);
			slider.setCurrentOption(CONTINUE);
		}
		if ( ! paused && this.paused ) {	// if unpausing a paused menu
			slider.removeOption(CONTINUE);
			slider.setCurrentOption(NEW_GAME);
		}
		
		this.paused = paused;
	}

    /**
     * Alter the options to give hte user the option to 
     * turn sound on / off according to {@code soundOn}
     *
     * @param soundOn
     */
	public void setSoundOn(boolean soundOn) {
		if ( soundOn && ! this.soundOn ) {	// if turning soundManager on when switched off
			slider.replaceOption(SOUND_ON, SOUND_OFF);
		}
		if ( ! soundOn && this.soundOn ) {	// if turning soundManager off when switched on
			slider.replaceOption(SOUND_OFF, SOUND_ON);
		}
		
		this.soundOn = soundOn;
	}

    /**
     * Alter the options to give the user the option to 
     * turn vibrations on / off according to {@code vibrateOn}
     *
     * @param vibrateOn
     */
	public void setVibrateOn(boolean vibrateOn) {
		if ( vibrateOn && ! this.vibrateOn ) {	// if turning vibrate on when switched off
			slider.replaceOption(VIBRATE_ON, VIBRATE_OFF);
		}
		if ( ! vibrateOn && this.vibrateOn ) {	// if turning vibrate off when switched on
			slider.replaceOption(VIBRATE_OFF, VIBRATE_ON);
		}
		
		this.vibrateOn = vibrateOn;
	}
	
	/**
	 * Set the high score that is displayed
	 * 
	 * @param highScore
	 */
	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}

}
