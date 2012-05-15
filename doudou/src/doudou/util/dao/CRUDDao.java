package doudou.util.dao;

import java.io.Serializable;

/**
 * "Don't repeat the DAO!"
 * A generic DAO interface to CRUD the domain object
 * @author  ChaoGao
 * 
 */

/**
 * T is the type of the domain object
 * PK is the type of the primary key
 * 
 */
public interface CRUDDao<T, K> {
    
    /** Persist the new object*/
    Object create(T newObject); 
    
    /**
     * Retrieve the object that was previously persisted to the
     * database using the indicated id as the primary key 
     */
    T read(K id);
    
    /** Persist changes made to the object */
    int update(T changedObject);
    
    /** Remove an object with the id as the primary key*/
    int delete(K id);
    
    
}
