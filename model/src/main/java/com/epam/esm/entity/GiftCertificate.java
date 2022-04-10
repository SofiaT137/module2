package com.epam.esm.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class GiftCertificate extends AbstractEntity {

    private String gift_certificate_name;
    private String description;
    private Double price;
    private Integer duration;
    private LocalDateTime create_date;
    private LocalDateTime last_update_date;
    private List<Tag> tags;

    public GiftCertificate() {

    }

    public GiftCertificate(String name, String description, Double price, Integer duration, LocalDateTime createDate, LocalDateTime lastUpdateDate, List<Tag> tags) {
        this.gift_certificate_name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.create_date = createDate;
        this.last_update_date = lastUpdateDate;
        this.tags = tags;
    }

    public GiftCertificate(Long id, String name, String description, Double price, Integer duration, LocalDateTime createDate, LocalDateTime lastUpdateDate, List<Tag> tags) {
        super(id);
        this.gift_certificate_name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.create_date = createDate;
        this.last_update_date = lastUpdateDate;
        this.tags = tags;
    }

    public String getGift_certificate_name() {
        return gift_certificate_name;
    }

    public void setGift_certificate_name(String gift_certificate_name) {
        this.gift_certificate_name = gift_certificate_name;
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

    public LocalDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }

    public LocalDateTime getLast_update_date() {
        return last_update_date;
    }

    public void setLast_update_date(LocalDateTime last_update_date) {
        this.last_update_date = last_update_date;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GiftCertificate)) return false;
        if (!super.equals(o)) return false;
        GiftCertificate that = (GiftCertificate) o;
        return Objects.equals(getGift_certificate_name(), that.getGift_certificate_name()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getDuration(), that.getDuration()) && Objects.equals(getCreate_date(), that.getCreate_date()) && Objects.equals(getLast_update_date(), that.getLast_update_date()) && Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getGift_certificate_name(), getDescription(), getPrice(), getDuration(), getCreate_date(), getLast_update_date(), tags);
    }

    @Override
    public String toString() {
        return "GiftCertificate{" +
                "gift_certificate_name='" + gift_certificate_name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", create_date=" + create_date +
                ", last_update_date=" + last_update_date +
                ", tags=" + tags +
                '}';
    }
}
