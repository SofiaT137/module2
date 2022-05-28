package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * Class GiftCertificateDto extends RepresentationModel and helps to create GiftCertificateDto entity
 */
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {

    private Long id;
    @NotEmpty(message = "{giftCertificateNameCannotBeNull}",groups = { PrePersist.class})
    @Size(min = 3,max = 50,message = "{giftCertificateLengthIsForbidden}",groups = { PrePersist.class})
    private String giftCertificateName;
    @Max(value = 450,message = "{giftCertificateDescriptionIsTooLong}",groups = { PrePersist.class})
    private String description;
    @DecimalMin(value = "0.01",message = "{giftCertificatePriceIsForbidden}",groups = { PrePersist.class})
    @DecimalMax(value = "9999.99",message = "{giftCertificatePriceIsForbidden}",groups = { PrePersist.class})
    private Double price;
    @NotNull(message = "{giftCertificateDurationCannotBeNull}",groups = {PreUpdate.class, PrePersist.class})
    @Min(value = 1,message = "{giftCertificateDurationIsForbidden}",groups = {PreUpdate.class, PrePersist.class})
    @Max(value = 90,message = "{giftCertificateDurationIsForbidden}",groups = {PreUpdate.class, PrePersist.class})
    private Integer duration;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastUpdateDate;
    private Set<TagDto> tags;

    public GiftCertificateDto(Long id, String giftCertificateName,
                              String description, Double price, Integer duration,
                              LocalDateTime createDate, LocalDateTime lastUpdateDate, Set<TagDto> tags) {
        this.id = id;
        this.giftCertificateName = giftCertificateName;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    public GiftCertificateDto(String giftCertificateName, String description,
                              Double price, Integer duration,
                              LocalDateTime createDate, LocalDateTime lastUpdateDate, Set<TagDto> tags) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<TagDto> getTags() {
        return tags;
    }

    public void setTags(Set<TagDto> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GiftCertificateDto)) return false;
        if (!super.equals(o)) return false;
        GiftCertificateDto that = (GiftCertificateDto) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getGiftCertificateName(), that.getGiftCertificateName())
                && Objects.equals(getDescription(), that.getDescription())
                && Objects.equals(getPrice(), that.getPrice())
                && Objects.equals(getDuration(), that.getDuration())
                && Objects.equals(getCreateDate(), that.getCreateDate())
                && Objects.equals(getLastUpdateDate(), that.getLastUpdateDate())
                && Objects.equals(getTags(), that.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getGiftCertificateName(),
                getDescription(), getPrice(), getDuration(), getCreateDate(), getLastUpdateDate(), getTags());
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
