package com.epam.esm.service;

import org.springframework.data.domain.Page;


/**
 * OrderService interface features Order Service functionality and extends CRDService functionality
 * @param <T> The entity object
 */
public interface OrderService<T> extends CRDService<T>{

    /**
     * The method provides a service layer functionality for searching all the user orders
     * @param userId User id (Long value)
     * @param pageNumber int page number
     * @param pageSize int page size
     * @return Page Of Order or OrderDto entities
     */
    Page<T> ordersByUserId(long userId,int pageNumber, int pageSize);
}
