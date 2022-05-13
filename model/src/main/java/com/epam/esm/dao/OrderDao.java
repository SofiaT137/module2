package com.epam.esm.dao;

import com.epam.esm.entity.Order;

import java.util.List;

/**
 * OrderDao interface implements CRUDDao functionality and adds a special Order DAO functionality
 */
public interface OrderDao extends CRDDao<Order> {

    /**
     * The method searches all user orders in a 'orders' table
     * @param userId User id (Long value)
     * @param pageSize int page size
     * @param pageNumber int page number
     * @return List of user orders
     */
    List<Order> ordersByUserId(long userId, int pageSize, int pageNumber);

}
