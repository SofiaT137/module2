package com.epam.esm.service;

import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.ServiceException;

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
    void insert(T entity) throws ServiceException, DaoException;

    /**
     * The method provides service layer logic for searching the entity object by its identifier
     * @param id Long id
     * @return The entity object
     */
    T getById(long id) throws DaoException, ServiceException;

    /**
     * The method provides service layer logic for searching all the entity objects
     * @return List of the entity objects
     */
    List<T> getAll() throws DaoException;

    /**
     * The method provides service layer logic for removing the entity object by its identifier
     * @param id Long id
     */
    void deleteByID(long id) throws DaoException, ServiceException;
}
