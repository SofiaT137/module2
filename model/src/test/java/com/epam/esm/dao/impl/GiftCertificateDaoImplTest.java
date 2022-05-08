package com.epam.esm.dao.impl;

import com.epam.esm.configuration.DevelopmentConfiguration;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DevelopmentConfiguration.class)
@ActiveProfiles("dev")
@Sql({
        "classpath:data.sql"
})
class GiftCertificateDaoImplTest {

    private final GiftCertificateDao giftCertificateDao;

    private static final int NEW_DURATION = 15;
    private static final long ID = 1L;
    private static final LocalDateTime data = LocalDateTime.now();

    @Autowired
    GiftCertificateDaoImplTest(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Test
    @Transactional
    void insert() {
        GiftCertificate giftCertificate = new GiftCertificate("Swimming with seals",
                "Not dangerous and very exciting",
                130.12,
                14,
                data,
                data);
        giftCertificateDao.insert(giftCertificate);
        Optional<GiftCertificate> giftCertificate1 = giftCertificateDao.getById(3L);
        if (!giftCertificate1.isPresent()){
            throw new RuntimeException("Cannot insert this gift certificate");
        }
        GiftCertificate giftCertificate2 = giftCertificate1.get();
        assertEquals(3L,giftCertificate2.getId());
    }

    @Test
    @Transactional
    void deleteByID() {
        giftCertificateDao.deleteByID(ID);
        Optional<GiftCertificate> giftCertificate = giftCertificateDao.getById(ID);
        assertEquals(Optional.empty(),giftCertificate);
    }

    @Test
    @Transactional
    void update() {
        Optional<GiftCertificate> giftCertificate = giftCertificateDao.getById(ID);
        if (!giftCertificate.isPresent()){
            throw new RuntimeException("No such gift certificate!");
        }
        GiftCertificate giftCertificate1 = giftCertificate.get();
        giftCertificateDao.update(NEW_DURATION,giftCertificate1);
        assertEquals(NEW_DURATION,giftCertificate1.getDuration());
    }

    @Test
    void getById() {
        Optional<GiftCertificate> giftCertificate = giftCertificateDao.getById(ID);
        if (!giftCertificate.isPresent()){
            throw new RuntimeException("No such gift certificate!");
        }
        GiftCertificate giftCertificate1 = giftCertificate.get();
        assertEquals(ID,giftCertificate1.getId());
    }

    @Test
    void getAll() {
        List<GiftCertificate> giftCertificateList = giftCertificateDao.getAll(2,1);
        assertEquals(2,giftCertificateList.size());
    }

    @Test
    void findGiftCertificatesByTransferredConditions() {
        MultiValueMap<String, String> map1 = new LinkedMultiValueMap<>();
        map1.set("tag_name","sea");
        map1.set("tag_name","dolphin");
        map1.set("partName","dolphins");
        List<GiftCertificate> giftCertificate = giftCertificateDao.findGiftCertificatesByTransferredConditions(map1);
        String giftCertificateName = "Swimming with dolphins";
        assertEquals(giftCertificateName,giftCertificate.get(0).getGiftCertificateName());
    }
}