package com.epam.esm.dao;

import java.util.Optional;

/**
 * CRDDao interface features CRD DAO functionality and implements RDao functionality
 * @param <T> The entity object from table
 */
public interface CRDDao<T> extends RDao<T>{
    /**
     * The method inserts the entity object to a destination table
     * @param entity The entity object from table
     */
    Optional<T> insert(T entity);

    /**
     * The method removes the entity object in a destination table by its identifier
     * @param entity The entity object from table
     */
    void delete(T entity);
}
