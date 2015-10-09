/**
 * Copyright 2006 William Robertson (www.rattat.com)
 */

package com.rattat.micro.game.frog.board.elements;

import java.util.Vector;
import java.util.Enumeration;

import com.rattat.micro.graphics.Layer;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import java.util.Random;
import javax.microedition.lcdui.game.Sprite;

/**
 * The component of the frogger board that is at the top and has gaps
 * where the player trys to get a frog into.
 *
 * @author william@rattat.com
 */
public class Home extends Layer {
    
    /**
     * The number of free home cells
     */
    public final static int NUM_CELLS = 5;
    
    /**
     * Represents an empty cell
     */
    public static final int NULL = -1;
    
    /**
     * Represents an invalid cell
     */
    public static final int INVALID_CELL = -2;
        
    /**
     * Represents a cell with a landed frog
     */
    public static final int FROG_HOME = 0;
    
    /**
     * Represents a cell with a lurking aligator
     */
    public static final int ALIGATOR_1 = 1;
    
    /**
     * Represents a cell with a fully displayed aligator
     */
    public static final int ALIGATOR_2 = 2;
    
    /**
     * Represents a cell with an insect
     */
    public static final int INSECT = 3;
    
    /**
     * Path to the image used to render the background of the Home component
     */
    private String backgroundImageFile = "/__top.png";
    
    /**
     * Paths to each of the possible images in a home layer
     *
     * index = FROG_HOME, ALIGATOR_1, ALIGATOR_2, INSECT
     */
    private String cellImageFiles[] = {
        "/top_frog.png",
        "/top_aligator_1.png",
        "/top_aligator_2.png",
        "/top_insect.png"
    };
    
    /**
     * Image objects of each thing that can be displayed in a cell
     *
     * index = FROG_HOME, ALIGATOR_1, ALIGATOR_2, INSECT
     */
    private Image[] cellImages = new Image[4];

    /**
     * The Image of the background
     */
    private Image backgroundImage = null;
    
    /**
     * Width of one cell
     */
    private int cellWidth = 0;

    /**
     * Contents of each of the NUM_CELLS possible cells
     */
    private int[] cellContents = new int[NUM_CELLS];
    
    /**
     * Y offsets of the cells
     */
    private int  cellYOffset = 0;
    
    /**
     * X offsets of the cells
     */
    private int[] cellXOffsets = new int[NUM_CELLS];
    
    /**
     * Age of the currently displayed insect
     */
    private int insectTimer = 0;
    
    /**
     * How active is the insect (how likely is he to appear) 0-100
     */
    private int insectActivity = 80;
    
    /**
     * How many game ticks does teh insect live for
     */
    private int insectLongevity = 100;
    
    /**
     * Which cell is the insect currently in
     */
    private int insectCell = NULL;
        
    /**
     * Age of the currently displayed aligator
     */
    private int aligatorTimer = 0;
    
    /**
     * How active is the aligator (how likely is he to appear) 0-100
     */
    private int aligatorActivity = 90;
    
    /**
     * How many game ticks does the aligator live for
     */
    private int aligatorLongevity = 100;

    /**
     * Which cell is the insect currently in
     */
    private int aligatorCell = NULL;
    
    /**
     * How long the aligator will lurk for before he appears fully
     */
    private int aligatorLurkTime = 20;
    
    /**
     * Used when determining whether or not to display an aligator or insect
     */
    private Random random = new Random();
    
    /**
     * Sprite to monitor - watch for collisions, landing on a home cell etc.
     */
    private Sprite sprite = null;
    
    /**
     * A collection of objects who should be notified of HomeEvents
     */
    private Vector listeners = new Vector();
    
    /**
     * Creates a new instance of Home
     */
    public Home () {
        init();
    }
    
    /**
     * Initialise the home layer
     */
    private void init() {
        try {
            backgroundImage = Image.createImage(backgroundImageFile);
            setWidth( backgroundImage.getWidth() );
            setHeight( backgroundImage.getHeight() );
                                   
        } catch (Exception e) {
            System.err.println("Error loading image" + e);
        }
        
        initCellImages();
        calculateOffsets();
        reset();
    }

    /**
     * Initialise the images used when painting
     */
    private void initCellImages() {
        for (int i=0 ; i<cellImages.length ; i++) {
            try {
                cellImages[i] = Image.createImage(cellImageFiles[i]);
            } catch (Exception e) {
                System.err.println("Error loading image (" + cellImages[i]  + "): " + e);
            }
        }
    }
    
    /**
     * Reset the Home component to contain empty cells
     */
    public void reset() {
        insectCell = NULL;
        insectTimer = 0;
        
        aligatorCell = NULL;
        aligatorTimer = 0;
        
        for (int i=0 ; i<cellContents.length ; i++) {
            cellContents[i] = NULL;
        }
    }
    
