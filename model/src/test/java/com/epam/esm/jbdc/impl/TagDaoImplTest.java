//package com.epam.esm.jbdc.impl;
//
//import com.epam.esm.entity.Tag;
//import com.epam.esm.exceptions.DaoException;
//import com.epam.esm.jbdc.TagDao;
//import com.epam.esm.jbdc.configuration.DevelopmentProfileConfig;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.util.Assert;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = DevelopmentProfileConfig.class)
//@ActiveProfiles("dev")
//class TagDaoImplTest {
//
//    private final TagDao tagDao;
//
//    @Autowired
//    public TagDaoImplTest(TagDao tagDao) {
//        this.tagDao = tagDao;
//    }
//
//    private static final String newTagName = "happiness";
//    private static final String tagNameToFind = "dolphin";
//
//    @Test
//    void insert(){
//        tagDao.insert(new Tag(newTagName));
//        Long id2 = tagDao.getTagByName(newTagName).getId();
//        assertNotNull(id2);
//    }
//
//    @Test
//    void getById(){
//        Tag tag = tagDao.getById(1);
//        assertEquals(1,tag.getId());
//    }
//
//    @Test
//    void getAll() {
//        List<Tag> allTheTags = tagDao.getAll();
//        assertEquals(3,allTheTags.size());
//    }
//
//    @Test
//    void deleteByID()  {
//        tagDao.deleteByID(2);
//        List<Tag> allTags = tagDao.getAll();
//        assertEquals(4, allTags.size());
//    }
//
//    @Test
//    void getTagByName(){
//        Tag happinessTag = tagDao.getTagByName(tagNameToFind);
//        assertNotNull(happinessTag.getId());
//    }
//
//    @Test
//    void getListWithTagsId(){
//        List<Tag> list = Collections.singletonList(new Tag("shark"));
//        List<Long> listOfTagID = tagDao.getListWithTagsId(list);
//        assertEquals(5L,listOfTagID.get(0));
//    }
//}