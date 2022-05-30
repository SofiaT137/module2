package com.epam.esm.converter.impl;

import com.epam.esm.converter.Converter;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Class GiftCertificateConverter presents converter for GiftCertificate and GiftCertificateDto entities
 */
@Component
public class GiftCertificateConverter implements Converter<GiftCertificate, GiftCertificateDto> {

    private final Converter<Tag, TagDto> tagConverter;

    @Autowired
    public GiftCertificateConverter(Converter<Tag, TagDto> tagConverter) {
        this.tagConverter = tagConverter;
    }


    @Override
    public GiftCertificate convert(GiftCertificateDto value) {
        LocalDateTime createDate = value.getCreateDate();
        LocalDateTime lastUpdateDate = validateDate(value.getLastUpdateDate());
        Set<TagDto> valueTagDtoList = value.getTags();
        List<Tag> valueTagList = new ArrayList<>();
        if(valueTagDtoList!=null){
            valueTagList = convertTagDtoList(valueTagDtoList);
        }
        return new GiftCertificate(value.getId(),value.getGiftCertificateName(),
                value.getDescription(),value.getPrice(),value.getDuration(),
                createDate,lastUpdateDate,valueTagList);
    }

    private List<Tag> convertTagDtoList(Set<TagDto> valueTagDtoList){
        return valueTagDtoList.stream().map(tagConverter::convert).collect(Collectors.toList());
    }

    private LocalDateTime validateDate(LocalDateTime dateTime){
        LocalDateTime date;
        if (dateTime != null){
            date = LocalDateTime.now();
        } else {
            date = null;
        }
        return date;
    }

    @Override
    public GiftCertificateDto convert(GiftCertificate value) {
        LocalDateTime createDate = validateDate(value.getCreateDate());
        LocalDateTime lastUpdateDate = validateDate(value.getLastUpdateDate());
        Set<TagDto> valueTagDtoList = this.convertTagList(value.getTagList());
        return new GiftCertificateDto(value.getId(), value.getName(), value.getDescription(),
                value.getPrice(), value.getDuration(), createDate,lastUpdateDate, valueTagDtoList);
    }

    private Set<TagDto> convertTagList(List<Tag> valueTagList){
        return valueTagList.stream().map(tagConverter::convert).collect(Collectors.toSet());
    }
}
