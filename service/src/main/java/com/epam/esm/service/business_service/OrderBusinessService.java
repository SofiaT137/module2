package com.epam.esm.service.business_service;

import com.epam.esm.converter.impl.OrderConverter;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class OrderBusinessService is implementation of the OrderService interface
 * The class presents service business logic for Order entity
 */
@Service("orderBusinessService")
public class OrderBusinessService implements OrderService<OrderDto> {

    private final OrderConverter orderConverter;
    private OrderService<Order> orderLogicService;

    @Autowired
    public OrderBusinessService(OrderConverter orderConverter) {
        this.orderConverter = orderConverter;
    }

    @Autowired
    @Qualifier("orderLogicService")
    public void setOrderLogicService(OrderService<Order> orderLogicService) {
        this.orderLogicService = orderLogicService;
    }

    @Override
    public OrderDto insert(OrderDto entity) {
        Order convertOrder = orderConverter.convert(entity);
        orderLogicService.insert(convertOrder);
        return orderConverter.convert(convertOrder);
    }

    @Override
    public void deleteByID(long id) {
        orderLogicService.deleteByID(id);
    }

    @Override
    public List<OrderDto> ordersByUserId(long userId, int pageSize, int pageNumber) {
        List<Order> orderList = orderLogicService.ordersByUserId(userId, pageSize, pageNumber);
        return orderList.stream().map(orderConverter::convert).collect(Collectors.toList());
    }


    @Override
    public OrderDto getById(long id) {
        Order order = orderLogicService.getById(id);
        return orderConverter.convert(order);
    }

    @Override
    public Page<OrderDto> getAll(int pageSize, int pageNumber) {
        Page<Order> orderList = orderLogicService.getAll(pageSize, pageNumber);
        return orderList.map(orderConverter::convert);
    }
}
