/**
 * Copyright 2006 William Robertson (www.rattat.com)
 */

package com.rattat.micro.game.frog.board.elements;

import com.rattat.micro.game.frog.board.elements.*;

import javax.microedition.lcdui.Image;

/**
 * Object used to generate channels for levels
 *
 * @author william@rattat.com
 */
public class ChannelGenerator {
    
    /**
     * Number of levels in total
     */
    private int numLevels = 5;
    
    /**
     * The topmost river channel
     */
    public final static int CHANNEL_RIVER_0 = 0;
    
    /**
     * The second from top river channel
     */
    public final static int CHANNEL_RIVER_1 = 1;
    
    /**
     * The third from top river channel
     */
    public final static int CHANNEL_RIVER_2 = 2;
    
    /**
     * The second from bottom river channel
     */
    public final static int CHANNEL_RIVER_3 = 3;
    
    /**
     * The bottom river channel
     */
    public final static int CHANNEL_RIVER_4 = 4;
    
    /**
     * The topmost road channel
     */
    public final static int CHANNEL_ROAD_0 = 5;
    
    /**
     * The second from top road channel
     */
    public final static int CHANNEL_ROAD_1 = 6;
    
    /**
     * The third from top road channel
     */
    public final static int CHANNEL_ROAD_2 = 7;
    
    /**
     * The second from bottom road channel
     */
    public final static int CHANNEL_ROAD_3 = 8;
    
    /**
     * The bottom road channel
     */
    public final static int CHANNEL_ROAD_4 = 9;

    /**
     * Two dimentional array holding the contents of the Channels for CHANNEL_RIVER_0.
     */
    private int[][] river0Cells = { 
        {   // level 1
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,   Channel.CELL_BG_RIVER,   Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER
        },
        {   // level 2
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,   Channel.CELL_BG_RIVER,   Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_ALIGATOR_TAIL, Channel.CELL_ALIGATOR_MID, Channel.CELL_ALIGATOR_HEAD, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER
        },
        {   // level 3
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,   Channel.CELL_BG_RIVER,   Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_ALIGATOR_TAIL, Channel.CELL_ALIGATOR_MID, Channel.CELL_ALIGATOR_OPEN, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER
        },
        {   // level 4
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,   Channel.CELL_BG_RIVER,   Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_ALIGATOR_TAIL, Channel.CELL_ALIGATOR_MID, Channel.CELL_ALIGATOR_OPEN, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER
        },
        {   // level 5
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,   Channel.CELL_BG_RIVER,   Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_ALIGATOR_TAIL, Channel.CELL_ALIGATOR_MID, Channel.CELL_ALIGATOR_OPEN, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_ALIGATOR_TAIL, Channel.CELL_ALIGATOR_MID, Channel.CELL_ALIGATOR_OPEN, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER
        }
    };

    /**
     * Two dimentional array holding the contents of the Channels for CHANNEL_RIVER_1
     */
    private int[][] river1Cells = { 
        {   // level 1
            Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_TURTLE,
            Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_TURTLE,
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, 
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER
        },
        {   // level 2
            Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_TURTLE,
            Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER,
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, 
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER
        },
        {   // level 3
            Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_TURTLE,
            Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER,
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, 
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER
        },
        {   // level 4
            Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER,
            Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER,
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE,             
        },
        {   // level 5
            Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER,
            Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER,
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE,             
        }
    };

    /**
     * Two dimentional array holding the contents of the Channels for CHANNEL_RIVER_2
     */
    private int[][] river2Cells = { 
        {   // level 1
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER
        }, 
        {   // level 2
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER
        }, 
        {   // level 3
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER
        }, 
        {   // level 4
            Channel.CELL_ALIGATOR_TAIL, Channel.CELL_ALIGATOR_MID, Channel.CELL_ALIGATOR_HEAD, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER
        }, 
        {   // level 5
            Channel.CELL_ALIGATOR_TAIL, Channel.CELL_ALIGATOR_MID, Channel.CELL_ALIGATOR_OPEN, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_ALIGATOR_TAIL, Channel.CELL_ALIGATOR_MID, Channel.CELL_ALIGATOR_OPEN, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER
        },
    };

    /**
     * Two dimentional array holding the contents of the Channels for CHANNEL_RIVER_3
     */
    private int[][] river3Cells = { 
        {   // level 1
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, 
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER
        },
        {   // level 2
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, 
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER
        },
        {   // level 3
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, 
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER
        },
        {   // level 4
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, 
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_MIDDLE, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER
        },
        {   // level 5
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, 
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_LOG_LEFT, Channel.CELL_LOG_RIGHT, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER,
            Channel.CELL_ALIGATOR_TAIL, Channel.CELL_ALIGATOR_MID, Channel.CELL_ALIGATOR_OPEN, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER
        }
    };

