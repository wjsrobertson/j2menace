/**
 * Copyright 2006 William Robertson (www.rattat.com)
 */

package com.rattat.micro.game.frog.board;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.Image;

import com.rattat.micro.game.frog.board.*;
import com.rattat.micro.game.frog.board.elements.*;

/**
 * Used to paint a model to an image
 *
 * Actually, this is pointles & a waste of resources. Need to do away
 * with this altogether.
 *
 * @todo refactor to do painting in model
 *
 * @author william@rattat.com
 */
public class BoardView {
    
    /**
     * The model - the data representation of the game state
     */
    BoardModel model;
    
    /**
     * Rendered Image representation of the model returned to the GameCanvas 
     */
    Image image = null;
    
    /**
     * Graphics component of the image - used for painting
     */
    Graphics graphics = null; 

    /**
     * Creates a new instance of BoardView
     *
     * @param BoardModel model
     */
    public BoardView (BoardModel model) {
        this.model = model;
        init();
    }
        
    /**
     * Initialise the model, ready for use
     */
    private void init() {
        image    = Image.createImage( model.getWidth(), model.getHeight() );
        graphics = image.getGraphics();
    }
    
    /**
     * Paint the game to the screen based on model data
     *
     * @return Image
     */
    public Image render() {
        graphics.setColor(255, 255, 255); // white
        graphics.fillRect(0, 0, model.getWidth(), model.getHeight());

        model.getScoreLayer().paint(graphics);
        model.getStatusLayer().paint(graphics);
        model.getHomeLayer().paint(graphics);
        
        for (int i=0 ; i<model.getChannels().length ; i++) {
            try {
                model.getChannel(i).paint(graphics);
            } catch (Exception e) {
                System.err.println("Error painting channel: " + e);
            }
        }
        
        for (int i=0 ; i<model.getBorderLayers().length ; i++) {
            model.getBorderLayers()[0].paint(graphics);
            model.getBorderLayers()[1].paint(graphics);
        }

        model.getFrog().paint(graphics);
        model.getDeadFrog().paint(graphics);
        
        return image;
    }
}
