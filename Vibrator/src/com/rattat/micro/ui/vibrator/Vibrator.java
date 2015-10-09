/**
 * Vibrator.java
 *
 * Subject to the apache license v. 2.0
 *
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 * @author william@rattat.com
 */

package com.rattat.micro.ui.vibrator;

import javax.microedition.lcdui.Display;

/**
 * Class for performing vibrations.
 *
 * Needs to have a Display object set with setDisplay or it won't
 * do anything.
 *
 * All methods must be called statically
 *
 * @author poobar
 */
public final class Vibrator {
    
    /**
     * Private constructor to stop instantiation
     */
    private Vibrator() {
    }
    
    /**
     * The Display instance which is uysed for vibrations
     */
    private static Display display;
    
    /**
     * Flag defining whether ot not the vibrator is active
     */
    private static boolean on = true;

    /**
     * Set the display object, used to perform the vibration 
     *
     * @param Display display 
     */
    public static void setDisplay(Display display) {
        Vibrator.display = display;
    }
    
    /**
     * Vibrate for a length of time
     *
     * Will quietly fail if there is no display object set
     *
     * @param duration  Duration in miliseconds
     */
    public static void vibrate(int duration) {
        if (display != null && on) {
            display.vibrate(duration);
        }
    }
    
    /**
     * Check if vibrations are turned on or off
     *
     * @return 
     */
    public static boolean isOn() {
        return on;
    }
    
    /**
     * Turn vibrations on or off
     *
     * @return 
     */
    public static void setOn(boolean on) {
        Vibrator.on = on;
    }
    
}
