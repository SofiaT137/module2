package com.epam.esm.service.business_service;

import com.epam.esm.converter.impl.GiftCertificateConverter;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class GiftCertificateBusinessService is implementation of the GiftCertificateService interface
 * The class presents service business logic for GiftCertificate entity
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
    public GiftCertificateDto insert(GiftCertificateDto entityDto){
        GiftCertificate entity = giftCertificateConverter.convert(entityDto);
        return giftCertificateConverter.convert(giftCertificateLogicService.insert(entity));
    }

    @Override
    public GiftCertificateDto getById(long id){
        GiftCertificate giftCertificate = giftCertificateLogicService.getById(id);
        return giftCertificateConverter.convert(giftCertificate);
    }

    @Override
    public Page<GiftCertificateDto> getAll(int pageSize, int pageNumber){
        Page<GiftCertificate> giftCertificateList = giftCertificateLogicService.getAll(pageSize, pageNumber);
        return giftCertificateList.map(giftCertificateConverter::convert);
    }


    @Override
    public void deleteByID(long id) {
        giftCertificateLogicService.deleteByID(id);
    }

    @Override
    public GiftCertificateDto update(Long id, GiftCertificateDto entity) {
        GiftCertificate giftCertificateEntity = giftCertificateConverter.convert(entity);
        GiftCertificate giftCertificate = giftCertificateLogicService.update(id,giftCertificateEntity);
        return giftCertificateConverter.convert(giftCertificate);
    }

    @Override
    public List<GiftCertificateDto> getQueryWithConditions(MultiValueMap<String, String> mapWithFilters){
        List<GiftCertificate> giftCertificateList = giftCertificateLogicService.getQueryWithConditions(mapWithFilters);
        return giftCertificateList.stream().map(giftCertificateConverter::convert).collect(Collectors.toList());
    }
}
