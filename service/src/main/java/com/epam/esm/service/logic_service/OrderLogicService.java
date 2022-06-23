package com.epam.esm.service.logic_service;

import com.epam.esm.configuration.AuditConfiguration;
import com.epam.esm.entity.Role;
import com.epam.esm.pagination.Pagination;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exceptions.NoSuchEntityException;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class OrderLogicService is implementation of interface OrderService
 * The class presents service logic layer for Order entity
 */
@Service("orderLogicService")
public class OrderLogicService implements OrderService<Order> {

    private final OrderRepository orderRepository;
    private UserService<User> userLogicService;
    private GiftCertificateService<GiftCertificate> giftCertificateLogicService;
    private final AuditConfiguration auditConfiguration;
    private final RoleRepository roleRepository;
    private final Pagination<Order> pagination;

    @Autowired
    public OrderLogicService(OrderRepository orderRepository, AuditConfiguration auditConfiguration,
                             RoleRepository roleRepository, Pagination<Order> pagination){
        this.orderRepository = orderRepository;
        this.auditConfiguration = auditConfiguration;
        this.roleRepository = roleRepository;
        this.pagination = pagination;
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
    private static final String NO_ROLE_WITH_THAT_ID_EXCEPTION_MESSAGE = "noRoleWithThatId";
    private static final String USER_HAVE_NOT_ANY_ORDERS_EXCEPTION_MESSAGE = "userDoesNotHaveAnyOrders";


    @Override
    @Transactional
    public Order insert(Order entity) {
        User user = getUser();
        entity.addUserToOrder(user);
        entity.setPrice(saveGiftCertificatesToOrder(entity));
        return orderRepository.save(entity);
    }

    private User getUser(){
       String currentUserLogin = auditConfiguration.getCurrentUser();
       return userLogicService.findUserByUserLogin(currentUserLogin);
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
        Optional<Order> receivedOrderById = orderRepository.findById(id);
        if (!receivedOrderById.isPresent()){
            throw new NoSuchEntityException(NO_ORDER_WITH_THAT_ID_EXCEPTION_MESSAGE);
        }
        orderRepository.delete(receivedOrderById.get());
    }

    @Override
    public Order getById(long id) {
        Optional<Order> receivedOrderById = orderRepository.findById(id);
        if (!receivedOrderById.isPresent()){
            throw new NoSuchEntityException(NO_ORDER_WITH_THAT_ID_EXCEPTION_MESSAGE);
        }
        return receivedOrderById.get();
    }

    @Override
    public Page<Order> getAll(int pageNumber, int pageSize) {
        User currentUser = getUser();
        Optional<Role> role = roleRepository.findById(1L);
        if (!role.isPresent()) {
            throw new NoSuchEntityException(NO_ROLE_WITH_THAT_ID_EXCEPTION_MESSAGE);
        }
        if (currentUser.getRoles().contains(role.get())) {
            return pagination.checkHasContent(orderRepository.findAll(PageRequest.of(pageNumber, pageSize)));
        } else {
            return pagination.checkHasContent(orderRepository.findAllOrderWhereUserId(currentUser.getId(),
                    PageRequest.of(pageNumber, pageSize)));
        }
    }



    @Override
    public Page<Order> ordersByUserId(long userId, int pageNumber,int pageSize){
        User user = userLogicService.getById(userId);
        if (user.getOrderList().isEmpty()){
            throw new NoSuchEntityException(USER_HAVE_NOT_ANY_ORDERS_EXCEPTION_MESSAGE);
        }
        return pagination.checkHasContent(orderRepository.
                findAllOrderWhereUserId(user.getId(),PageRequest.of(pageNumber,pageSize)));
    }


}
