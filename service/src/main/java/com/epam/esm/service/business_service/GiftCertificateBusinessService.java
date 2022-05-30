package com.epam.esm.service.business_service;

import com.epam.esm.converter.impl.GiftCertificateConverter;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exceptions.IncorrectTransferredParametersException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validator.ValidationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class GiftCertificateBusinessService is implementation of the GiftCertificateService interface
 * The class presents service business logic for GiftCertificate entity
 */
@Service("giftCertificateBusinessService")
public class GiftCertificateBusinessService implements GiftCertificateService<GiftCertificateDto> {

    private final GiftCertificateConverter giftCertificateConverter;
    private final ValidationFacade validationFacade;
    private GiftCertificateService<GiftCertificate> giftCertificateLogicService;

    private static final String INCORRECT_TRANSFERRED_GET_VALUES_EXCEPTION = "checkTheValuesThatYouTransferred";

    @Autowired
    public GiftCertificateBusinessService(GiftCertificateConverter giftCertificateConverter,
                                          ValidationFacade validationFacade) {
        this.giftCertificateConverter = giftCertificateConverter;
        this.validationFacade = validationFacade;
    }

    @Autowired
    @Qualifier("giftCertificateLogicService")
    public void setGiftCertificateLogicService(GiftCertificateService<GiftCertificate> giftCertificateLogicService) {
        this.giftCertificateLogicService = giftCertificateLogicService;
    }

    @Override
    public GiftCertificateDto insert(GiftCertificateDto entityDto){
        validationFacade.validate(entityDto);
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
    public int update(Long id, GiftCertificateDto entity) {
        validationFacade.validate(entity);
        GiftCertificate giftCertificateEntity = giftCertificateConverter.convert(entity);
        return giftCertificateLogicService.update(id,giftCertificateEntity);
    }

    @Override
    public List<GiftCertificateDto> getQueryWithConditions(MultiValueMap<String, String> mapWithFilters){
        checkTransferredMapParameters(mapWithFilters);
        List<GiftCertificate> giftCertificateList = giftCertificateLogicService.getQueryWithConditions(mapWithFilters);
        return giftCertificateList.stream().map(giftCertificateConverter::convert).collect(Collectors.toList());
    }

    private void checkTransferredMapParameters(MultiValueMap<String, String> mapWithFilters){
        List<String> allowedKeys = Arrays.asList("tagName", "partName", "partDescription", "sortByName",
                "sortByCreationDate");
        List<String> transferredKeys = new ArrayList<>(mapWithFilters.keySet());
        for (String transferredKey : transferredKeys) {
            if (!allowedKeys.contains(transferredKey)) {
                throw new IncorrectTransferredParametersException("INCORRECT_TRANSFERRED_GET_VALUES_EXCEPTION");
            }
        }
    }
}
