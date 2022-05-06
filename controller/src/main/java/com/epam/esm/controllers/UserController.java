package com.epam.esm.controllers;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.hateoas.Hateoas;
import com.epam.esm.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService<UserDto> userService;
    private final Hateoas<UserDto> userDtoHateoas;

    @Autowired
    public UserController(Hateoas<UserDto> userDtoHateoas) {
        this.userDtoHateoas = userDtoHateoas;
    }


    @Autowired
    @Qualifier("userBusinessService")
    public void setUserService(UserService<UserDto> userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserByID(@PathVariable Long id){
        UserDto user = userService.getById(id);
        userDtoHateoas.addLinks(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> getAllGiftCertificates(@RequestParam int pageSize, @RequestParam int pageNumber) {
        List<UserDto> userDtoList = userService.getAll(pageSize,pageNumber);
        userDtoList.forEach(userDtoHateoas::addLinks);
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }
}
