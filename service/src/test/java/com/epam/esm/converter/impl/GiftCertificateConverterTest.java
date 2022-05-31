package com.epam.esm.converter.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TagConverter.class)
class GiftCertificateConverterTest {

    private static GiftCertificateConverter giftCertificateConverter;

    @Spy
    private static TagConverter tagConverter = Mockito.spy(TagConverter.class);

    private static final String CORRECT_NAME = "Swimming with seals";
    private static final String CORRECT_DESCRIPTION = "Fun,joy ans seals";
    private static final Double CORRECT_PRICE = 56.13;
    private static final Integer CORRECT_DURATION = 15;
    private static final Set<TagDto> tag_set = new HashSet<>();
    private static final TagDto tagDto1 = new TagDto(1L,"joy");
    private static final TagDto tagDto2 = new TagDto(2L,"happiness");
    private static final TagDto tagDto3 = new TagDto(3L,"seal");
    private static List<Tag> tag_list_convert;
    private static GiftCertificateDto giftCertificateDto;
    private static GiftCertificate giftCertificate;
    private static LocalDateTime localDateTime = LocalDateTime.now();


    @BeforeAll
    static void setUp(){
        tag_set.add(tagDto1);
        tag_set.add(tagDto2);
        tag_set.add(tagDto3);
        giftCertificateConverter = new GiftCertificateConverter(tagConverter);
        tag_list_convert = tag_set.stream().map(tagConverter::convert).collect(Collectors.toList());
        giftCertificateDto = new GiftCertificateDto(CORRECT_NAME,CORRECT_DESCRIPTION
                ,CORRECT_PRICE,CORRECT_DURATION,localDateTime,localDateTime,tag_set);
        giftCertificate = new GiftCertificate(CORRECT_NAME,CORRECT_DESCRIPTION,CORRECT_PRICE
                ,CORRECT_DURATION,tag_list_convert);
    }

    @Test
    void convertDTOEntityToEntity() {
        GiftCertificate convertGiftCertificate = giftCertificateConverter.convert(giftCertificateDto);
        assertEquals(giftCertificate,convertGiftCertificate);
    }

    @Test
    void convertEntityToDTOEntity() {
        GiftCertificateDto convertGiftCertificateDto = giftCertificateConverter.convert(giftCertificate);
        assertEquals(giftCertificateDto,convertGiftCertificateDto);
    }

    @AfterAll
    static void destroy(){
        tagConverter = null;
        giftCertificateConverter = null;
    }
}