/**
 * Copyright 2006 William Robertson (www.rattat.com)
 */

package com.rattat.micro.game.frog.board;

import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.Image;

import com.rattat.micro.game.frog.board.elements.*;

import javax.microedition.lcdui.game.TiledLayer;

/**
 * Holds everything needed to actually play the game. Instances of
 * channels (roads, river lanes), the status screen, the frog moved by the user
 * etc.
 *
 * @todo Stop calling this "model", remove the view class and move the paint()
 *       method in here
 *
 * @author william@rattat.com
 */
public class BoardModel implements HomeListener {
    
    /**
     * User attempts to move left
     */
    public final static int USERINPUT_LEFT  = 1;
    
    /**
     * User attempts to move right
     */
    public final static int USERINPUT_RIGHT = 2;
    
    /**
     * User attempts to move up
     */
    public final static int USERINPUT_UP    = 3;
    
    /**
     * User attempts to move down
     */
    public final static int USERINPUT_DOWN  = 4;
    
    /**
     * The current level that the player has reached
     */
    private int currentLevel = 1;
    
    /**
     * The number of levels in this game in total
     */
    private int numLevels = 5;
    
    /**
     * width / height of one cell in the game in pixels (board is approx 15 * 15 cells)
     */
    public static final int CELL_SIZE = 16;
    
    /**
     * Number of cells wide of the board
     */
    private int NUM_CELLS_WIDE = 15;
    
    /**
     * Number of cells high of the board
     */
    private int NUM_CELLS_HIGH = 16;
    
    /**
     * Width of the board in pixels
     */
    private int width = CELL_SIZE * NUM_CELLS_WIDE;
    
    /**
     * Height of the board in pixels
     */
    private int height = CELL_SIZE * NUM_CELLS_HIGH;
    
    /**
     * path to image file used in the frog sprite
     */
    private String frogImageFile = "/frog.png";
    
    /**
     * The player's frog sprite that gets moved around the screen
     */
    private Sprite frog = null;
    
    /**
     * Path to image file used to display a dying frog
     */
    private String deadFrogImageFile = "/deadfrog.png";
    
    /**
     * Sprite used to display a dying frog
     */
    private Sprite deadFrog = null;
    
    /**
     * Is the frog in play?
     */
    private boolean frogActive = false;
    
    /**
     * Is the dead frog being displayed
     */
    private boolean deadFrogActive = false;
    
    /**
     * How long the dead frog has been displayed for
     */
    private int deadFrogTimer = 0;
    
    /**
     * How long the dead frog will be shown for
     */
    private int deadFrogLonjevity = 10;
    
    /**
     * Path to the file containing the border images
     */
    private static String tilesImageFile = "/tiles.png";
    
    /**
     * index of the border image in the tilesImageFile
     */
    private int borderImageTile = 1;
    
    /**
     * Number of lives the player will be given when starting the game
     */
    private final static int NUM_LIVES_START = 3;
    
    /**
     * Number of lives the player currently has
     */
    private int numLives = NUM_LIVES_START;
    
    /**
     * Length of time the frog has been moving for
     *
     * Used when performing frog movement
     */
    private int frogMoveCounter = 0;
    
    /**
     * Current frog movement: FROG_NULL, FROG_UP etc
     */
    private int frogMovement = 0;
    
    /**
     * No frog movement
     */
    public static final int FROG_NULL = 0;
    
    /**
     * Frog moving up
     */
    public static final int FROG_UP = 1;
    
    /**
     * Frog moving down
     */
    public static final int FROG_DOWN = 2;
    
    /**
     * Frog moving left
     */
    public static final int FROG_LEFT = 3;
    
    /**
     * Frog moving right
     */
    public static final int FROG_RIGHT = 4;
    
    /**
     * How far to move in the Y direction for every tick that the frog is moving
     */
    public static final int FROG_VERTICAL_INCREMENT = 2 + CELL_SIZE / 2;
    
