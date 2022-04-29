package com.epam.esm.dao;

import java.util.Optional;

/**
 * CRDDao interface features CRUD DAO functionality and implements CRDDao interface
 * @param <T> The entity object from table
 */
public interface CRUDDao<T> extends CRDDao<T> {
    /**
     * The method updates the entity object in a destination table
     * @param entity The entity object from table
     * @return The updated entity object from table
     */
   Optional<T> update(T entity);
}
