package com.epam.esm.service.business_service;

import com.epam.esm.converter.impl.GiftCertificateConverter;
import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class GiftCertificateServiceImpl is implementation of interface GiftCertificateService
 * The class presents service layer logic for GiftCertificateService entity
 */
@Service("giftCertificateBusinessService")
public class GiftCertificateBusinessService implements GiftCertificateService<GiftCertificateDto> {

    private final GiftCertificateConverter giftCertificateConverter;
    private GiftCertificateService<GiftCertificate> giftCertificateLogicService;

    @Autowired
    public GiftCertificateBusinessService(GiftCertificateConverter giftCertificateConverter) {
        this.giftCertificateConverter = giftCertificateConverter;
    }

    @Autowired
    @Qualifier("giftCertificateLogicService")
    public void setGiftCertificateLogicService(GiftCertificateService<GiftCertificate> giftCertificateLogicService) {
        this.giftCertificateLogicService = giftCertificateLogicService;
    }

    @Override
    public void insert(GiftCertificateDto entityDto){
        GiftCertificate entity = giftCertificateConverter.convert(entityDto);
        giftCertificateLogicService.insert(entity);
    }

    @Override
    public GiftCertificateDto getById(long id){
        GiftCertificate giftCertificate = giftCertificateLogicService.getById(id);
        return giftCertificateConverter.convert(giftCertificate);
    }

    @Override
    public List<GiftCertificateDto> getAll(int pageSize,int pageNumber){
        List<GiftCertificate> giftCertificateList = giftCertificateLogicService.getAll(pageSize, pageNumber);
        return giftCertificateList.stream().map(giftCertificateConverter::convert).collect(Collectors.toList());
    }

    @Override
    public void deleteByID(long id) {
        giftCertificateLogicService.deleteByID(id);
    }

    @Override
    public void update(Long id,List<Tag> tags,GiftCertificateDto entity) {
        entity.setId(id);
        entity.setLastUpdateDate(getCurrentDate());
        List<String> tagNames = entity.getTags();
        if (tagNames != null) {
            tags = getNewTagList(tagNames);
        }
        GiftCertificate giftCertificateEntity = giftCertificateConverter.convert(entity);
        giftCertificateLogicService.update(id,tags,giftCertificateEntity);
    }

    private String getCurrentDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return LocalDateTime.now().format(formatter);
    }

    private List<Tag> getNewTagList(List<String> listOfTagNames){
        return listOfTagNames.stream().map(Tag::new).collect(Collectors.toList());
    }
    @Override
    public List<GiftCertificateDto> getQueryWithConditions(MultiValueMap<String, String> mapWithFilters){
        List<GiftCertificate> giftCertificateList = giftCertificateLogicService.getQueryWithConditions(mapWithFilters);
        return giftCertificateList.stream().map(giftCertificateConverter::convert).collect(Collectors.toList());
    }
}
