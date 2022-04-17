package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.ServiceException;
import com.epam.esm.jbdc.TagDao;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.GiftCertificateValidator;
import com.epam.esm.validator.TagValidator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = TagValidator.class)
class TagServiceImplTest {

    @Spy
    private TagValidator tagValidator = Mockito.spy(TagValidator.class);

    @Mock
    private TagDao tagDao = Mockito.mock(TagDao.class);

    @InjectMocks
    private TagServiceImpl tagService;

//    @Autowired
//    public TagServiceImplTest(TagValidator tagValidator) {
//       this.tagService = new TagServiceImpl(this.tagDao,tagValidator);
//    }

    private static final Tag TAG_1 = new Tag(1L, "joy");
    private static final Tag TAG_2 = new Tag(2L, "happiness");
    private static final Tag TAG_3 = new Tag(3L, "luck");
    private static final Tag INVALID_TAG = new Tag("luck*5?j-");

    @Test
    void insertValidTagEntity() throws DaoException {
        try{
            tagService.insert(TAG_1);
        }catch (ServiceException exception){
            exception.getLocalizedMessage();
        }
        Mockito.verify(tagDao, Mockito.times(1)).insert(TAG_1);
    }

    @Test
    void insertInvalidTagEntity() throws DaoException {
        try{
            tagService.insert(INVALID_TAG);
        }catch (ServiceException exception){
           exception.getLocalizedMessage();
        }
        Mockito.verify(tagDao, Mockito.times(0)).insert(INVALID_TAG);
    }

    @Test
    void getByValidId() throws DaoException {
        Long id = TAG_1.getId();
        try{
            tagService.getById(id);
        }catch (ServiceException exception){
            exception.getLocalizedMessage();
        }
        Mockito.verify(tagDao, Mockito.times(1)).getById(id);
    }

    @Test
    void getByInvalidId() throws DaoException {
        long id = -9L;
        try{
            tagService.getById(id);
        }catch (ServiceException exception){
            exception.getLocalizedMessage();
        }
        Mockito.verify(tagDao, Mockito.times(0)).getById(id);
    }

    @Test
    void getAll() throws DaoException {
        List<Tag> tagList = Arrays.asList(TAG_1,TAG_2,TAG_3);
        Mockito.when(tagDao.getAll()).thenReturn(tagList);
        List<Tag> actual = tagService.getAll();
        assertEquals(tagList, actual);
    }

    @Test
    void deleteByValidID() throws DaoException, ServiceException {
        Long id = TAG_1.getId();
        try {
            tagService.deleteByID(id);
        }catch (ServiceException exception){
            exception.printStackTrace();
        }
        Mockito.verify(tagDao, Mockito.times(1)).deleteByID(id);
    }

    @Test
    void deleteByInvalidID() throws DaoException{
        long invalidID = -1L;
        try {
            tagService.deleteByID(invalidID);
        }catch (ServiceException exception){
            exception.printStackTrace();
        }
        Mockito.verify(tagDao, Mockito.times(0)).deleteByID(invalidID);
    }


    @Test
    void getTagByName() throws DaoException {
        Mockito.when(tagDao.getTagByName(Mockito.any())).thenAnswer(i -> new Tag(0L, (String) i.getArguments()[0]));
        Mockito.when(tagDao.getTagByName("happiness")).thenReturn(TAG_2);
        Tag actual = tagService.getTagByName("happiness");
        assertEquals(TAG_2, actual);
    }
}