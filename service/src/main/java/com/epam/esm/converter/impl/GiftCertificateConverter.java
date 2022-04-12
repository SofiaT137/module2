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
        List<String> valueTagsList = value.getTags();
        List<Tag> convertNamesToTags;
        if (valueTagsList == null){
            convertNamesToTags = new ArrayList<>();
        }else{
            convertNamesToTags = getTagList(valueTagsList);
        }
        LocalDateTime createDate = null;
        if (createDateDto != null){
            createDate = LocalDateTime.parse(createDateDto);
        }
        LocalDateTime lastUpdateDate = LocalDateTime.parse(value.getLastUpdateDate());
        return new GiftCertificate(value.getId(),value.getGiftCertificateName(),value.getDescription(),value.getPrice(),value.getDuration(),createDate,lastUpdateDate,convertNamesToTags);
    }

    private List<Tag> getTagList(List<String> listTagsName) {
        return listTagsName.stream().map(Tag::new).collect(Collectors.toList());
    }

    private List<String> getStringList(List<Tag> listTags){
        return listTags.stream().map(Tag::getName).collect(Collectors.toList());
    }

    @Override
    public GiftCertificateDto convert(GiftCertificate value) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String createDate = value.getCreateDate().format(formatter);
        String lastUpdateDate = value.getLastUpdateDate().format(formatter);
        return new GiftCertificateDto(value.getId(), value.getGiftCertificateName(), value.getDescription(), value.getPrice(), value.getDuration(), createDate,lastUpdateDate,getStringList(value.getTags()));
    }
}
