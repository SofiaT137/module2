package com.epam.esm.service.logic_service;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TagLogicServiceTest {

    @Mock
    private TagDao tagDao;
    @Mock
    private UserService<User> userLogicService;
    @InjectMocks
    private TagLogicService tagLogicService;

    @BeforeEach
    void init(){
        tagLogicService.setUserLogicService(userLogicService);
    }

    private static final Tag tag2 = new Tag("joy");
    private static final Tag tag = new Tag("shark");
    private static final User user = new User(1L,"AlexRendal","dddd");
    private static final GiftCertificate giftCertificate = new GiftCertificate(1L,"fff"
            ,"ffff",40.12,30,
            LocalDateTime.now(),LocalDateTime.now(),Arrays.asList(tag,tag2));
    private static final Order order = new Order(40.12,LocalDateTime.now(),user);

    @Test
    void insert() {
        Mockito.when(tagDao.save(tag)).thenReturn(tag);
        Tag tag1 = tagLogicService.insert(tag);
        assertEquals(tag,tag1);
    }

    @Test
    void getById() {
        Mockito.when(tagDao.findById(1L)).thenReturn(Optional.of(tag));
        Tag tag1 = tagLogicService.getById(1L);
        assertEquals(tag,tag1);
    }

    @Test
    void getAll() {
        Mockito.when(tagDao.findAll()).thenReturn(Collections.singletonList(tag));
        Page<Tag> tagList = tagLogicService.getAll(1,1);
        assertEquals(1,tagList.getTotalElements());
    }

    @Test
    void deleteByID() {
        Mockito.when(tagDao.findById(1L)).thenReturn(Optional.of(tag));
        tagLogicService.deleteByID(1L);
        Mockito.verify(tagDao,Mockito.times(1)).delete(tag);
    }

    @Test
    void findTheMostWidelyUsedUserTagWithHigherOrderCost() {
        Mockito.when(userLogicService.getById(1L)).thenReturn(user);
        Mockito.when(tagDao.findTheMostWidelyUsedUserTagWithHighersOrderCost(user.getId()))
                .thenReturn(Collections.singletonList(tag));
        Tag tagFound = tagLogicService.findTheMostWidelyUsedUserTagWithHigherOrderCost(user.getId());
        assertEquals(tag,tagFound);
    }

    @Test
    void findTagByTagName() {
        Mockito.when(tagDao.findByTagName("shark")).thenReturn(Optional.of(tag));
        Tag foundTag = tagLogicService.findTagByTagName("shark");
        assertEquals(tag,foundTag);
    }

    @Test
    void getCertificateTagList() {
        Mockito.when(tagDao.findByTagName("shark")).thenReturn(Optional.of(tag));
        Mockito.when(tagDao.findByTagName("joy")).thenReturn(Optional.of(tag2));
        List<Tag> tagList = tagLogicService.getCertificateTagList(giftCertificate.getTagList());
        assertEquals(2,tagList.size());
    }
}