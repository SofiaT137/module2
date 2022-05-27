package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import annotations.IT;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@IT
class GiftCertificateDaoImplTestIT {

//    private final GiftCertificateDao giftCertificateDao;
//
//    private static final int NEW_DURATION = 15;
//    private static final long ID = 1L;
//    private static final LocalDateTime DATA = LocalDateTime.now();
//    private static final String GIFT_CERTIFICATE_NAME = "Swimming with seals";
//    private static final String GIFT_CERTIFICATE_NAME_TO_FIND = "Swimming with dolphins";
//    private static final String GIFT_CERTIFICATE_DESCRIPTION = "Not dangerous and very exciting";
//    private static final String TAG_NAME_KEY = "tag_name";
//    private static final String PART_NAME_KEY = "partName";
//    private static final String DOLPHIN_VALUE = "dolphin";
//    private static final String SEA_VALUE = "sea";
//    private static final String CANNOT_INSERT_GIFT_CERTIFICATE_EXCEPTION_MESSAGE = "Cannot insert gift certificate tag";
//    private static final String CANNOT_FIND_GIFT_CERTIFICATE_EXCEPTION_MESSAGE = "Cannot find gift certificate tag";
//    private static final Double GIFT_CERTIFICATE_PRICE = 130.12;
//    private static final Integer GIFT_CERTIFICATE_DURATION = 14;
//    private static final Long NEW_CERTIFICATES_ID = 3L;
//    private static final Integer PAGE_SIZE = 2;
//    private static final Integer PAGE_NUMBER = 1;
//    private static final Integer GIFT_CERTIFICATE_LIST_SIZE = 2;
//
//    @Autowired
//    GiftCertificateDaoImplTestIT(GiftCertificateDao giftCertificateDao) {
//        this.giftCertificateDao = giftCertificateDao;
//    }
//
//    @Test
//    @Transactional
//    void insert() {
//        GiftCertificate giftCertificate = new GiftCertificate(GIFT_CERTIFICATE_NAME,
//                GIFT_CERTIFICATE_DESCRIPTION,
//                GIFT_CERTIFICATE_PRICE,
//                GIFT_CERTIFICATE_DURATION,
//                DATA,
//                DATA,new ArrayList<>());
//        giftCertificateDao.insert(giftCertificate);
//        Optional<GiftCertificate> insertedCertificate = giftCertificateDao.getById(NEW_CERTIFICATES_ID);
//        if (!insertedCertificate.isPresent()){
//            throw new NoSuchElementException(CANNOT_INSERT_GIFT_CERTIFICATE_EXCEPTION_MESSAGE);
//        }
//        GiftCertificate certificate = insertedCertificate.get();
//        assertEquals(NEW_CERTIFICATES_ID,certificate.getId());
//    }
//
//    @Test
//    @Transactional
//    void delete() {
//        Optional<GiftCertificate> giftCertificate = giftCertificateDao.getById(ID);
//        if (!giftCertificate.isPresent()) {
//            throw new NoSuchElementException(CANNOT_FIND_GIFT_CERTIFICATE_EXCEPTION_MESSAGE);
//        }
//        giftCertificateDao.delete(giftCertificate.get());
//        Optional<GiftCertificate> giftCertificateAfterDelete = giftCertificateDao.getById(ID);
//        assertEquals(Optional.empty(),giftCertificateAfterDelete);
//    }
//
//    @Test
//    @Transactional
//    void update() {
//        Optional<GiftCertificate> giftCertificate = giftCertificateDao.getById(ID);
//        if (!giftCertificate.isPresent()){
//            throw new NoSuchElementException(CANNOT_FIND_GIFT_CERTIFICATE_EXCEPTION_MESSAGE);
//        }
//        GiftCertificate foundedGiftCertificate = giftCertificate.get();
//        giftCertificateDao.update(NEW_DURATION,foundedGiftCertificate);
//        assertEquals(NEW_DURATION,foundedGiftCertificate.getDuration());
//    }
//
//    @Test
//    void getById() {
//        Optional<GiftCertificate> giftCertificate = giftCertificateDao.getById(ID);
//        if (!giftCertificate.isPresent()){
//            throw new NoSuchElementException(CANNOT_FIND_GIFT_CERTIFICATE_EXCEPTION_MESSAGE);
//        }
//        GiftCertificate foundedGiftCertificate = giftCertificate.get();
//        assertEquals(ID,foundedGiftCertificate.getId());
//    }
//
//    @Test
//    void getAll() {
//        List<GiftCertificate> giftCertificateList = giftCertificateDao.getAll(PAGE_SIZE,PAGE_NUMBER);
//        assertEquals(GIFT_CERTIFICATE_LIST_SIZE,giftCertificateList.size());
//    }
//
//    @Test
//    void findGiftCertificatesByTransferredConditions() {
//        MultiValueMap<String, String> map1 = new LinkedMultiValueMap<>();
//        map1.set(TAG_NAME_KEY,SEA_VALUE);
//        map1.set(TAG_NAME_KEY,DOLPHIN_VALUE);
//        map1.set(PART_NAME_KEY,DOLPHIN_VALUE);
//        List<GiftCertificate> giftCertificate = giftCertificateDao.findGiftCertificatesByTransferredConditions(map1);
//        assertEquals(GIFT_CERTIFICATE_NAME_TO_FIND,giftCertificate.get(0).getGiftCertificateName());
//    }
}