    /**
     * Two dimentional array holding the contents of the Channels for CHANNEL_RIVER_4
     */
    private int[][] river4Cells = { 
        {   // level 1
            Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_TURTLE, 
            Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_TURTLE, 
            Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_TURTLE, 
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER       
        },
        {   // level 2
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, 
            Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_TURTLE, 
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_TURTLE, 
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER       
        },
        {   // level 3
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, 
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_TURTLE, 
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_TURTLE, 
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER       
        },
        {   // level 4
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, 
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_TURTLE, 
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, 
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER     
        },
        {   // level 5
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, 
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, 
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_TURTLE, Channel.CELL_BG_RIVER, 
            Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER, Channel.CELL_BG_RIVER     
        },
    };

    /**
     * Two dimentional array holding the contents of the Channels for CHANNEL_ROAD_0
     */
    private int[][] road0Cells = {
        {   // level 1
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_LORRY_LEFT, Channel.CELL_LORRY_RIGHT,
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_LORRY_LEFT, Channel.CELL_LORRY_RIGHT,
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_LORRY_LEFT, Channel.CELL_LORRY_RIGHT,
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD,  Channel.CELL_BG_ROAD,      
        },
        {   // level 2
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_LORRY_LEFT, Channel.CELL_LORRY_RIGHT,
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_LORRY_LEFT, Channel.CELL_LORRY_RIGHT,
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_LORRY_LEFT, Channel.CELL_LORRY_RIGHT,
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD,  Channel.CELL_BG_ROAD,      
        },
        {   // level 3
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_LORRY_LEFT, Channel.CELL_LORRY_RIGHT,
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_LORRY_LEFT, Channel.CELL_LORRY_RIGHT,
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_LORRY_LEFT, Channel.CELL_LORRY_RIGHT,
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD,  Channel.CELL_BG_ROAD,      
        },
        {   // level 4
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_LORRY_LEFT, Channel.CELL_LORRY_RIGHT,
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_LORRY_LEFT, Channel.CELL_LORRY_RIGHT,
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_LORRY_LEFT, Channel.CELL_LORRY_RIGHT,
            Channel.CELL_BG_ROAD, Channel.CELL_LORRY_LEFT, Channel.CELL_LORRY_RIGHT,
        },
        {   // level 5
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_LORRY_LEFT, Channel.CELL_LORRY_RIGHT,
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_LORRY_LEFT, Channel.CELL_LORRY_RIGHT,
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_LORRY_LEFT, Channel.CELL_LORRY_RIGHT,
            Channel.CELL_BG_ROAD, Channel.CELL_LORRY_LEFT, Channel.CELL_LORRY_RIGHT,
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_LORRY_LEFT, Channel.CELL_LORRY_RIGHT,
        },
    };
    
    /**
     * Two dimentional array holding the contents of the Channels for CHANNEL_ROAD_1
     */
    private int[][] road1Cells = {
        {   // level 1
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, 
            Channel.CELL_CAR3
        },
        {   // level 2
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR3, Channel.CELL_BG_ROAD, 
            Channel.CELL_CAR3, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, 
            Channel.CELL_BG_ROAD
        },
        {   // level 3
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR3, Channel.CELL_CAR3, Channel.CELL_BG_ROAD, 
            Channel.CELL_CAR3, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, 
            Channel.CELL_CAR3
        },
        {   // level 4
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR3, Channel.CELL_BG_ROAD, 
            Channel.CELL_CAR3, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR3, Channel.CELL_BG_ROAD, 
            Channel.CELL_BG_ROAD
        },
        {   // level 5
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR3, Channel.CELL_BG_ROAD, 
            Channel.CELL_CAR3, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, 
            Channel.CELL_CAR3, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR3, Channel.CELL_BG_ROAD, 
            Channel.CELL_BG_ROAD
        },
    };

    /**
     * Two dimentional array holding the contents of the Channels for CHANNEL_ROAD_2
     */
    private int[][] road2Cells = {
        {   // level 1
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR1, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR1, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR1, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD,
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, 
        },
        {   // level 2
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR1, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR1, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR1, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD,
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, 
        },     
        {   // level 3
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR1, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR1, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR1, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD,
            Channel.CELL_BG_ROAD, Channel.CELL_CAR1,
        },     
        {   // level 4
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR1, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR1, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR1, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD,
            Channel.CELL_BG_ROAD, Channel.CELL_CAR1,
        },     
        {   // level 5
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR1, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR1, 
            Channel.CELL_BG_ROAD, Channel.CELL_CAR1, Channel.CELL_BG_ROAD, Channel.CELL_CAR1, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD,
            Channel.CELL_BG_ROAD, Channel.CELL_CAR1,
        },
    };
    
    /**
     * Two dimentional array holding the contents of the Channels for CHANNEL_ROAD_3
     */
    private int[][] road3Cells = {
        {   // level 1
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD,  
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_TRACTOR, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_TRACTOR, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_TRACTOR
        },
        {   // level 2
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_TRACTOR, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD,  
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_TRACTOR, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_TRACTOR, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_TRACTOR
        },
        {   // level 3
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_TRACTOR, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_TRACTOR,  
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_TRACTOR, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_TRACTOR, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_TRACTOR
        },
        {   // level 4
            Channel.CELL_TRACTOR, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_TRACTOR, Channel.CELL_BG_ROAD, 
            Channel.CELL_TRACTOR, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, 
            Channel.CELL_BG_ROAD
        },
        {   // level 4
            Channel.CELL_TRACTOR, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_TRACTOR, Channel.CELL_BG_ROAD, 
            Channel.CELL_TRACTOR, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, 
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_TRACTOR, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, 
            Channel.CELL_BG_ROAD
        },
    };
    
