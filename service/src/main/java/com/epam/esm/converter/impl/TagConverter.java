package com.epam.esm.converter.impl;

import com.epam.esm.converter.Converter;
import com.epam.esm.dto.impl.TagDto;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagConverter implements Converter<Tag, TagDto> {

    @Override
    public Tag convert(TagDto value) {
        Long id = value.getId();
        String name = value.getName();
        return new Tag(id,name);
    }

    @Override
    public TagDto convert(Tag value) {
        Long id = value.getId();
        String name = value.getName();
        return new TagDto(id,name);
    }
}
