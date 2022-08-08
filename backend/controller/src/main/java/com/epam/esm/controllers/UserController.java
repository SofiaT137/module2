package com.epam.esm.controllers;

import com.epam.esm.dto.UserDto;
import com.epam.esm.hateoas.Hateoas;
import com.epam.esm.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController class presents REST controller for the User entity
 */
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


    /**
     * Method getUserByID returns ResponseEntity with UserDto entity and HttpStatus "OK"
     * @param id User id(Long value)
     * @return Response entity with UserDto entity and HttpStatus "OK"
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserByID(@PathVariable Long id){
        UserDto user = userService.getById(id);
        userDtoHateoas.addLinks(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Method getAllUsers returns all the UserDto entity
     * @param pageNumber Number of the page with UserDto entities(default value is 0)
     * @param pageSize Number of UserDto entities per page(default value is 5)
     * @return Response entity with page of UserDto entity and HttpStatus "OK"
     */
    @GetMapping
    public ResponseEntity<Object> getAllUsers(@RequestParam(defaultValue = "0",required = false)
                                                                     int pageNumber,
                                                         @RequestParam (defaultValue = "5", required = false)
                                                                 int pageSize){
        Page<UserDto> userDtoList = userService.getAll(pageNumber,pageSize);
        userDtoList.forEach(userDtoHateoas::addLinks);
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    /**
     * Method getAllUsers blocks the User entity
     * @param login String users login
     * @return Response entity with HttpStatus "OK"
     */
    @PatchMapping("/block/{login}")
    public ResponseEntity<Object> blockUser(@PathVariable String login){
        userService.blockUser(login);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Method getAllUsers unblocks the User entity
     * @param login String users login
     * @return Response entity with HttpStatus "OK"
     */
    @PatchMapping("/unblock/{login}")
    public ResponseEntity<Object> unBlockUser(@PathVariable String login){
        userService.unblockUser(login);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
