package com.epam.esm.controllers;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.hateoas.Hateoas;
import com.epam.esm.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Object> getAllGiftCertificates(@RequestParam(defaultValue = "5",required = false) int pageSize,
                                                         @RequestParam (defaultValue = "1", required = false)
                                                                 int pageNumber){
        List<UserDto> userDtoList = userService.getAll(pageSize,pageNumber);
        userDtoList.forEach(userDtoHateoas::addLinks);
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> insertUser(@RequestBody UserDto entity) {
        userService.insert(entity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
