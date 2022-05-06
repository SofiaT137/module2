package com.epam.esm.dto.impl;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class TagDto extends RepresentationModel<TagDto> {

    private Long id;
    private String name;

    public TagDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TagDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagDto)) return false;
        if (!super.equals(o)) return false;
        TagDto tagDto = (TagDto) o;
        return getId() == tagDto.getId() && Objects.equals(getName(), tagDto.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getName());
    }

    @Override
    public String toString() {
        return "TagDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