    /**
     * How far to move in the X direction for every tick that the frog is moving
     */
    public static final int FROG_HORIZONTAL_INCREMENT = 2 + CELL_SIZE / 2;
    
    /**
     * How far to move the frog in total in the Y direction
     */
    public static final int FROG_VERTICAL_TOTAL = CELL_SIZE;
    
    /**
     * How far to move the frog in total in the X direction
     */
    public static final int FROG_HORIZONTAL_TOTAL = CELL_SIZE;
    
    /**
     * Used to generate different channels for each level
     */
    private ChannelGenerator channelGenerator = new ChannelGenerator ();
    
    /**
     * Component for displaying the player's status (lives, level, time)
     */
    private StatusLayer statusLayer = null;
    
    /**
     * Component for dipalying the player's score
     */
    private ScoreLayer scoreLayer = null;
    
    /**
     * Component that represnts the "home" spaces the player trys to get the
     * frog into
     */
    private Home homeLayer = null;
    
    /**
     * Channel components of the current level
     */
    private Channel[] channels = new Channel[10];
    
    /**
     * The two border layer omponents
     */
    private TiledLayer[] borderLayers = new TiledLayer[2];
    
    /**
     * The player's current score
     */
    private int score = 0;
    
    /**
     * points awarded for each successful jump
     */
    private int POINTS_JUMP = 20;
    
    /**
     * points awarded for each frog that gets to the home position
     */
    private int POINTS_FROG_HOME = 50;
    
    /**
     * points awarded for each completed level
     */
    private int POINTS_LEVEL = 1000;
    
    /**
     * points awarded for each second remaining when a frog gets home
     *
     * @todo unused
     */
    private int POINTS_SECOND_REMAINING = 10;
    
    /**
     * points awarded for getting a lady frog home
     *
     * @todo unused
     */
    private int POINTS_LADY_FROG_HOME = 200;
    
    /**
     * points awarded for eating an insect
     *
     * @todo unused
     */
    private int POINTS_EAT_INSECT = 200;
    
    /**
     * The highest point on the screen that the frog has reached
     *
     * This is used in scoring - for every successful jump forwards the
     * player recieves 20 points. We keep track of the highest position reached
     * so that if a successful jump passes this point we can award points.
     */
    private int uppermostYOrdinate = 0;
    
    /**
     * The identifiers for each of the channels - just the integer
     * representation of the channel that is used in array indexes
     */
    private int[] channelIdentifiers = {
        ChannelGenerator.CHANNEL_RIVER_0, ChannelGenerator.CHANNEL_RIVER_1,
        ChannelGenerator.CHANNEL_RIVER_2, ChannelGenerator.CHANNEL_RIVER_3,
        ChannelGenerator.CHANNEL_RIVER_4, ChannelGenerator.CHANNEL_ROAD_0,
        ChannelGenerator.CHANNEL_ROAD_1,  ChannelGenerator.CHANNEL_ROAD_2,
        ChannelGenerator.CHANNEL_ROAD_3,  ChannelGenerator.CHANNEL_ROAD_4
    };
    
    /**
     * Holds the Y offsets of the ten channels
     */
    private int[] channelOffsetY = new int[10];
    
    /**
     * Holds BoardListeners that will be notified of BoardEvents
     */
    private Vector listeners = new Vector ();
    
    /**
     * The number of game ticks that the player has to complete a level
     */
    private static final int LEVEL_TIME = 600;
    
    /**
     * Creates a new instance of BoardModel
     */
    public BoardModel () {
        init ();
    }
    
    /**
     * Get the width of this board in pixels
     */
    public int getWidth () {
        return width;
    }
    
    /**
     * Get the height of this board in pixels
     */
    public int getHeight () {
        return height;
    }
    
    /**
     * Initialise all of the components that make up the board
     */
    private void init () {
        try {
            createFrog ();
        } catch (Exception e) {
            System.err.println ("Error creating frog: " + e);
        }
        
        try {
            createDeadFrog ();
        } catch (Exception e) {
            System.err.println ("Error creating frog: " + e);
        }
        
        createStatusLayer ();
        createScoreLayer ();
        createHomeLayer ();
        
        initChannelOffsetY ();
        createChannels ();

        createBorderLayers ();
    }
    
