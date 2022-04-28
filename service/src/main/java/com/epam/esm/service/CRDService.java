package com.epam.esm.service;

import com.epam.esm.exceptions.ValidatorException;

import java.util.List;

/**
 * CRDService interface features CRD Service functionality
 * @param <T> The entity object
 */
public interface CRDService<T> {

    /**
     * The method provides service layer logic for inserting the entity object
     * @param entity The entity object
     */
    void insert(T entity);

    /**
     * The method provides service layer logic for searching the entity object by its identifier
     * @param id Long id
     * @return The entity object
     */
    T getById(long id);

    /**
     * The method provides service layer logic for searching all the entity objects
     * @return List of the entity objects
     */
    List<T> getAll();

    /**
     * The method provides service layer logic for removing the entity object by its identifier
     * @param id Long id
     */
    void deleteByID(long id);
}
