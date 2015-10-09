/*
 * SelectSlider.java
 */

package com.rattat.micro.ui.selectslider;

import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author william robertson <wr@rattat.com>
 */
public class SelectSlider {

    /**
     * Vector holding all possible options
     */
    private Vector options = new Vector();

    /**
     * Vector holding all listeners (observers)
     */
    private Vector listeners = new Vector();

    /**
     * The index current of the current option in the options vector
     */
    private int current = -1;

    /**
     * The background color of the slider
     */
    private int backgroundColor = 0x000000AA;

    /**
     * The color of the slider text
     */
    private int textColor = 0x00FFFFFF;
    
    /**
     * Flag to keep track of if the option has changed in some way
     * i.e. does the image need to be
     */
    private boolean isLooped = true;

    /**
     * Flag to keep track of if the option has changed in some way
     * i.e. does the image need to be
     */
    private boolean isChanged = true;

    /**
     * Add an option to the slider.
     *
     * @param option option to add
     */
    public final void addOption(Option option) {
        options.addElement(option);
        if (current == -1) {
            setCurrentOption(option);
        }
        isChanged = true;
    }
    
    /**
     * Add an option to the slider.
     *
     * @param option option to add
     * @param position position to insert the option at
     */
    public final void addOption(Option option, int position) 
           throws IllegalArgumentException {

    	try {
    		options.insertElementAt(option, position);
    	} catch (ArrayIndexOutOfBoundsException e) {
    		throw new IllegalArgumentException( e.getMessage() );
    	}
        
        if (current == -1) {
            setCurrentOption(option);
        }
        
        isChanged = true;
    }

    /**
     * Replace one option with another.
     *
     * @param oldOption old option to be replaced
     * @param newOption new option to replace with
     * @return true if the option was found, false otherwise
     */
    public final boolean replaceOption(Option oldOption, Option newOption) {
        int index = options.indexOf(oldOption);
        if (index == -1) {
            return false;
        } else {
            options.removeElement(oldOption);
            options.insertElementAt(newOption, index);
            isChanged = true;
            return true;
        }
    }

    /**
     * Remove an option from the slider.
     *
     * @param option option to remove
     * @return true if the option was found, false otherwise
     */
    public final boolean removeOption(Option option) {
        isChanged = true;
        return options.removeElement(option);
    }

    /**
     * Remove all options
     */
    public final void removeAllOptions() {
        isChanged = true;
        current = -1;
        options.removeAllElements();
    }

    /**
     * Add a listener to listen for when a selection is made.
     *
     * When a selection is made, the optionSelected() method of the listener
     * will be called with the selected option as a paremeter.
     *
     * @param listener listener object (observer)
     * @see optionSelected()
     */
    public final void addSelectListener(SelectListener listener) {
        listeners.addElement(listener);
    }

    /**
     * Notify all registered listeners that a slection has been made
     *
     * Passes the selected option to the optionSelected method of the
     * listener
     */
    private final void notifyListeners() {
        Option currentOption = null;
        try {
            currentOption = (Option) options.elementAt(current);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }

        Enumeration e = listeners.elements();
        while (e.hasMoreElements()) {
            SelectListener l = (SelectListener) e.nextElement();
            l.optionSelected(currentOption);
        }
    }

    /**
     * Set the current option
     *
     * @param option option to set as current
     * @return true on success, false otherwise
     */
    public final boolean setCurrentOption(Option option) {
        int index = options.indexOf(option);
        if (index == -1) {
            return false;
        } else {
            isChanged = true;
            current = index;
            return true;
        }
    }

    /**
     * @return currently selected option or null if none
     */
    public final Option getCurrentOption() {
    	if ( current != -1 ) {
    		try {
    			Option option = (Option) options.elementAt(current);
    			return option;
    		} catch (ArrayIndexOutOfBoundsException e) {
    		}
    	}
        return null;
    }
    
    /**
     * Set to true if the slider is to be looped, or false if it is not. 
     *
     * A looped slider will go back to the start after it reaches the end, or
     * vice versa
     *
     * @param looped
     */
    public final void setLooped(boolean looped) {
        isLooped = looped;
    }

    /**
     * Check if there is an option to the left of the current option
     *
     * @return boolean tru if there is an element on the left, false otherwise
     */
    public final boolean hasLeft() {
        return (current > 0) || (isLooped && options.size()>1);
    }

    /**
     * Check if there is an option to the right of the current option
     *
     * @return boolean true if there is an element on the right, false otherwise
     */
    public final boolean hasRight() {
        return (current < (options.size()-1)) || (isLooped && options.size()>1);
    }

    /**
     * Move the current option the be the one to the left of the current 
     * option
     *
     * @param true if moved, false otherwise
     */
    public final boolean moveLeft() {
        if (hasLeft()) {
            current--;
            if (options.size()>1) {
                current = (options.size() + current) % options.size();
            }
            isChanged = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Move the current option the be the one to the right of the current 
     * option
     *
     * @param true if moved, false otherwise
     */
    public final boolean moveRight() {
        if (hasRight()) {
            current++;
            if (options.size()>1) {
                current %= options.size();
            }
            isChanged = true;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Select the current option
     */
    public final void select() {
        notifyListeners();
    }

    /**
     * Paint the slider into the current 
     * clip area of the Graphics parameter
     * 
     * @param g
     */
    public void paint(Graphics g) {

    	int width  = g.getClipWidth();
    	int height = g.getClipHeight();
    	int x      = g.getClipX();
    	int y      = g.getClipY();

        g.setColor(backgroundColor);
        g.fillRect(x, y, width, height);

        Option currentOption = getCurrentOption();
        if ( currentOption == null ) {
        	return;
        }

        // work out the text to be displayed

        String displayText = currentOption.getText();

        if (hasLeft()) {
            displayText = "<< " + displayText;
        }
        if (hasRight()) {
            displayText = displayText + " >>";
        }
        // work out the size / position of the text

        Font font = g.getFont();
        int stringWidth = font.stringWidth( "<< " + 
                                            currentOption.getText() + " >>");
        int stringHeight = font.getHeight();
        // draw the text on the image

        g.setColor(textColor);

    	// Graphics.HCENTER | Graphics.VCENTER

        g.drawString(displayText, 
	        		 x + (width-stringWidth)/2, 
	        		 y + (height-stringHeight)/2, 
                     0 );
        isChanged = false;

    }
}

