package com.epam.esm.converter.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest(classes = UserConverter.class)
class UserConverterTest {

    @InjectMocks
    private static UserConverter userConverter;

    private static final Long USER_ID = 1L;
    private static final String USER_NAME = "AlexRendal";
    private static User user;
    private static UserDto userDto;

    @BeforeAll
    static void init(){
        user = new User(USER_NAME,"aaa");
        userDto = new UserDto(USER_ID,USER_NAME,"ddd");
    }

    @Test
    void convertDTOEntityToEntity() {
        User convertedUser = userConverter.convert(userDto);
        assertEquals(user,convertedUser);
    }

    @Test
    void convertEntityToDTOEntity(){
        UserDto convertedUserDto = userConverter.convert(user);
        assertEquals(userDto,convertedUserDto);
    }

    @AfterAll
    static void destroy(){
        userConverter = null;
    }
}