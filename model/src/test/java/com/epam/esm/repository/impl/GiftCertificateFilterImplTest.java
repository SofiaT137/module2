package com.epam.esm.repository.impl;

import annotations.IT;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

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
    private static final LocalDateTime data = LocalDateTime.now();
    private final GiftCertificate giftCertificate1 = new GiftCertificate();
    private final GiftCertificate giftCertificate2 = new GiftCertificate();


    @BeforeEach
    void init() {
        giftCertificate1.setDescription(DESCRIPTION);
        giftCertificate1.setDuration(DURATION);
        giftCertificate1.setName(CERTIFICATE_NAME);
        giftCertificate1.setPrice(PRICE);
        giftCertificate1.setTagList(new ArrayList<>());
        giftCertificateRepository.save(giftCertificate1);
        giftCertificate2.setDescription(DESCRIPTION);
        giftCertificate2.setDuration(DURATION);
        giftCertificate2.setName(CERTIFICATE_NAME+"aaa");
        giftCertificate2.setPrice(PRICE);
        giftCertificate2.setTagList(new ArrayList<>());
        giftCertificateRepository.save(giftCertificate2);
    }

    @Test
    void findGiftCertificatesByTransferredConditions() {
        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.put("partName", Arrays.asList("dolphin","swim"));
        Page<GiftCertificate> giftCertificates = giftCertificateRepository.
                findGiftCertificatesByTransferredConditions(map,1,8);
        System.out.println(giftCertificates.getNumberOfElements());
        assertEquals(2,giftCertificates.getNumberOfElements());
    }

    @Test
    void update(){
        Optional<GiftCertificate> updatedCertificate = giftCertificateRepository.update(80,giftCertificate1);
        assertEquals(80,updatedCertificate.get().getDuration());
    }
}