package com.epam.esm.hateoas.impl;

import com.epam.esm.controllers.GiftCertificateController;
import com.epam.esm.controllers.TagController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.hateoas.Hateoas;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * GiftCertificateHateoas class allows to add the HATEOAS logic to the GiftCertificateDto entity
 */
@Component
public class GiftCertificateHateoas implements Hateoas<GiftCertificateDto> {

    private static final Class<GiftCertificateController> GIFT_CERTIFICATE_CONTROLLER = GiftCertificateController.class;
    private static final Class<TagController> TAG_CONTROLLER = TagController.class;

    @Override
    public void addLinks(GiftCertificateDto entity) {
        entity.add(linkTo(methodOn(GIFT_CERTIFICATE_CONTROLLER).
                getCertificateByID(entity.getId())).withSelfRel());
        entity.getTags().forEach(
                tagDto -> tagDto.add(linkTo(methodOn(TAG_CONTROLLER).getTagById(tagDto.getId())).withSelfRel()));
    }
}