    /**
     * Determine the Y offsets for each of the Channels
     */
    private void initChannelOffsetY () {
        int channelsOffset = scoreLayer.getHeight () + homeLayer.getHeight ();
        
        // loop better? nah
        
        channelOffsetY[ChannelGenerator.CHANNEL_RIVER_0] = channelsOffset;
        channelOffsetY[ChannelGenerator.CHANNEL_RIVER_1] = channelsOffset + CELL_SIZE;
        channelOffsetY[ChannelGenerator.CHANNEL_RIVER_2] = channelsOffset + 2*CELL_SIZE;
        channelOffsetY[ChannelGenerator.CHANNEL_RIVER_3] = channelsOffset + 3*CELL_SIZE;
        channelOffsetY[ChannelGenerator.CHANNEL_RIVER_4] = channelsOffset + 4*CELL_SIZE;
        
        channelOffsetY[ChannelGenerator.CHANNEL_ROAD_0] = channelsOffset + 6*CELL_SIZE;
        channelOffsetY[ChannelGenerator.CHANNEL_ROAD_1] = channelsOffset + 7*CELL_SIZE;
        channelOffsetY[ChannelGenerator.CHANNEL_ROAD_2] = channelsOffset + 8*CELL_SIZE;
        channelOffsetY[ChannelGenerator.CHANNEL_ROAD_3] = channelsOffset + 9*CELL_SIZE;
        channelOffsetY[ChannelGenerator.CHANNEL_ROAD_4] = channelsOffset + 10*CELL_SIZE;
    }
    
    /**
     * Get / create the frog Sprite
     *
     * @return Sprite
     */
    private Sprite createFrog () throws Exception {
        if (frog == null) {
            Image image = Image.createImage (frogImageFile);
            frog = new Sprite (image);
            frog.defineReferencePixel (frog.getWidth ()/2, frog.getHeight ()/2);
        }
        
        return frog;
    }
    
    /**
     * Get the frog sprite - the one the user moves around the screen
     */
    public Sprite getFrog () {
        return frog;
    }
    
    /**
     * Get the dead frog sprite
     */
    public Sprite getDeadFrog () {
        return deadFrog;
    }
    
    /**
     * Create the dead frog sprite
     */
    private Sprite createDeadFrog () throws Exception {
        if (deadFrog == null) {
            Image image = Image.createImage (deadFrogImageFile);
            deadFrog = new Sprite (image, image.getHeight (), image.getHeight ()); // yes, height
            deadFrog.setVisible (false);
        }
        
        return deadFrog;
    }
    
    /**
     * Create & initialise the status layer
     */
    private StatusLayer createStatusLayer () {
        if (statusLayer == null) {
            statusLayer = new StatusLayer ();
            statusLayer.setX (0);
            statusLayer.setY ( height -  statusLayer.getHeight () );
            statusLayer.setWidth (width);
            statusLayer.setMaxTime (LEVEL_TIME);
        }
        
        return statusLayer;
    }
    
    /**
     * Get the game's high score
     */
    public int getHighScore () {
        return scoreLayer.getHighScore ();
    }
    
    /**
     * Set the game's high score
     */
    public void setHighScore (int highScore) {
        scoreLayer.setHighScore (highScore);
    }
    
    /**
     * Initialise the frog position ready for play. Also sets the frog to visible
     * and sets it to have null transform
     */
    private void initFrogPosition () {
        haltFrog ();
        frog.setPosition ( (width - frog.getWidth ()) / 2, 1 + 14*CELL_SIZE + (CELL_SIZE-frog.getWidth ())/2 );
        frog.setVisible (true);
        frog.setTransform (Sprite.TRANS_NONE);
        uppermostYOrdinate = frog.getY ();
    }
    
