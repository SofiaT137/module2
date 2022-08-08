package com.epam.esm.hateoas.impl;

import com.epam.esm.controllers.GiftCertificateController;
import com.epam.esm.controllers.OrderController;
import com.epam.esm.controllers.TagController;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.hateoas.Hateoas;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * OrderHateoas class allows to add the HATEOAS logic to the OrderDto entity
 */
@Component
public class OrderHateoas implements Hateoas<OrderDto> {

    private static final Class<OrderController> ORDER_CONTROLLER = OrderController.class;
    private static final Class<GiftCertificateController> GIFT_CERTIFICATE_CONTROLLER = GiftCertificateController.class;
    private static final Class<TagController> TAG_CONTROLLER = TagController.class;

    @Override
    public void addLinks(OrderDto entity) {
        entity.add(linkTo(methodOn(ORDER_CONTROLLER).getOrderByID(entity.getId())).withSelfRel());
        entity.getGiftCertificateDto().forEach(
                giftCertificateDto -> giftCertificateDto.add(linkTo(methodOn(GIFT_CERTIFICATE_CONTROLLER)
                        .getCertificateByID(giftCertificateDto.getId())).withSelfRel()));
        entity.getGiftCertificateDto().forEach(
                giftCertificateDto -> giftCertificateDto.getTags().forEach(tagDto -> tagDto
                        .add(linkTo(methodOn(TAG_CONTROLLER)
                        .getTagById(tagDto.getId())).withSelfRel())));
    }
}
