package com.epam.esm.dao.impl;

import annotations.IT;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@IT
class TagDaoImplTestIT {

    private final TagDao tagDao;


    private static final String NEW_TAG_NAME = "happiness";
    private static final String TAG_NAME_TO_FIND = "sea";
    private static final String CANNOT_INSERT_TAG_EXCEPTION_MESSAGE = "Cannot insert this tag";
    private static final String CANNOT_FIND_TAG_EXCEPTION_MESSAGE = "Cannot find this tag";
    private static final Long ID = 1L;
    private static final Integer PAGE_SIZE = 3;
    private static final Integer PAGE_NUMBER = 1;
    private static final Integer TAGS_LIST_SIZE = 3;

    @Autowired
    TagDaoImplTestIT(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Test
    @Transactional
    void insert(){
        tagDao.insert(new Tag(NEW_TAG_NAME));
        Optional<Tag> tag = tagDao.findTagByTagName(NEW_TAG_NAME);
        if (!tag.isPresent()){
            throw new NoSuchElementException(CANNOT_INSERT_TAG_EXCEPTION_MESSAGE);
        }
        Tag foundedTag = tag.get();
        assertEquals(NEW_TAG_NAME,foundedTag.getName());
    }

    @Test
    void getById(){
        Optional<Tag> tag = tagDao.getById(ID);
        if (!tag.isPresent()){
            throw new NoSuchElementException(CANNOT_FIND_TAG_EXCEPTION_MESSAGE);
        }
        Tag foundedTag = tag.get();
        assertEquals(TAG_NAME_TO_FIND,foundedTag.getName());
    }

    @Test
    void getAll() {
        List<Tag> allTheTags = tagDao.getAll(PAGE_SIZE,PAGE_NUMBER);
        assertEquals(TAGS_LIST_SIZE,allTheTags.size());
    }

    @Test
    @Transactional
    void deleteByID()  {
        Optional<Tag> tag = tagDao.getById(ID);
        if (!tag.isPresent()) {
            throw new NoSuchElementException(CANNOT_FIND_TAG_EXCEPTION_MESSAGE);
        }
        tagDao.delete(tag.get());
        Optional<Tag> tagAfterDelete = tagDao.getById(ID);
        assertEquals(Optional.empty(),tagAfterDelete);
    }

    @Test
    void getTagByName(){
        Optional<Tag> foundTag = tagDao.findTagByTagName(TAG_NAME_TO_FIND);
        if (!foundTag.isPresent()){
            throw new NoSuchElementException(CANNOT_FIND_TAG_EXCEPTION_MESSAGE);
        }
        assertEquals(TAG_NAME_TO_FIND,foundTag.get().getName());
    }

    @Test
    void getTheMostWidelyUsedUserTagWithHighersOrderCost(){
        List<Tag> listOfTags= tagDao.findTheMostWidelyUsedUserTagWithHighersOrderCost(ID);
        if (listOfTags.isEmpty()){
            throw new NoSuchElementException(CANNOT_FIND_TAG_EXCEPTION_MESSAGE);
        }
        assertNotNull(listOfTags);
    }

}