    /**
     * Get the status layer
     *
     * @return StatusLayer 
     */
    public StatusLayer getStatusLayer () {
        return statusLayer;
    }
    
    /**
     * Create the score layer - the component used to display player score and high score
     *
     * @return ScoreLayer 
     */
    private ScoreLayer createScoreLayer () {
        if (scoreLayer == null) {
            scoreLayer = new ScoreLayer ();
            scoreLayer.setX (0);
            scoreLayer.setY (0);
        }
        
        return scoreLayer;
    }
    
    /**
     * Get the score layer - the component used to display player score and high score
     *
     * @return ScoreLayer 
     */
    public ScoreLayer getScoreLayer () {
        return scoreLayer;
    }
    
    /**
     * Create the home layer - the layer that the player aims to get the frog into
     *
     * @return Home
     */
    public Home createHomeLayer () {
        if (homeLayer == null) {
            homeLayer = new Home();
            homeLayer.setX (0);
            homeLayer.setY ( getScoreLayer ().getHeight () );
            homeLayer.setWidth (width);
            homeLayer.setSprite (frog);
            homeLayer.addListener (this);
        }
        
        return homeLayer;
    }
    
    /**
     * Get the home layer - the layer that the player aims to get the frog into
     *
     * @return Home
     */
    public Home getHomeLayer () {
        return homeLayer;
    }
    
    /**
     * Create the channels - the road and river components
     */
    private void createChannels () {
        for (int i=0 ; i<channelIdentifiers.length ; i++) {
            try {
                channels[ channelIdentifiers[i] ] = channelGenerator.createChannel (channelIdentifiers[i], currentLevel);
                channels[ channelIdentifiers[i] ].setPosition ( (int) (-CELL_SIZE * 1.5) , channelOffsetY[ channelIdentifiers[i] ]);
            } catch (Exception e) {
                System.err.println ("Error creating channel: " + e);
            }
        }
    }

    /**
     * Get a specific channel - road or river
     *
     * @int channel The channel identifier
     */
    public Channel getChannel (int channel) throws Exception {
        return channels[channel];
    }
    
    /**
     * Get the array containing all channels
     *
     * @return Channel[]
     */
    public Channel[] getChannels () {
        return channels;
    }

    /**
     * Create the border layers - the pavement parts of the board
     */
    private void createBorderLayers () {
        try {
            Image tilesImage = Image.createImage (tilesImageFile);
            
            borderLayers[0] = new TiledLayer (16, 1, tilesImage, CELL_SIZE, CELL_SIZE);
            for (int i=0 ; i<NUM_CELLS_WIDE+1 ; i++) {
                borderLayers[0].setCell (i, 0, borderImageTile);
            }
            borderLayers[0].setPosition ( -CELL_SIZE/2, 8*CELL_SIZE );
            
            borderLayers[1] = new TiledLayer (16, 1, tilesImage, CELL_SIZE, CELL_SIZE);
            for (int i=0 ; i<NUM_CELLS_WIDE+1 ; i++) {
                borderLayers[1].setCell (i, 0, borderImageTile);
            }
            borderLayers[1].setPosition ( -CELL_SIZE/2, 14*CELL_SIZE );
            
        } catch (Exception e) {
            System.out.println ("Error loading tile image: " + e);
        }
    }
    
    /**
     * Get the border layers - the pavement parts of the board
     *
     * @return TiledLayer[]
     */
    public TiledLayer[] getBorderLayers () {
        return borderLayers;
    }
    
    /**
     * Get the score of the current game
     *
     * @return int
     */
    private int getScore () {
        return score;
    }
    
    /**
     * Iniatalise the board for a new game
     */
    public void newGame () {
        score = 0;
        numLives = NUM_LIVES_START;
        homeLayer.reset ();
        initLevel (1);
        notifyListeners (BoardEvent.GAME_START);
    }
    
