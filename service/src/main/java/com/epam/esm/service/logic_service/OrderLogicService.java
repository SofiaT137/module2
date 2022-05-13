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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.exceptions.ExceptionErrorCode.*;

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
    public OrderLogicService(OrderDao orderDao) {
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

    @Override
    @Transactional
    public Order insert(Order entity) {
        if (entity.getUser().getId() != null) {
            User user = userLogicService.getById(entity.getUser().getId());
            entity.setUser(null);
            entity.setUser(user);
        }else{
            throw new NoPermissionException(USER_ID_CANNOT_BE_NULL_EXCEPTION_MESSAGE, INCORRECT_ID);
        }
        double totalCertificatePrice = saveGiftCertificatesToOrder(entity);
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
            GiftCertificate certificateById = giftCertificateLogicService.getById(giftCertificate.getId());
                entity.addGiftCertificateToOrder(certificateById);
                price+=certificateById.getPrice();
        }
        return price;
    }

    @Override
    @Transactional
    public void deleteByID(long id) {
        Optional<Order> receivedOrderById = orderDao.getById(id);
        if (!receivedOrderById.isPresent()){
            throw new NoSuchEntityException(NO_ORDER_WITH_THAT_ID_EXCEPTION_MESSAGE,NO_SUCH_ENTITY_CODE);
        }
        orderDao.deleteByID(id);
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

    @Override
    public List<Order> ordersByUserId(long userId,int pageSize, int pageNumber){
        User user = userLogicService.getById(userId);
        return orderDao.ordersByUserId(user.getId(),pageSize,pageNumber);
    }
}
