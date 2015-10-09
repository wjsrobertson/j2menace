/*
 * AsteroidsGameCanvas.java
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

import javax.microedition.lcdui.Graphics;

import com.rattat.micro.game.aster.mvc.GameController;
import com.rattat.micro.game.aster.mvc.Model;
import com.rattat.micro.game.aster.mvc.ShipController;
import com.rattat.micro.game.aster.mvc.View;
import com.rattat.micro.ui.canvas.LoopGameCanvas;

/**
 * The game canvas - displayed during gameplay
 *
 * @author william@rattat.com
 */
class AsteroidsGameCanvas extends LoopGameCanvas { 

	/**
	 * Scale of the game - objects will be scaled up by this amount. 
	 * Can possibly use in a lter implementation to support over-large/small
	 * screen sizes. Doesn't currently affect speeds / velocities in most places. 
	 */
    private int gameScale = 10;

    /**
     * The view object contains all logic for rendering the model to 
     * screen on the form that the user sees as the game
     */
    private View view = null;
	
	/**
	 * Holds the game controller - handles all game logic
	 */
	private GameController controller = null; 
	
	/**
	 * Holds the ship controller which controls the ship movement
	 */
	private ShipController shipController = null;
	
	/**
	 * The model that 
	 * 
	 * @todo - this class shoildn't need access to the model
	 */
	private Model model = null;

	/**
	 * Time to wait between game loop iterations
	 */
    private final int THREAD_MAX_WAIT = 80;

    /**
     * Construct a new AsteroidsGameCanvas object
     * 
     * @param gameController
     * @param shipController
     * @param model
     */
    public AsteroidsGameCanvas(GameController gameController, ShipController shipController, Model model) {
    	this.controller = gameController;
    	this.shipController = shipController;
    	this.model = model;
    	
        setFullScreenMode(true);
        init();
    }
    
    /**
     * Initialise all objects and get ready for gameplay
     */
    private void init() {
        initSize();
        initView();
        controller.setGameScale(gameScale);
        controller.newGame();
        setLoopMaxWait(THREAD_MAX_WAIT);
    }
    
    /**
     * Get the size of the game canvas and set it up accordingly
     */
    private void initSize() {
    	controller.setGameSize( getWidth(), getHeight() );
    }

    /**
     * Canvas lifecycle method - called when screen sinze changes
     */
    public void sizeChanged(int w, int h) {
        initSize();
        initView();
    }
    
    /**
     * Initialise the view object
     */
    private void initView() {
    	view = new View( getWidth(), getHeight(), gameScale, model );
    }

    /**
     * Game loop. Increment loop in controller and accept user input
     */
    public void tick() {
        controller.tick();
        userInput();
    }
    
    /**
     * Check what keys are pressed and move the ship / shoot appropriately
     */
    private void userInput() {
        int keyStates = getKeyStates(); // get KeyState object

        if ((keyStates & LEFT_PRESSED) != 0) { 
        	shipController.rotateShipAntiClockwise();
        } else if ((keyStates & RIGHT_PRESSED) != 0) {
        	shipController.rotateShipClockwise();
        }

        if ((keyStates & UP_PRESSED) != 0) {
        	shipController.forwardThrustShip();
        } else if ((keyStates & DOWN_PRESSED) != 0) {
        	shipController.reverseThrustShip();
        } else {
        	shipController.clearThruster();
        }

        if ((keyStates & FIRE_PRESSED) != 0) {
        	shipController.lanunchMissile();
        }
    }

    /**
     * GameCanvas method - render the view to screen
     */
    public void paint(Graphics g) {
    	view.paint(g);
        flushGraphics();    
    }
}