    /**
     * Setup the board for beginning a new level
     *
     * @param int level
     */
    public void initLevel (int level) {
        if (level > numLevels) {
            notifyListeners (BoardEvent.GAME_WON);
            return;
        }
        
        currentLevel = level;
        initFrogPosition ();
        frogActive = true;
        deadFrog.setVisible (false);
        homeLayer.reset ();
        statusLayer.setLives (numLives);
        statusLayer.setTime (LEVEL_TIME);
        createChannels ();
        notifyListeners (BoardEvent.LEVEL_START);
    }
    
    /**
     * Update the board by one unit of time:
     *
     * Handle frog movement
     *
     * Update home layer, channels and status / score layers
     *
     * Check if the frog is dying
     *
     * Finish the game is the player is dead and the dead frog aninmation 
     * is complete
     */
    public void tick () {
        homeLayer.tick ();
        
        updateFrogPositionUser ();
        updateDeadFrog ();
        
        for (int i=0 ; i<channels.length ; i++) {
            channels[i].update ();
            
            if (channels[i].isLanding (frog)) {
                if ( channels[i].cellType ( channels[i].landingCell (frog) ) == Channel.TYPE_MOVING ) {
                    frog.move ( channels[i].getVelocity (), 0 );
                }
            }
            
            if (channels[i].isColliding (frog)) {
                if ( channels[i].isDeadlyCollision (frog) ) {
                    System.out.println ( "Channel Frog Death" );
                    frogDeath ();
                }
            }
        }
        
        if (frogActive && (frog.getX () < 0 || frog.getX () + frog.getWidth () > getWidth () ) ) {
            frogDeath ();
        }
        
        if ( statusLayer.getTime () == 0 ) {
            frogDeath ();
        }
        
        if (frogActive) {   // don't increment timer when frog inactive
            statusLayer.tick ();
        }
        
        // check if game is over
        
        if (numLives == 0 && deadFrogTimer > deadFrogLonjevity) {
            finishGame ();
        }
    }
    
    /**
     * Handle loosing a life
     *
     * Hide the frog, repoisition him at the foot of the screen, deactivate him
     * and mark the dead frog sprite as active
     */
    public void frogDeath () {
        startDeadFrog();
        initFrogPosition();
        looseLife();
        frog.setVisible (false);
        deadFrogActive = true;
        frogActive = false;
    }
    
    /**
     * 
     */
    private void updateDeadFrog () {
        if (deadFrogActive) {
            double frameRatio = deadFrogLonjevity / deadFrog.getFrameSequenceLength ();
            
            int frame = (int) Math.floor ( deadFrogTimer / frameRatio );
            frame = Math.min ( deadFrog.getFrameSequenceLength ()-1, frame );
            
            if ( deadFrogTimer >= deadFrogLonjevity ) {
                deadFrogActive = false;
                deadFrog.setVisible (false);
                frog.setVisible (true);
                frogActive = true;
            } else {
                deadFrog.setFrame (frame);
            }
            
            deadFrogTimer++;
        }
    }
    
    private void startDeadFrog () {
        int offsetX = (deadFrog.getWidth () - frog.getWidth ()) / 2; // offset from the frog position, not origin
        int offsetY = (deadFrog.getHeight () - frog.getHeight ()) / 2; // offset from the frog position, not origin
        
        deadFrog.setPosition ( frog.getX () - offsetX, frog.getY () - offsetY );
        deadFrog.setVisible (true);
        deadFrogActive = true;
        deadFrogTimer = 0;
    }
    
    public void looseLife () {
        numLives--;
        statusLayer.setLives (numLives);
        notifyListeners (BoardEvent.FROG_DEATH);
        
        if (numLives > 0) {
            resetTime ();
        }
    }
    
    private void finishGame () {
        notifyListeners (BoardEvent.GAME_LOST);
    }
    
    private void resetTime () {
        statusLayer.setTime (LEVEL_TIME);
    }
    
    /**
     * Stop the frog from moving
     */
    private void haltFrog () {
        frogMoveCounter = 0;
        frogMovement = FROG_NULL;
    }
    
