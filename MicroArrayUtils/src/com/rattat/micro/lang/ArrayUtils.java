/**
 * ArrayUtils.java
 *
 * Subject to the apache license v. 2.0
 *
 * http://www.apache.org/licenses/LICENSE-2.0.txt
 *
 * @author william@rattat.com
 */

package com.rattat.micro.lang;

import java.util.Random;

/**
 * <p>
 * A collection of static methods providing basic functionality for dealing with 
 * arrays.
 * </p>
 *
 * <p>
 * This class is something like the great 
 * <a href="http://jakarta.apache.org/commons/lang/api/org/apache/commons/lang/StringUtils.html">Apache Commons StringUtils library</a>
 * for j2se. and aims to be the stripped-down equivalent for j2me.
 * </p>
 *
 * <p>Due to size limitations, only methods for dealing with int, 
 * double and Object arrays are included since these are the most commonly used. 
 * String arrays can be dealt with by using the Object methods and casting 
 * the return type where required.
 * </p>
 *
 * <p>
 * This class can't be instanciated.
 * </p>
 *
 * @author william@rattat.com
 */
public class ArrayUtils {
    
    /**
     * Random instance used to shuffle arrays
     */
    private static Random random = null;
    
    static {
        random = new Random( System.currentTimeMillis() );
    }
    
    /**
     * Check if an integer value is in an integer array
     *
     * @param array The array to check
     * @param value The value to search for
     *
     * @return true if value is in array, false otherwise
     */
    public static boolean contains( int[] array, int value) {
        return indexOf(array, value) != -1;
    }
        
    /**
     * Check if an object is in an object array
     *
     * @param array The array to check
     * @param value The value to search for
     *
     * @return true if value is in array, false otherwise
     */
    public static boolean contains( Object[] array, Object value) {
        return indexOf(array, value) != -1;
    }
        
    /**
     * Check if a double value is in a double array
     *
     * @param array The array to check
     * @param value The value to search for
     *
     * @return true if value is in array, false otherwise
     */
    public static boolean contains( double[] array, double value) {
        return indexOf(array, value) != -1;
    }
    
    /**
     * <p>Get the index of a value in an array</p>
     *
     * <p>Returns -1 if the integer is not in the array</p>
     *
     * @param array The array to check
     * @param value The value to search for
     *
     * @return  index or -1 if not found
     *
     * @throw   NullPointerException if array is null
     */
    public static int indexOf( int[] array, int value) {
        if (array == null) {
            throw new NullPointerException("array can't be null");
        }
        
        for ( int i=0 ; i<array.length ; i++ ) {
            if ( array[i] == value ) {
                return i;
            }
        }

        return -1;
    }
    
    /**
     * <p>Get the index of a value in an array</p>
     *
     * <p>Returns -1 if the object is not in the array</p>
     *
     * @param array The array to check
     * @param value The value to search for
     *
     * @return  index or -1 if not found
     *
     * @throw   NullPointerException if array is null
     */
    public static int indexOf( Object[] array, Object value) {
        if (array == null) {
            throw new NullPointerException("array can't be null");
        }
        
        for ( int i=0 ; i<array.length ; i++ ) {
            if ( array[i].equals(value) ) {
                return i;
            }
        }

        return -1;
    }

