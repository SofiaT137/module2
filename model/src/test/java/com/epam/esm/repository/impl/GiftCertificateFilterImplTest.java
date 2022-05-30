package com.epam.esm.repository.impl;

import annotations.IT;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@IT
class GiftCertificateFilterImplTest {

    @Resource
    private GiftCertificateRepository giftCertificateRepository;

    private static final String CERTIFICATE_NAME = "Swimming with dolphins!";
    private static final String CERTIFICATE_NAME_2 = "Swimming with seals!";
    private static final int DURATION = 90;
    private static final LocalDateTime TIME = LocalDateTime.now();
    private static final String DESCRIPTION = "Let's swim with dolphins!";
    private static final String DESCRIPTION_2 = "Let's swim with seals!";
    private static final double PRICE = 70.12;


    @BeforeEach
    void init() {
        GiftCertificate giftCertificate1 = new GiftCertificate(CERTIFICATE_NAME,
                DESCRIPTION,PRICE,DURATION,TIME,TIME,new ArrayList<>());
        giftCertificate1.setOperation("INSERT");
        giftCertificate1.setTimestamp(TIME);
        giftCertificateRepository.save(giftCertificate1);
        GiftCertificate giftCertificate2 = new GiftCertificate(CERTIFICATE_NAME_2,
                DESCRIPTION_2,PRICE,DURATION,TIME,TIME,new ArrayList<>());
        giftCertificate2.setOperation("INSERT");
        giftCertificate2.setTimestamp(TIME);
        giftCertificateRepository.save(giftCertificate1);
    }

    @Test
    void findGiftCertificatesByTransferredConditions() {
        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.put("partName", Arrays.asList("dolphin", "swim"));
        List<GiftCertificate> giftCertificateList = giftCertificateRepository.
                findGiftCertificatesByTransferredConditions(map);
        assertEquals(1,giftCertificateList.size());
    }
}