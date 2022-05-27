package com.epam.esm.service;

import java.util.List;

/**
 * RService interface features R Service functionality
 * @param <T> The entity object
 */
public interface RService<T>{
    /**
     * The method provides service layer functionality for searching the entity object by its identifier
     * @param id Long id
     * @return The entity object
     */
    T getById(long id);

    /**
     * The method provides service layer functionality for searching all the entity objects
     * @return List of the entity objects
     */
    List<T> getAll(int pageSize, int pageNumber);
    List<T> getAll();
}
