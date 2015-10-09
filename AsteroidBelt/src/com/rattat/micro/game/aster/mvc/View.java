/*
 * View.java
 * 
 * Copyright 2007 William Robertson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.rattat.micro.game.aster.mvc;

import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;

import com.rattat.math.geometry.vectored2D.Line2D;
import com.rattat.math.geometry.vectored2D.Polygon2D;
import com.rattat.math.geometry.vectored2D.Triangle2D;
import com.rattat.math.geometry.vectored2D.Vector2D;
import com.rattat.micro.game.aster.elements.Asteroid;
import com.rattat.micro.game.aster.elements.ExplodingPolygon;
import com.rattat.micro.game.aster.elements.FloatingObject;
import com.rattat.micro.game.aster.elements.FlyingSaucer;
import com.rattat.micro.game.aster.elements.Missile;
import com.rattat.micro.game.aster.elements.SpaceShip;
import com.rattat.micro.game.aster.elements.Star;

/**
 * Class which can render a Model to a Graphics object
 * 
 * @author william@rattat.com
 */
public class View {
	
	/**
	 * The width of the view in pixels
	 */
	int width;
	
	/**
	 * The height of the view in pixels
	 */
	int height;
	
	/**
	 * The model to render
	 */
	Model model;
	
	/**
	 * The scale of the game - used to scale the model up
	 * or down when rendering. It is pretty crap and may be dumped
	 * in later versions.
	 */
	int gameScale;
	
	/**
	 * The initial brightness of an explosion
	 */
	private static final int EXPLOSION_COLORSTRENGTH = 218;

	/**
	 * The background colour of an asteroid
	 */
	private static final int ASTEROID_BACKGROUND_COLOR = 0x00000000;
    
	/**
	 * Outline colour of an asteroid
	 */
	private static final int ASTEROID_OUTLINE_COLOR = 0x00FFFFFF;
	
	/**
	 * The colour of a flying saucer
	 */
    private static final int SAUCER_COLOR = 0x00FFFFFF;
        
    /**
     * The colour of the spaceship. This is on component
     * of the R,B,G in RGB, if this is set the 0xFF then
     * the colour will be 0x00FFFFFF
     */
    private int SPACESHIP_COLOR = 0xFF;
            
    /**
     * Initialise the view
     * 
     * @param width
     * @param height
     * @param gameScale
     * @param model
     */
	public View(int width, int height, int gameScale, Model model) {
		this.width = width;
		this.height = height;
		this.model = model;
		this.gameScale = gameScale;
	}

	/**
	 * Render the view onto the {@code graphics} object
	 * 
	 * @param graphics
	 */
    public void paint(Graphics graphics) {
    	
        graphics.setColor(0, 0, 0); // black
        graphics.fillRect(0, 0, width, height);

        paintStars(graphics);
        graphics.setColor(255, 255, 255); // white
        graphics.setColor(0, 0, 0); // black
        paintFloatingObjects(graphics);

        // score

        String score = "" + model.getScore();
        graphics.setColor(255, 255, 255); // white
        graphics.drawString(score, 5, 5, Graphics.TOP|Graphics.LEFT);

        // lives

        Vector2D a = new Vector2D( width, 5 );
        Vector2D b = new Vector2D( width+4, 5+16 );
        Vector2D c = new Vector2D( width, 5+12 );
        Vector2D d = new Vector2D( width-4, 5+16 );
        int yOffset = 10;

        graphics.setColor(0x00FFFFFF);
        graphics.setColor(0x00FFFFFF);
        
        for (int i=0 ; i<model.getLives() ; i++) {
            graphics.fillTriangle((int)a.getX()-yOffset, (int)a.getY(), 
                           (int)b.getX()-yOffset, (int)b.getY(), 
                           (int)c.getX()-yOffset, (int)c.getY());

            graphics.fillTriangle((int)a.getX()-yOffset, (int)a.getY(), 
                           (int)d.getX()-yOffset, (int)b.getY(), 
                           (int)c.getX()-yOffset, (int)c.getY());
            yOffset += 10;
        }

    }
    
    /**
     * Paint the stars
     * 
     * @param graphics
     */
    private void paintStars(Graphics graphics) {
        int x;
        int y;
        int radius; 
        graphics.setColor(255, 255, 255); // white
        
        Star[] stars = model.getStars();

        for (int i=0 ; i < stars.length ; i++) {
        	if ( stars[i] != null ) {
                x = (int) stars[i].getPosition().getX() / gameScale;
                y = (int) stars[i].getPosition().getY() / gameScale;
                radius = (int) stars[i].getRadius();

                graphics.fillArc(x-radius, y-radius, 2 * radius, 2 * radius, 0, 360);
        	}
        }
    }

