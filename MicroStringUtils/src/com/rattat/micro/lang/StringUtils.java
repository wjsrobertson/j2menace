/**
 * StringUtils.java
 *
 * Subject to the apache license v. 2.0
 *
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 * @author william@rattat.com
 */

package com.rattat.micro.lang;

import java.util.Vector;

/**
 * <p>
 * A collection of static methods providing basic functionality for dealing with 
 * strings.
 * </p>
 *
 * <p>
 * This class is modeled after the fantastic 
 * <a href="http://jakarta.apache.org/commons/lang/api/org/apache/commons/lang/StringUtils.html">Apache Commons StringUtils library</a>
 * and aims to be the stripped-down equivalent for j2me. Although the implementation 
 * is different, the methods do try to here adhere to roughly the same contracts 
 * as the AC versions. This includes the desirable behaviour of dealing with nulls 
 * in a friendly and intuitive fashion.
 * </p>
 *
 * <p>
 * To keep the compiled class as small as possible, the StringUtils 
 * does not contain any means of dealing with characters directly - only strings.
 * To use this API with characters, you should convert the character to a string first
 * using <code>String.valueOf(char c)</code>. 
 * </p>
 *
 * <p>
 * e.g. to do a search and replace with characters you should do: 
 * </p>
 *
 * <p>
 * <code>StringUtils.replace("test", String.valueOf('s'), String.valueOf('n'))</code>
 * </p>
 *
 * <p>
 * This class can't be instanciated.
 * </p>
 *
 * @author william@rattat.com
 */
public class StringUtils {
    
    /**
     * Left hand side of a string
     */
    public static final int LEFT = 1;
    
    /**
     * Right hand side of a string
     */
    public static final int RIGHT = 2;
    
    /**
     * Centre of a string
     */
    public static final int CENTER = LEFT | RIGHT;
    
    /**
     * Left and right hand side of a string
     */
    public static final int BOTH = LEFT | RIGHT;

    /** 
     * Private constructor to stop instanciation. The methods in this class are 
     * all static and this class should never be instanciated. 
     */
    private StringUtils() {
    }
    
    /**
     * <p>Split a string into a String[] array based on another string to
     * split by.</p>
     * 
     * <p>
     * e.g. The following will produce a 3 element array with the elements "1", 
     * "2" and "3"
     * </p>
     * 
     * <p>
     * <code>
     * String[] pieces = StringUtils.split("1:2:3", ":");<br />
     * </code>
     * </p>
     * 
     * <p>
     * If <code>splitBy</code> is longer than the string to split by then
     * a single element array will be returned containing the original string.
     * </p>
     *
     * <p>
     * <code>splitBy</code> may be null or an empty 
     * string - in which case the result is identical to that returned by 
     * {@link StringUtils#split(String toSplit)}. i.e. The string will be split
     * into individual strings, one for each character.
     * </p>
     *
     * <p>
     * <code>toSplit</code> parameter may be null - in which case the response 
     * is null.
     * </p>
     * 
     * <p>
     * <code>toSplit</code> may be an empty 
     * string - in which case the result is an empty array.
     * </p>
     * 
     * @param   toSplit    The String to split up into pieces
     * @param   splitBy     The String to split by
     *
     * @return  A String[] array composed of parts of the tosplit parameter
     *
     * @see StringUtils#split(String toSplit)
     * @see StringUtils#join(String[] toJoin, String glue)
     *
     * @since 0.1
     *
     * @todo - optimise - do away with Vector
     */
    public static String[] split(String toSplit, String splitBy) {
        
        if (toSplit == null) {
            return null;
        }
        if (splitBy == null) {
            return split(toSplit);
        }  
        if (toSplit.equals("")) {
            return new String[]{};
        }
        if (splitBy.equals("")) {
            return split(toSplit);
        }
        if (toSplit.length() <= splitBy.length()) {
            return new String[]{toSplit};
        }

        // perform calculation
        
        Vector parts = new Vector();
        int start = 0;
        int end   = 0;
        String remainder = toSplit;

        do {
            end = remainder.indexOf(splitBy);
            if ( end < 0 ) {
                break;
            } else {
                parts.addElement( remainder.substring( 0, end ) );
                remainder = remainder.substring( end + splitBy.length() );
            }
        } while ( true );
        
        if ( remainder.length() >= 0 ) {
            parts.addElement( remainder );
        }

        String[] splitVals = new String[ parts.size() ];
        parts.copyInto(splitVals);
        
        return splitVals;
    }

