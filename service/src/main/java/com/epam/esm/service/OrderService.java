package com.epam.esm.service;

import com.epam.esm.entity.Order;

import java.util.List;

/**
 * OrderService interface features Order Service functionality and extends CRDService functionality
 * @param <T> The entity object
 */
public interface OrderService<T> extends CRDService<T>{

    /**
     * The method provides a service layer functionality for searching all the user orders
     * @param userId User id (Long value)
     * @param pageSize int page size
     * @param pageNumber int page number
     * @return List Of Order or OrderDto entities
     */
    List<T> ordersByUserId(long userId,int pageSize, int pageNumber);
}
