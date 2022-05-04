package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.CRDService;

import java.util.List;

/**
 * CRUDService interface features CRUD Service functionality and extends CRDService
 * @param <T> The entity object
 */
public interface CRUDService<T> extends CRDService<T> {
    /**
     * The method provides service layer logic for updating the entity object
     * @param id  Long id
     * @param entity The entity object
     */
    void update(Long id, T entity);
}
