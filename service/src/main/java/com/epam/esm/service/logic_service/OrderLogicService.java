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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.exceptions.ExceptionErrorCode.CANNOT_INSERT_ENTITY_CODE;
import static com.epam.esm.exceptions.ExceptionErrorCode.NO_SUCH_ENTITY_CODE;

@Service
public class OrderLogicService implements OrderService<Order> {

    private final UserDao userDao;
    private final OrderDao orderDao;
    private final GiftCertificateDao giftCertificateDao;

    @Autowired
    public OrderLogicService(UserDao userDao, OrderDao orderDao, GiftCertificateDao giftCertificateDao) {
        this.userDao = userDao;
        this.orderDao = orderDao;
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    @Transactional
    public Order saveOrder(long userId, Order entity) {
        Optional<User> user = userDao.getById(userId);
        if (!user.isPresent()){
            throw new NoSuchEntityException("No user with that id!",NO_SUCH_ENTITY_CODE);
        }
        List<GiftCertificate> list = entity.getGiftCertificateList();
        entity.setGiftCertificateList(null);
        Double price = 0.0;
        for (GiftCertificate giftCertificate : list) {
            Optional<GiftCertificate> certificateById = giftCertificateDao.getById(giftCertificate.getId());
            if (!certificateById.isPresent()){
                throw new NoSuchEntityException("No certificate with that id!",NO_SUCH_ENTITY_CODE);
            }else{
                entity.addGiftCertificateToOrder(certificateById.get());
                price+=certificateById.get().getPrice();
            }
        }
        entity.setUser(user.get());
        entity.setPrice(price);
        entity.setPurchaseTime(LocalDateTime.now());
        Optional<Order> insertedOrder = orderDao.insert(entity);
        if (!insertedOrder.isPresent()){
            throw new CannotInsertEntityException("Cannot insert this order!",CANNOT_INSERT_ENTITY_CODE);
        }
        return insertedOrder.get();
    }

    @Override
    @Transactional
    public void deleteOrder(long orderId) {
        Optional<Order> receivedOrderById = orderDao.getById(orderId);
        if (!receivedOrderById.isPresent()){
            throw new NoSuchEntityException("No order with that id!",NO_SUCH_ENTITY_CODE);
        }
        orderDao.deleteByID(orderId);
    }

    @Override
    public Order getById(long id) {
        Optional<Order> receivedOrderById = orderDao.getById(id);
        if (!receivedOrderById.isPresent()){
            throw new NoSuchEntityException("No order with that id!",NO_SUCH_ENTITY_CODE);
        }
        return receivedOrderById.get();
    }

    @Override
    public List<Order> getAll(int pageSize, int pageNumber) {
        return orderDao.getAll(pageSize,pageNumber);
    }

}
