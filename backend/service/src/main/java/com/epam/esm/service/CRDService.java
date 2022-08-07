package com.epam.esm.service;

/**
 * CRDService interface features CRD Service functionality and extends CRService functionality
 * @param <T> The entity object
 */
public interface CRDService<T> extends CRService<T>{

    /**
     * The method provides service layer functionality for removing the entity object by its identifier
     * @param id Long id
     */
    void deleteByID(long id);

}
