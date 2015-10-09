/*
 * Missile.java
 * 
 * Copyright 2007 William Robertson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */

package com.rattat.micro.game.aster.elements;
 
/**
 * A class representing a missile
 *
 * @author william@rattat.com
 */
public class Missile extends FloatingObject {

	/**
	 * The age of the missile - missile becomes inactive 
	 * after a period of time
	 */
    private int age = 0;

    /** Creates a new instance of Missile */
    public Missile() {
        
    }

    /**
     * Get the age of the missile
     * 
     * @return
     */
    public int getAge() {
        return age;
    }
    
    /**
     * Set the age of the missile
     * 
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Increment the age by 1
     */
    public void incAge() {
        age++;
    }
    
    /**
     * Called when vehicle subclass is updated - calls {@code incAge()}
     */
    public void onUpdate() {
    	incAge();
    }
}
