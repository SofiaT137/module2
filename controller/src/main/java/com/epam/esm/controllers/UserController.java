package com.epam.esm.controllers;

import com.epam.esm.entity.User;
import com.epam.esm.service.logic_service.UserLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserLogicService userLogicService;

    @Autowired
    public UserController(UserLogicService userLogicService) {
        this.userLogicService = userLogicService;
    }

    /**
     * Method getUserByID returns User entity by its id
     * @param id Long id
     * @return User entity
     */
    @GetMapping("/{id}")
    public User getUserByID(@PathVariable Long id){
        return userLogicService.getById(id);
    }

    /**
     * Method getAllGiftCertificates returns all the User entity
     * @return List of User entity
     */
    @GetMapping
    public List<User> getAllGiftCertificates(@RequestParam int pageSize, @RequestParam int pageNumber) {
        return userLogicService.getAll(pageSize,pageNumber);
    }
}
