package com.epam.esm.service;

import com.epam.esm.entity.Order;

import java.util.List;

public interface OrderService<T> extends RService<T>{

    Order insertOrder(T entity);

    void deleteOrder(long orderId);

    List<T> ordersByUserId(long userId,int pageSize, int pageNumber);
}
