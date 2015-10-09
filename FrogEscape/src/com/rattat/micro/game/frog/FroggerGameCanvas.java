/**
 * Copyright 2006 William Robertson (www.rattat.com)
 */

package com.rattat.micro.game.frog;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.Image;

import com.rattat.micro.game.frog.board.*;
import com.rattat.micro.game.frog.board.listeners.*;
import com.rattat.micro.db.SimpleStore;

/**
 * GameCanvas class for frogger game
 *
 * Contains the game loop - runs in its own thread.
 *
 * Accepts user input during gameplay and delegates it to the model
 *
 * Sends paint requests to the view object.
 *
 * @author william@rattat.com
 */
public class FroggerGameCanvas extends GameCanvas implements Runnable, 
                                                             BoardListener {
        
    /**
     * The model - basically a glorified data structure holding all information
     * about the state of the game
     */
    private BoardModel model = new BoardModel();

    /**
     * View object - used to paint the model to a Graphics object
     */
    private BoardView view = new BoardView(model);
    
    /**
     * Flag to keep track of whether game thread is running or not
     *
     * Setting this to fale during execution will cause the run method to return
     * and the game thread to finish
     */
    private boolean gameThreadRunning = false;
    
    /**
     * The maximum length of time to wait between game updates in miliseconds
     */
    private int THREAD_MAX_WAIT = 80;
    
    /**
     * Leftmost pixel of game board
     */
    private int gameMinX   = 0;
    
    /**
     * Topmost pixel of game board
     */
    private int gameMinY   = 0;
    
    /**
     * Rightmost pixel of game board
     */
    private int gameMaxX   = 0;
    
    /**
     * Bottom pixel of game board
     */
    private int gameMaxY   = 0;
    
    /**
     * Width of the game board
     */
    private int gameWidth  = 0;
    
    /**
     * Height of the game board
     */
    private int gameHeight = 0;    
    
    /**
     * Image used to render board. Copied in from view
     *
     * @todo this is shit
     */
    private Image gameImage = null;
    
    /**
     * Receives board events and plays appropriate sounds
     */
    private BoardSoundListener soundListener = new BoardSoundListener();
    
    /**
     * Receives board events and vibrates the handset at the appropriate times
     */
    private VibratorBoardListener vibratorListener = new VibratorBoardListener();
    
    /**
     * True if game is paused, false otherwise
     */
    private boolean paused = true;
    
    /**
     * Thread used for game loop.
     */
    private Thread gameThread = null;
    
    /**
     * Used to persist/fetch high score
     */
    private SimpleStore dataStore;
    
    /**
     * Used to switch to the game won screen if the user wins
     */
    private ScreenChooser screenChooser;
    
    /**
     * Creates a new instance of FroggerGameCanvas
     *
     * @param SimpleStore dataStore
     * @param ScreenChooser screenChooser
     */
    public FroggerGameCanvas (SimpleStore dataStore, ScreenChooser screenChooser) {
        super(true);
        setFullScreenMode(true);
        this.dataStore = dataStore;
        this.screenChooser = screenChooser;
        init();
    }
    
    /**
     * Get the game ready for use
     */
    public void init() {
        initSize();
        initListeners();
        initHighScore();
    }
        
    /**
     * Determine the size and placement of the board
     */
    private void initSize() {
        gameWidth  = model.getWidth();
        gameHeight = model.getHeight();
        
        gameMinX = (getWidth() - gameWidth) / 2;
        gameMaxX = gameMinX + gameWidth;
        
        gameMinY = (getHeight() - gameHeight) / 2;
        gameMaxY = gameMinY + gameHeight;
    }
    
    /**
     * Setup the board listeners.
     *
     * The vibrator, the sound player and this FroggerGameCanvas instance.
     */
    private void initListeners() {
        model.addListener(soundListener);
        model.addListener(vibratorListener);
        model.addListener(this);
    }
    
    
    /**
     * Let the model know the high score model the high score
     */
    private void initHighScore() {
        if ( dataStore.get("highScore")!=null ) {
            int highScore = Integer.parseInt( dataStore.get("highScore") );
            model.setHighScore( highScore );
        }
    }
    
    /**
     * Perform actions needed when game ends
     *
     * currently just stores high score
     */
    public void finishGame() {
        saveHighScore();
    }
    
    /**
     * Save the score as the high score
     *
     * Will only save it if the current high score is greater than the
     * existing high score
     */
    public void saveHighScore() {
        int savedHighScore = 0;
        if ( dataStore.get("highScore")!=null ) {
            savedHighScore = Integer.parseInt( dataStore.get("highScore") );
        }
        if ( savedHighScore < model.getHighScore() ) {
            dataStore.put( "highScore", Integer.toString( model.getHighScore() ) );
        }
    }

    /**
     * Start a new game
     */
    public void start() {
        gameThreadRunning = true;
        
        if (gameThread == null) {
            gameThread = new Thread(this);
            gameThread.setPriority(Thread.MAX_PRIORITY);
            gameThread.start();
        }
    }
    
    /**
     * Reset the model ready for a new game
     */
    public void reset() {
        model.newGame();
    }

    /**
     * Stop the game
     */
    public void stop() {
        gameThreadRunning = false; 
    }

    //
    // Runnable interface method
    //

    /**
     * The main thread run method containing the game loop
     */ 
    public void run()  {
        Graphics g = getGraphics(); // graphics object for rendering
                
        int  waitTime = 0;   // period to between updating the game
        long startTime;      // used to calcuate timeDifference
        long endTime;        // used to calcuate timeDifference
        int  timeDifference; // used to keep track of how fast/slow game is running

        // main game loop

        while (gameThreadRunning) {    
            startTime = System.currentTimeMillis();

            if ( ! isPaused() ) {
                model.tick();
                userInput();
                paint(g);
            }

            // work out how long to pause the game for...
            
            endTime = System.currentTimeMillis();
            timeDifference = (int) (endTime - startTime);
            waitTime = THREAD_MAX_WAIT - timeDifference;

            // pause for the calculated length of time

            if (waitTime > 0) {
                try {
                    Thread.sleep(waitTime); 
                } catch (InterruptedException ie) { 
                     stop(); 
                }
            } else {
                System.err.println("Running too slow");
                // application is running too slow for device ;-(
            }
        }
    }

    /**
     * Paint the game to the screen
     *
     * Just delegates to the view
     *
     * @todo - this is shit. should remove the view - not needed. implemnent
     *       painting in model & stop calling it "model" 
     */
    public void paint(Graphics g) {
        g.setColor(0, 0, 0); // black
        g.fillRect(0, 0, getWidth(), getHeight());

        gameImage = view.render();
        g.drawImage(gameImage, gameMinX, gameMinY, Graphics.TOP|Graphics.LEFT);

        flushGraphics();   
    }

    /**
     * Read user input and delegate it to the model
     */
    private void userInput() {
        int keyStates = getKeyStates(); // get KeyState object

        if ((keyStates & LEFT_PRESSED) != 0) { 
            model.userInput(BoardModel.USERINPUT_LEFT);
        } else if ((keyStates & RIGHT_PRESSED) != 0) {
            model.userInput(BoardModel.USERINPUT_RIGHT);
        }

        if ((keyStates & UP_PRESSED) != 0) {
            model.userInput(BoardModel.USERINPUT_UP);
        } else if ((keyStates & DOWN_PRESSED) != 0) {
            model.userInput(BoardModel.USERINPUT_DOWN);
        } 

        getKeyStates();
    }
    
    /**
     * Listen for board events.
     *
     * Interested in GAME_WON event to display game won screen and GAME_LOST
     * event to display main menu
     */
    public void boardEvent(int event, BoardModel model) {
        switch (event) {
            case BoardEvent.GAME_WON:
                saveHighScore();
                try {
                    screenChooser.displayScreen(ScreenChooser.SCREEN_WON);
                } catch (Exception e) {
                    System.err.println("Error changing screen: " + e);
                }
                break;
                
            case BoardEvent.GAME_LOST:
                // update high score if needed
                saveHighScore();
                try {
                    screenChooser.displayScreen(ScreenChooser.SCREEN_MAIN_MENU);
                } catch (Exception e) {
                    System.err.println("Error changing screen: " + e);
                }
                break;
        }
    }

    /**
     * returns true if the game is paused, false if not
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * Set the paused / unpaused status of the game
     *
     * @param boolean paused
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
