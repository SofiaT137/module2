package com.epam.esm.hateoas;

import org.springframework.hateoas.RepresentationModel;

/**
 * Hateoas allows to add the HATEOAS logic to the RepresentationModel entity
 * @param <T>
 */
public interface Hateoas <T extends RepresentationModel<T>>  {

    /**
     * Method addLinks allows adding links to the RepresentationModel entity
     * @param entity RepresentationModel entity
     */
    void addLinks(T entity);
}
