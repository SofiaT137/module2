package com.epam.esm.dto.impl;

import com.epam.esm.dto.AbstractDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

public class GiftCertificateDto extends AbstractDto<Long> {

    private String giftCertificateName;
    private String description;
    private Double price;
    private Integer duration;
    private String createDate;
    private String lastUpdateDate;
    private List<String> tags;

    public GiftCertificateDto(Long id, String giftCertificateName, String description, Double price, Integer duration, String createDate, String lastUpdateDate, List<String> tags) {
        this.id = id;
        this.giftCertificateName = giftCertificateName;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    public GiftCertificateDto(String giftCertificateName, String description, Double price, Integer duration, String createDate, String lastUpdateDate, List<String> tags) {
        this.giftCertificateName = giftCertificateName;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    public GiftCertificateDto() {
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GiftCertificateDto)) return false;
        GiftCertificateDto that = (GiftCertificateDto) o;
        return Objects.equals(getGiftCertificateName(), that.getGiftCertificateName())
                && Objects.equals(getDescription(), that.getDescription())
                && Objects.equals(getPrice(), that.getPrice())
                && Objects.equals(getDuration(), that.getDuration())
                && Objects.equals(getCreateDate(), that.getCreateDate())
                && Objects.equals(getLastUpdateDate(), that.getLastUpdateDate())
                && Objects.equals(getTags(), that.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGiftCertificateName(),
                getDescription(),
                getPrice(),
                getDuration(),
                getCreateDate(),
                getLastUpdateDate(),
                getTags());
    }

    @Override
    public String toString() {
        return "GiftCertificateDto{" +
                "id=" + id +
                ", giftCertificateName='" + giftCertificateName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate='" + createDate + '\'' +
                ", lastUpdateDate='" + lastUpdateDate + '\'' +
                ", tags=" + tags +
                '}';
    }
}
