/**
 * Copyright 2006 William Robertson (www.rattat.com)
 */

package com.rattat.micro.game.frog.board.elements;

import javax.microedition.lcdui.game.TiledLayer;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Graphics;

/**
 * A channel is a TiledLayer subclass with height
 * of one cell. It scrolls across the screen in a loop and is used to 
 * represent one "lane" in the game - i.e one row of cars in the road or one row 
 * of logs in the river.
 *
 * @author william@rattat.com
 */
public class Channel extends TiledLayer {

    /**
     * left moving Channel
     */
    public static final int LEFT = 1;
    
    /**
     * Right moving channel
     */
    public static final int RIGHT = 2;

    /**
     * Deadly cell type - if the frog lands on this then the user looses
     * a life
     */
    public static final int TYPE_DEADLY = 1;
    
    /**
     * Safe cell type - frog can step on it and nothing happens
     */
    public static final int TYPE_SAFE   = 2;
    
    /**
     * Moving cell type - if frog lands on it then the frog gets moved along
     * at the same speed as then channel. e.g. standing on a log
     */
    public static final int TYPE_MOVING = 3;
    
    /**
     * Array to hold the type of each image in the Channel
     */
    private int[] types = new int[18];
    
    /**
     * Empty Image index - cell is empty
     */
    public final static int CELL_NULL          = 0;
    
    /**
     * Border tile image index (median)
     */
    public final static int CELL_BG_BORDER     = 1;
    
    /**
     * River tile image index
     */
    public final static int CELL_BG_RIVER      = 2;
    
    /**
     * Road tile image index
     */
    public final static int CELL_BG_ROAD       = 3;
    
    /**
     * Car 1 tile image index
     */
    public final static int CELL_CAR1          = 4;
    
    /**
     * Car 2 tile image index
     */
    public final static int CELL_CAR2          = 5;
    
    /**
     * Car 3 tile image index
     */
    public final static int CELL_CAR3          = 6;
    
    /**
     * LHS of log tile image index
     */
    public final static int CELL_LOG_LEFT      = 7;
    
    /**
     * Middle of log tile image index
     */
    public final static int CELL_LOG_MIDDLE    = 8;
    
    /**
     * RHS of log tile image index
     */
    public final static int CELL_LOG_RIGHT     = 9;
    
    /**
     * LHS of lorry tile image index
     */
    public final static int CELL_LORRY_LEFT    = 10;
    
    /**
     * RHS of lorry tile image index
     */
    public final static int CELL_LORRY_RIGHT   = 11;
    
    /**
     * Tractor tile image index
     */
    public final static int CELL_TRACTOR       = 12;
    
    /**
     * Tile tile image index
     */
    public final static int CELL_TURTLE        = 13;
    
    /**
     * Tail of aligator tile image index
     */
    public final static int CELL_ALIGATOR_TAIL = 14;
    
    /**
     * Middle of aligator tile image index
     */
    public final static int CELL_ALIGATOR_MID  = 15;
    
    /**
     * Head of aligator tile image index
     */
    public final static int CELL_ALIGATOR_HEAD = 16;
    
    /**
     * Open mouthed head of aligator tile image index
     */
    public final static int CELL_ALIGATOR_OPEN = 17;

    /**
     * Holds the type of each possible tile image (deadly, safe, moving). 
     */
    private Image[] images;
    
    /**
     * Holds the cell types for all cells in the channel
     */
    private int[] cellTypes;
    
    /**
     * Holds the cell contents for all cells in the channel
     */
    private int[] cellContents;
    
    /**
     * The direction this channel moves in - LEFT or RIGHT
     */
    private int direction;
    
    /**
     * The boundary point where the channel loops back on itsself
     */
    private int scrollBoundary;
    
    /**
     * The speed and direction that the channel moves in - negative moves
     * left, positive moves right. 
     */
    private int velocity = 0;
    
    /**
     * How far the the channel has moved from it's original position. Used 
     * to determine which cells are to be dipslayed in which position
     */
    private int currentOffset = 0;
    
    /**
     * Path to the image containing all the tiles
     */
    private static String imageFile = "/tiles.png";
    
    /**
     * Holds the Image containing the tiles
     */
    private static Image image = null;

    /**
     * Private constructor
     * 
     * Please use Channel.create() to create a Channel
     *
     * @param int numCells
     * @param Image image
     * @param int tileWidth
     * @param int tileHeight
     */
    private Channel (int numCells, Image image, int tileWidth, int tileHeight) {
        super(numCells, 1, image, tileWidth, tileHeight);
        initTypes();
    }
    
    /**
     * Get / create the image containing the tiles
     *
     * @return Image
     */
    private static Image getImage() throws Exception {
        if (image == null) {
            image = Image.createImage(imageFile);
        }
        
        return image;
    }