    /**
     * <p>Get the index of a value in an array</p>
     *
     * <p>Returns -1 if the double is not in the array</p>
     *
     * @param array The array to check
     * @param value The value to search for
     *
     * @return  index or -1 if not found
     *
     * @throw   NullPointerException if array is null
     */
    public static int indexOf( double[] array, double value) {
        if (array == null) {
            throw new NullPointerException("array can't be null");
        }
        
        for ( int i=0 ; i<array.length ; i++ ) {
            if ( array[i] == value ) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Shuffle the values of an int array into random order
     *
     * @param array The array to shuffle
     */
    public static void shuffle(int[] array) {
        if (array == null) {
            return;
        }
        
        for (int i=0 ; i<array.length ; i++) {
            int r = i + random.nextInt( array.length-i );
            
            int tmp = array[i];
            array[i] = array[r];
            array[r] = tmp;
        }
    }
    
    /**
     * Shuffle the values of an object array into random order
     *
     * @param array The array to shuffle
     */
    public static void shuffle(Object[] array) {
        if (array == null) {
            return;
        }
        
        for (int i=0 ; i<array.length ; i++) {
            int r = i + random.nextInt( array.length-i );
            
            Object tmp = array[i];
            array[i] = array[r];
            array[r] = tmp;
        }
    }
    
    /**
     * Shuffle the values of a double array into random order
     *
     * @param array The array to shuffle
     */
    public static void shuffle(double[] array) {
        if (array == null) {
            return;
        }
        
        for (int i=0 ; i<array.length ; i++) {
            int r = i + random.nextInt( array.length-i );
            
            double tmp = array[i];
            array[i] = array[r];
            array[r] = tmp;
        }
    }
    
    /**
     * Append one integer array to another
     *
     * Returns a new array. Either array may be null. If both are null
     * then null is returned.
     *
     * @param array1    First array
     * @param array2    Array to append to first
     *
     * @return  The new array
     */
    public static int[] append(int[] array1, int[] array2) {
        
        if (array1 == null && array2 == null) {
            return null;
        }
        if (array1 == null) {
            return clone( array2 );
        }
        if (array2 == null) {
            return clone( array1 );
        }
        
        int length1 = array1.length;
        int length2 = array2.length;
        int[] newArray = new int[length1+length2];
        
        for ( int i=0 ; i<length1 ; i++ ) {
            newArray[i] = array1[i];
        }
        for ( int i=0 ; i<length1 ; i++ ) {
            int index = length1 + i;
            newArray[index] = array1[i];
        }
        
        return newArray;
    }

    /**
     * Append one object array to another
     *
     * Returns a new array. Either array may be null. If both are null
     * then null is returned.
     *
     * @param array1    First array
     * @param array2    Array to append to first
     *
     * @return  The new array
     */
    public static Object[] append(Object[] array1, Object[] array2) {
        
        if (array1 == null && array2 == null) {
            return null;
        }
        if (array1 == null) {
            return clone( array2 );
        }
        if (array2 == null) {
            return clone( array1 );
        }
        
        int length1 = array1.length;
        int length2 = array2.length;
        Object[] newArray = new Object[length1+length2];
        
        for ( int i=0 ; i<length1 ; i++ ) {
            newArray[i] = array1[i];
        }
        for ( int i=0 ; i<length1 ; i++ ) {
            int index = length1 + i;
            newArray[index] = array1[i];
        }
        
        return newArray;
    }

    /**
     * Append one double array to another
     *
     * Returns a new array. Either array may be null. If both are null
     * then null is returned.
     *
     * @param array1    First array
     * @param array2    Array to append to first
     *
     * @return  The new array
     */
    public static double[] append(double[] array1, double[] array2) {

        if (array1 == null && array2 == null) {
            return null;
        }
        if (array1 == null) {
            return clone( array2 );
        }
        if (array2 == null) {
            return clone( array1 );
        }
        
        int length1 = array1.length;
        int length2 = array2.length;
        double[] newArray = new double[length1+length2];

        for ( int i=0 ; i<length1 ; i++ ) {
            newArray[i] = array1[i];
        }
        for ( int i=0 ; i<length1 ; i++ ) {
            int index = length1 + i;
            newArray[index] = array1[i];
        }
        
        return newArray;
    }
    
    /**
     * Return a new copy of a double aray holding the same elements
     *
     * @param array    Array to append to clone
     *
     * @return  The cloned array
     */
    public static double[] clone(double[] array) {
        if (array == null) {
            return null;
        }
        
        double[] clone = new double[array.length];
        
        for (int i=0 ; i<array.length ; i++) {
            clone[0] = array[i];
        }
        
        return clone;
    }
    
    /**
     * Return a new copy of an int aray holding the same elements
     *
     * @param array    Array to append to clone
     *
     * @return  The cloned array
     */
    public static int[] clone(int[] array) {
        if (array == null) {
            return null;
        }
        
        int[] clone = new int[array.length];
        
        for (int i=0 ; i<array.length ; i++) {
            clone[0] = array[i];
        }
        
        return clone;
    }
    
    /**
     * Return a new copy of an object aray holding the same elements
     *
     * @param array    Array to append to clone
     *
     * @return  The cloned array
     */
    public static Object[] clone(Object[] array) {
        if (array == null) {
            return null;
        }
        
        Object[] clone = new Object[array.length];
        
        for (int i=0 ; i<array.length ; i++) {
            clone[0] = array[i];
        }
        
        return clone;
    }
    
}
