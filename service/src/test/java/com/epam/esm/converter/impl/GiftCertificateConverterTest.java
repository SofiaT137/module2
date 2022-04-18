package com.epam.esm.converter.impl;

import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
class GiftCertificateConverterTest {

    @InjectMocks
    private GiftCertificateConverter giftCertificateConverter;

    private static final String CORRECT_NAME = "Swimming with seals";
    private static final String CORRECT_DESCRIPTION = "Fun,joy ans seals";
    private static final Double CORRECT_PRICE = 56.13;
    private static final Integer CORRECT_DURATION = 15;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    private static final LocalDateTime localDate = LocalDateTime.now();
    private static final String date = localDate.format(formatter);
    private static final List<String> list = Arrays.asList("joy","happiness","seal");
    private static final List<Tag> tag_list = Arrays.asList(new Tag("joy"),new Tag("happiness"),new Tag ("seal"));
    private static final GiftCertificateDto DTOEntity = new GiftCertificateDto(CORRECT_NAME,CORRECT_DESCRIPTION,CORRECT_PRICE,CORRECT_DURATION,date,date,list);
    private static final GiftCertificate entity = new GiftCertificate(CORRECT_NAME,CORRECT_DESCRIPTION,CORRECT_PRICE,CORRECT_DURATION,localDate,localDate,tag_list);

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
}