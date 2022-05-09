package com.epam.esm.hateoas.impl;

import com.epam.esm.controllers.GiftCertificateController;
import com.epam.esm.controllers.OrderController;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.hateoas.Hateoas;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderHateoas implements Hateoas<OrderDto> {

    private static final Class<OrderController> ORDER_CONTROLLER = OrderController.class;
    private static final Class<GiftCertificateController> GIFT_CERTIFICATE_CONTROLLER = GiftCertificateController.class;

    @Override
    public void addLinks(OrderDto entity) {
        entity.add(linkTo(methodOn(ORDER_CONTROLLER).getOrderByID(entity.getId())).withSelfRel());
        entity.getGiftCertificateDto().forEach(
                giftCertificateDto -> giftCertificateDto.add(linkTo(methodOn(GIFT_CERTIFICATE_CONTROLLER)
                        .getCertificateByID(giftCertificateDto.getId())).withSelfRel()));
    }
}
