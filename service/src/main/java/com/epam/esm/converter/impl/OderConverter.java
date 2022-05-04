package com.epam.esm.converter.impl;

import com.epam.esm.converter.Converter;
import com.epam.esm.dto.impl.OrderDto;
import com.epam.esm.entity.AbstractEntity;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OderConverter implements Converter<Order, OrderDto,Long> {

    @Override
    public Order convert(OrderDto value) {
        Double price = value.getPrice();
        LocalDateTime purchaseTime = LocalDateTime.parse(value.getPurchaseTime());
        List<GiftCertificate> giftCertificates = getGiftCertificatesList(value.getGiftCertificateId());
        User user = getNewUser(value.getUserId());
        return new Order(price,purchaseTime,giftCertificates,user);
    }

    private List<GiftCertificate> getGiftCertificatesList(List<Long> giftCertificateId){
        return giftCertificateId.stream().map(GiftCertificate::new).collect(Collectors.toList());
    }

    private List<Long> getGiftCertificatesIdList(List<GiftCertificate> giftCertificates){
        return giftCertificates.stream().map(AbstractEntity::getId).collect(Collectors.toList());
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
        List<Long> giftCertificatesIdList = getGiftCertificatesIdList(value.getGiftCertificateList());
        long userId = value.getUser().getId();
        return new OrderDto(id,price,purchaseDate,giftCertificatesIdList,userId);
    }
}