    /**
     * Initialise the offsets for the positions of the "home" cells
     *
     * These are the cells which the player is aiming to get the frog into
     */
    private void calculateOffsets() {        
        cellWidth = cellImages[0].getWidth();

        cellYOffset = getY() + backgroundImage.getHeight() / 4;

        int minXOffset = getX() + backgroundImage.getHeight() / 4;
        int seperatorDistance = (int) (minXOffset / 3) * 26;

        for (int i=0 ; i<cellXOffsets.length ; i++) {
            cellXOffsets[i] = minXOffset + (i * seperatorDistance);
        }
    }
    
    /**
     * Paint the Home component onto a graphics object
     */
    public void paint(Graphics g) {
        calculateOffsets();
        
        g.drawImage(backgroundImage, getX(), getY(), Graphics.LEFT|Graphics.TOP);
        
        for (int i=0 ; i<cellContents.length ; i++) {
            if (cellContents[i] != NULL) {
                g.drawImage(cellImages[ cellContents[i] ], cellXOffsets[i], cellYOffset, Graphics.TOP|Graphics.LEFT);
            }
        }
    }
    
    /**
     * Check if all five slots have been filled with frog home's - i.e. the 
     * player has got a from in each cell.
     *
     * @return boolean
     */
    public boolean isComplete() {
        for (int i=0 ; i<cellContents.length ; i++) {
            if (cellContents[i] != FROG_HOME) {
                return false;
            }
        }

        return true;
    }
    
    /**
     * Randomly create aligators and insects as required
     *
     * Also, update aligators / insects
     *
     * Also, check for things happening to the player's frog
     */
    public void tick() {
        checkSprite();
        updateInsect();
        updateAligator();
    }
    
    /**
     * Randomly create an insect if there isn't one
     *
     * Hide the insect if he has been around for long enough (insectLongevity)
     */
    private void updateInsect() {
        if ( insectCell == NULL && 
             hasFreeCell() &&
             randomActionHappens(insectActivity) ) {
            
            insectCell = chooseRandomFreeCell();
            
            if (insectCell != NULL) {
                insectTimer = 0;
                cellContents[insectCell] = INSECT;
                notifyListeners(HomeEvent.INSECT_ARRIVE);
            }
        }
        
        if (insectTimer >= insectLongevity) {
            if (insectCell != NULL && cellContents[insectCell] == INSECT) {
                cellContents[insectCell] = NULL;
            }
            insectCell = NULL;
            insectTimer = 0;
            notifyListeners(HomeEvent.INSECT_LEAVE);
        }
        
        if (insectCell != NULL) {
            insectTimer++;
        }
    }
        
    /**
     * Update the aligator
     *
     * Randomly create an aligator if there isn't one
     *
     * Display lurking / fully displayed aligator according to 
     * how long the aligator has been displayed for (aligatorLurkTime)
     *
     * Hide aligator if he has been around for long enough (aligatorLongevity)
     */
    private void updateAligator() {
        
        // randomly create an alligator if one doesn't exist
        
        if ( aligatorCell == NULL && 
             hasFreeCell() &&
             randomActionHappens(aligatorActivity) ) {
            
            aligatorCell = chooseRandomFreeCell();
            
            if (aligatorCell != NULL) {
                aligatorTimer = 0;
                cellContents[aligatorCell] = ALIGATOR_1;
                notifyListeners(HomeEvent.ALIGATOR_ARRIVE);
            }
        }
        
        if (aligatorTimer > aligatorLurkTime) {
            cellContents[aligatorCell] = ALIGATOR_2;
        }
        
        if (aligatorTimer >= aligatorLongevity) {
            if (aligatorCell != NULL && cellContents[aligatorCell] == ALIGATOR_2) {
                cellContents[aligatorCell] = NULL;
            }
            aligatorCell = NULL;
            aligatorTimer = 0;
            notifyListeners(HomeEvent.ALIGATOR_LEAVE);
        }
        
        if (aligatorCell != NULL) {
            aligatorTimer++;
        }
    }

    /**
     * Randomly decide if an action is to happen or not
     *
     * @param int activity  Higher number means action is more likely to happen
     */
    private boolean randomActionHappens(int activity) {
        if (activity <= 0 || numFreeCells() == 0) {
            return false;
        }
        
        /*
         scale the activity down according to how many free cells there are
         otherwise an alligator can appear over andover in one remaining free
        */
        int scaledActivity = (int) (activity * ((double)numFreeCells() / NUM_CELLS));
        
        int activityFactor = 1 + 10 * (100 - Math.min(scaledActivity, 100));
        
        return random.nextInt( activityFactor ) >= activityFactor-1;
    }
       
    /**
     * Set the contents of a cell
     *
     * @param int cell
     * @parma int setWhat
     */
    private void setCellContents(int cell, int setWhat) {
        cellContents[cell] = setWhat;
    }
    
