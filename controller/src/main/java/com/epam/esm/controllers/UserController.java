package com.epam.esm.controllers;

import com.epam.esm.dto.impl.UserDto;
import com.epam.esm.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Autowired
    @Qualifier("userBusinessService")
    public void setUserService(UserService<UserDto> userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDto getUserByID(@PathVariable Long id){
        return userService.getById(id);
    }


    @GetMapping
    public List<UserDto> getAllGiftCertificates(@RequestParam int pageSize, @RequestParam int pageNumber) {
        return userService.getAll(pageSize,pageNumber);
    }
}
