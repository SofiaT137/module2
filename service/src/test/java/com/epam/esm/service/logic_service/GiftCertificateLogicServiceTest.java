package com.epam.esm.service.logic_service;

import com.epam.esm.configuration.AuditConfiguration;
import com.epam.esm.pagination.Pagination;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GiftCertificateLogicServiceTest {

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private TagService<Tag> tagLogicService;

    @Mock
    private Pagination<GiftCertificate> pagination;

    @InjectMocks
    private GiftCertificateLogicService giftCertificateLogicService;

    List<Tag> tagList = Arrays.asList(new Tag("hello"), new Tag("season"));
    GiftCertificate giftCertificate = new GiftCertificate(1L,"abc","abc"
            ,50.00,13,tagList);
    GiftCertificate giftCertificateForUpdate = new GiftCertificate(1L,null,null
            ,null,15, new ArrayList<>());
    GiftCertificate updatedGiftCertificate = new GiftCertificate(1L,"abc","abc"
            ,50.00,30, tagList);

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
        Mockito.when(pagination.checkHasContent(giftCertificateRepository.findAll(PageRequest.of(0,1))))
                .thenReturn(page);
        Page<GiftCertificate> giftCertificates = giftCertificateLogicService.getAll(0,1);
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
        Mockito.when(giftCertificateRepository.update(15,giftCertificate))
                .thenReturn(Optional.ofNullable(updatedGiftCertificate));
        GiftCertificate giftCertificate = giftCertificateLogicService.update(1L,giftCertificateForUpdate);
        assertEquals(30, giftCertificate.getDuration());
    }

    @Test
    void getQueryWithConditions() {
        Page<GiftCertificate> page = new PageImpl<>(new ArrayList<>(Collections.singletonList(giftCertificate)));
        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
        map.set("tagName","season");
        map.set("tagName","hello");
        map.set("pageNumber","0");
        map.set("pageSize","1");
        Mockito.when(pagination.checkHasContent(giftCertificateRepository.
                        findGiftCertificatesByTransferredConditions(map,0,1))).
                thenReturn(page);
        Page<GiftCertificate> giftCertificates = giftCertificateLogicService.getQueryWithConditions(map);
        assertEquals(1L,giftCertificates.getNumberOfElements());
    }
}