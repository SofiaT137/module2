package com.epam.esm.service.logic_service;

import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.GiftCertificateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GiftCertificateLogicServiceTest {

    @Mock
    private GiftCertificateDaoImpl giftCertificateDao;

    @Mock
    private GiftCertificateValidator certificateValidator;

    @Mock
    private TagService<Tag> tagLogicService;

    @InjectMocks
    private GiftCertificateLogicService giftCertificateLogicService;

    List<Tag> tagList = Collections.singletonList(new Tag("hello"));
    GiftCertificate giftCertificate = new GiftCertificate(1L,"abc","abc"
            ,50.00,13, LocalDateTime.now(),LocalDateTime.now(),tagList);
    GiftCertificate giftCertificateForUpdate = new GiftCertificate(1L,null,null
            ,null,15, null,null,null);
    GiftCertificate updatedGiftCertificate = new GiftCertificate(1L,"abc","abc"
            ,50.00,30, LocalDateTime.now(),LocalDateTime.now(),tagList);

    @BeforeEach
    void init(){
        giftCertificateLogicService.setTagLogicService(tagLogicService);
    }

    @Test
    void insert() {
        Mockito.when(tagLogicService.getCertificateTagList(giftCertificate.getTagList())).thenReturn(tagList);
        Mockito.when(giftCertificateDao.insert(giftCertificate)).thenReturn(Optional.of(giftCertificate));
        GiftCertificate giftCertificate1 = giftCertificateLogicService.insert(giftCertificate);
        assertEquals(giftCertificate,giftCertificate1);
    }

    @Test
    void getById() {
        Mockito.when(giftCertificateDao.getById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
        GiftCertificate giftCertificate1 = giftCertificateLogicService.getById(giftCertificate.getId());
        assertEquals(giftCertificate,giftCertificate1);
    }

    @Test
    void getAll() {
        Mockito.when(giftCertificateDao.getAll(1,1))
                .thenReturn(Collections.singletonList(giftCertificate));
        List<GiftCertificate> giftCertificates = giftCertificateLogicService.getAll(1,1);
        assertEquals(1,giftCertificates.size());
    }

    @Test
    void deleteByID() {
        Mockito.when(giftCertificateDao.getById(1L))
                .thenReturn(Optional.of(giftCertificate));
        giftCertificateLogicService.deleteByID(giftCertificate.getId());
        Mockito.verify(giftCertificateDao, Mockito.times(1)).deleteByID(giftCertificate.getId());
    }

    @Test
    void update(){
        Mockito.when(giftCertificateDao.getById(1L))
                .thenReturn(Optional.of(giftCertificate));
        Mockito.when(giftCertificateDao.update(15,giftCertificate))
                .thenReturn(Optional.of(updatedGiftCertificate));
        GiftCertificate resultCertificate = giftCertificateLogicService.update(1L,giftCertificateForUpdate);
        assertEquals(updatedGiftCertificate,resultCertificate);
    }

    @Test
    void getQueryWithConditions() {
        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.set("hello","season");
        Mockito.when(giftCertificateDao.findGiftCertificatesByTransferredConditions(map)).thenReturn(new ArrayList<>());
        List<GiftCertificate> giftCertificates = giftCertificateLogicService.getQueryWithConditions(map);
        assertEquals(0,giftCertificates.size());
    }
}