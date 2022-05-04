package com.epam.esm.service.logic_service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exceptions.CannotInsertEntityException;
import com.epam.esm.exceptions.NoSuchEntityException;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.exceptions.ExceptionErrorCode.CANNOT_INSERT_ENTITY_CODE;
import static com.epam.esm.exceptions.ExceptionErrorCode.NO_SUCH_ENTITY_CODE;

@Service
public class OrderLogicService implements OrderService<Order> {

    private final OrderDao orderDao;
    private final UserDao userDao;
    private final GiftCertificateDao giftCertificateDao;

    @Autowired
    public OrderLogicService(OrderDao orderDao, UserDao userDao, GiftCertificateDao giftCertificateDao) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.giftCertificateDao = giftCertificateDao;
    }

    private static final String NO_ORDER_WITH_THAT_ID_EXCEPTION_MESSAGE = "No order with that id!";
    private static final String CANNOT_INSERT_THIS_ORDER_EXCEPTION_MESSAGE = "No order with that id!";
    private static final String NO_USER_WITH_THAT_ID_EXCEPTION_MESSAGE = "No user with that id!";

    @Override
    public Order saveOrder(long userId, Order entity) {
        Optional<User> user = userDao.getById(userId);
        if (!user.isPresent()){
            throw new NoSuchEntityException(NO_USER_WITH_THAT_ID_EXCEPTION_MESSAGE,NO_SUCH_ENTITY_CODE);
        }
        double totalCertificatePrice = saveGiftCertificatesToOrder(entity);
        entity.setUser(user.get());
        entity.setPrice(totalCertificatePrice);
        entity.setPurchaseTime(LocalDateTime.now());

        Optional<Order> insertedOrder = orderDao.insert(entity);
        if (!insertedOrder.isPresent()){
            throw new CannotInsertEntityException(CANNOT_INSERT_THIS_ORDER_EXCEPTION_MESSAGE,CANNOT_INSERT_ENTITY_CODE);
        }
        return insertedOrder.get();
    }

    private double saveGiftCertificatesToOrder(Order entity){
        double price = 0.0;
        List<GiftCertificate> list = entity.getGiftCertificateList();
        entity.setGiftCertificateList(null);
        for (GiftCertificate giftCertificate : list) {
            Optional<GiftCertificate> certificateById = giftCertificateDao.getById(giftCertificate.getId());
            if (!certificateById.isPresent()){
                throw new NoSuchEntityException(NO_USER_WITH_THAT_ID_EXCEPTION_MESSAGE,NO_SUCH_ENTITY_CODE);
            }else{
                entity.addGiftCertificateToOrder(certificateById.get());
                price+=certificateById.get().getPrice();
            }
        }
        return price;
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
