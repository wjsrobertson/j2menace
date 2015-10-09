/**
 * ImageStringDrawer.java
 *
 * Subject to the apache license v. 2.0
 *
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 * @author william@rattat.com
 */

package com.rattat.micro.ui.effects.imagestring;

import java.io.IOException;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Graphics;
import java.util.Hashtable;

/**
 * <p>ImageStringDrawer is used for drawing strings built from a 
 * collection of images representing individual characters.</p>
 *
 * <p>There are two possible ways to construct an ImageStringDrawer instance: 
 * 
 * <p>One method uses a less convenient constructor which accepts a path to the 
 * character images and an arary of which characters there are images for. 
 * The class will attempt to load these images and if one does not exist 
 * then an IOException will be thrown.
 *
 * <p>The second method uses a constructor which accepts only the path to the 
 * images. The object will attempt to load images for a default range of 
 * characters (those containted in DEFAULT_CHARS). This method will not throw
 * an exception if any of the images can't be found. This makes it more 
 * convenient to construct, but more risky since exceptions may be thrown later
 * if the user attempts to draw a string containing a character which was not 
 * loaded. 
 *
 * <p>The constructor argument with the path to the character images must 
 * contain a wildcard character (*). When an image is loaded for a 
 * character, ImageStringDrawer will look for the appropriate file by replacing
 * the wildcard in the path with character being loaded. e.g. If the path is 
 * "images/*.jpg" then the file representing the character 'a' must be contained 
 * in the images directory and must be named "a.jpg"</p>
 *
 * <p>Instances of this class are immutable, stateless and thread safe.</p>
 *
 * <p>
 * <b>Construction with defined list of characters</b>
 * </p>
 *
 * <p>
 * <code>
 * try {<br>
 * &nbsp;&nbsp;&nbsp; char[] chars = new char[]{'h','e','l','o','w','r','d',' '};<br>
 * &nbsp;&nbsp;&nbsp; ImageStringDrawer drawer = new ImageStringDrawer("letters/*.jpg", chars);<br>
 * } catch (IOException) {<br>
 * }<br>
 * <br>
 * drawer.draw("hello world", 0, 0, ImageStringDrawer.TOP | ImageStringDrawer.LEFT);<br>
 * </code>
 * </p>
 *
 * <p>
 * <b>Construction without defined list of characters</b>
 * </p>
 *
 * <p>
 * <code>
 * ImageStringDrawer drawer = new ImageStringDrawer("letters/*.jpg");<br>
 * drawer.draw("hello world", 0, 0, ImageStringDrawer.TOP | ImageStringDrawer.LEFT);<br>
 * </code>
 * </p>
 *
 * @todo - handle newlines in strings
 *
 * @author william@rattat.com
 */
public class ImageStringDrawer {
    
    /**
     * Top alignment - string will be alligned at top when rendered, in relation 
     * to the y anchor point
     */
    public static final int TOP = 1;
    
    /**
     * Bottom alignment - string will be alligned at bottom, in relation 
     * to the y anchor point
     */
    public static final int BOTTOM = 2;
    
    /**
     * Vertical center alignment - string will be vertically alligned in middle, in relation 
     * to the y anchor point
     */
    public static final int VCENTER = 4;
    
    /**
     * Left alignment - string will be horizontally aligned to the left, in relation 
     * to the x anchor point
     */
    public static final int LEFT = 8;
    
    /**
     * Right alignment - string will be horizontally aligned to the right, in relation 
     * to the x anchor point
     */
    public static final int RIGHT = 16;
    
    /**
     * Center alignment - string will be horizontally aligned in the centre, in relation 
     * to the x anchor point
     */
    public static final int CENTER = 32;
    
    /**
     * Holds map of string of character -> Image of character
     */
    private Hashtable images = new Hashtable();

    /**
     * A character array holding all of the characters which will be loaded 
     * if no characters list is passed to the constructor.
     *
     * Currently, this is the set of printable ASCII characters.
     */
    public static final char[] DEFAULT_CHARS = new char[]{
        ' ','!','"','#','$','%','&','\'','(',')','*','+',',','-','.','/',
        '0','1','2','3','4','5','6','7','8','9',':',';','<','=','>','?','@',
        'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q',
        'R','S','T','U','V','W','X','Y','Z','[','\\',']','^','_','`',
        'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q',
        'r','s','t','u','v','w','x','y','z','{','|','}','~'
    };
    
