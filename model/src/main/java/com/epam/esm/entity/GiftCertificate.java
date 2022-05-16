package com.epam.esm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * GiftCertificate class extends AbstractEntity and presents creation of the GiftCertificate entity
 */
@Entity
@Table(name = "gift_certificates")
public class GiftCertificate extends AbstractEntity<Long>{

    @Column(name = "gift_certificate_name")
    private String giftCertificateName;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @ManyToMany
    @JoinTable(
            name = "gift_certificate_tag",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tagList = new ArrayList<>();

    @ManyToMany(mappedBy = "giftCertificateList")
    private List<Order> orderList = new ArrayList<>();

    public GiftCertificate() {}

    public GiftCertificate(Long id) {super(id);}

    public GiftCertificate(Long id, String giftCertificateName, String description, Double price,
                           Integer duration, LocalDateTime createDate, LocalDateTime lastUpdateDate,List<Tag> tagList) {
        super(id);
        this.giftCertificateName = giftCertificateName;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tagList = tagList;
    }

    public GiftCertificate(String name, String description, Double price, Integer duration,
                           LocalDateTime createDate, LocalDateTime lastUpdateDate,List<Tag> tagList) {
        this.giftCertificateName = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tagList = tagList;
    }

    public void addTagToGiftCertificate(Tag tag){
        tagList.add(tag);
        tag.getGiftCertificates().add(this);
    }

    public String getGiftCertificateName() {
        return giftCertificateName;
    }

    public void setGiftCertificateName(String giftCertificateName) {
        this.giftCertificateName = giftCertificateName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GiftCertificate)) return false;
        if (!super.equals(o)) return false;
        GiftCertificate that = (GiftCertificate) o;
        return Objects.equals(getGiftCertificateName(), that.getGiftCertificateName())
                && Objects.equals(getDescription(), that.getDescription())
                && Objects.equals(getPrice(), that.getPrice())
                && Objects.equals(getDuration(), that.getDuration())
                && Objects.equals(getCreateDate(), that.getCreateDate());

    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),
                getGiftCertificateName(),
                getDescription(),
                getPrice(),
                getDuration(),
                getCreateDate());
    }

    @Override
    public String toString() {
        return "GiftCertificate{" +
                "id='" + super.getId() + '\'' +
                "giftCertificateName='" + giftCertificateName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate=" + createDate +
                '}';
    }
}
