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
    static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    static String dataAsString = formatter.format(DATA);
    private static final Double PRICE = 300.12;
    static List<GiftCertificate> giftCertificates = new ArrayList<>();
    static List<GiftCertificateDto> giftCertificatesDto = new ArrayList<>();
    static User user = new User(1L);
    Long id = user.getId();
    static Order order = new Order(1L,PRICE,DATA,user);
    static OrderDto orderDto = new OrderDto(1L,PRICE,dataAsString,giftCertificatesDto,user.getId());

    @BeforeAll
    static void setUp(){
        orderConverter = new OrderConverter(new GiftCertificateConverter(tagConverter));
        order.setGiftCertificateList(giftCertificates);
    }

    @Test
    void convertDTOEntityToEntity() {
        Order convertOrder = orderConverter.convert(orderDto);
        assertEquals(order,convertOrder);
    }

    @Test
    void convertEntityToDTOEntity(){
        OrderDto convertOrder = orderConverter.convert(order);
        assertEquals(orderDto,convertOrder);
    }

    @AfterAll
    static void destroy(){
        orderConverter = null;
        giftCertificateConverter = null;
    }
}