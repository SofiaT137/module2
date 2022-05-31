package com.epam.esm.dto;

import com.epam.esm.validator.onCreate;
import com.epam.esm.validator.onUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * Class GiftCertificateDto extends RepresentationModel and helps to create GiftCertificateDto entity
 */
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {

    private Long id;
    @NotEmpty(message = "giftCertificateNameCannotBeNull",groups = onCreate.class)
    @Size(min = 3,max = 50,message = "{giftCertificateLengthIsForbidden}",groups = onCreate.class)
    private String giftCertificateName;
    @Size(max = 450,message = "{giftCertificateDescriptionIsTooLong}",groups = onCreate.class)
    private String description;
    @DecimalMin(value = "0.01",message = "{giftCertificatePriceIsForbidden}",groups = onCreate.class)
    @DecimalMax(value = "9999.99",message = "{giftCertificatePriceIsForbidden}",groups = onCreate.class)
    private Double price;
    @NotNull(message = "{giftCertificateDurationCannotBeNull}",groups = {onCreate.class, onUpdate.class})
    @Min(value = 1,message = "{giftCertificateDurationIsForbidden}",groups = {onCreate.class, onUpdate.class})
    @Max(value = 90,message = "{giftCertificateDurationIsForbidden}",groups = {onCreate.class, onUpdate.class})
    private Integer duration;
    private Set<TagDto> tags;

    public GiftCertificateDto(Long id, String giftCertificateName,
                              String description, Double price, Integer duration, Set<TagDto> tags) {
        this.id = id;
        this.giftCertificateName = giftCertificateName;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }

    public GiftCertificateDto(String giftCertificateName, String description,
                              Double price, Integer duration, Set<TagDto> tags) {
        this.giftCertificateName = giftCertificateName;
        this.description = description;
        this.price = price;
        this.duration = duration;
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
                && Objects.equals(getTags(), that.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getGiftCertificateName(),
                getDescription(), getPrice(), getDuration(), getTags());
    }

    @Override
    public String toString() {
        return "GiftCertificateDto{" +
                "id=" + id +
                ", giftCertificateName='" + giftCertificateName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", tags=" + tags +
                '}';
    }
}
