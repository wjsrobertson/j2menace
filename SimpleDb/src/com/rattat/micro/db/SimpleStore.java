/**
 * Facade for accessing rms.
 *
 * @author william@rattat.com
 */

package com.rattat.micro.db;

import java.util.Hashtable;
import javax.microedition.rms.*;

/**
 * Simplifies fetching and storing variables with name, value pairs behaviour 
 * and get(). put() and remove() methods. Makes it a little more intuative.
 *
 * @author william@rattat.com
 */
public final class SimpleStore {

    /**
     * Name of the store. Corresponds to RecordStore name.
     */
    private String storeName = null;
    
    /**
     * Holds name, value pairs
     */
    private Hashtable values = new Hashtable();
    
    /**
     * Holds name, id pairs. The ID corresponding to each name
     */
    private Hashtable ids = new Hashtable();
    
    /**
     * Holds the RecordStore instance for accessing records
     */
    private RecordStore store = null;

    /**
     * Creates a new instance of SimpleStore 
     *
     * @param storeName unique name of the RecordStore
     *
     * @throws RecordStoreFullException
     * @throws RecordStoreNotFoundException
     * @throws RecordStoreException
     * @throws IllegalArgumentException
     */
    public SimpleStore(String storeName) throws RecordStoreException {
        if (storeName == null) {
            throw new NullPointerException("storeName may not be null");
        }

        this.storeName = storeName;
        store = RecordStore.openRecordStore(storeName, true);
        initRecords();
    }

    /**
     * Get the value of a record
     *
     * @param name name of record
     *
     * @return encoded value of record
     */
    public synchronized String get(String name) {
        if (name == null) {
            throw new NullPointerException("name may not be null");
        }
        
        return (String) values.get(name);
    }

    /**
     * Set the value of a stored record
     *
     * @param name name of record
     * @param value value of record
     */
    public synchronized void put(String name, String value) {
        if (name == null) {
            throw new NullPointerException("name may not be null");
        }
        if (value == null) {
            throw new NullPointerException("value may not be null");
        }
        
        values.put(name, value);
        saveRecord(name, value);
    }

    /**
     * Remove a record
     *
     * @param name
     */
    public synchronized boolean remove(String name) {
        if (name == null) {
            throw new NullPointerException("name may not be null");
        }
        
        String idString = (String) ids.get(name);
        
        if (idString != null) {
            int id = Integer.parseInt(idString);
            
            try {
                store.deleteRecord(id);
                return true;
            } catch (Exception e) {
            }
        }
        
        return false;
    }

    /**
     * Destroy the entire recordstore
     *
     * @return 
     */
    public boolean destroy() {
        if (storeName != null) {
            try {
                RecordStore.deleteRecordStore(storeName);
                return true;
            } catch (Exception e) {
            }
        } 
            
        return false;
    }

    /**
     * Close the recordstore
     *
     * @return 
     */
    public boolean close() {
        if (store != null) {
            try {
                store.closeRecordStore();
                return true;
            } catch (Exception e) {
            }
        } 
        
        return false;
    }

    /**
     * Load all stored records
     */
    private boolean initRecords() {
        if (store != null) {

            try {
                RecordEnumeration enumeration = store.enumerateRecords(null, null, false);
                while (enumeration.hasNextElement()) {
                    int id = enumeration.nextRecordId();

                    int size = store.getRecordSize( id );
                    byte[] data = new byte[ size ];
                    store.getRecord(id, data, 0);

                    String tmpString = new String(data,0,data.length);
                    int tmpPosition = tmpString.indexOf(": ");

                    if (tmpPosition > -1) {
                        String name  = tmpString.substring(0, tmpPosition);
                        String value = tmpString.substring(tmpPosition+2);

                        ids.put(name, String.valueOf(id) );
                        values.put(name, value);
                    }
                }
            } catch (Exception e) {
                return false;
            }
        }
        
        return true;
    }
 
    /**
     * Save a record for retrieval later
     *
     * @param name name of record to be stored
     * @param value value of record to be saved
     *
     * @return boolean
     */
    private boolean saveRecord(String name, String value) {

        if (store != null) {
            
            int id;
            String idString = (String)ids.get(name);
            String record = name + ": " + value;
            byte[] data = record.getBytes();

            try {
                if (idString != null) {
                    id = Integer.parseInt(idString);
                    store.setRecord( id, data, 0, data.length );
                    values.put(name, value);
                    
                    return true;
                } else {
                    id = store.addRecord( data, 0, data.length );
                    values.put(name, value);
                    ids.put( name, String.valueOf(id) );
                    
                    return true;
                }
            } catch (Exception e) {
            }
        } 
        
        return false;
    }   

}
