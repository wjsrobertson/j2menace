/*
 * Midlet.java
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

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;

import com.rattat.micro.game.aster.mvc.GameController;
import com.rattat.micro.game.aster.mvc.GameListener;
import com.rattat.micro.game.aster.mvc.Model;
import com.rattat.micro.game.aster.mvc.ShipController;
import com.rattat.micro.ui.selectslider.Option;
import com.rattat.micro.ui.selectslider.SelectListener;
import com.rattat.micro.ui.selectslider.SelectSlider;
import com.rattat.micro.ui.vibrator.Vibrator;

/**
 * Main MIDlet class
 *
 * @author william@rattat.com
 */
public class Midlet extends MIDlet implements CommandListener, SelectListener, GameListener {

	/**
	 * Keep a reference to the Display object
	 */
    private Display display = null;
    
    /**
     * Game canvas which is displayed when game is in play
     */
    private AsteroidsGameCanvas gameCanvas = null;
    
    /**
     * Menu slider
     */
    private SelectSlider slider = new SelectSlider();
    
    /**
     * Canvas to display when menu is active
     */
    private MenuCanvas menuCanvas = new MenuCanvas(slider);
    
    /**
     * Exit command - will cause the application to exit
     */
    private Command exit  = new Command("Exit", Command.EXIT, 0);
    
    /**
     * Menu command - will display menu if game is in play
     */
    private Command menu  = new Command("Menu", Command.SCREEN, 0);
    
    /**
     * Helper object for storing / retreiving persistent data
     */
    private AsteroidsRecords records = new AsteroidsRecords();

	/**
	 * Holds the model - all of the game data
	 */
    private Model model = new Model(); 
	    
	/**
	 * Holds the game controller - handles all game logic
	 */
    private GameController controller = new GameController(model); 
	
    /**
     * Controller object for moving around the spaceship when game is in play
     */
    private ShipController shipController = new ShipController(model);

    /**
     * Object which responds to events and plays sounds
     */
    private SoundManager soundManager = new SoundManager(); 
	
    /**
     * Object which responds to events and vibrates
     */
    private VibrationManager vibrationManager = new VibrationManager(); 
	
    /**
     * Description to display on about screen 
     */
    private String alertDescription = "Based on Asteroids - the classic Atari arcade game from 1979\n\n" +
								 	  "Music and coding by William Robertson\n\n" +
									  "(c) 2006, 2007\n" +
									  "www.rattat.com\n";

    /** Creates a new instance of Midlet */
    public Midlet() {
        display = Display.getDisplay(this); // main display
        Vibrator.setDisplay(display);
    }
    
    /**
     * <p>
     * MIDlet lifecycle method called when application starts
     * </p>
     * 
     * <p>
     * Initialises all objects used in game
     * </p>
     */
    protected void startApp() {
    	// initialise app using data from records
    	
        menuCanvas.setHighScore( records.getHighScore() );
        menuCanvas.setSoundOn( records.isSoundOn() );
        menuCanvas.setVibrateOn( records.isVibrateOn() );
        
        Vibrator.setOn( records.isVibrateOn() );
        soundManager.setOn( records.isSoundOn() );
    	
        // Create the main game gameCanvas and start it
    	
    	slider.addSelectListener(this);
    	
    	controller.addListener(soundManager);
    	shipController.addListener(soundManager);
    	soundManager.setOn(true);
    	
    	controller.addListener(vibrationManager);
    	shipController.addListener(vibrationManager);
    	vibrationManager.setOn(true);
    	
    	controller.addListener(this);
    	
        gameCanvas = new AsteroidsGameCanvas(controller, shipController, model);
        gameCanvas.setCommandListener(this);
        gameCanvas.setPaused(true);
        gameCanvas.start();
        
        menuCanvas.addCommand(exit);
        gameCanvas.addCommand(menu);

        display.setCurrent(menuCanvas);
        
        soundManager.play(SoundManager.SOUND_MENU);
    }