    /**
     * Paint the floating objects - spaceship, asteroids etc.
     * 
     * @param graphics
     */
    private void paintFloatingObjects(Graphics g) {    
        
        // draw explosions
        paintExplosions(g);

        // draw the asteroids
        paintAsteroids(g);
        
        SpaceShip spaceShip   = model.getSpaceShip();
    	Missile missile       = model.getMissile();
    	Missile saucerMissile = model.getSaucerMissile();
        FlyingSaucer saucer   = model.getSaucer();

        // draw the spaceship
        if ( FloatingObject.isActive( spaceShip ) ) {
        	paintSpaceShip(g, spaceShip);  
        }

        // draw the missile if there is one
        if ( FloatingObject.isActive( missile ) ) {
            g.setColor(255, 255, 255); // white
            int x = (int) missile.getPosition().getX() / gameScale;
            int y = (int) missile.getPosition().getY() / gameScale;
            g.fillArc (x, y, 3, 3, 0, 360); 
        }
        
        // draw the missile if there is one
        if ( FloatingObject.isActive( saucerMissile ) ) {
            g.setColor(255, 255, 255); // white
            int x = (int) saucerMissile.getPosition().getX() / gameScale;
            int y = (int) saucerMissile.getPosition().getY() / gameScale;
            g.fillArc (x, y, 3, 3, 0, 360); 
        }
        
        // draw the flying saucer is there is one
        if ( FloatingObject.isActive( saucer ) ) {
            g.setColor(255, 255, 255); // white
            paintFlyingSaucer(g, saucer);
        }
        
        // draw explosions
        paintExplosions(g);
    }
    
    /**
     * Paint the spaceship
     * 
     * @param graphics
     * @param ship
     */
    private void paintSpaceShip(Graphics g, SpaceShip ship ) {
    	
    	Polygon2D polygon = ship.getPolygon();
    	Vector2D position = ship.getPosition();
    	
    	int color = SPACESHIP_COLOR;
    	
        if ( ! ship.isMortal() ) {
            color = (int) (0.65 * SPACESHIP_COLOR * ship.getAge() / SpaceShip.MORTAL_AGE);
        } else if (ship.getAge() == SpaceShip.MORTAL_AGE) {
        	color = SPACESHIP_COLOR;
        }
    	
        g.setColor(color, color, color);
        
        Enumeration e = null;
        
        for (e = polygon.triangles() ; e.hasMoreElements() ; ) {
            Triangle2D triangle = (Triangle2D) e.nextElement();
            Vector2D a = triangle.getA();
            Vector2D b = triangle.getB();
            Vector2D c = triangle.getC();

            g.fillTriangle((int) ((position.getX() + a.getX()) / gameScale),
                           (int) ((position.getY() + a.getY()) / gameScale), 
                           (int) ((position.getX() + b.getX()) / gameScale), 
                           (int) ((position.getY() + b.getY()) / gameScale), 
                           (int) ((position.getX() + c.getX()) / gameScale), 
                           (int) ((position.getY() + c.getY()) / gameScale));
        }
                
        for (e = polygon.edges() ; e.hasMoreElements() ; ) {
            Line2D line = (Line2D) e.nextElement();
            Vector2D a = line.getStart();
            Vector2D b = line.getEnd();

            g.drawLine((int) ((position.getX() + a.getX()) / gameScale), 
                       (int) ((position.getY() + a.getY()) / gameScale), 
                       (int) ((position.getX() + b.getX()) / gameScale), 
                       (int) ((position.getY() + b.getY()) / gameScale));
        }
    }
        
