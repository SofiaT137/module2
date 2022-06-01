package com.epam.esm.controllers;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.security.JwtTokenProvider;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * AuthController class presents REST controller for the authentication
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private UserService<UserDto> userService;

    private static final String INVALID_LOGIN_OR_PASSWORD = "invalidLoginOrPassword";
    private static final String ACCESS_DENIED = "accessDenied";
    private static final String LOGIN = "login";
    private static final String TOKEN = "token";


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

    /**
     * Method signUp returns ResponseEntity with HttpStatus "CREATED" and presents registration User logic
     * @param userDto UserDto entity
     * @return HttpStatus "CREATED"
     */
    @PostMapping(value = "/signUp")
    public ResponseEntity<Object> signUp(@RequestBody UserDto userDto){
        userService.insert(userDto);
        userDto.setEnabled(true);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Method signIn returns ResponseEntity with map response(which contains the users login and users token) and
     * HttpStatus "OK"
     * @param userDto UserDto entity
     * @return Response map (which contains the users login and users token) and HttpStatus "OK"
     */
    @PostMapping(value = "/signIn")
    public ResponseEntity<Object> signIn(@RequestBody UserDto userDto){
        try {
            String login = userDto.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login,userDto.getPassword()));
            User user = userService.findUserByUserLogin(userDto.getLogin());
            checkUserAccess(user);
            Map<Object, Object> responseMap = createResponseWithLoginAndToken(user,login);
            return new ResponseEntity<>(responseMap,HttpStatus.OK);
        }catch (AuthenticationException exception) {
           throw new BadCredentialsException(INVALID_LOGIN_OR_PASSWORD);
        }
    }

    private void checkUserAccess(User user){
        if (user.getEnabled() == 0){
            throw new AccessDeniedException(ACCESS_DENIED);
        }
    }

    private Map<Object,Object> createResponseWithLoginAndToken(User user,String login){
        String token = jwtTokenProvider.createToken(user);
        Map<Object, Object> response = new HashMap<>();
        response.put(LOGIN, login);
        response.put(TOKEN, token);
        return response;
    }
}
