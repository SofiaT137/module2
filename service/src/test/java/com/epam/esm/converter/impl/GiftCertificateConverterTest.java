package com.epam.esm.converter.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest(classes = {GiftCertificateConverter.class, TagConverter.class})
class GiftCertificateConverterTest {

    private static GiftCertificateConverter giftCertificateConverter;

    @Spy
    private static TagConverter tagConverter = Mockito.spy(TagConverter.class);

    @BeforeAll
    static void setUp(){
        giftCertificateConverter = new GiftCertificateConverter(tagConverter);
    }

    private static final String CORRECT_NAME = "Swimming with seals";
    private static final String CORRECT_DESCRIPTION = "Fun,joy ans seals";
    private static final Double CORRECT_PRICE = 56.13;
    private static final Integer CORRECT_DURATION = 15;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    private static final LocalDateTime localDate = LocalDateTime.now();
    private static final String date = localDate.format(formatter);
    private static final List<TagDto> tag_list = Arrays.asList(new TagDto(1L,"joy")
            ,new TagDto(2L,"happiness"),new TagDto(3L,"seal"));
    private final List<Tag> tag_list_convert = tag_list.stream().map(tagConverter::convert).collect(Collectors.toList());
    private static final GiftCertificateDto DTOEntity = new GiftCertificateDto(CORRECT_NAME,CORRECT_DESCRIPTION
            ,CORRECT_PRICE,CORRECT_DURATION,date,date,tag_list);
    private final GiftCertificate entity = new GiftCertificate(CORRECT_NAME,CORRECT_DESCRIPTION,CORRECT_PRICE
            ,CORRECT_DURATION,localDate,localDate,tag_list_convert);

    @Test
    void convertDTOEntityToEntity() {
        GiftCertificate giftCertificate = giftCertificateConverter.convert(DTOEntity);
        assertEquals(giftCertificate,entity);
    }

    @Test
    void convertEntityToDTOEntity() {
        GiftCertificateDto giftCertificateDto = giftCertificateConverter.convert(entity);
        assertEquals(DTOEntity,giftCertificateDto);
    }

    @AfterAll
    static void destroy(){
        tagConverter = null;
        giftCertificateConverter = null;
    }
}