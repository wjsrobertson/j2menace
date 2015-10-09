/*
 * SelectListener.java
 */

package com.rattat.micro.ui.selectslider;

/**
 *
 * @author william robertson <wr@rattat.com>
 */
public interface SelectListener {
    
    /**
     * Called when an option is selected. Observer pattern - observer.
     *
     * @param The option that was selected
     */
    public void optionSelected(Option o);
}
