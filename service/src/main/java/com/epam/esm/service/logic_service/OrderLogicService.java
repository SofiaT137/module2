package com.epam.esm.service.logic_service;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import com.epam.esm.exceptions.CannotInsertEntityException;
import com.epam.esm.exceptions.NoSuchEntityException;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.exceptions.ExceptionErrorCode.CANNOT_INSERT_ENTITY_CODE;
import static com.epam.esm.exceptions.ExceptionErrorCode.NO_SUCH_ENTITY_CODE;

@Service("orderLogicService")
public class OrderLogicService implements OrderService<Order> {

    private final OrderDao orderDao;

    @Autowired
    public OrderLogicService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }
    private static final String NO_ORDER_WITH_THAT_ID_EXCEPTION_MESSAGE = "No order with that id!";
    private static final String CANNOT_INSERT_THIS_ORDER_EXCEPTION_MESSAGE = "No order with that id!";

    @Override
    public Order saveOrder(long userId, Order entity) {
        Optional<Order> insertedOrder = orderDao.insert(entity);
        if (!insertedOrder.isPresent()){
            throw new CannotInsertEntityException(CANNOT_INSERT_THIS_ORDER_EXCEPTION_MESSAGE,CANNOT_INSERT_ENTITY_CODE);
        }
        return insertedOrder.get();
    }

    @Override
    public void deleteOrder(long orderId) {
        Optional<Order> receivedOrderById = orderDao.getById(orderId);
        if (!receivedOrderById.isPresent()){
            throw new NoSuchEntityException(NO_ORDER_WITH_THAT_ID_EXCEPTION_MESSAGE,NO_SUCH_ENTITY_CODE);
        }
        orderDao.deleteByID(orderId);
    }

    @Override
    public Order getById(long id) {
        Optional<Order> receivedOrderById = orderDao.getById(id);
        if (!receivedOrderById.isPresent()){
            throw new NoSuchEntityException(NO_ORDER_WITH_THAT_ID_EXCEPTION_MESSAGE,NO_SUCH_ENTITY_CODE);
        }
        return receivedOrderById.get();
    }

    @Override
    public List<Order> getAll(int pageSize, int pageNumber) {
        return orderDao.getAll(pageSize,pageNumber);
    }

}
