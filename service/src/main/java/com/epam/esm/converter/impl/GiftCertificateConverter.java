package com.epam.esm.converter.impl;

import com.epam.esm.converter.Converter;
import com.epam.esm.dto.impl.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GiftCertificateConverter implements Converter<GiftCertificate, GiftCertificateDto,Long> {

    @Override
    public GiftCertificate convert(GiftCertificateDto value) {

        String createDateDto = value.getCreateDate();
        String lastUpdateDateDto = value.getLastUpdateDate();
        LocalDateTime createDate = validateDate(createDateDto);
        LocalDateTime lastUpdateDate = validateDate(lastUpdateDateDto);
        List<String> valueTagsList = value.getTags();
        List<Tag> finalTagList = convertNamesToTagList(valueTagsList);

        return new GiftCertificate(value.getId(),value.getGiftCertificateName(),value.getDescription(),value.getPrice(),value.getDuration(),createDate,lastUpdateDate,finalTagList);
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

    private List<Tag> convertNamesToTagList(List<String> valueTagsList){
        List<Tag> convertNamesToTags;
        if (valueTagsList == null){
            convertNamesToTags = new ArrayList<>();
        }else{
            convertNamesToTags = getTagList(valueTagsList);
        }
        return convertNamesToTags;
    }


    private List<Tag> getTagList(List<String> listTagsName) {
        return listTagsName.stream().map(Tag::new).collect(Collectors.toList());
    }

    private List<String> getNamesOfTagsList(List<Tag> listTags){
        return listTags.stream().map(Tag::getName).collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto convert(GiftCertificate value) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String createDate = value.getCreateDate().format(formatter);
        String lastUpdateDate = value.getLastUpdateDate().format(formatter);
        return new GiftCertificateDto(value.getId(), value.getGiftCertificateName(), value.getDescription(), value.getPrice(), value.getDuration(), createDate,lastUpdateDate, getNamesOfTagsList(value.getTags()));
    }
}
