package com.epam.esm.service.business_service;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exceptions.NoSuchEntityException;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.epam.esm.exceptions.ExceptionErrorCode.NO_SUCH_ENTITY_CODE;

@Service("orderBusinessService")
public class OrderBusinessService implements OrderService<Order> {

    private final UserDao userDao;
    private final GiftCertificateDao giftCertificateDao;
    private OrderService<Order> orderLogicService;

    private static final String NO_USER_WITH_THAT_ID_EXCEPTION_MESSAGE = "No user with that id!";

    @Autowired
    public OrderBusinessService(UserDao userDao, GiftCertificateDao giftCertificateDao) {
        this.userDao = userDao;
        this.giftCertificateDao = giftCertificateDao;
    }

    @Autowired
    @Qualifier("orderLogicService")
    public void setOrderLogicService(OrderService<Order> orderLogicService) {
        this.orderLogicService = orderLogicService;
    }

    @Override
    @Transactional
    public Order saveOrder(long userId, Order entity) {
        Optional<User> user = userDao.getById(userId);
        if (!user.isPresent()){
            throw new NoSuchEntityException(NO_USER_WITH_THAT_ID_EXCEPTION_MESSAGE,NO_SUCH_ENTITY_CODE);
        }
        double totalCertificatePrice = saveGiftCertificatesToOrder(entity);
        entity.setUser(user.get());
        entity.setPrice(totalCertificatePrice);
        entity.setPurchaseTime(LocalDateTime.now());
        return orderLogicService.saveOrder(userId,entity);
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
    @Transactional
    public void deleteOrder(long orderId) {
        orderLogicService.deleteOrder(orderId);
    }

    @Override
    public Order getById(long id) {
        return orderLogicService.getById(id);
    }

    @Override
    public List<Order> getAll(int pageSize, int pageNumber) {
        return orderLogicService.getAll(pageSize,pageNumber);
    }
}
