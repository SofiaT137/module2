package com.epam.esm.service.logic_service;

import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
//import com.epam.esm.validator.GiftCertificateValidator;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GiftCertificateLogicServiceTest {

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private TagService<Tag> tagLogicService;

    @InjectMocks
    private GiftCertificateLogicService giftCertificateLogicService;

    List<Tag> tagList = Arrays.asList(new Tag("hello"), new Tag("season"));
    GiftCertificate giftCertificate = new GiftCertificate(1L,"abc","abc"
            ,50.00,13, LocalDateTime.now(),LocalDateTime.now(),tagList);
    GiftCertificate giftCertificateForUpdate = new GiftCertificate(1L,null,null
            ,null,15, null,null,new ArrayList<>());
    GiftCertificate updatedGiftCertificate = new GiftCertificate(1L,"abc","abc"
            ,50.00,30, LocalDateTime.now(),LocalDateTime.now(),tagList);

    @BeforeEach
    void init(){
        giftCertificateLogicService.setTagLogicService(tagLogicService);
    }

    @Test
    void insert() {
        Mockito.when(tagLogicService.getCertificateTagList(giftCertificate.getTagList())).thenReturn(tagList);
        Mockito.when(giftCertificateRepository.save(giftCertificate)).thenReturn(giftCertificate);
        GiftCertificate giftCertificate1 = giftCertificateLogicService.insert(giftCertificate);
        assertEquals(giftCertificate,giftCertificate1);
    }

    @Test
    void getById() {
        Mockito.when(giftCertificateRepository.findById(giftCertificate.getId())).thenReturn(Optional.of(giftCertificate));
        GiftCertificate giftCertificate1 = giftCertificateLogicService.getById(giftCertificate.getId());
        assertEquals(giftCertificate,giftCertificate1);
    }

    @Test
    void getAll() {
        Page<GiftCertificate> page = new PageImpl<>(new ArrayList<>(Collections.singletonList(giftCertificate)));
        Mockito.when(giftCertificateRepository.findAll(PageRequest.of(1,1)))
                .thenReturn(page);
        Page<GiftCertificate> giftCertificates = giftCertificateLogicService.getAll(1,1);
        assertEquals(1L,giftCertificates.getTotalElements());
    }

    @Test
    void deleteByID() {
        Mockito.when(giftCertificateRepository.findById(1L))
                .thenReturn(Optional.of(giftCertificate));
        giftCertificateLogicService.deleteByID(giftCertificate.getId());
        Mockito.verify(giftCertificateRepository, Mockito.times(1)).delete(giftCertificate);
    }

    @Test
    void update(){
        Mockito.when(giftCertificateRepository.findById(1L))
                .thenReturn(Optional.of(giftCertificate));
        Mockito.when(giftCertificateRepository.update(15,giftCertificate.getId()))
                .thenReturn(1);
        int updatedRows=giftCertificateLogicService.update(1L,giftCertificateForUpdate);
        assertEquals(1, updatedRows);
    }

    @Test
    void getQueryWithConditions() {
        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.set("tag_name","season");
        map.set("tag_name","hello");
        Mockito.when(giftCertificateRepository
                .findGiftCertificatesByTransferredConditions(map))
                .thenReturn(Collections.singletonList(giftCertificate));
        List<GiftCertificate> giftCertificates = giftCertificateLogicService.getQueryWithConditions(map);
        assertEquals(1,giftCertificates.size());
    }
}