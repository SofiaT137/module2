package com.epam.esm.converter.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
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
@SpringBootTest(classes = TagConverter.class)
class TagConverterTest {

    @InjectMocks
    private static TagConverter tagConverter;

    private static final Long TAG_ID = 1L;
    private static final String TAG_NAME = "sea";
    private static Tag tag;
    private static TagDto tagDto;

    @BeforeAll
    static void init(){
        tag = new Tag(TAG_NAME);
        tagDto = new TagDto(TAG_ID,TAG_NAME);
    }

    @Test
    void convertDTOEntityToEntity() {
        Tag convertedTag = tagConverter.convert(tagDto);
        assertEquals(tag,convertedTag);
    }

    @Test
    void convertEntityToDTOEntity(){
        TagDto convertedTagDto = tagConverter.convert(tag);
        assertEquals(tagDto,convertedTagDto);
    }

    @AfterAll
    static void destroy(){
        tagConverter = null;
    }
}