    /**
     * <p>Split a string into an array composed of the string of each character.</p>
     *
     * <p>
     * <code>toSplit</code> can be null in which case the response is null
     * </p>
     *
     * <p>
     * <code>toSplit</code> may be an empty string in which case 
     * the result is an empty array.
     * </p>
     *
     * @param toSplit    The String to split into individual character strings
     *
     * @return  A String[] array containing each individual character of toSplit
     *
     * @see StringUtils#split(String toSplit, String splitBy)
     * @see StringUtils#join(String[] toJoin, String glue)
     *
     * @since 0.1
     */
    public static String[] split(String toSplit) {
        
        // fail-fast with null pointer exception
        
        if (toSplit == null) {
            return null;
        }
        
        // return empty array if string is empty
        
        if (toSplit.equals("")) {
            return new String[]{};
        }
        
        char[] chars = toSplit.toCharArray();
        String[] strings = new String[chars.length];
        
        for (int i=0 ; i<chars.length ; i++) {
            strings[i] = toSplit.valueOf( chars[i] );
        }
        
        return strings;
    }
    
    /**
     * <p>Join an  array of strings using glue.</p> 
     *
     * <p>
     * i.e. concatenate an array of strings into one string consisting of the indiual
     * strings in sequence, seperated by the glue string.
     * </p>
     *
     * <p>
     * e.g. The following will produce the string <code>"a@b@c@d"</code>
     * </p>
     * 
     * <p>
     * <code>
     * String[] pieces = {"a","b","c","d"};<br />
     * String joined = StringUtils.join(pieces, "@");
     * </code>
     * </p>
     *
     * <p>
     * <code>toJoin</code> may be an empty array - in which case
     * the response will be an empty string. 
     * </p>
     * 
     * <p>
     * <code>toJoin</code> may
     * be null in ehich case the response is null. 
     * </p>
     * 
     * <p>
     * <code>glue</code> may be null or an empty 
     * string - in which case the result is a single string containing
     * the sequence of strings in the toJoin array.
     * </p>
     *
     * 
     * @param toJoin    A String[] array containing strings to join
     * @param glue      The string to join with
     *
     * @return  The joined String
     *
     * @see StringUtils#split(String toSplit)
     * @see StringUtils#split(String toSplit, String splitBy)
     *
     * @since 0.1
     */
    public static String join(String[] toJoin, String glue) {

        // fail-fast with null pointer exceptions
        
        if (toJoin == null) {
            return null;
        }
        if (glue == null) {
            glue = "";
        }
        
        // special case for speed
        
        if ( toJoin.length == 0 ) {
            return "";
        }
        
        StringBuffer buffer = new StringBuffer();
        
        for (int i=0 ; i<toJoin.length ; i++) {
            buffer.append( toJoin[i] );
            
            if ( i != toJoin.length-1 ) {
                buffer.append(glue);
            }
        }
        
        return buffer.toString();
    }
    
