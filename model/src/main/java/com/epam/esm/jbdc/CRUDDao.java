package com.epam.esm.jbdc;

import com.epam.esm.exceptions.DaoException;

/**
 * CRDDao interface features CRUD DAO functionality and implements CRDDao interface
 * @param <T> The entity object from table
 */
public interface CRUDDao<T> extends CRDDao<T> {
    /**
     * The method updates the entity object in a destination table
     * @param entity The entity object from table
     */
    void update(T entity);
}
