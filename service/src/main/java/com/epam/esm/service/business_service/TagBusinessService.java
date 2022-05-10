package com.epam.esm.service.business_service;

import com.epam.esm.converter.impl.TagConverter;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("tagBusinessService")
public class TagBusinessService implements TagService<TagDto> {

    private final TagConverter tagConverter;
    private TagService<Tag> tagLogicService;

    @Autowired
    public TagBusinessService(TagConverter tagConverter) {
        this.tagConverter = tagConverter;
    }

    @Autowired
    @Qualifier("tagLogicService")
    public void setTagLogicService(TagService<Tag> tagLogicService) {
        this.tagLogicService = tagLogicService;
    }

    @Override
    public TagDto insert(TagDto entityDto) {
        Tag entity = tagConverter.convert(entityDto);
        return tagConverter.convert(tagLogicService.insert(entity));
    }

    @Override
    public void deleteByID(long id) {
        tagLogicService.deleteByID(id);
    }

    @Override
    public TagDto getById(long id) {
        Tag entity = tagLogicService.getById(id);
        return tagConverter.convert(entity);
    }

    @Override
    public List<TagDto> getAll(int pageSize, int pageNumber) {
        List<Tag> tagList = tagLogicService.getAll(pageSize,pageNumber);
        return tagList.stream().map(tagConverter::convert).collect(Collectors.toList());
    }

    @Override
    public TagDto findTheMostWidelyUsedUserTagWithHigherOrderCost(Long userId) {
        Tag tag = tagLogicService.findTheMostWidelyUsedUserTagWithHigherOrderCost(userId);
        return tagConverter.convert(tag);
    }

    @Override
    public TagDto findTagByTagName(String name) {
        Tag tag = tagLogicService.findTagByTagName(name);
        return tagConverter.convert(tag);
    }

    @Override
    public List<Tag> getCertificateTagList(List<Tag> tagList) {
        return tagLogicService.getCertificateTagList(tagList);
    }
}
