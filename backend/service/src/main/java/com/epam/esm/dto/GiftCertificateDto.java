package com.epam.esm.dto;

import com.epam.esm.validator.OnCreate;
import com.epam.esm.validator.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * Class GiftCertificateDto extends RepresentationModel and helps to create GiftCertificateDto entity
 */
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {

    private Long id;
    @NotEmpty(message = "giftCertificateNameCannotBeNull",groups = OnCreate.class)
    @Size(min = 6,max = 48,message = "{giftCertificateLengthIsForbidden}",groups = {OnCreate.class, OnUpdate.class})
    private String giftCertificateName;
    @Size(min = 12,max = 1000,message = "{giftCertificateDescriptionIsTooLong}",groups = {OnCreate.class,
            OnUpdate.class})
    private String description;
    @DecimalMin(value = "0.01",message = "{giftCertificatePriceIsForbidden}",groups = {OnCreate.class, OnUpdate.class})
    @DecimalMax(value = "9999.99",message = "{giftCertificatePriceIsForbidden}",groups = {OnCreate.class,
            OnUpdate.class})
    private Double price;
    @NotNull(message = "{giftCertificateDurationCannotBeNull}",groups = {OnCreate.class})
    @Min(value = 1,message = "{giftCertificateDurationIsForbidden}",groups = {OnCreate.class, OnUpdate.class})
    private Integer duration;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createDate;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastUpdateDate;
    private Set<TagDto> tags;

    public GiftCertificateDto(Long id, String giftCertificateName,
                              String description, Double price, Integer duration,LocalDateTime createDate,
                              LocalDateTime lastUpdateDate,Set<TagDto> tags) {
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
                              Double price, Integer duration,LocalDateTime createDate,
                              LocalDateTime lastUpdateDate,Set<TagDto> tags) {
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

    public Set<TagDto> getTags() {
        return tags;
    }

    public void setTags(Set<TagDto> tags) {
        this.tags = tags;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GiftCertificateDto)) return false;
        if (!super.equals(o)) return false;
        GiftCertificateDto that = (GiftCertificateDto) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getGiftCertificateName(), that.getGiftCertificateName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getGiftCertificateName());
    }

    @Override
    public String toString() {
        return "GiftCertificateDto{" +
                "id=" + id +
                ", giftCertificateName='" + giftCertificateName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", tags=" + tags +
                '}';
    }
}
