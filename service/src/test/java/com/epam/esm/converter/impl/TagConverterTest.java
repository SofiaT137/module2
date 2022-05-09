package com.epam.esm.converter.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest(classes = TagConverter.class)
class TagConverterTest {

    @InjectMocks
    private static TagConverter tagConverter;

    private final Tag tag = new Tag("sea");
    private final TagDto tagDto = new TagDto(1L,"sea");

    @Test
    void convertDTOEntityToEntity() {
        Tag convertedTag = tagConverter.convert(tagDto);
        assertEquals(tag,convertedTag);
    }

    @Test
    void convertEntityToDTOEntity(){
        TagDto convertedDtoTag = tagConverter.convert(tag);
        assertEquals(tagDto,convertedDtoTag);
    }
}