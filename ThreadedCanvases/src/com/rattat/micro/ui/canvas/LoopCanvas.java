/**
 * @author william@rattat.com
 */

package com.rattat.micro.ui.canvas;

import javax.microedition.lcdui.*;

/**
 * Threaded class for creating a game loop based on Canvas class
 *
 * @author william@rattat.com
 */
public abstract class LoopCanvas extends Canvas implements Runnable {
    
    private boolean paused = false;
    
    /**
     * Thread used for game loop.
     */
    private Thread gameThread = null;
    
    /**
     * The maximum length of time to wait between game ticks in miliseconds
     *
     * 40 by default (25 ticks per second)
     */
    private int loopMaxWait = 40;
    
    /**
     * Flag to keep track of whether game thread is running or not
     *
     * Setting this to fale during execution will cause the run method to return
     * and the game thread to finish
     */
    private boolean gameThreadRunning = false;
    
    /** 
     * Creates a new instance of TetrisGameCanvas 
     */
    public LoopCanvas () {
        //super(true);
    }

    /**
     * Called from the game loop - called once every loopMaxWait miliseconds
     *
     * Subclasses should utilise this method for game logic
     */
    public abstract void tick();

    /**
     * Determine if the game is paused
     *
     * @return 
     */
    public boolean isPaused () {
        return paused;
    }

    /**
     * Set whether or not the game is paused
     *  
     * @param paused
     */
    public void setPaused (boolean paused) {
        this.paused = paused;
    }
    
    /**
     * Start a new game - kick off the thread
     */
    public void start() {
        setGameThreadRunning(true);
        
        if (gameThread == null) {
            gameThread = new Thread(this);
            gameThread.setPriority(Thread.MAX_PRIORITY);
            gameThread.start();
        }
    }
    
    /**
     * Stop the game thread - let the thread end
     */
    public void stop() {
        setGameThreadRunning(false); 
    }
    
    /**
     * The main thread run method containing the game loop
     */ 
    public void run()  {
        //Graphics g = getGraphics(); // graphics object for rendering
                
        int  waitTime = 0;   // period to between updating the game
        long startTime;      // used to calcuate timeDifference
        long endTime;        // used to calcuate timeDifference
        int  timeDifference; // used to keep track of how fast/slow game is running

        // main game loop

        while (isGameThreadRunning()) {    
            startTime = System.currentTimeMillis();

            if ( ! isPaused() ) {
                tick();
                repaint();
            }

            // work out how long to pause the game for...
            
            endTime = System.currentTimeMillis();
            timeDifference = (int) (endTime - startTime);
            waitTime = getLoopMaxWait() - timeDifference;

            // pause for the calculated length of time

            if (waitTime > 0) {
                try {
                    Thread.sleep(waitTime); 
                    
                } catch (InterruptedException ie) { 
                     stop(); 
                }
            } else {
                System.err.println("Application running too slow");
                // application is running too slow for device ;-(
            }
        }
    }

    /**
     * Set the maximum length of time between ticks in the run method
     *
     * @return 
     */
    public int getLoopMaxWait () {
        return loopMaxWait;
    }

    /**
     * Set the maximum length of time between ticks in the run method
     *
     * @param loopMaxWait
     */
    public void setLoopMaxWait (int loopMaxWait) {
        this.loopMaxWait = loopMaxWait;
    }

    /**
     * Determine if the game thread is running
     */
    public boolean isGameThreadRunning() {
        return gameThreadRunning;
    }

    /**
     * Set whether or not the game thread is running
     *
     * Setting this to false will cause the game thread to die
     *
     * @param gameThreadRunning
     */
    private void setGameThreadRunning(boolean gameThreadRunning) {
        this.gameThreadRunning = gameThreadRunning;
    }
}
