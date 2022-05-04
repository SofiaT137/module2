package com.epam.esm.service;

import com.epam.esm.entity.Order;

public interface OrderService<T> extends RService<T>{

    Order saveOrder(T entity);

    void deleteOrder(long orderId);
}
