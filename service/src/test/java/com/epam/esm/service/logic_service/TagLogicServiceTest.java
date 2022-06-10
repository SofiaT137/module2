package com.epam.esm.service.logic_service;

import com.epam.esm.pagination.Pagination;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TagLogicServiceTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private UserService<User> userLogicService;

    @Mock
    private Pagination<Tag> pagination;

    @InjectMocks
    private TagLogicService tagLogicService;

    private static final int PAGE_NUMBER = 0;
    private static final int PAGE_SIZE = 1;
    private static final long ID = 1L;
    private static final String TAG_NAME1 = "shark";
    private static final String TAG_NAME2 = "joy";
    private static final String LOGIN = "AlexRendal";
    private static final String PASSWORD = "dddd";
    private static final double PRICE = 40.12;
    private static final String CERTIFICATE_NAME_AND_DESCRIPTION = "fff";
    private static Tag tag2;
    private static Tag tag1;
    private static User user;
    private static GiftCertificate giftCertificate;
    private static Order order;

    @BeforeAll
    static void init(){
        tag2 = new Tag(TAG_NAME2);
        tag1 = new Tag(TAG_NAME1);
        user = new User(ID,LOGIN,PASSWORD);
        order = new Order(PRICE,user);
        giftCertificate = new GiftCertificate(ID,CERTIFICATE_NAME_AND_DESCRIPTION,CERTIFICATE_NAME_AND_DESCRIPTION,
                PRICE,30,Arrays.asList(tag1,tag2));
    }

    @BeforeEach
    void setUserLogicService(){
        tagLogicService.setUserLogicService(userLogicService);
    }

    @Test
    void insert() {
        Mockito.when(tagRepository.save(tag1)).thenReturn(tag1);
        Tag tag1 = tagLogicService.insert(TagLogicServiceTest.tag1);
        assertEquals(TagLogicServiceTest.tag1,tag1);
    }

    @Test
    void getById() {
        Mockito.when(tagRepository.findById(ID)).thenReturn(Optional.of(tag1));
        Tag tag1 = tagLogicService.getById(ID);
        assertEquals(TagLogicServiceTest.tag1,tag1);
    }

    @Test
    void getAll() {
        Page<Tag> page = new PageImpl<>(new ArrayList<>(Collections.singletonList(tag1)));
        Mockito.when(pagination.checkHasContent(tagRepository.findAll(PageRequest.of(PAGE_NUMBER,PAGE_SIZE))))
                .thenReturn(page);
        Page<Tag> tagList = tagLogicService.getAll(PAGE_NUMBER,PAGE_SIZE);
        assertEquals(1,tagList.getTotalElements());
    }

    @Test
    void deleteByID() {
        Mockito.when(tagRepository.findById(ID)).thenReturn(Optional.of(tag1));
        tagLogicService.deleteByID(ID);
        Mockito.verify(tagRepository,Mockito.times(1)).delete(tag1);
    }

    @Test
    void findTheMostWidelyUsedUserTagWithHigherOrderCost() {
        Mockito.when(userLogicService.getById(ID)).thenReturn(user);
        Mockito.when(tagRepository.findTheMostWidelyUsedUserTagWithHighersOrderCost(user.getId()))
                .thenReturn(Optional.of(tag1));
        Tag tagFound = tagLogicService.findTheMostWidelyUsedUserTagWithHigherOrderCost(user.getId());
        assertEquals(tag1,tagFound);
    }

    @Test
    void findTagByTagName() {
        Mockito.when(tagRepository.findTagByName(TAG_NAME1)).thenReturn(Optional.of(tag1));
        Tag foundTag = tagLogicService.findTagByTagName(TAG_NAME1);
        assertEquals(tag1,foundTag);
    }

    @Test
    void getCertificateTagList() {
        Mockito.when(tagRepository.findTagByName(TAG_NAME1)).thenReturn(Optional.of(tag1));
        Mockito.when(tagRepository.findTagByName(TAG_NAME2)).thenReturn(Optional.of(tag2));
        List<Tag> tagList = tagLogicService.getCertificateTagList(giftCertificate.getTagList());
        assertEquals(2,tagList.size());
    }
}