    /**
     * Move the frog
     *
     * @param int dx
     * @param int dy
     */
    public void moveFrog (int dx, int dy) {
        notifyListeners (BoardEvent.FROG_MOVE);
        frog.move (dx, dy);
    }
    
    /**
     * Set the direction the frog is moving in
     *
     * USERINPUT_LEFT, USERINPUT_RIGHT, USERINPUT_UP, USERINPUT_DOWN
     *
     * @param int frogMovement
     */
    public void setFrogMovement (int frogMovement) {
        this.frogMovement = frogMovement;
    }
    
    /**
     * Get the direction that the frog is moving 
     *
     * @return int
     */
    public int getFrogMovement () {
        return frogMovement;
    }
    
    /**
     * Check if the frog is moving
     *
     * @return boolean
     */
    public boolean isFrogMoving () {
        return frogMovement != FROG_NULL;
    }
    
    /**
     * Add a value to the frog's movement
     * 
     * This is used when moving the frog after the user has started
     * the frog moving
     *
     * @param int
     */
    public void addFrogMoveCounter (int add) {
        frogMoveCounter += add;
    }
    
    /**
     * Get how far the frog has moved in the current move
     * 
     * @return int
     */
    public int getFrogMoveCounter () {
        return frogMoveCounter;
    }
    
    /**
     * Set how far the frog has moved in the current move
     * 
     * @return int
     */
    public void setFrogMoveCounter (int frogMoveCounter) {
        this.frogMoveCounter = frogMoveCounter;
    }
    
    /**
     * Rotate the frog - used when moving left or right to make the frog
     * face the right direction
     *
     * @param int rotation
     */
    public void rotateFrog (int rotation) {
        frog.setTransform ( rotation );
    }
    
    /**
     * Add points to the current score
     *
     * @param int add
     */
    private void addScore (int add) {
        score += add;
        scoreLayer.setScore (score);
        if ( score > scoreLayer.getHighScore () ) {
            scoreLayer.setHighScore (score);
        }
    }
    
    /**
     * @param int event
     * @param Home home
     */
    public void homeEvent (int event, Home home) {
        switch (event) {
            case HomeEvent.FROG_DEATH:
                if (frogActive) {
                    frogDeath ();
                }
                break;
                
            case HomeEvent.FROG_HOME:
                initFrogPosition ();
                resetTime ();
                notifyListeners (BoardEvent.FROG_HOME);
                
                addScore (POINTS_FROG_HOME);
                
                if ( home.isComplete () ) {
                    addScore (POINTS_LEVEL);
                    notifyListeners (BoardEvent.LEVEL_COMPLETE);
                    initLevel ( currentLevel+1 );
                }
                break;
                
            case HomeEvent.INSECT_EATEN:
                addScore (POINTS_EAT_INSECT);
                break;
        }
    }
    
    /**
     * Remove a listener. The listener will no longer be notified of Board events
     *
     * @param BoardListener listener
     */
    public void removeListener (BoardListener listener) {
        listeners.removeElement (listener);
    }
    
    /**
     * Add a listener. The listener will be notified of Board events
     *
     * @param BoardListener listener
     */
    public void addListener (BoardListener listener) {
        listeners.addElement (listener);
    }
    
    /**
     * Notify listeners of an event
     *
     * @param int event
     */
    private void notifyListeners (int event) {
        Enumeration enumeration = listeners.elements ();
        while (enumeration.hasMoreElements ()) {
            BoardListener listener = (BoardListener) enumeration.nextElement ();
            listener.boardEvent (event, this);
        }
    }
    