    /**
     * <p>Replace substrings of a string with another string.</p>
     *
     * <p>
     * e.g. The following will produce the string <code>"there 
     * is nothing there"</code>
     * </p>
     * 
     * <p>
     * <code>
     * String replaced = StringUtils.replace("it is nothing it", "it", "there");
     * </code>
     * </p>
     *
     * <p>
     * If <code>subject</code> is null then null will be returned.
     * </p>
     *
     * <p>
     * If <code>search</code> is null or empty then the value of 
     * <code>toReplace</code> will be returned.
     * </p>
     *
     * <p>
     * If the <code>replace</code> parameter is null it will be treated as an 
     * empty string.
     * </p>
     *
     * @param subject   The string in which to perform the search/replace
     * @param search    The string to search for
     * @param replace   The string to replace by
     *
     * @return
     *
     * @since 0.1
     */
    public static String replace(String subject, String search, String replace) {
        
        // fail-fast with null pointer exceptions
        
        if (subject == null) {
            return null;
        }
        if (search == null || search.equals("")) {
            return subject;
        }
        if (replace == null) {
            replace = "";
        }
        
        String[] split = split(subject, search);
        
        return join(split, replace);
    }
    
    /**
     * <p>Reverse a string.</p>
     * 
     * <p>Reverse the sequence of characters in the string.</p>
     *
     * <p>
     * e.g. The following will produce the string <code>"edcba"</code>
     * </p>
     * 
     * <p>
     * <code>
     * String reversed = StringUtils.reverse("abcde");
     * </code>
     * </p>
     *
     * <p>
     * <code>toReverse</code> may not be null, but can be an empty 
     * string
     * </p>
     *
     * @param toReverse The string to reverse
     *
     * @return String   The reversed string 
     *
     * @since 0.1
     */
    public static String reverse( String toReverse ) {

        if (toReverse == null) {
            return null;
        }
        if ( toReverse.equals("") ) {
            return "";
        }
        
        StringBuffer buffer = new StringBuffer();
        
        for ( int i=toReverse.length()-1 ; i >= 0 ; i-- ) {
            buffer.append( toReverse.charAt(i) );
        }
        
        return buffer.toString();
    }
    
    /**
     * <p>
     * Strip characters from the left hand side, right hand side or both 
     * sides of a string.
     * </p>
     *
     * <p>
     * Which sides will be trimmed is decided by the <code>side</code> parameter.
     * Legal values are <code>LEFT, RIGHT or BOTH</code>
     * </p>
     *
     * <p>
     * e.g. 1) The following will produce the string <code>"las"</code>
     * </p>
     * 
     * <p>
     * <code>
     * String trimmed = StringUtils.trim("glasgow", "gow", StringUtils.BOTH);
     * </code>
     * </p>
     *
     * <p>
     * e.g. 2) The following will produce the string <code>"dund"</code>
     * </p>
     * 
     * <p>
     * <code>
     * String trimmed = StringUtils.trim("dundee", "e", StringUtils.RIGHT);
     * </code>
     * </p>
     *
     * @param toTrim    The string to trim
     * @param chars     A sequence of characters to trim 
     * @param side      Side to trim - LEFT, RIGHT or BOTH
     *
     * @return String   The trimmed string
     *
     * @since 0.1
     */
    public static String trim(String toTrim, String chars, int side) {

        if ( ((RIGHT & side) | (LEFT & side)) == 0 ) {
            throw new IllegalArgumentException("side must be LEFT, RIGHT or BOTH");
        }
        if (toTrim == null) {
            return null;
        }
        if (chars == null) {
            return toTrim;
        }
        
        // handle special case of empty string
        
        if (toTrim.length() == 0) {
            return "";
        }
        
        // handle special case of empty chars
        
        if (chars.length() == 0) {
            return toTrim;
        }
        
        // do the trim
        
        int first = 0;
        int last = toTrim.length() - 1;
        
        // left hand side
        
        if ( (side | LEFT) > 0 ) {
            for (int i=0 ; i<toTrim.length() ; i++ ) {

                first = i;
                String testChar = toTrim.substring(i,i+1);

                if ( chars.indexOf(testChar) == -1 ) {
                    break;
                }
            }
        }
        
        // right hand side
        
        if ( (side | RIGHT) > 0 ) {
            for (int i=toTrim.length()-1 ; i>=0 ; i-- ) {

                last = i;
                String testChar = toTrim.substring(i,i+1);

                if ( chars.indexOf(testChar) == -1 ) {
                    break;
                }           
            }
        }
        
        return toTrim.substring(first, last+1);
    }
    
