package com.epam.esm.controllers;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.security.JwtTokenProvider;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private UserService<UserDto> userService;

    @Autowired
    @Qualifier("userBusinessService")
    public void setUserService(UserService<UserDto> userService) {
        this.userService = userService;
    }

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping(value = "/signUp")
    public ResponseEntity<Object> signUp(@RequestBody UserDto userDto){
        userService.insert(userDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/signIn")
    public ResponseEntity<Object> signIn(@RequestBody UserDto userDto){
        try {
            String login = userDto.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login,userDto.getPassword()));
            User user = userService.findUserByUserLogin(userDto.getLogin());
            String token = jwtTokenProvider.createToken(user);
            Map<Object, Object> response = new HashMap<>();
            response.put("login", login);
            response.put("token", token);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (AuthenticationException e) {
           throw new BadCredentialsException("Invalid username or password");
        }
    }
}
