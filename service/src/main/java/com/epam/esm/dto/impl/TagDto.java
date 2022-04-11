package com.epam.esm.dto;

import com.epam.esm.entity.Tag;

import java.util.Objects;

public class TagDto extends AbstractDto<Long> {

    private String name;

    public TagDto(String name) {
        this.name = name;
    }

    public TagDto(Long id, String name) {
        super(id);
        this.name = name;
    }

    public TagDto(){

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
        if (!(o instanceof Tag)) return false;
        if (!super.equals(o)) return false;
        Tag tag = (Tag) o;
        return Objects.equals(getName(), tag.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName());
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                '}';
    }

}
