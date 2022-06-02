package com.epam.esm.service.logic_service;

import com.epam.esm.configuration.AuditConfiguration;
import com.epam.esm.entity.*;
import com.epam.esm.pagination.Pagination;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.RoleRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class OrderLogicServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService<User> userLogicService;

    @Mock
    private AuditConfiguration auditConfiguration;

    @Mock
    private Pagination<Order> pagination;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private GiftCertificateService<GiftCertificate> giftCertificateLogicService;

    @InjectMocks
    private OrderLogicService orderLogicService;

    private static final String LOGIN = "AlexRendal";
    private static final int PAGE_NUMBER = 0;
    private static final int PAGE_SIZE = 1;
    private static final long ID = 1L;
    private static final List<Tag> tagList = Collections.singletonList(new Tag("hello"));
    private static GiftCertificate GIFT_CERTIFICATE;
    private static Order ORDER;
    private static Role ROLE;
    private static User USER;

    @BeforeEach
    void setUserLogicService(){
        orderLogicService.setUserLogicService(userLogicService);
        orderLogicService.setGiftCertificateLogicService(giftCertificateLogicService);
    }

    @BeforeAll
    static void init(){
        ROLE =  new Role(ID, "ROLE_ADMINISTRATOR");
        USER = new User(ID,LOGIN,"sss");
        GIFT_CERTIFICATE = new GiftCertificate(ID, "abc","abc", 50.00, 13,  tagList);
        ORDER = new Order();
        ORDER.addUserToOrder(USER);
        ORDER.addGiftCertificateToOrder(GIFT_CERTIFICATE);
        ORDER.setPrice(GIFT_CERTIFICATE.getPrice());
        ORDER.setId(1L);
        USER.addRoleToUser(ROLE);
    }


    @Test
    void insertOrder() {
        Mockito.when(auditConfiguration.getCurrentUser()).thenReturn(USER.getLogin());
        Mockito.when(userLogicService.findUserByUserLogin(USER.getLogin())).thenReturn(USER);
        Mockito.when(giftCertificateLogicService.getById(ID)).thenReturn(GIFT_CERTIFICATE);
        Mockito.when(orderRepository.save(ORDER)).thenReturn(ORDER);
        Order order1 = orderLogicService.insert(ORDER);
        assertEquals(ORDER,order1);
    }

    @Test
    void deleteOrder() {
        Mockito.when(orderRepository.findById(ID)).thenReturn(Optional.of(ORDER));
        orderLogicService.deleteByID(ORDER.getId());
        Mockito.verify(orderRepository, Mockito.times(1)).delete(ORDER);
    }

    @Test
    void getById() {
        Mockito.when(orderRepository.findById(ORDER.getId())).thenReturn(Optional.of(ORDER));
        Order foundOrder = orderLogicService.getById(ORDER.getId());
        assertEquals(ORDER,foundOrder);
    }

    @Test
    void getAll() {
        Page<Order> page = new PageImpl<>(new ArrayList<>(Collections.singletonList(ORDER)));
        Mockito.when(auditConfiguration.getCurrentUser()).thenReturn(USER.getLogin());
        Mockito.when(userLogicService.findUserByUserLogin(LOGIN)).thenReturn(USER);
        Mockito.when(roleRepository.findById(ID)).thenReturn(Optional.of(ROLE));
        Mockito.when(pagination.checkHasContent(orderRepository.findAll(PageRequest.of(PAGE_NUMBER,PAGE_SIZE))))
                .thenReturn(page);
        Page<Order> orderList = orderLogicService.getAll(PAGE_NUMBER,PAGE_SIZE);
        assertEquals(ID,orderList.getTotalElements());
    }

    @Test
    void ordersByUserId() {
        Page<Order> page = new PageImpl<>(new ArrayList<>(Collections.singletonList(ORDER)));
        Mockito.when(userLogicService.getById(ID)).thenReturn(USER);
        Mockito.when(pagination.checkHasContent(orderRepository.
                        findAllOrderWhereUserId(ID, PageRequest.of(PAGE_NUMBER,PAGE_SIZE)))).thenReturn(page);
        Page<Order> orders = orderLogicService.ordersByUserId(ID,PAGE_NUMBER,PAGE_SIZE);
        assertEquals(1,orders.getTotalElements());
    }
}