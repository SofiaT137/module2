package com.epam.esm.service.logic_service;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exceptions.CannotInsertEntityException;
import com.epam.esm.exceptions.NoPermissionException;
import com.epam.esm.exceptions.NoSuchEntityException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class OrderLogicService is implementation of interface OrderService
 * The class presents service logic layer for Order entity
 */
@Service("orderLogicService")
public class OrderLogicService implements OrderService<Order> {

    private final OrderDao orderDao;
    private UserService<User> userLogicService;
    private GiftCertificateService<GiftCertificate> giftCertificateLogicService;

    @Autowired
    public OrderLogicService(OrderDao orderDao){
        this.orderDao = orderDao;
    }

    @Autowired
    @Qualifier("userLogicService")
    public void setUserLogicService(UserService<User> userLogicService) {
        this.userLogicService = userLogicService;
    }

    @Autowired
    @Qualifier("giftCertificateLogicService")
    public void setGiftCertificateLogicService(GiftCertificateService<GiftCertificate> giftCertificateLogicService) {
        this.giftCertificateLogicService = giftCertificateLogicService;
    }

    private static final String NO_ORDER_WITH_THAT_ID_EXCEPTION_MESSAGE = "noOrderWithThatId";
    private static final String CANNOT_INSERT_THIS_ORDER_EXCEPTION_MESSAGE = "cannotInsertThisOrder!";
    private static final String NO_USER_WITH_THAT_ID_EXCEPTION_MESSAGE = "noUserWithId";
    private static final String NO_GIFT_CERTIFICATE_WITH_THAT_ID_EXCEPTION_MESSAGE = "noGiftCertificateWithThatId";
    private static final String USER_ID_CANNOT_BE_NULL_EXCEPTION_MESSAGE = "userIdCannotBeNull";
    private static final String USER_HAVE_NOT_ANY_ORDERS_EXCEPTION_MESSAGE = "userDoesNotHaveAnyOrders";


    @Override
    @Transactional
    public Order insert(Order entity) {
        User user = getUser(entity);
        entity.addUserToOrder(user);
        entity.setPrice(saveGiftCertificatesToOrder(entity));
        entity.setPurchaseTime(LocalDateTime.now());
        return orderDao.save(entity);
    }

    private User getUser(Order entity){
        User user;
        if (entity.getUser().getId() != null) {
            user = userLogicService.getById(entity.getUser().getId());
        }else{
            throw new NoPermissionException(USER_ID_CANNOT_BE_NULL_EXCEPTION_MESSAGE);
        }
        return user;
    }

    private double saveGiftCertificatesToOrder(Order entity){
        double price = 0.0;
        List<GiftCertificate> list = entity.getGiftCertificateList();
        entity.setGiftCertificateList(new ArrayList<>());
        for (GiftCertificate giftCertificate : list) {
            GiftCertificate certificateById = giftCertificateLogicService.getById(giftCertificate.getId());
                entity.addGiftCertificateToOrder(certificateById);
                price+=certificateById.getPrice();
        }
        return price;
    }

    @Override
    @Transactional
    public void deleteByID(long id) {
        Optional<Order> receivedOrderById = orderDao.findById(id);
        if (!receivedOrderById.isPresent()){
            throw new NoSuchEntityException(NO_ORDER_WITH_THAT_ID_EXCEPTION_MESSAGE);
        }
        orderDao.delete(receivedOrderById.get());
    }

    @Override
    public Order getById(long id) {
        Optional<Order> receivedOrderById = orderDao.findById(id);
        if (!receivedOrderById.isPresent()){
            throw new NoSuchEntityException(NO_ORDER_WITH_THAT_ID_EXCEPTION_MESSAGE);
        }
        return receivedOrderById.get();
    }

    @Override
    public Page<Order> getAll(int pageSize, int pageNumber) {
        return orderDao.findAll(PageRequest.of(pageSize,pageNumber));
    }

    @Override
    public List<Order> ordersByUserId(long userId, int pageSize, int pageNumber){
        User user = userLogicService.getById(userId);
        if (user.getOrderList().isEmpty()){
            throw new NoSuchEntityException(USER_HAVE_NOT_ANY_ORDERS_EXCEPTION_MESSAGE);
        }
        return orderDao.findAllByUserId(user.getId(),PageRequest.of(pageSize,pageNumber));
    }

}