    /**
     * <p>
     * Strip characters from both sides of a string.
     * </p>
     *
     * <p>
     * e.g. 1) The following will produce the string <code>"yz"</code>
     * </p>
     * 
     * <p>
     * <code>
     * String trimmed = StringUtils.trim("xyzx,", "x,");
     * </code>
     * </p>
     *
     * Please see {@link StringUtils#trim(String toTrim, String chars, int side)} 
     * for more details.
     *
     * @param toTrim    The string to trim
     * @param chars     A sequence of characters to trim 
     *
     * @return String   The trimmed string
     *
     * @see StringUtils#trim(String toTrim, String chars, int side)
     *
     * @since 0.1
     */
    public static String trim(String toTrim, String chars) {
        return trim(toTrim, chars, BOTH);
    }
    
    /**
     * Add padding to either the left hand side, right hand side or both 
     * sides of a string.
     *
     * @param toPad     The string to add padding to 
     * @param padding   The string to pad by
     * @param size      The final length of the padded string
     * @param side      The side to pad - LEFT, RIGHT or CENTER
     *
     * @return  The padded string
     *
     * @todo optimise and tidy
     */
    public static String pad(String toPad, String padding, int size, int side) {

        if ( ((RIGHT & side) | (LEFT & side)) == 0 ) {
            throw new IllegalArgumentException("side must be LEFT, RIGHT or CENTER");
        }
        if (padding == null || padding.length() == 0) {
            return toPad;
        }
        
        size = Math.max(0, size);   // negative numbers are zero
        
        int totalPaddingSize = 0;
        
        if (toPad == null) {
            return repeat( padding, size );
        } else {
            totalPaddingSize = size - toPad.length();
            
            if ( totalPaddingSize <= 0 ) {
                return toPad;
            }
        }
                
        // get the lengths of padding for the left and right hand sides
        
        int leftPaddingSize  = 0;
        int rightPaddingSize = 0;
        
        boolean padLeft  = (side & LEFT) > 0;
        boolean padRight = (side & RIGHT) > 0;

        if ( padLeft && padRight ){
            leftPaddingSize = totalPaddingSize / 2;
            rightPaddingSize = totalPaddingSize - leftPaddingSize;
        } else if ( padLeft ) {
            leftPaddingSize = totalPaddingSize;
        } else if ( padRight ) {
            rightPaddingSize = totalPaddingSize;
        }
        
        // build a string consisting only of padding with length size / 2 + 1
        
        int numRepeats = (size/2) + 1;
        String repeatedPadding = repeat(padding, numRepeats);
        
        // build the final string
        
        StringBuffer buffer = new StringBuffer();
        buffer.append( repeatedPadding.substring(0, leftPaddingSize) );
        if ( toPad != null ) {
            buffer.append( toPad );
        }
        buffer.append( repeatedPadding.substring(0, rightPaddingSize) );
        
        return buffer.toString();
    }
    
    /**
     * Repeat a string <code>num</code> times
     *
     * <p>
     * e.g. The following will produce the string <code>"hellohello"</code>
     * </p>
     * 
     * <p>
     * <code>
     * String trimmed = StringUtils.repeat("hello,", 2);
     * </code>
     * </p>
     *
     * @param toRepeat  The string to repeat
     * @param num       The number of times to repeat the string
     *
     * @return          The repeated string
     *
     * @since 0.1
     */
    public static String repeat(String toRepeat, int num) {

        // special cases when null, empty or less than or = zero
        
        if (toRepeat == null ) {
            return null;
        } 
        if (toRepeat.equals("") || num <= 0) {
            return "";
        }
        
        StringBuffer buffer = new StringBuffer();
        for (int i=0 ; i<num ; i++) {
            buffer.append(toRepeat);
        }
        
        return buffer.toString();
    }
}
