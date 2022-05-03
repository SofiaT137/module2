package com.epam.esm.service;

import java.util.List;

public interface RService<T>{
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
    List<T> getAll(int pageSize, int pageNumber);
}
