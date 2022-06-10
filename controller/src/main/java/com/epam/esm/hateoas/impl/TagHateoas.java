package com.epam.esm.hateoas.impl;

import com.epam.esm.controllers.TagController;
import com.epam.esm.dto.TagDto;
import com.epam.esm.hateoas.Hateoas;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * TagHateoas class allows to add the HATEOAS logic to the TagDto entity
 */
@Component
public class TagHateoas implements Hateoas<TagDto> {

    private static final Class<TagController> controller = TagController.class;

    @Override
    public void addLinks(TagDto entity) {
        entity.add(linkTo(methodOn(controller).getTagById(entity.getId())).withSelfRel());
    }
}
