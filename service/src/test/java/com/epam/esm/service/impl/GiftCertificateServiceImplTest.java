package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.GiftCertificateConverter;
import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.ServiceException;
import com.epam.esm.jbdc.GiftCertificateDao;
import com.epam.esm.validator.GiftCertificateValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
class GiftCertificateServiceImplTest {

    @Spy
    private GiftCertificateValidator giftCertificateValidator = Mockito.spy(GiftCertificateValidator.class);

    @Spy
    private GiftCertificateConverter giftCertificateConverter = Mockito.spy(GiftCertificateConverter.class);

    @Mock
    private final GiftCertificateDao giftCertificateDao = Mockito.mock(GiftCertificateDao.class);

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;


    private static final String CORRECT_NAME = "Swimming with seals";
    private static final String UPDATE_NAME = "Swimming with dolphins";
    private static final String INCORRECT_NAME = "$52-vmt[****";
    private static final String CORRECT_DESCRIPTION = "Fun,joy ans seals";
    private static final String UPDATE_DESCRIPTION = "Fun,joy ans dolphins";
    private static final String INCORRECT_DESCRIPTION = new String(new char[451]).replace('\0', ' ');
    private static final Double CORRECT_PRICE = 56.13;
    private static final Double INCORRECT_PRICE = 111111111.9990;
    private static final Integer CORRECT_DURATION = 15;
    private static final Integer INCORRECT_DURATION= 150;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    private static final LocalDateTime localDate = LocalDateTime.now();
    private static final String date = localDate.format(formatter);
    private static final List<String> list = Arrays.asList("joy","happiness","seal");
    private static final List<Tag> tag_list = Arrays.asList(new Tag("joy"),new Tag("happiness"),new Tag ("seal"));
    private final GiftCertificateDto entityDTO = new GiftCertificateDto(1L,CORRECT_NAME,CORRECT_DESCRIPTION,CORRECT_PRICE,CORRECT_DURATION,date,date,list);
    private final GiftCertificateDto entityDTO2 = new GiftCertificateDto(2L,CORRECT_NAME,CORRECT_DESCRIPTION,CORRECT_PRICE,CORRECT_DURATION,date,date,list);
    private final GiftCertificate entity = new GiftCertificate(1L,CORRECT_NAME,CORRECT_DESCRIPTION,CORRECT_PRICE,CORRECT_DURATION,localDate,localDate,tag_list);
    private final GiftCertificate entity2 = new GiftCertificate(2L,CORRECT_NAME,CORRECT_DESCRIPTION,CORRECT_PRICE,CORRECT_DURATION,localDate,localDate,tag_list);
    private final GiftCertificate updateEntity2 = new GiftCertificate(2L,UPDATE_NAME,UPDATE_DESCRIPTION,CORRECT_PRICE,CORRECT_DURATION,localDate,localDate,tag_list);

    @Test
    void insert() throws DaoException {
        try{
            giftCertificateService.insert(entityDTO);
        }catch (ServiceException exception){
            exception.getLocalizedMessage();
        }
        Mockito.verify(giftCertificateDao).insert(entity);
    }

    @Test
    void getById() throws DaoException, ServiceException {
        Long ID = entityDTO.getId();
        Mockito.when(giftCertificateDao.getById(ID)).thenReturn(entity);
        GiftCertificateDto certificateActual = giftCertificateService.getById(ID);
        assertEquals(entityDTO,certificateActual);
    }

    @Test
    void getAll() throws DaoException {
        List<GiftCertificate> certificateList = Arrays.asList(entity,entity2);
        List<GiftCertificateDto> certificateDto = Arrays.asList(entityDTO,entityDTO2);
        Mockito.when(giftCertificateDao.getAll()).thenReturn(certificateList);
        List<GiftCertificateDto> actual = giftCertificateService.getAll();
        assertEquals(certificateDto, actual);
    }

    @Test
    void deleteByID() throws DaoException {
        Long id = entity.getId();
        try {
            giftCertificateService.deleteByID(id);
        }catch (ServiceException exception){
            exception.printStackTrace();
        }
        Mockito.verify(giftCertificateDao, Mockito.times(1)).deleteByID(id);
    }

    @Test
    void update() throws ServiceException, DaoException {
        entityDTO2.setGiftCertificateName(UPDATE_NAME);
        entityDTO2.setDescription(UPDATE_DESCRIPTION);
        giftCertificateService.update(entityDTO2.getId(),entityDTO2);
        Mockito.verify(giftCertificateDao).deleteListOfCertificateTags(updateEntity2.getId());
        Mockito.verify(giftCertificateDao).update(updateEntity2);
        Mockito.verify(giftCertificateDao).addTagsToCertificate(updateEntity2.getId(),tag_list);
    }

    @Test
    void getQueryWithConditions() throws ServiceException, DaoException {
        List<GiftCertificate> certificateList = Arrays.asList(entity,entity2);
        List<GiftCertificateDto> certificateDto = Arrays.asList(entityDTO,entityDTO2);
        Map<String,String> testMap = new HashMap<>();
        testMap.put("tag_name","joy");
        Mockito.when(giftCertificateDao.getQueryWithConditions(testMap)).thenReturn(certificateList);
        List<GiftCertificateDto> actual = giftCertificateService.getQueryWithConditions(testMap);
        assertEquals(certificateDto,actual);

    }
}