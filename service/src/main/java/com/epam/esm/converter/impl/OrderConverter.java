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
import java.util.List;
import java.util.stream.Collectors;


/**
 * Class OrderConverter presents converter for Order and OrderDto entities
 */
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
        double price = value.getPrice() != null ? value.getPrice() : 0.0;
        List<GiftCertificate> giftCertificates = convertGiftCertificateDtoList(value.getGiftCertificateDto());
        User user = getNewUser(value.getUserId());
        order.setPrice(price);
        order.setGiftCertificateList(giftCertificates);
        order.setUser(user);
        return order;
    }

    private User getNewUser(Long userId){
        User user = new User();
        user.setId(userId);
        return user;
    }

    private List<GiftCertificate> convertGiftCertificateDtoList(List<GiftCertificateDto> list){
        return list.stream()
                .map(giftCertificateConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto convert(Order value) {
        Long id = value.getId();
        double price = value.getPrice();
        LocalDateTime purchaseDate = value.getCreatedDate();
        List<GiftCertificateDto> giftCertificates = convertGiftCertificateList(value.getGiftCertificateList());
        long userId = value.getUser().getId();
        return new OrderDto(id,price,purchaseDate,giftCertificates,userId);
    }


    private List<GiftCertificateDto> convertGiftCertificateList(List<GiftCertificate> list){
        return list.stream()
                .map(giftCertificateConverter::convert)
                .collect(Collectors.toList());
    }
}

