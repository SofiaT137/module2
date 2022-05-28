package com.epam.esm.service;

/**
 * CRDService interface features CRUD Service functionality and extends CRDService functionality
 * @param <T> The entity object
 */
public interface CRUDService<T> extends CRDService<T>{

    /**
     * The method provides a service layer functionality for updating the entity object by its identifier
     * @param id Object id(Long value)
     * @param entity The entity object
     */
    int update(Long id, T entity);
}
