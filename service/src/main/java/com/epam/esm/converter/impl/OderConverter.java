package com.epam.esm.converter.impl;

import com.epam.esm.converter.Converter;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OderConverter implements Converter<Order, OrderDto> {

    @Autowired
    private Converter<GiftCertificate, GiftCertificateDto> giftCertificateConverter;

    @Override
    public Order convert(OrderDto value) {
        double price = 0.0;
        List<GiftCertificate> giftCertificates = value.getGiftCertificateId()
                .stream()
                .map(gs->giftCertificateConverter.convert(gs))
                .collect(Collectors.toList());
        User user = getNewUser(value.getUserId());
        return new Order(price, null,user);
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
                .map(gs->giftCertificateConverter.convert(gs))
                .collect(Collectors.toList());
        long userId = value.getUser().getId();
        return new OrderDto(id,price,purchaseDate,giftCertificates,userId);
    }
}