    /**
     * Creates a new instance of ImageStringDrawer 
     * 
     * @param imageFilePath Path to character images contining a wildcard 
     *                      character (*). The wildcard will be replaced with 
     *                      the appropriate character when determining 
     *                      filenames.
     *
     * @param characters    Array of characters to load images for
     *
     * @throws IOException  Throws exception when a required image can't be loaded
     *
     * @see ImageStringDrawer(String imageFilePath)
     */
    public ImageStringDrawer(String imageFilePath, char[] characters) throws IOException {
         initImages(imageFilePath, characters);
    }
    
    /**
     * <p>Creates a new instance of ImageStringDrawer</p>
     *
     * <p>This will attempt to load images for all of the characters in the 
     * DEFAULT_CHARS array.</p>
     *
     * <p>
     * <b>Warning:</b> This constructor will not throw an IOException when
     * a character image is not found, unlike the checked counterpart which 
     * accepts an array of characters. This makes it more likely to encounter 
     * an exception whilst calling draw() later if an image is not found.
     * </p>
     *
     * @param imageFilePath Path to character images contining a wildcard 
     *                      character (*). The wildcard will be replaced with 
     *                      the appropriate character when determining 
     *                      filenames.
     *
     * @see ImageStringDrawer#DEFAULT_CHARS
     *
     * @see ImageStringDrawer(String imageFilePath, char[] characters)
     */
    public ImageStringDrawer(String imageFilePath) {
         initImages(imageFilePath);
    }
    
    /**
     * Initialise the images - load all possible character images based on 
     * the paramaters.
     *
     * @param imageFilePath  
     *
     * @param characters
     *
     * @throws IOException  If an image representing one of characters is not found.
     */
    private void initImages(String imageFilePath, char[] characters) throws IOException {
        for (int i=0 ; i<characters.length ; i++) {
            String file = imageFilePath.replace( '*', characters[i] );
            images.put(String.valueOf(characters[i]), Image.createImage(file));
        }
    }
     
    /**
     * Initialise the images - load all possible character images based on 
     * the the characters in DEFAULT_CHARS. This method will not throw 
     * exceptions if the images are not found, unlike its counterpart which
     * accepts a character list.
     *
     * @param imageFilePath  
     *
     * @see ImageStringDrawer#DEFAULT_CHARS
     */
    private void initImages(String imageFilePath) {
        for (int i=0 ; i<DEFAULT_CHARS.length ; i++) {
            String file = imageFilePath.replace( '*', DEFAULT_CHARS[i] );
            try {
                images.put(String.valueOf(DEFAULT_CHARS[i]), Image.createImage(file));
            } catch (IOException e) {
                // never mind
            }
        }
    }
    
    /**
     * <p>Render the string onto the Graphics, built from individual character 
     * images.</p>
     * 
     * <p>The anchor paramater can contain the following values
     * TOP, BOTTOM, VCENTER, LEFT, RIGHT or CENTER.</p>
     * 
     * <p>To use a combination value do a boolean OR - e.g. TOP | CENTER</p>
     *
     * <p>This method throws runtime exceptions when arguments are invalid or 
     * when a required image representing a character can't be found</p>
     * 
     * @param   g       The graphics instance to draw on
     * @param   string  The string to draw
     * @param   x       The x anchor point
     * @param   y       The y anchor point
     * @param   anchor  The anchor types: TOP, BOTTOM, VCENTER, LEFT, RIGHT or 
     *                  CENTER.
     *
     * @throws IllegalArgumentException If the anchor contains an invalid number
     *                                  and exception wil be thrown
     *
     * @throws NullPointerException If a null Graphics or String object is passed
     *                              in as a parameter
     *
     * @throws RuntimeException If the string contains a character for which there
     *                          is no corresponding image
     *
     * @see ImageStringDrawer#draw(Graphics g, int number, int x, int y, int anchor)
     */
    public final void draw(Graphics g, String string, int x, int y, int anchor) {
        
        if ( g == null ) {
            throw new NullPointerException("Graphics may not be null");
        }
        if ( string == null ) {
            throw new NullPointerException("String may not be null");
        }
                
        char[] stringChars = getChars(string);

        int width  = getWidth(stringChars);
        int height = getHeight(stringChars);

        int xPos;
        int yPos;

        if ((anchor & TOP) != 0) {
            xPos = x;
        } else if ((anchor & BOTTOM) != 0) {
            xPos = x - height;
        } else if ((anchor & VCENTER) != 0) {
            xPos = x - height / 2;
        } else {
            throw new IllegalArgumentException("Invalid vertical anchor");
        }

        if ((anchor & LEFT) != 0) {
            yPos = y;
        } else if ((anchor & RIGHT) != 0) {
            yPos = y - width;
        } else if ((anchor & CENTER) != 0) {
            yPos = y - width / 2;
        } else {
            throw new IllegalArgumentException("Invalid horizontal anchor");
        }
        
        for (int i=0 ; i<stringChars.length ; i++) {
            String drawChar = String.valueOf(stringChars[i] );
            Image drawImage = (Image) images.get(drawChar);
            if (drawImage == null) {
                throw new RuntimeException("Missing character: " + drawChar);
            }
            
            g.drawImage(drawImage, xPos, yPos, Graphics.LEFT|Graphics.TOP);
            
            xPos += drawImage.getWidth();
        }
    }
    
