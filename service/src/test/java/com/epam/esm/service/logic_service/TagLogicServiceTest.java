package com.epam.esm.service.logic_service;

import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.TagValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TagLogicServiceTest {

    @Mock
    private TagDaoImpl tagDao;
    @Mock
    private UserService<User> userLogicService;
    @Mock
    private TagValidator tagValidator;
    @InjectMocks
    private TagLogicService tagLogicService;

    @BeforeEach
    void init(){
        tagLogicService.setUserLogicService(userLogicService);
    }

    Tag tag2 = new Tag("joy");
    Tag tag = new Tag("shark");
    User user = new User(1L,"AlexRendal");
    GiftCertificate giftCertificate = new GiftCertificate(1L,"fff"
            ,"ffff",40.12,30,
            LocalDateTime.now(),LocalDateTime.now(),Arrays.asList(tag,tag2));
    Order order = new Order(40.12,LocalDateTime.now(),user);

    @Test
    void insert() {
        Mockito.when(tagDao.insert(tag)).thenReturn(Optional.of(tag));
        Tag tag1 = tagLogicService.insert(tag);
        assertEquals(tag,tag1);
    }

    @Test
    void getById() {
        Mockito.when(tagDao.getById(1L)).thenReturn(Optional.of(tag));
        Tag tag1 = tagLogicService.getById(1L);
        assertEquals(tag,tag1);
    }

    @Test
    void getAll() {
        Mockito.when(tagDao.getAll(1,1)).thenReturn(Collections.singletonList(tag));
        List<Tag> tagList = tagLogicService.getAll(1,1);
        assertEquals(1,tagList.size());
    }

    @Test
    void deleteByID() {
        Mockito.when(tagDao.getById(1L)).thenReturn(Optional.of(tag));
        tagLogicService.deleteByID(1L);
        Mockito.verify(tagDao,Mockito.times(1)).deleteByID(1L);
    }

    @Test
    void findTheMostWidelyUsedUserTagWithHigherOrderCost() {
        Mockito.when(userLogicService.getById(1L)).thenReturn(user);
        Mockito.when(tagDao.findTheMostWidelyUsedUserTagWithHighersOrderCost(user.getId()))
                .thenReturn(Optional.ofNullable(tag));
        Tag tagFound = tagLogicService.findTheMostWidelyUsedUserTagWithHigherOrderCost(user.getId());
        assertEquals(tag,tagFound);
    }

    @Test
    void findTagByTagName() {
        Mockito.when(tagDao.findTagByTagName("shark")).thenReturn(Optional.of(tag));
        Tag foundTag = tagLogicService.findTagByTagName("shark");
        assertEquals(tag,foundTag);
    }

    @Test
    void getCertificateTagList() {
        Mockito.when(tagDao.findTagByTagName("shark")).thenReturn(Optional.of(tag));
        Mockito.when(tagDao.findTagByTagName("joy")).thenReturn(Optional.of(tag2));
        List<Tag> tagList = tagLogicService.getCertificateTagList(giftCertificate.getTagList());
        assertEquals(2,tagList.size());
    }
}