    /**
     * Paint the spaceship
     * 
     * @param graphics
     * @param saucer
     */
    private void paintFlyingSaucer(Graphics g, FlyingSaucer saucer) {
    	
    	Polygon2D polygon = saucer.getPolygon();
    	Vector2D position = saucer.getPosition();
    	
        Enumeration e = polygon.triangles();
        while (e.hasMoreElements()) {
            Triangle2D triangle = (Triangle2D) e.nextElement();
            Vector2D a = triangle.getA();
            Vector2D b = triangle.getB();
            Vector2D c = triangle.getC();

            g.setColor(SAUCER_COLOR);
            g.fillTriangle((int) ((position.getX() + a.getX()) / gameScale),
                           (int) ((position.getY() + a.getY()) / gameScale), 
                           (int) ((position.getX() + b.getX()) / gameScale), 
                           (int) ((position.getY() + b.getY()) / gameScale), 
                           (int) ((position.getX() + c.getX()) / gameScale), 
                           (int) ((position.getY() + c.getY()) / gameScale));
        }

        e = polygon.edges();
        while (e.hasMoreElements()) {
            Line2D line = (Line2D) e.nextElement();
            Vector2D a = line.getStart();
            Vector2D b = line.getEnd();

            g.drawLine((int) ((position.getX() + a.getX()) / gameScale), 
                       (int) ((position.getY() + a.getY()) / gameScale), 
                       (int) ((position.getX() + b.getX()) / gameScale), 
                       (int) ((position.getY() + b.getY()) / gameScale));
        }
    }
    
    /**
     * Paint the asteroids
     * 
     * @param graphics
     */
    private void paintAsteroids(Graphics g) {     

    	Vector asteroids = model.getAsteroids();
    	
        Enumeration e = asteroids.elements();
        while (e.hasMoreElements()) {
            Asteroid asteroid = (Asteroid) e.nextElement();
            if ( asteroid.isActive() ) {
            	paintAsteroid(g, asteroid);  
            }
        }
    }
    
    /**
     * Paint an asteroid
     * 
     * @param graphics
     * @param asteroid
     */
    private void paintAsteroid( Graphics g, Asteroid asteroid ) {
        
    	Polygon2D polygon = asteroid.getPolygon();
    	Vector2D position = asteroid.getPosition();
    	
        for (Enumeration e = asteroid.getPolygon().edges() ; e.hasMoreElements() ; ) {
            Line2D line = (Line2D) e.nextElement();
            Vector2D a = line.getStart();
            Vector2D b = line.getEnd();
            Vector2D c = polygon.midPoint();

            g.setColor(ASTEROID_BACKGROUND_COLOR);
            g.fillTriangle((int) ((position.getX() + a.getX()) / gameScale),
                           (int) ((position.getY() + a.getY()) / gameScale), 
                           (int) ((position.getX() + b.getX()) / gameScale), 
                           (int) ((position.getY() + b.getY()) / gameScale), 
                           (int) ((position.getX() + c.getX()) / gameScale), 
                           (int) ((position.getY() + c.getY()) / gameScale));

            g.setColor(ASTEROID_OUTLINE_COLOR);
            g.drawLine((int) ((position.getX() + a.getX()) / gameScale), 
                       (int) ((position.getY() + a.getY()) / gameScale), 
                       (int) ((position.getX() + b.getX()) / gameScale), 
                       (int) ((position.getY() + b.getY()) / gameScale));
        }
    }

    /**
     * Paint the explosions
     * 
     * @param graphics
     */
    private void paintExplosions(Graphics g) {     

    	Vector explosions = model.getExplosions();
    	
        Enumeration e = explosions.elements();
        while (e.hasMoreElements()) {
            ExplodingPolygon explosion = (ExplodingPolygon) e.nextElement();
            if ( explosion.isActive() && explosion.getAge() <= ExplodingPolygon.MAX_AGE ) {
            	paintExplosion(g, explosion); 
            }
        }
    }

    /**
     * Paint an explosion 
     *  
     * @param graphics
     * @param explosion
     */
    private void paintExplosion(Graphics g, ExplodingPolygon explosion) {
        Vector2D position = explosion.getPosition();
        double ageRatio = (double) explosion.getAge() / (double) ExplodingPolygon.MAX_AGE;
        int colorStrength = EXPLOSION_COLORSTRENGTH - (int) (EXPLOSION_COLORSTRENGTH * ageRatio);
        
        for (Enumeration e = explosion.getLines().elements(); e.hasMoreElements() ;) {
            Line2D line       = (Line2D) e.nextElement();
            Vector2D start    = line.getStart();
            Vector2D end      = line.getEnd();
       
            g.setColor(colorStrength, colorStrength, colorStrength);
            g.drawLine((int) ((position.getX() + start.getX()) / gameScale), 
                       (int) ((position.getY() + start.getY()) / gameScale), 
                       (int) ((position.getX() + end.getX()) / gameScale), 
                       (int) ((position.getY() + end.getY()) / gameScale));
        }
    }
    
}