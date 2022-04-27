//package com.epam.esm.jbdc.impl;
//
//import com.epam.esm.entity.GiftCertificate;
//import com.epam.esm.entity.Tag;
//import com.epam.esm.exceptions.DaoException;
//import com.epam.esm.jbdc.GiftCertificateDao;
//import com.epam.esm.jbdc.configuration.DevelopmentProfileConfig;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = DevelopmentProfileConfig.class)
//@ActiveProfiles("dev")
//class GiftCertificateDaoImplTest {
//
//    @Autowired
//    private GiftCertificateDao giftCertificateDao;
//
//    private static LocalDateTime createDate;
//    private static LocalDateTime lastUpdateDate;
//    private static String certificateName;
//    private static String certificateDescription;
//    private static Double certificatePrice;
//    private static Integer certificateDuration;
//    private static List<Tag> newTags;
//    private static String newDescription;
//    private static Map<String,String> filters;
//
//
//    @BeforeAll
//    static void init(){
//        createDate =  LocalDateTime.of(2022, 4, 13, 13, 13, 44);
//        lastUpdateDate =  LocalDateTime.of(2022, 4, 13, 13, 13, 44);
//        certificateName = "3 swimming lessons";
//        certificateDescription = "Lessons with personal teacher";
//        certificatePrice = 120.33;
//        certificateDuration = 30;
//        newTags = Arrays.asList(new Tag("teacher"),new Tag("lessons"));
//        newDescription = "The cutest swimming lessons in your life!";
//        filters = new HashMap<>();
//        filters.put("partName","shark");
//    }
//
//
//    @Test
//    void insert() throws DaoException {
//        GiftCertificate giftCertificate = new GiftCertificate(certificateName, certificateDescription, certificatePrice, certificateDuration, createDate, lastUpdateDate);
//        giftCertificateDao.insert(giftCertificate);
//        Integer size = giftCertificateDao.getAll().size();
//        assertEquals(3,size);
//    }
//
//    @Test
//    void getById() throws DaoException {
//        GiftCertificate giftCertificate = giftCertificateDao.getById(2);
//        assertEquals(2,giftCertificate.getId());
//    }
//
//    @Test
//    void getAll() throws DaoException {
//        List<GiftCertificate> allTheTags = giftCertificateDao.getAll();
//        assertEquals(2,allTheTags.size());
//    }
//
//    @Test
//    void update() throws DaoException {
//        GiftCertificate giftCertificate = giftCertificateDao.getById(1);
//        Integer oldDuration = giftCertificate.getDuration();
//        assertEquals(80,oldDuration);
//        giftCertificate.setDuration(89);
//        giftCertificate.setDescription(newDescription);
//        giftCertificateDao.update(giftCertificate);
//        Integer newDuration = giftCertificate.getDuration();
//        String certificateDescription = giftCertificate.getDescription();
//        assertEquals(89,newDuration);
//        assertEquals(newDescription,certificateDescription);
//    }
//
//    @Test
//    void deleteByID() throws DaoException {
//        giftCertificateDao.deleteByID(1);
//        Integer size = giftCertificateDao.getAll().size();
//        assertEquals(2,size);
//    }
//
//    @Test
//    void deleteListOfCertificateTags() throws DaoException {
//        GiftCertificate giftCertificate = giftCertificateDao.getById(2);
//        Integer tagListSize = giftCertificate.getTags().size();
//        assertEquals(2,tagListSize);
//        giftCertificateDao.deleteListOfCertificateTags(2);
//        Integer tagsListSize = giftCertificateDao.getById(2).getTags().size();
//        assertEquals(0,tagsListSize);
//    }
//
//    @Test
//    void getQueryWithConditions() throws DaoException {
//        List<GiftCertificate> list = giftCertificateDao.getQueryWithConditions(filters);
//        assertEquals(1,list.size());
//    }
//}