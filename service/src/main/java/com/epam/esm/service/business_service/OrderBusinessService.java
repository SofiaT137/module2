package com.epam.esm.service.business_service;

import com.epam.esm.converter.impl.OderConverter;
import com.epam.esm.dto.impl.OrderDto;
import com.epam.esm.entity.Order;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("orderBusinessService")
public class OrderBusinessService implements OrderService<OrderDto> {

    private final OderConverter orderConverter;
    private OrderService<Order> orderLogicService;

    public OrderBusinessService(OderConverter oderConverter) {
        this.orderConverter = oderConverter;
    }

    @Autowired
    @Qualifier("orderLogicService")
    public void setOrderLogicService(OrderService<Order> orderLogicService) {
        this.orderLogicService = orderLogicService;
    }

    @Override
    public Order saveOrder(OrderDto entity) {
        Order convertOrder = orderConverter.convert(entity);
        orderLogicService.saveOrder(convertOrder);
        return convertOrder;
    }

    @Override
    public void deleteOrder(long orderId) {
        orderLogicService.deleteOrder(orderId);
    }

    @Override
    public OrderDto getById(long id) {
        Order order = orderLogicService.getById(id);
        return orderConverter.convert(order);
    }

    @Override
    public List<OrderDto> getAll(int pageSize, int pageNumber) {
        List<Order> orderList = orderLogicService.getAll(pageSize, pageNumber);
        return orderList.stream().map(orderConverter::convert).collect(Collectors.toList());
    }
}
