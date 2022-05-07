package com.epam.esm.dao.impl;

import com.epam.esm.configuration.DevelopmentConfiguration;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DevelopmentConfiguration.class)
@ActiveProfiles("dev")
@Sql({
        "classpath:data.sql"
})
class TagDaoImplTest {

    private final TagDao tagDao;

    private static final String newTagName = "happiness";
    private static final String tagNameToFind = "dolphin";

    @Autowired
    TagDaoImplTest(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Test
    @Transactional
    void insert(){
        tagDao.insert(new Tag(newTagName));
        Optional<Tag> tag = tagDao.findTagByTagName(newTagName);
        if (!tag.isPresent()){
            throw new RuntimeException("Cannot insert this tag");
        }
        assertEquals(newTagName,tag.get().getName());
    }

    @Test
    void getById(){
        long id = 2L;
        Optional<Tag> tag = tagDao.getById(id);
        if (!tag.isPresent()){
            throw new RuntimeException("No such tag!");
        }
        Tag tag1 = tag.get();
        assertEquals(tagNameToFind,tag1.getName());
    }

    @Test
    void getAll() {
        List<Tag> allTheTags = tagDao.getAll(3,1);
        assertEquals(3,allTheTags.size());
    }

    @Test
    @Transactional
    void deleteByID()  {
        long id = 1L;
        tagDao.deleteByID(id);
        Optional<Tag> deletedTag = tagDao.getById(id);
        assertEquals(Optional.empty(),deletedTag);
    }

    @Test
    void getTagByName(){
        Optional<Tag> foundTag = tagDao.findTagByTagName(tagNameToFind);
        if (!foundTag.isPresent()){
            throw new RuntimeException("No such tag!");
        }
        assertEquals(tagNameToFind,foundTag.get().getName());
    }

    @Test
    void getTheMostWidelyUsedUserTagWithHighersOrderCost(){
        List<Tag> list = Collections.singletonList(new Tag("shark"));
        Optional<Tag> listOfTagID = tagDao.findTheMostWidelyUsedUserTagWithHighersOrderCost(1L);
        if (!listOfTagID.isPresent()){
            throw new RuntimeException("No such tag!");
        }
        assertNotNull(listOfTagID);
    }
}