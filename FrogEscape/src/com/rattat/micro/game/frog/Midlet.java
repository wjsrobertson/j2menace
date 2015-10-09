/**
 * Copyright 2006 William Robertson (www.rattat.com)
 */

package com.rattat.micro.game.frog;

import java.io.IOException;
import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.*;

import com.rattat.micro.game.frog.*;
import com.rattat.micro.game.frog.sound.SoundManager;
import com.rattat.micro.ui.vibrator.Vibrator;
import com.rattat.micro.db.SimpleStore;
import com.rattat.micro.ui.selectslider.*;

/**
 * @author william@rattat.com
 */
public class Midlet extends MIDlet 
                           implements CommandListener, SelectListener {
    
    /**
     * Display object associated with this midlet
     */
    private Display display = null;
    
    /**
     * The actual game canvas displayed whilst in play
     */
    private FroggerGameCanvas gameCanvas;
    
    /**
     * The main menu - displays options to start game, turn off sound etc
     */
    private MenuCanvas menuCanvas;
    
    /**
     * Used to persist / retreive sound / vibrate options
     */
    private SimpleStore dataStore;
    
    /**
     * The object used to play sounds - in this case the theme music
     */
    private SoundManager soundManager;
    
    /**
     * Used to switch between different screens - the game screen, menu screeen
     * etc.
     */
    private ScreenChooser screenChooser;
    
    /**
     * The canvas that displays a well done message if the user wins the game
     */
    private WonCanvas wonCanvas;
    
    /**
     * An alert that tells the user a little about this game
     */
    private Alert aboutAlert;
    
    /**
     * Pause game command - sent by the device if the user wants to 
     * pause the game
     */
    Command pause = new Command("Pause", Command.SCREEN, 0);
    
    /**
     * Creates a new instance of FroggerMIDLet
     */
    public Midlet () {
        init();
    }
    
    /**
     * Initialise the game, ready for the user to select something 
     * from the game menu
     */
    private void init() {
        try {
            dataStore = new SimpleStore("config");           
        } catch (Exception e) {
            System.err.println("Error creating data store: " + e);
        }

        display = Display.getDisplay(this); // keep display handy
                
        screenChooser = new ScreenChooser(this);
        
        // initialise the Vibrator, ready to be used by any other objects
        Vibrator.setDisplay(display);   
        
        // create the game won canvas
        wonCanvas = new WonCanvas(screenChooser);

        // create the FroggerGameCanvas for displaying the game
        gameCanvas = new FroggerGameCanvas(dataStore, screenChooser);
        gameCanvas.addCommand(pause);
        gameCanvas.setCommandListener(this);
        
        menuCanvas = new MenuCanvas(dataStore);
        menuCanvas.getSelectSlider().addSelectListener(this);
   
        soundManager = SoundManager.getInstance();
        
        initConfig();
        
        soundManager.play(SoundManager.SOUND_MENU, -1);
        
        // set the frogger game canvas to be the current Displayable
        display.setCurrent(menuCanvas);
    }
    
    /**
     * Initialise the game according to the user's preferences
     */
    public void initConfig() {       
        // turn sound either on or off depending on config
        if ( dataStore.get("sound")!=null && dataStore.get("sound").equals("N") ) {
            soundManager.off();
        } else {
            soundManager.on();
        }
        
        // turn vibrations either on or off depending on config
        if ( dataStore.get("vibrate")!=null && dataStore.get("vibrate").equals("N") ) {
            Vibrator.off ();
        } else {
            Vibrator.on();
        }
    }
    
    /**
     * Display the main menu, with extra continue option
     */
    public void displayPause() {
        menuCanvas.setPaused(true);
        gameCanvas.setPaused(true);
        display.setCurrent(menuCanvas);
    }
    
    /**
     * Display the main menu
     */
    public void displayMainMenu() {
        menuCanvas.setPaused(false);
        gameCanvas.setPaused(true);
        display.setCurrent(menuCanvas);
    }
    
    /**
     * Display the game board ready for play
     */
    public void displayBoard() {
        soundManager.stop(SoundManager.SOUND_MENU);
        menuCanvas.setPaused(false);
        gameCanvas.setPaused(false);
        display.setCurrent(gameCanvas);
    }
    
    /**
     * Display the well done page - used when user wins game
     */
    public void displayWon() {
        gameCanvas.setPaused(true);
        display.setCurrent(wonCanvas);
    }
    
    /**
     * Display the about alert
     */
    public void displayAbout() {
        gameCanvas.setPaused(true);
        display.setCurrent(getAboutAlert()); 
        
    }
    
    //
    // MIDlet methods
    //
    
    /**
     * midlet lifecycle method - called when the midelt starts
     */
    public void startApp() {      
        gameCanvas.start();
    }
    
    /**
     * midlet lifecycle method - called when the midelt is paused
     */
    public void pauseApp() {
        if (gameCanvas != null) {
            gameCanvas.setPaused(true);
        }
    }
    
    /**
     * midlet lifecycle method - called when the midelt is destroyed
     *
     * @param boolean unconditional
     */
    public void destroyApp(boolean unconditional) {
        if (gameCanvas != null) {
            // stop the game thread
            gameCanvas.stop();
            
            // stop sounds from playing
            soundManager.stop();
        }
    }

    /**
     * CommandListener interface method - called when the device sends 
     * a command to the midlet
     *
     * @param Command c
     * @param Displayable d
     */
    public void commandAction(Command c, Displayable d) {
        if (c == pause) {
            soundManager.play(SoundManager.SOUND_MENU, -1);
            gameCanvas.setPaused(true);
            displayPause();
        }
    }
    
    /**
     * SelectListener interface method
     *
     * Called when an option is selected in the main menu
     *
     * @param Option o
     */
    public void optionSelected(Option o) {
        if (o.getText() == "about") {
            displayAbout();
        } else if (o.getText() == "vibrate is on") {
            Vibrator.off();
        } else if (o.getText() == "vibrate is off") {
             Vibrator.on();
        } else if (o.getText() == "sound is on") {
            soundManager.off();
        } else if (o.getText() == "sound is off") {
            soundManager.on();
            soundManager.play(SoundManager.SOUND_MENU, -1);
        } else if (o.getText() == "new game") {
            gameCanvas.finishGame();
            gameCanvas.reset();
            gameCanvas.start();
            displayBoard();
        } else if (o.getText() == "continue game") {
            displayBoard();
        } else if (o.getText() == "exit") {
            gameCanvas.finishGame();
            destroyApp(true);
            notifyDestroyed();
        }
    }
    
    /**
     * Get / create the about alert screen
     *
     * @return Alert
     */
    private Alert getAboutAlert() { 
        if (aboutAlert == null) {
            aboutAlert = new Alert("Frogger clone", 
                                   "Based on Frogger - the classic Konami arcade game from 1981\n\n" +
                                   "Programming & music by William Robertson\n\n" +
                                   "Graphics based on original arcade version\n\n" +
                                   "www.rattat.com\n\n"
                                   , null, null);
            aboutAlert.setTimeout(Alert.FOREVER);
            aboutAlert.setType(AlertType.INFO);
        }
        
        return aboutAlert;
    }
}
