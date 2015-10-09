/*
 * Option.java
 */

package com.rattat.micro.ui.selectslider;

/**
 *
 *
 * @author william robertson <wr@rattat.com>
 */
public class Option {
    
    private int id;
    
    private String text;

    /** 
     * Creates a new instance of Option 
     *
     * @param Unique identifier for option
     * @param Text of option
     */
    public Option(int id, String text) {
        this.id = id;
        this.text = text;
    }
    
    public int getId() {
        return id;
    }

    /** 
     * Get the text of the option
     *
     * @return text of option
     */
    public String getText() {
        return text;
    }

    /** 
     * Check this option against another for equality
     *
     * @param option to check
     * @return true if the options are equal, false otherwise
     */
    public boolean equals(Object o) {
    	if ( o instanceof Option ) {
        	Option option = (Option) o;
            return (option.getId()==id);
    	}
    	
    	return false;
    }

    /**
     * Textual representation of this option
     *
     * @return text of option
     */
    public String toString() {
        return text;
    }

}
