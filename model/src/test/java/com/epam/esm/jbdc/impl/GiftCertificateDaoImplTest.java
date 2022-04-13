package com.epam.esm.jbdc.impl;

import com.epam.esm.jbdc.configuration.DevelopmentProfileConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DevelopmentProfileConfig.class)
@ActiveProfiles("dev")
class GiftCertificateDaoImplTest {

    @Test
    void insert() {
    }

    @Test
    void addTagsToCertificate() {
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
    void deleteListOfCertificateTags() {
    }

    @Test
    void getQueryWithConditions() {
    }
}