    /**
     * Create a channel instance
     * 
     * cellContents paramater is an array containing which cells the Channel 
     * consists of. e.g. {CELL_BG_ROAD, BGROAD, CELL_CAR1, CELL_BG_ROAD} would be a four cell
     * wide channel.
     * 
     * 
     * 
     * @param int[] cellContents
     * @param int velocity
     * @return Channel
     * @throws Exception
     */
    public static Channel create(int[] cellContents, int velocity) throws Exception {
        Image tileImage = getImage();

        int cellHeight = tileImage.getHeight();
        int cellWidth  = tileImage.getHeight(); // yes, width = cellHeight (square)

        Channel channel = new Channel(cellContents.length, tileImage, cellWidth, cellHeight);

        channel.setCellContents( cellContents );

        channel.setVelocity( velocity );
        
        int direction = (velocity > 0) ? RIGHT : LEFT;
        channel.setDirection( direction );
        
        //int scrollBoundary = (velocity < 0) ? channel.getWidth() + channel.getCellWidth () : -channel.getCellWidth();
        int scrollBoundary = -channel.getCellWidth();
        channel.setScrollBoundary( scrollBoundary );

        channel.init();
        
        return channel;
    }
    
    /**
     * Initialise the array containing all possible cell types
     */
    private void initTypes() {
        types[CELL_NULL]          = TYPE_SAFE;
        types[CELL_BG_BORDER]     = TYPE_SAFE;
        types[CELL_BG_RIVER]      = TYPE_DEADLY;
        types[CELL_BG_ROAD]       = TYPE_SAFE;
        types[CELL_CAR1]          = TYPE_DEADLY;
        types[CELL_CAR2]          = TYPE_DEADLY;
        types[CELL_CAR3]          = TYPE_DEADLY;
        types[CELL_LOG_LEFT]      = TYPE_MOVING;
        types[CELL_LOG_MIDDLE]    = TYPE_MOVING;
        types[CELL_LOG_RIGHT]     = TYPE_MOVING;
        types[CELL_LORRY_LEFT]    = TYPE_DEADLY;
        types[CELL_LORRY_RIGHT]   = TYPE_DEADLY;
        types[CELL_TRACTOR]       = TYPE_DEADLY;
        types[CELL_TURTLE]        = TYPE_MOVING;
        types[CELL_ALIGATOR_TAIL] = TYPE_MOVING;
        types[CELL_ALIGATOR_MID]  = TYPE_MOVING;
        types[CELL_ALIGATOR_HEAD] = TYPE_MOVING;
        types[CELL_ALIGATOR_OPEN] = TYPE_DEADLY;
    }

    /**
     * Initialise the channel - Setup the cell contents according to the 
     * cellContents array
     */
    private void init() {
        for (int i=0 ; i<cellContents.length ; i++) {
            setCell(i, cellContents[i]);
        }
    }
    
    /**
     * Update the channel 
     *
     * move acording to velocity then update position & cell contents to give 
     * the impression on a constantly moving image
     */
    public void update() {
        // move according to velocity & update cell contents according to position
        
        if (velocity != 0) {
            move(velocity, 0);
            int offsetPixels = 0;
            
            if (direction == LEFT) {
                if ( (getX() + getCellWidth())  < scrollBoundary) {
                    offsetPixels = scrollBoundary - getCellWidth() - getX();
                }
            } else if (direction == RIGHT) {
                if ( getX() > scrollBoundary) {
                    offsetPixels = scrollBoundary -  getX();
                }
            }
            
            int offsetCells = offsetPixels / getCellWidth();
            if (offsetCells != 0) {
                shuntCells(offsetCells);
                moveByCells(offsetCells); 
            }
       } 
    }
    
    /**
     * Shift the TiledLayer cell contents by a pixel offset
     *
     * @param int offset
     */
    private void shuntCells(int offset) {
        currentOffset += offset;
        
        // modulo arithmetic in base cellContents.length
        // transform the currentOffset to a positive value less than cellContents.length
        
        if (currentOffset < 0) {
            currentOffset += cellContents.length;
        }
        currentOffset = currentOffset % cellContents.length;

        for (int i=0 ; i<cellContents.length ; i++) {
            int index = ( i +currentOffset ) % cellContents.length;
            setCell(i, cellContents[index]);
        }
    }
    
    /**
     * move the channel 
     *
     * @param int numCells
     */
    public void moveByCells(int numCells) {
        int pixelMoveX = numCells * getCellWidth();
        
        move(pixelMoveX, 0);
    }
    
    /**
     * Check if a sprite is collising with the channel
     *
     * @todo implement me
     */
    public boolean isColliding(Sprite sprite) {
        return true;
    }
    
