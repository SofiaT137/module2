package com.epam.esm.service;

import org.springframework.data.domain.Page;

public interface CRService<T> {
    /**
     * The method provides service layer functionality for inserting the entity object
     * @param entity The entity object
     */
    T insert(T entity);

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
    Page<T> getAll(int pageSize, int pageNumber);
}
