//package com.epam.esm.service.impl;
//
//import com.epam.esm.converter.impl.GiftCertificateConverter;
//import com.epam.esm.dto.impl.GiftCertificateDto;
//import com.epam.esm.entity.GiftCertificate;
//import com.epam.esm.entity.Tag;
//import com.epam.esm.exceptions.DaoException;
//import com.epam.esm.exceptions.ValidatorException;
//import com.epam.esm.jbdc.GiftCertificateDao;
//import com.epam.esm.service.business_service.GiftCertificateBusinessService;
//import com.epam.esm.service.logic_service.GiftCertificateLogicService;
//import com.epam.esm.validator.GiftCertificateValidator;
//import com.epam.esm.validator.TagValidator;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.Spy;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@ExtendWith({MockitoExtension.class})
//@ComponentScan("com.epam.esm")
//@ContextConfiguration(classes = {GiftCertificateValidator.class, GiftCertificateConverter.class})
////@SpringBootTest(classes = {GiftCertificateValidator.class, GiftCertificateConverter.class})
//class GiftCertificateBusinessServiceTest {
//
//    @Mock
//    private final GiftCertificateDao giftCertificateDao = Mockito.mock(GiftCertificateDao.class);
//
//    @Spy
//    private final GiftCertificateConverter giftCertificateConverter = Mockito.spy(GiftCertificateConverter.class);
//
//    @Spy
//    private final TagValidator tagValidator = Mockito.spy(TagValidator.class);
//
//    @Spy
//    private final GiftCertificateValidator giftCertificateValidator = Mockito.spy(GiftCertificateValidator.class);
//
//    @InjectMocks
//    private GiftCertificateBusinessService giftCertificateBusinessService;
//
//    public GiftCertificateBusinessServiceTest() {
//        this.giftCertificateBusinessService = new GiftCertificateBusinessService(this.giftCertificateConverter);
//        giftCertificateBusinessService.setGiftCertificateLogicService(new GiftCertificateLogicService(this.giftCertificateDao,this.giftCertificateValidator,this.tagValidator));
//    }
//
//
//    private static final String CORRECT_NAME = "Swimming with seals";
//    private static final String UPDATE_NAME = "Swimming with dolphins";
//    private static final String CORRECT_DESCRIPTION = "Fun,joy ans seals";
//    private static final String UPDATE_DESCRIPTION = "Fun,joy ans dolphins";
//    private static final Double CORRECT_PRICE = 56.13;
//    private static final Integer CORRECT_DURATION = 15;
//    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
//    private static final LocalDateTime localDate = LocalDateTime.now();
//    private static final String date = localDate.format(formatter);
//    private static final List<String> list = Arrays.asList("joy","happiness","seal");
//    private static final List<Tag> tag_list = Arrays.asList(new Tag("joy"),new Tag("happiness"),new Tag ("seal"));
//    private static GiftCertificateDto entity1DTO;
//    private static GiftCertificateDto entity2DTO;
//    private static GiftCertificate entity;
//    private static GiftCertificate entity2;
//    private static GiftCertificate updateEntity2;
//    private static final Map<String,String> testMap = new HashMap<>();
//
//
//    @BeforeAll
//     static void init(){
//        entity1DTO = new GiftCertificateDto(1L,CORRECT_NAME,CORRECT_DESCRIPTION,CORRECT_PRICE,CORRECT_DURATION,date,date,list);
//        entity2DTO = new GiftCertificateDto(2L,CORRECT_NAME,CORRECT_DESCRIPTION,CORRECT_PRICE,CORRECT_DURATION,date,date,list);
//        entity =  new GiftCertificate(1L,CORRECT_NAME,CORRECT_DESCRIPTION,CORRECT_PRICE,CORRECT_DURATION,localDate,localDate,tag_list);
//        entity2 = new GiftCertificate(2L,CORRECT_NAME,CORRECT_DESCRIPTION,CORRECT_PRICE,CORRECT_DURATION,localDate,localDate,tag_list);
//        updateEntity2 = new GiftCertificate(2L,UPDATE_NAME,UPDATE_DESCRIPTION,CORRECT_PRICE,CORRECT_DURATION,localDate,localDate,tag_list);
//        testMap.put("tag_name","joy");
//    }
//
//
//    @Test
//    void insert() throws DaoException {
//        try{
//            giftCertificateBusinessService.insert(entity1DTO);
//        }catch (ValidatorException exception){
//            exception.getLocalizedMessage();
//        }
//        Mockito.verify(giftCertificateDao).insert(entity);
//    }
//
//    @Test
//    void getById() throws DaoException, ValidatorException {
//        Long ID = entity1DTO.getId();
//        Mockito.when(giftCertificateDao.getById(ID)).thenReturn(entity);
//        GiftCertificateDto certificateActual = giftCertificateBusinessService.getById(ID);
//        assertEquals(entity1DTO,certificateActual);
//    }
//
//    @Test
//    void getAll() throws DaoException {
//        List<GiftCertificate> certificateList = Arrays.asList(entity,entity2);
//        List<GiftCertificateDto> certificateDto = Arrays.asList(entity1DTO,entity2DTO);
//        Mockito.when(giftCertificateDao.getAll()).thenReturn(certificateList);
//        List<GiftCertificateDto> actual = giftCertificateBusinessService.getAll();
//        assertEquals(certificateDto, actual);
//    }
//
//    @Test
//    void deleteByID() throws DaoException {
//        Long id = entity.getId();
//        try {
//            giftCertificateBusinessService.deleteByID(id);
//        }catch (ValidatorException exception){
//            exception.printStackTrace();
//        }
//        Mockito.verify(giftCertificateDao, Mockito.times(1)).deleteByID(id);
//    }
//
//    @Test
//    void update() throws ValidatorException, DaoException {
//        entity2DTO.setGiftCertificateName(UPDATE_NAME);
//        entity2DTO.setDescription(UPDATE_DESCRIPTION);
//        giftCertificateBusinessService.update(entity2DTO.getId(),null,entity2DTO);
//        Mockito.verify(giftCertificateDao).deleteListOfCertificateTags(updateEntity2.getId());
//        Mockito.verify(giftCertificateDao).update(updateEntity2);
//        Mockito.verify(giftCertificateDao).addTagsToCertificate(updateEntity2.getId(),tag_list);
//    }
//
//    @Test
//    void getQueryWithConditions() throws ValidatorException, DaoException {
//        List<GiftCertificate> certificateList = Arrays.asList(entity,entity2);
//        List<GiftCertificateDto> certificateDto = Arrays.asList(entity1DTO,entity2DTO);
//        Mockito.when(giftCertificateDao.getQueryWithConditions(testMap)).thenReturn(certificateList);
//        List<GiftCertificateDto> actual = giftCertificateBusinessService.getQueryWithConditions(testMap);
//        assertEquals(certificateDto,actual);
//    }
//}