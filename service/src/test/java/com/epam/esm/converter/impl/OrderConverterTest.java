package com.epam.esm.converter.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class,MockitoExtension.class})
@SpringBootTest(classes = {OrderConverter.class,GiftCertificateConverter.class,TagConverter.class})
class OrderConverterTest {

    private static OrderConverter orderConverter;

    @InjectMocks
    private static GiftCertificateConverter giftCertificateConverter;

    @Spy
    private static final TagConverter tagConverter = Mockito.spy(TagConverter.class);

    private static final LocalDateTime DATA = LocalDateTime.now();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    private static final String dataAsString = formatter.format(DATA);
    private static final Double PRICE = 300.12;
    private static final List<GiftCertificate> giftCertificates = new ArrayList<>();
    private static final List<GiftCertificateDto> giftCertificatesDto = new ArrayList<>();
    private static final Long USER_ID = 1L;
    private static final Long ORDER_ID = 1L;
    private static User user;
    private static Order order;
    private static OrderDto orderDto;

    @BeforeAll
    static void setUp(){
        orderConverter = new OrderConverter(new GiftCertificateConverter(tagConverter));
        user = new User(USER_ID);
        order = new Order(ORDER_ID,PRICE,DATA,user);
        orderDto = new OrderDto(ORDER_ID,PRICE,dataAsString,giftCertificatesDto,USER_ID);
        order.setGiftCertificateList(giftCertificates);
    }

    @Test
    void convertDTOEntityToEntity() {
        Order convertOrder = orderConverter.convert(orderDto);
        assertEquals(order,convertOrder);
    }

    @Test
    void convertEntityToDTOEntity(){
        OrderDto convertOrderDto = orderConverter.convert(order);
        assertEquals(orderDto,convertOrderDto);
    }

    @AfterAll
    static void destroy(){
        orderConverter = null;
        giftCertificateConverter = null;
    }
}