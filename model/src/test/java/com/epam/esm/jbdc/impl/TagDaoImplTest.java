package com.epam.esm.jbdc.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.jbdc.TagDao;
import com.epam.esm.jbdc.configuration.DevelopmentProfileConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DevelopmentProfileConfig.class)
@ActiveProfiles("dev")
class TagDaoImplTest {

    @Autowired
    private TagDao tagDao;

    @Test
    void insert() throws DaoException {
        tagDao.insert(new Tag("happiness"));
        Long id2 = tagDao.getTagByName("happiness").getId();
        assertNotNull(id2);
    }

    @Test
    void getById() throws DaoException {
        Tag tag = tagDao.getById(1);
        assertEquals(1,tag.getId());
    }

    @Test
    void getAll() throws DaoException {
        List<Tag> allTheTags = tagDao.getAll();
        assertEquals(3,allTheTags.size());
    }

    @Test
    void deleteByID() {
    }

    @Test
    void deleteListOfCertificateTags() {
    }

    @Test
    void getTagByName() throws DaoException {
        Tag happinessTag = tagDao.getTagByName("sea");
        assertNotNull(happinessTag.getId());
    }
}