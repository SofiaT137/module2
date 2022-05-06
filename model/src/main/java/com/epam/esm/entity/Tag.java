package com.epam.esm.entity;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * Tag class extends AbstractEntity and presents creation of the Tag entity
 */
@Entity
@Table(name = "tags")
//@Audited
public class Tag extends AbstractEntity<Long> implements Serializable {

    @Column(name = "tag_name")
    private String name;

    public Tag(String name) {
        this.name = name;
    }

    public Tag(){}

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
                "id='" + super.getId() +'\''+
                "name='" + name + '\'' +
                '}';
    }
}
