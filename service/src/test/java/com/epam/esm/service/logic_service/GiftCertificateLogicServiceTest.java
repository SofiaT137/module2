package com.epam.esm.service.logic_service;

import com.epam.esm.pagination.Pagination;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
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

    private static final List<Tag> tagList = new ArrayList<>();
    private static GiftCertificate giftCertificate = new GiftCertificate();
    private static GiftCertificate giftCertificateForUpdate = new GiftCertificate();
    private static GiftCertificate updatedGiftCertificate = new GiftCertificate();
    private static final int PAGE_NUMBER = 0;
    private static final int PAGE_SIZE = 1;
    private static final long ID = 1L;
    private static MultiValueMap<String,String> map;
    private static final String TAG_NAME1 = "shark";
    private static final String TAG_NAME2 = "joy";
    private static final List<Tag> TAG_LIST = Arrays.asList(new Tag(1L,TAG_NAME1), new Tag(2L,TAG_NAME2));
    private static final String CERTIFICATE_NAME_AND_DESCRIPTION = "fff";
    private static final double PRICE = 50.00;
    private static final int DURATION = 15;
    private static final int NEW_DURATION = 30;

    @BeforeAll
    static void initCertificates() {
       giftCertificate = new GiftCertificate(ID, CERTIFICATE_NAME_AND_DESCRIPTION, CERTIFICATE_NAME_AND_DESCRIPTION
                ,PRICE , DURATION, tagList);
       giftCertificateForUpdate = new GiftCertificate(ID, null, null
                , null, DURATION, new ArrayList<>());
       updatedGiftCertificate = new GiftCertificate(ID, CERTIFICATE_NAME_AND_DESCRIPTION,
               CERTIFICATE_NAME_AND_DESCRIPTION,PRICE, NEW_DURATION, tagList);
    }

    @BeforeAll
    static void initMap() {
        map = new LinkedMultiValueMap<>();
        map.set("tagName","shark");
        map.set("tagName","joy");
        map.set("pageNumber","0");
        map.set("pageSize","1");
    }

    @BeforeEach
    void setTagLogicService(){
        giftCertificateLogicService.setTagLogicService(tagLogicService);
    }

    @Test
    void insert() {
        Mockito.when(giftCertificateRepository.findByName(CERTIFICATE_NAME_AND_DESCRIPTION)).
                thenReturn(Optional.empty());
        Mockito.when(giftCertificateRepository.save(giftCertificate)).thenReturn(giftCertificate);
        GiftCertificate giftCertificate1 = giftCertificateLogicService.insert(giftCertificate);
        assertEquals(giftCertificate,giftCertificate1);
    }

    @Test
    void getById() {
        Mockito.when(giftCertificateRepository.findById(ID)).thenReturn(Optional.of(giftCertificate));
        GiftCertificate giftCertificate1 = giftCertificateLogicService.getById(giftCertificate.getId());
        assertEquals(giftCertificate,giftCertificate1);
    }

    @Test
    void getAll() {
        Page<GiftCertificate> page = new PageImpl<>(new ArrayList<>(Collections.singletonList(giftCertificate)));
        Mockito.when(pagination.checkHasContent(giftCertificateRepository.
                        findAll(PageRequest.of(PAGE_NUMBER,PAGE_SIZE)))).thenReturn(page);
        Page<GiftCertificate> giftCertificates = giftCertificateLogicService.getAll(PAGE_NUMBER,PAGE_SIZE);
        assertEquals(ID,giftCertificates.getTotalElements());
    }

    @Test
    void deleteByID() {
        Mockito.when(giftCertificateRepository.findById(ID)).thenReturn(Optional.of(giftCertificate));
        giftCertificateLogicService.deleteByID(giftCertificate.getId());
        Mockito.verify(giftCertificateRepository, Mockito.times(1)).delete(giftCertificate);
    }

    @Test
    void update(){
        Mockito.when(giftCertificateRepository.findById(giftCertificate.getId()))
                .thenReturn(Optional.of(giftCertificate));
        Mockito.when(giftCertificateRepository.update(giftCertificateForUpdate,giftCertificate))
                .thenReturn(Optional.ofNullable(updatedGiftCertificate));
        GiftCertificate giftCertificate = giftCertificateLogicService.
                update(giftCertificateForUpdate.getId(),giftCertificateForUpdate);
        assertEquals(NEW_DURATION, giftCertificate.getDuration());
    }

    @Test
    void getQueryWithConditions() {
        Page<GiftCertificate> page = new PageImpl<>(new ArrayList<>(Collections.singletonList(giftCertificate)));
        Mockito.when(pagination.checkHasContent(giftCertificateRepository.
                        findGiftCertificatesByTransferredConditions(map,PAGE_NUMBER,PAGE_SIZE))).
                thenReturn(page);
        Page<GiftCertificate> giftCertificates = giftCertificateLogicService.getQueryWithConditions(map);
        assertEquals(ID,giftCertificates.getNumberOfElements());
    }

}