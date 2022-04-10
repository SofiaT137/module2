package com.epam.esm.jbdc.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = DataBaseTestConfiguration.class)
class GiftCertificateDaoImplTest {

    @Autowired
    private GiftCertificateDaoImpl giftCertificateDao;

    @Test
    void insert() throws DaoException {
        giftCertificateDao.insert(new GiftCertificate("swimmingWithDolphins","best swimming in your life",70.75,60, LocalDateTime.of(2022,4,8,7,15,36), LocalDateTime.of(2022,4,8,7,15,36),
                Arrays.asList(new Tag(1L,"sea"),new Tag(2L,"dolphin"),new Tag(3L,"joy"))));
        GiftCertificate giftCertificate = giftCertificateDao.getById(1L);
        long actual = giftCertificate.getId();
        long expected = 1L;
        assertEquals(expected,actual);
    }

    @Test
    void getById() {
    }

    @Test
    void getAll() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteByID() {
    }

    @Test
    void getQueryWithConditions() {
    }
}