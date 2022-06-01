package com.epam.esm.service.business_service;

import com.epam.esm.converter.impl.OrderConverter;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import com.epam.esm.service.OrderService;
import com.epam.esm.validator.ValidationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
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
    private final ValidationFacade validationFacade;
    private OrderService<Order> orderLogicService;

    @Autowired
    public OrderBusinessService(OrderConverter orderConverter, ValidationFacade validationFacade) {
        this.orderConverter = orderConverter;
        this.validationFacade = validationFacade;
    }

    @Autowired
    @Qualifier("orderLogicService")
    public void setOrderLogicService(OrderService<Order> orderLogicService) {
        this.orderLogicService = orderLogicService;
    }

    @Override
    public OrderDto insert(OrderDto entity) {
        validationFacade.validate(entity);
        Order convertOrder = orderConverter.convert(entity);
        orderLogicService.insert(convertOrder);
        return orderConverter.convert(convertOrder);
    }

    @Override
    public void deleteByID(long id) {
        orderLogicService.deleteByID(id);
    }

    @Override
    public Page<OrderDto> ordersByUserId(long userId, int pageNumber, int pageSize) {
        Page<Order> orderList = orderLogicService.ordersByUserId(userId, pageNumber, pageSize);
        return orderList.map(orderConverter::convert);
    }


    @Override
    public OrderDto getById(long id) {
        Order order = orderLogicService.getById(id);
        return orderConverter.convert(order);
    }

    @Override
    public Page<OrderDto> getAll(int pageNumber, int pageSize ) {
        Page<Order> orderList = orderLogicService.getAll(pageNumber,pageSize);
        return orderList.map(orderConverter::convert);
    }
}
