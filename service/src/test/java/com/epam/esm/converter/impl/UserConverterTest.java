package com.epam.esm.converter.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest(classes = UserConverter.class)
class UserConverterTest {

    @InjectMocks
    private static UserConverter userConverter;

    User user = new User(1L,"Alex","Rendal");
    UserDto userDto = new UserDto(1L,"Alex","Rendal");

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
}