    /**
     * <p>Render the integer onto the Graphics, built from individual character 
     * images.</p>
     *
     * <p>This method throws runtime exceptions when arguments are invalid or 
     * when a required image representing a character can't be found</p>
     * 
     * @param   g       The graphics instance to draw on
     * @param   number  The number to draw
     * @param   x       The x anchor point
     * @param   y       The y anchor point
     * @param   anchor  The anchor types: TOP, BOTTOM, VCENTER, LEFT, RIGHT or 
     *                  CENTER.
     *
     * @throws IllegalArgumentException If the anchor contains an invalid number
     *                                  and exception wil be thrown
     *
     * @throws NullPointerException If a null Graphics or String object is passed
     *                              in as a parameter
     *
     * @throws RuntimeException If the string contains a character for which there
     *                          is no corresponding image
     *
     * @see ImageStringDrawer#draw(Graphics g, String string, int x, int y, int anchor)
     */
    public final void draw(Graphics g, int number, int x, int y, int anchor) throws Exception {
        String string = Integer.toString(number);
        
        draw(g, string, x, y, anchor);
    }
    
    /**
     * Convert a string into a character array consisting of the letters of the
     * string in a character array
     *
     * @param string
     *
     * @return 
     */
    private char[] getChars(String string) {

        int numChars = string.length();
        char[] chars = new char[numChars];
        string.getChars(0,string.length(), chars, 0);
        
        return chars;
    }
    
    /**
     * Get the width of the area that will be drawn when rendering the string
     * represented by a list of characters
     * 
     * Widths for individual characters are not the same in general, e.g. for
     * non-fixed width fonts.
     *
     * @param chars
     *
     * @return
     */
    private int getWidth(char[] chars) {
        int width = 0;

        for (int i=0 ; i<chars.length ; i++) {
            Image charImage = (Image) images.get( String.valueOf(chars[i]) );
            if ( charImage==null ) {
                throw new RuntimeException("Missing character: " + String.valueOf(chars[i]));
            }

            width += charImage.getWidth();
        }

        return width;
    }
    
    /**
     * Get the height of the area that will be drawn when rendering the string
     * represented by a list of characters
     *
     * @param chars
     *
     * @return
     */
    private int getHeight(char[] chars) {
        int height = 0;
        
        for (int i=0 ; i<chars.length ; i++) {
            Image charImage = (Image) images.get( String.valueOf(chars[i]) );
            if ( charImage==null ) {
                throw new RuntimeException("Missing character: " + String.valueOf(chars[i]));
            }
            
            height = Math.max( height, charImage.getHeight() );
        }

        return height;
    }
    
    /**
     * Get the height of the area that will be drawn when rendering the string
     *
     * @param string
     *
     * @return Height of string when rendred
     */
    public final int getHeight(String string) {
        char[] stringChars = getChars(string);
        
        return getHeight(stringChars);
    }
    
    /**
     * Get the width of the area that will be drawn when rendering the string
     *
     * @param string
     *
     * @return Width of the string when rendred
     */
    public final int getWidth(String string) {
        char[] stringChars = getChars(string);
        
        return getWidth(stringChars);
    }
}
