package com.epam.esm.dto;

import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Class TagDto extends RepresentationModel and helps to create TagDto entity
 */
public class TagDto extends RepresentationModel<TagDto> {

    private Long id;
    @NotEmpty(message = "{tagNameCannotBeNull}")
    @Size(min = 2, max = 35,message = "{thisTagLengthIsForbidden}")
    private String name;

    public TagDto(String name){
        this.name = name;
    }

    public TagDto(Long id,String name) {
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
        TagDto tagDto = (TagDto) o;
        return Objects.equals(getName(), tagDto.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName());
    }

    @Override
    public String toString() {
        return "TagDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