    /**
     * Two dimentional array holding the contents of the Channels for CHANNEL_ROAD_4
     */
    private int[][] road4Cells = {
        {   // level 1
            Channel.CELL_CAR2, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD,
            Channel.CELL_CAR2, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD,
            Channel.CELL_CAR2, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD,
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, 
        },
        {   // level 2
            Channel.CELL_CAR2, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD,
            Channel.CELL_CAR2, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD,
            Channel.CELL_CAR2, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD,
            Channel.CELL_CAR2, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, 
        },
        {   // level 3
            Channel.CELL_CAR2, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD,
            Channel.CELL_CAR2, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD,
            Channel.CELL_CAR2, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD,
            Channel.CELL_CAR2, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR2, Channel.CELL_BG_ROAD, 
        },
        {   // level 4
            Channel.CELL_CAR2, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR2,
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR2, Channel.CELL_BG_ROAD,
            Channel.CELL_CAR2, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD,
            Channel.CELL_CAR2, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR2, Channel.CELL_BG_ROAD, 
        },
        {   // level 5
            Channel.CELL_CAR2, Channel.CELL_BG_ROAD, Channel.CELL_CAR2,
            Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR2, Channel.CELL_BG_ROAD,
            Channel.CELL_CAR2, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD,
            Channel.CELL_CAR2, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR2, Channel.CELL_BG_ROAD, 
            Channel.CELL_CAR2, Channel.CELL_BG_ROAD, Channel.CELL_BG_ROAD, Channel.CELL_CAR2, Channel.CELL_BG_ROAD, 
        },
    };
    
    /**
     * Two dimentional array holding the velocities for each channel on each level
     */
    private int[][] velocities = {
        // river 0
        { 1, 1, 2, 3, 4},
        // river 1
        { -3, -3, -4, -4, -5},
        // river 2
        { 2, 3, 3, 3, 4},
        // river 3
        { 3, 3, 4, 5, 7},
        // river 4
        { -3, -3, -4, -4, -5},
        // road 0
        {-1, -1, -1, -3, -4},
        // road 1
        {1, 7, 2, 7, 3},
        // road 2
        {-2, -2, -3, -4, -5},
        // road 3
        {2, 1, 1, 4, 3},
        // road 4
        {-1, -1, -2, -2, -3}
    };
    
    /**
     * Creates a new instance of ChannelGenerator
     */
    public ChannelGenerator () {
    }
    
    /**
     * Create a channel by channel identifier and level 
     *
     * @param int channelType
     * @param int level
     */
    public Channel createChannel(int channelType, int level) throws Exception {
        if (level < 1 || level > numLevels) {
            throw new Exception("Invalid level");
        }
        
        int velocity = 0;
        int[] cellContents;
        
        level = level-1;    // 0-indexed internally
        
        switch (channelType) {
            case CHANNEL_RIVER_0:
                velocity = velocities[CHANNEL_RIVER_0][level];
                cellContents = river0Cells[level];
                break;
                
            case CHANNEL_RIVER_1:
                velocity = velocities[CHANNEL_RIVER_1][level];
                cellContents = river1Cells[level];
                break;
                
            case CHANNEL_RIVER_2:
                velocity = velocities[CHANNEL_RIVER_2][level];
                cellContents = river2Cells[level];
                break;
                
            case CHANNEL_RIVER_3:
                velocity = velocities[CHANNEL_RIVER_3][level];
                cellContents = river3Cells[level];
                break;
                
            case CHANNEL_RIVER_4:
                velocity = velocities[CHANNEL_RIVER_4][level];
                cellContents = river4Cells[level];
                break;
                
            case CHANNEL_ROAD_0:
                velocity = velocities[CHANNEL_ROAD_0][level];
                cellContents = road0Cells[level];
                break;
                
            case CHANNEL_ROAD_1:
                velocity = velocities[CHANNEL_ROAD_1][level];
                cellContents = road1Cells[level];
                break;
                
            case CHANNEL_ROAD_2:
                velocity = velocities[CHANNEL_ROAD_2][level];
                cellContents = road2Cells[level];
                break;
                
            case CHANNEL_ROAD_3:
                velocity = velocities[CHANNEL_ROAD_3][level];
                cellContents = road3Cells[level];
                break;
                
            case CHANNEL_ROAD_4:
                velocity = velocities[CHANNEL_ROAD_4][level];
                cellContents = road4Cells[level];
                break;
                
            default:
                throw new Exception("Invalid channel type");
        }
        
        return Channel.create(cellContents, velocity);
        
    }
    
}
