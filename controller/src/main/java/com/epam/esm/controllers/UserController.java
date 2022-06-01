package com.epam.esm.controllers;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.hateoas.Hateoas;
import com.epam.esm.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * @param pageSize Number of UserDto entities per page(default value is 5)
     * @param pageNumber Number of the page with UserDto entities(default value is 1)
     * @return Response entity with list of UserDto entity and HttpStatus "OK"
     */
    @GetMapping
    public ResponseEntity<Object> getAllUsers(@RequestParam(defaultValue = "5",required = false)
                                                                     int pageSize,
                                                         @RequestParam (defaultValue = "1", required = false)
                                                                 int pageNumber){
        Page<UserDto> userDtoList = userService.getAll(pageSize,pageNumber);
        userDtoList.forEach(userDtoHateoas::addLinks);
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @PatchMapping("/block/{login}")
    public ResponseEntity<Object> blockUser(@PathVariable String login){
        userService.blockUser(login);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/unblock/{login}")
    public ResponseEntity<Object> unBlockUser(@PathVariable String login){
        userService.unblockUser(login);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