    /**
     * Check if Sprite is colliding with a specific cell
     *
     * @param Sprite sprite
     * @param int cell
     */
    private boolean isColliding(Sprite sprite, int cell) {
        
        /*
         allow some leeway for the user in the form of padding inside
         the sprites borders
        */
        int padding = (int) ((double)sprite.getWidth() / 4);
        
        if (padding == 0) {
            padding = 1;
        }
        
        int spriteMinX = sprite.getX() + padding;
        int spriteMaxX = sprite.getX() + sprite.getWidth() - padding;
        int spriteMinY = sprite.getY() + padding;
        int spriteMaxY = sprite.getY() + sprite.getHeight() - padding;
        
        int cellMinY = getY();
        int cellMaxY = getY() + getCellHeight();
        
        // check for y collision
        
        if ( spriteMinY > cellMinY && spriteMinY < cellMaxY 
             || spriteMaxY > cellMinY && spriteMaxY < cellMaxY) {
            
        } else {
            return false;
        }
        
        // check for X collision
        
        int cellMinX = getX() + (cell) * getCellWidth();
        int cellMaxX = getX() + (cell+1) * getCellWidth();
        
        if (  (spriteMinX > cellMinX && spriteMinX < cellMaxX)
               || (spriteMaxX > cellMinX && spriteMaxX < cellMaxX)
               ) {

               return true;
        }
        
        return false;
    }

    /**
     * Check if a sprite is colliding with the Channel, and if so, is
     * the cell it is colliding with a deadly cell
     *
     * @param Sprite sprite
     */
    public boolean isDeadlyCollision(Sprite sprite) {      

        for (int i=0 ; i<getColumns() ; i++) {
            if (isColliding(sprite, i)) {
                if ( cellType(i) == TYPE_DEADLY ) {
                    return true;
                }
            }
        }

        return false;
    }
    
    /**
     * Check is a sprite is landing on the Channel.
     *
     * We consider the Sprite to be landing on the 
     * Channel if its centre pixel is within the bounds
     * of the layer.
     *
     * @param Sprite sprite
     */
    public boolean isLanding(Sprite sprite) {
        int spriceCentreX = sprite.getX() + (sprite.getWidth()/2);
        int spriteCentreY = sprite.getY() + (sprite.getHeight()/2);
        
        return     spriceCentreX >= getX()
                && spriteCentreY >= getY()
                && spriceCentreX < (getX() + getWidth()) 
                && spriteCentreY < (getY() + getHeight());
    }
    
    /**
     * Determine which cell (if any) a sprite is landing on
     * 
     * isLanding() is more efficient if the specific cell is not required
     *
     * @param Sprite sprite
     *
     * @return int  The cell the sprite is landing or -1 if none
     */
    public int landingCell(Sprite sprite) {
        int spriceCentreX = sprite.getX() + (sprite.getWidth()/2) ;
        int spriteCentreY = sprite.getY() + (sprite.getHeight()/2);

        for (int i=0 ; i<getColumns() ; i++) {
            if (    spriteCentreY >= getY()
                 && spriteCentreY < (getY() + getCellHeight())
                 && spriceCentreX > (getX() + i * getCellWidth())
                 && spriceCentreX <= (getX() + (i+1) * getCellWidth()) ) {
                
                return i;
            }
        }

        return -1;
    }
    
    /**
     * Get the type of a cell
     *
     * @param int
     */
    public int cellType(int x) {
        // work out the index based on our current offset
        int index = (x + currentOffset) % getColumns();

        // get the cell contents
        int content = cellContents[index];

        return types[content];
    }
    
    /**
     * Get the contents of a specific cell
     *
     * @param x Cell index
     */
    public int getCell(int x) {
        return getCell(x,0);
    }
    
    /**
     * Set the contents of a specific cell
     * 
     * 
     * @param int x Cell to set
     * @param int   index of tile image (CELL_BG_ROAD etc)
     */
    public void setCell(int x, int tileIndex) {
        setCell(x, 0, tileIndex);
    }

    /**
     * Set the contents of the channel 
     *
     * @param int[] cellContents
     */
    public void setCellContents (int[] cellContents) {
        this.cellContents = cellContents;
    }

    /**
     * Set the direction in which the channel should move
     *
     * @param int direction
     */
    public void setDirection (int direction) {
        this.direction = direction;
    }

    /**
     * Set the boundary pixel where the channel should loop back in itsself
     *
     * @param int scrollBoundary
     */
    public void setScrollBoundary (int scrollBoundary) {
        this.scrollBoundary = scrollBoundary;
    }
    
    /**
     * Get the velocity of the channel
     */
    public int getVelocity () {
        return velocity;
    }

    /**
     * Set the velocity of the channel
     *
     * @param int velocity
     */
    public void setVelocity (int velocity) {
        this.velocity = velocity;
    }
    
    /**
     * Set the current offset of the channel in pixels
     *
     * @param int currentOffset
     */
    public void setCurrentOffset (int currentOffset) {
        this.currentOffset = currentOffset;
    }
        

}
