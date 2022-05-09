package com.epam.esm.converter.impl;

import com.epam.esm.converter.Converter;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderConverter implements Converter<Order, OrderDto> {

    private final Converter<GiftCertificate, GiftCertificateDto> giftCertificateConverter;

    @Autowired
    public OrderConverter(Converter<GiftCertificate, GiftCertificateDto> giftCertificateConverter) {
        this.giftCertificateConverter = giftCertificateConverter;
    }

    @Override
    public Order convert(OrderDto value) {
        Order order = new Order();
        LocalDateTime localDateTime = null;
        double price = 0.0;
        if (value.getPrice() != null){
            price = value.getPrice();
        }
        if (value.getPurchaseTime() != null){
            localDateTime = LocalDateTime.parse(value.getPurchaseTime());
        }
        List<GiftCertificate> giftCertificates = value.getGiftCertificateDto()
                .stream()
                .map(giftCertificateConverter::convert)
                .collect(Collectors.toList());
        User user = getNewUser(value.getUserId());
        order.setPrice(price);
        order.setPurchaseTime(localDateTime);
        order.setGiftCertificateList(giftCertificates);
        order.setUser(user);
        return order;
    }

    private User getNewUser(Long userId){
        User user = new User();
        user.setId(userId);
        return user;
    }

    @Override
    public OrderDto convert(Order value) {
        Long id = value.getId();
        double price = value.getPrice();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String purchaseDate = value.getPurchaseTime().format(formatter);
        List<GiftCertificateDto> giftCertificates = value.getGiftCertificateList()
                .stream()
                .map(giftCertificateConverter::convert)
                .collect(Collectors.toList());
        long userId = value.getUser().getId();
        return new OrderDto(id,price,purchaseDate,giftCertificates,userId);
    }
}