    /**
     * <p>
     * MIDlet lifecycle method called when application is paused
     * </p>
     * 
     * <p>
     * Pauses game thread
     * </p>
     */
    public void pauseApp() {
    	gameCanvas.setPaused(true);
    }

    /**
     * <p>
     * MIDlet lifecycle method called when application is unpaused
     * </p>
     * 
     * <p>
     * Resumes game thread
     * </p>
     */
    public void unpauseApp() {
    	gameCanvas.setPaused(false);
    }
    
    /**
     * <p>
     * MIDlet lifecycle method called when application is destroyed
     * </p>
     * 
     * <p>
     * Stops game thread
     * </p>
     */
    public void destroyApp(boolean unconditional)  {
    	gameCanvas.stop();
    }

    /**
     * CommandListener interface method - either exit or
     * display menu
     */
    public void commandAction(Command c, Displayable d) {
        if (c.getCommandType() == Command.EXIT) {
            destroyApp(true);
            notifyDestroyed();
        } else if (c == menu) {
            pauseGame();
        }
    }

    /**
     * SelectSlider interface method - respond to slider
     * events in the menu
     * 
     * @param option
     */
    public void optionSelected(Option option) {
    	
        if (option == MenuCanvas.CONTINUE) {
        	continueGame();
        } else if (option == MenuCanvas.EXIT) {
            destroyApp(true);
            notifyDestroyed();
        } else if (option == MenuCanvas.ABOUT) {
            displayAbout();
        } else if (option == MenuCanvas.SOUND_ON) {
        	soundManager.setOn(true);
        	records.setSoundOn(true);
        	soundManager.play(SoundManager.SOUND_MENU);
        } else if (option == MenuCanvas.SOUND_OFF) {
        	soundManager.setOn(false);
        	records.setSoundOn(false);
        	soundManager.stop(SoundManager.SOUND_MENU);
        } else if (option == MenuCanvas.VIBRATE_ON) {
        	vibrationManager.setOn(true);
        	records.setVibrateOn(true);
        } else if (option == MenuCanvas.VIBRATE_OFF) {
        	vibrationManager.setOn(false);
        	records.setVibrateOn(false);
        } else if (option == MenuCanvas.NEW_GAME) {
        	newGame();
        }
    }

    /**
     * GameListener method - respond to game events
     * 
     * @param gameEvent
     * @param model
     */
    public void gameEvent(int gameEvent, Model model) {
    	if ( gameEvent == GameListener.EVENT_GAME_END ) {
    		endGame( model.getScore() );
    	}
    }
    
    /**
     * Start a new game
     */
    private void newGame() {
    	gameCanvas.setPaused(false);
    	controller.newGame();
    	gameCanvas.repaint();
    	display.setCurrent(gameCanvas);
    	soundManager.stop(SoundManager.SOUND_MENU);
    }
    
    /**
     * End the current game, saving high score if needed
     * 
     * @param score
     */
    private void endGame(int score) {
    	
    	// update high score, if required
		if ( score > records.getHighScore() ) { 
			records.setHighScore( score );
			menuCanvas.setHighScore( score );
		}
    	
    	gameCanvas.setPaused(true);
        menuCanvas.setPaused(false);
        display.setCurrent(menuCanvas);
    	soundManager.play(SoundManager.SOUND_MENU);
    }
    
    /**
     * Continue a paused game
     */
    private void continueGame() {
    	gameCanvas.setPaused(false);
    	display.setCurrent(gameCanvas);
    	soundManager.stop(SoundManager.SOUND_MENU);
    }
    
    /**
     * Pause the game
     */
    private void pauseGame() {
    	gameCanvas.setPaused(true);
        menuCanvas.setPaused(true);
    	soundManager.play(SoundManager.SOUND_MENU);
        display.setCurrent(menuCanvas);
    }
    
    /**
     * Display the about screen
     */
    private void displayAbout() {
        Alert alert = new Alert("Asteroid Belt", alertDescription, null, null);
        alert.setTimeout(Alert.FOREVER);
        alert.setType(AlertType.INFO);
        display.setCurrent(alert); 
    }
    
}
