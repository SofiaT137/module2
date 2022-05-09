package com.epam.esm.converter.impl;

import com.epam.esm.converter.Converter;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GiftCertificateConverter implements Converter<GiftCertificate, GiftCertificateDto> {

    private final Converter<Tag, TagDto> tagConverter;

    @Autowired
    public GiftCertificateConverter(Converter<Tag, TagDto> tagConverter) {
        this.tagConverter = tagConverter;
    }


    @Override
    public GiftCertificate convert(GiftCertificateDto value) {
        String createDateDto = value.getCreateDate();
        String lastUpdateDateDto = value.getLastUpdateDate();
        LocalDateTime createDate = validateDate(createDateDto);
        LocalDateTime lastUpdateDate = validateDate(lastUpdateDateDto);
        List<TagDto> valueTagDtoList = value.getTags();
        List<Tag> valueTagList = new ArrayList<>();
        if(valueTagDtoList!=null){
            valueTagList = valueTagDtoList.stream().map(tagConverter::convert).collect(Collectors.toList());
        }
        return new GiftCertificate(value.getId(),value.getGiftCertificateName(),
                value.getDescription(),value.getPrice(),value.getDuration(),createDate,lastUpdateDate,valueTagList);
    }

    private LocalDateTime validateDate(String dateTime){
        LocalDateTime date;
        if (dateTime != null){
            date = LocalDateTime.parse(dateTime);
        } else {
            date = getCurrentDateTime();
        }
        return date;
    }

    private LocalDateTime getCurrentDateTime(){
        return LocalDateTime.now();
    }

    @Override
    public GiftCertificateDto convert(GiftCertificate value) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String createDate = value.getCreateDate().format(formatter);
        String lastUpdateDate = value.getLastUpdateDate().format(formatter);
        List<Tag> valueTagList = value.getTags();
        List<TagDto> valueTagDtoList = valueTagList.stream().map(tagConverter::convert).collect(Collectors.toList());
        return new GiftCertificateDto(value.getId(), value.getGiftCertificateName(), value.getDescription(),
                value.getPrice(), value.getDuration(), createDate,lastUpdateDate, valueTagDtoList);
    }
}
