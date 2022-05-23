package com.epam.esm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Tag class extends AbstractEntity and presents creation of the Tag entity
 */
@Entity
@Table(name = "tags")
public class Tag extends AbstractEntity<Long> {

    @Column(name = "tag_name")
    private String tagName;

    @ManyToMany(mappedBy = "tagList")
    private List<GiftCertificate> giftCertificates = new ArrayList<>();

    public Tag(){}

    public Tag(Long id,String tagName) {
        super(id);
        this.tagName = tagName;
    }

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<GiftCertificate> getGiftCertificates() {
        return giftCertificates;
    }

    public void setGiftCertificates(List<GiftCertificate> giftCertificates) {
        this.giftCertificates = giftCertificates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        if (!super.equals(o)) return false;
        Tag tag = (Tag) o;
        return Objects.equals(getTagName(), tag.getTagName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTagName());
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagName='" + tagName + '\'' +
                '}';
    }
}