    /**
     * Get the contents od a particular cell
     *
     * @param int
     *  
     * @return int
     */
    private int getCellContents(int cell) {
        return cellContents[cell];
    }

    /**
     * Check if the Home instance has a free cell
     */
    private boolean hasFreeCell() {
        for (int i=0 ; i<cellContents.length ; i++) {
            if (cellContents[i] == NULL) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Get the number of free cells
     */
    private int numFreeCells() {
        int num = 0;
        
        for (int i=0 ; i<cellContents.length ; i++) {
            if (cellContents[i] == NULL) {
                num++;
            }
        }
        
        return num;
    }
    
    /**
     * Randomly choose a free cell (a cell without anything in it)
     *
     * @return int
     */
    private int chooseRandomFreeCell() {   
        int tmpCells[] = new int[cellContents.length];
        int numFound = 0;
        
        for (int i=0 ; i<cellContents.length ; i++) {
            if (cellContents[i] == NULL) {
                tmpCells[numFound] = i;
                numFound++;
            }
        }

        if ( numFound == 0 ) {
            return -1;
        } else if ( numFound == 1 ) {
            return tmpCells[0];
        }
        
        int randomIndex = random.nextInt(numFound);
        
        return tmpCells[randomIndex];
    }
        
    /**
     * If there is a sprite to check, then check if it is landing on a cell.
     *
     * If it is landing on a cell, notify listeners of the relevant event
     */
    private void checkSprite() {
        if (sprite != null) {
            // if sprite isn't touching us, so we don't care about it
            if (! isColliding(sprite)) {
                return;
            }

            int landingCell = landingCell(sprite);

            if (landingCell == INVALID_CELL) { 
                notifyListeners(HomeEvent.FROG_DEATH);     
            } else {
                switch ( getCellContents(landingCell) ) {
                    case INSECT:   
                        setCellContents(landingCell, FROG_HOME);
                        notifyListeners(HomeEvent.INSECT_EATEN);
                        notifyListeners(HomeEvent.FROG_HOME);
                        break;

                    case NULL:
                        setCellContents(landingCell, FROG_HOME);
                        notifyListeners(HomeEvent.FROG_HOME);
                        break;

                    case ALIGATOR_2:   
                    case ALIGATOR_1: 
                    case FROG_HOME: 
                    default:
                        notifyListeners(HomeEvent.FROG_DEATH);
                        break;
                }
            }
        }
    }
     
    /**
     * Set the sprite that will be watched
     *
     * @param Sprite sprite
     */
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
    
    /**
     * Check if the sprite is colliding with this layer.
     *
     * Only checks y coordinates of the sprite since the home layer takes
     * up the whole width of the screen
     *
     * @param Sprite sprite
     *
     * @return boolean
     */
    private boolean isColliding(Sprite sprite) {
        return sprite.getY() <= (getY() + getHeight());
    }
    
    /**
     * Determine which cell a sprite is landing on (if any)
     *
     * returns INVALID_CELL if no cell is being landed on
     *
     * @param Sprite sprite
     *
     * @return int
     */
    private int landingCell(Sprite sprite) {       
        for (int i=0 ; i<cellContents.length ; i++) {
            try {
                if (isLanding(sprite, i)) {
                    return i;
                }
            } catch (Exception e) {
            }
        }
        
        return INVALID_CELL;
    }
    
    /**
     * Check if a sprite is landing on one of the cells
     *
     * A sprite is landing on one of the cells if it is fully within the 
     * LHS and RHS of cell
     *
     * @param Sprite sprite     Sprite to check
     * @param int cell          index of the cell to check
     *
     * @return boolean
     */
    private boolean isLanding(Sprite sprite, int cell) throws Exception {
        if (cell <0 || cell >= cellXOffsets.length) {
            throw new Exception("Invalid cell: " + cell);
        }

        int cellMinX = cellXOffsets[cell];
        int cellMaxX = cellMinX + cellWidth;

        if ( sprite.getY() <= (getY() + getHeight())
             && (sprite.getX() >= cellMinX)
             && ((sprite.getX() + sprite.getWidth()) <= cellMaxX )) {

            return true;
        }

        return false;
    }
    
    /**
     * Remove a listener. The listener will no longer be notified of Home events
     *
     * @param HomeListener listener
     */
    public void removeListener(HomeListener listener) {
        listeners.removeElement(listener);
    }
    
    /**
     * Add a listener. The listener will be notified of Home events
     *
     * @param HomeListener listener
     */
    public void addListener(HomeListener listener) {
        listeners.addElement(listener);
    }
    
    /**
     * Notify listeners of an event
     *
     * @param int event
     */
    private void notifyListeners(int event) {
        Enumeration enumeration = listeners.elements();
        while (enumeration.hasMoreElements()) {
            HomeListener listener = (HomeListener) enumeration.nextElement();
            listener.homeEvent(event, this);
        }
    }
}