    /**
     * Process user input
     *
     * @param int inputType
     */
    public void userInput (int inputType) {
        if (getFrog () != null && frogActive) {
            switch(inputType) {
                case USERINPUT_LEFT:
                    if (! isFrogMoving ()) {
                        frog.setTransform (Sprite.TRANS_ROT270);
                        setFrogMovement (FROG_LEFT);
                    }
                    break;
                    
                case USERINPUT_RIGHT:
                    if (! isFrogMoving ()) {
                        frog.setTransform (Sprite.TRANS_ROT90);
                        setFrogMovement (FROG_RIGHT);
                    }
                    break;
                    
                case USERINPUT_UP:
                    if (! isFrogMoving ()) {
                        frog.setTransform (Sprite.TRANS_NONE);
                        setFrogMovement (FROG_UP);
                    }
                    break;
                    
                case USERINPUT_DOWN:
                    if (! isFrogMoving ()) {
                        if ( frog.getY () + 1 < 14*CELL_SIZE + (CELL_SIZE-frog.getWidth ())/2 ) {
                            frog.setTransform (Sprite.TRANS_ROT180);
                            setFrogMovement (FROG_DOWN);
                        }
                    }
                    break;
            }
        }
    }
    
    
    /**
     * update the position of the frog if the user is moving it
     */
    private void updateFrogPositionUser () {
        if ( isFrogMoving () ) {
            
            int horizontalIncrement = 0;
            int verticalIncrement   = 0;
            int potentialIncrement  = 0;
            int dx = 0;
            int dy = 0;
            
            switch (getFrogMovement ()) {
                case BoardModel.FROG_UP:
                    
                    verticalIncrement = BoardModel.FROG_VERTICAL_INCREMENT;
                    potentialIncrement = getFrogMoveCounter () + verticalIncrement;
                    if ( potentialIncrement >= BoardModel.FROG_VERTICAL_TOTAL) {
                        verticalIncrement = BoardModel.FROG_VERTICAL_TOTAL - getFrogMoveCounter ();
                    }
                    
                    moveFrog (0, -verticalIncrement);
                    addFrogMoveCounter (verticalIncrement);
                    
                    break;
                    
                case BoardModel.FROG_DOWN:
                    
                    verticalIncrement = BoardModel.FROG_VERTICAL_INCREMENT;
                    potentialIncrement = getFrogMoveCounter () + verticalIncrement;
                    if ( potentialIncrement >= BoardModel.FROG_VERTICAL_TOTAL) {
                        verticalIncrement = BoardModel.FROG_VERTICAL_TOTAL - getFrogMoveCounter ();
                    }
                    
                    moveFrog (0, verticalIncrement);
                    addFrogMoveCounter (verticalIncrement);
                    
                    break;
                    
                case BoardModel.FROG_LEFT:
                    
                    horizontalIncrement = BoardModel.FROG_HORIZONTAL_INCREMENT;
                    potentialIncrement = getFrogMoveCounter () + horizontalIncrement;
                    if ( potentialIncrement >= BoardModel.FROG_HORIZONTAL_TOTAL) {
                        horizontalIncrement = BoardModel.FROG_HORIZONTAL_TOTAL - getFrogMoveCounter ();
                    }
                    
                    moveFrog (-horizontalIncrement, 0);
                    addFrogMoveCounter (horizontalIncrement);
                    
                    break;
                    
                case BoardModel.FROG_RIGHT:
                    
                    horizontalIncrement = BoardModel.FROG_HORIZONTAL_INCREMENT;
                    potentialIncrement = getFrogMoveCounter () + horizontalIncrement;
                    if ( potentialIncrement >= BoardModel.FROG_HORIZONTAL_TOTAL) {
                        horizontalIncrement = BoardModel.FROG_HORIZONTAL_TOTAL - getFrogMoveCounter ();
                    }
                    
                    moveFrog (horizontalIncrement, 0);
                    addFrogMoveCounter (horizontalIncrement);
                    
                    break;
            }
            
            if (verticalIncrement <= 0 && horizontalIncrement <= 0) {
                if ( getFrogMovement () == BoardModel.FROG_UP && frog.getY () < uppermostYOrdinate ) {
                    uppermostYOrdinate = frog.getY ();
                    addScore (POINTS_JUMP);
                }
                setFrogMoveCounter (0);
                setFrogMovement (BoardModel.FROG_NULL);
            }
        }
    }
    
    //public void paint(Graphics g) {
    
    //}
    
}
