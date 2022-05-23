package com.epam.esm.converter.impl;

import com.epam.esm.converter.Converter;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

/**
 * Class TagConverter presents converter for Tag and TadDto entities
 */
@Component
public class TagConverter implements Converter<Tag, TagDto> {

    @Override
    public Tag convert(TagDto value) {
        String name = value.getName();
        return new Tag(name);
    }

    @Override
    public TagDto convert(Tag value) {
        Long id = value.getId();
        String name = value.getTagName();
        return new TagDto(id,name);
    }
}
