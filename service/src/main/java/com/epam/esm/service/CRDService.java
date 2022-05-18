package com.epam.esm.service;

/**
 * CRDService interface features CRD Service functionality and extends RDao functionality
 * @param <T> The entity object
 */
public interface CRDService<T> extends RService<T>{

    /**
     * The method provides service layer functionality for inserting the entity object
     * @param entity The entity object
     */
    T insert(T entity);


    /**
     * The method provides service layer functionality for removing the entity object by its identifier
     * @param id Long id
     */
    void deleteByID(long id);
}
