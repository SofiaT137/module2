package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

public interface OrderDao extends CRUDDao<Order> {

    Optional<Double> getTheHighestCostOfAllUserOrder(User user);

    List<Order> getInfoAboutAllUserOrder(User user);
}
