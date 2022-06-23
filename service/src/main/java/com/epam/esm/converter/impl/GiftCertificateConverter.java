package com.epam.esm.converter.impl;

import com.epam.esm.converter.Converter;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        Set<TagDto> valueTagDtoList = value.getTags();
        List<Tag> valueTagList = new ArrayList<>();
        if(valueTagDtoList!=null){
            valueTagList = convertTagDtoList(valueTagDtoList);
        }
        return new GiftCertificate(value.getId(),value.getGiftCertificateName(),
                value.getDescription(),value.getPrice(),value.getDuration(),
                valueTagList);
    }

    private List<Tag> convertTagDtoList(Set<TagDto> valueTagDtoList){
        return valueTagDtoList.stream().map(tagConverter::convert).collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto convert(GiftCertificate value) {
        Set<TagDto> valueTagDtoList = this.convertTagList(value.getTagList());
        return new GiftCertificateDto(value.getName(), value.getDescription(),
                value.getPrice(), value.getDuration(),value.getCreatedDate(), value.getLastModifiedDate(),
                valueTagDtoList);
    }

    private Set<TagDto> convertTagList(List<Tag> valueTagList){
        return valueTagList.stream().map(tagConverter::convert).collect(Collectors.toSet());
    }
}
