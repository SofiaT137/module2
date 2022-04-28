//package com.epam.esm.service.impl;
//
//import com.epam.esm.entity.Tag;
//import com.epam.esm.exceptions.DaoException;
//import com.epam.esm.exceptions.ValidatorException;
//import com.epam.esm.jbdc.TagDao;
//import com.epam.esm.service.logic_service.TagLogicService;
//import com.epam.esm.validator.TagValidator;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith({SpringExtension.class})
//@ComponentScan("com.epam.esm")
//@ContextConfiguration(classes = TagValidator.class)
//class TagLogicServiceTest {
//
//    @Mock
//    private TagDao tagDao = Mockito.mock(TagDao.class);
//
//    @InjectMocks
//    private TagLogicService tagService;
//
//    @Autowired
//    public TagLogicServiceTest(TagValidator tagValidator) {
//        this.tagService = new TagLogicService(this.tagDao,tagValidator);
//    }
//
//    private static final String TAG_NAME = "happiness";
//    private static Tag TAG_1;
//    private static Tag TAG_2;
//    private static Tag TAG_3;
//    private static Tag INVALID_TAG;
//
//    @BeforeAll
//    static void init(){
//        TAG_1 = new Tag(1L, "joy");
//        TAG_2 = new Tag(2L, "happiness");
//        TAG_3 = new Tag(3L, "luck");
//        INVALID_TAG = new Tag("luck*5?j-");
//    }
//
//    @Test
//    void insertValidTagEntity() throws DaoException {
//        try{
//            tagService.insert(TAG_1);
//        }catch (ValidatorException exception){
//            exception.getLocalizedMessage();
//        }
//        Mockito.verify(tagDao, Mockito.times(1)).insert(TAG_1);
//    }
//
//    @Test
//    void insertInvalidTagEntity() throws DaoException {
//        try{
//            tagService.insert(INVALID_TAG);
//        }catch (ValidatorException exception){
//           exception.getLocalizedMessage();
//        }
//        Mockito.verify(tagDao, Mockito.times(0)).insert(INVALID_TAG);
//    }
//
//    @Test
//    void getByValidId() throws DaoException {
//        Long id = TAG_1.getId();
//        try{
//            tagService.getById(id);
//        }catch (ValidatorException exception){
//            exception.getLocalizedMessage();
//        }
//        Mockito.verify(tagDao, Mockito.times(1)).getById(id);
//    }
//
//
//    @Test
//    void getAll() throws DaoException {
//        List<Tag> tagList = Arrays.asList(TAG_1,TAG_2,TAG_3);
//        Mockito.when(tagDao.getAll()).thenReturn(tagList);
//        List<Tag> actual = tagService.getAll();
//        assertEquals(tagList, actual);
//    }
//
//    @Test
//    void deleteByValidID() throws DaoException {
//        Long id = TAG_1.getId();
//        try {
//            tagService.deleteByID(id);
//        }catch (ValidatorException exception){
//            exception.printStackTrace();
//        }
//        Mockito.verify(tagDao, Mockito.times(1)).deleteByID(id);
//    }
//
//
//    @Test
//    void getTagByName() throws DaoException {
//        Mockito.when(tagDao.getTagByName(Mockito.any())).thenAnswer(i -> new Tag(0L, (String) i.getArguments()[0]));
//        Mockito.when(tagDao.getTagByName(TAG_NAME)).thenReturn(TAG_2);
//        Tag actual = tagService.getTagByName(TAG_NAME);
//        assertEquals(TAG_2, actual);
//    }
//}