package com.epam.esm.hateoas.impl;

import com.epam.esm.controllers.OrderController;
import com.epam.esm.controllers.UserController;
import com.epam.esm.dto.UserDto;
import com.epam.esm.hateoas.Hateoas;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserHateoas implements Hateoas<UserDto> {

    private static final Class<OrderController> ORDER_CONTROLLER = OrderController.class;
    private static final Class<UserController> USER_CONTROLLER = UserController.class;

    @Override
    public void addLinks(UserDto entity) {
        entity.add(linkTo(methodOn(USER_CONTROLLER).getUserByID(entity.getId())).withSelfRel());
        entity.add(linkTo(methodOn(ORDER_CONTROLLER).ordersByUserId(entity.getId(),3,1)).withRel("orders"));
    }
}
