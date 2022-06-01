package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.tomcat.jni.Local;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Class OrderDto extends RepresentationModel and helps to create OrderDto entity
 */
public class OrderDto extends RepresentationModel<OrderDto> {

    private Long id;
    private Double price;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime purchaseTime;
    @NotEmpty(message = "{giftCertificateListCannotBeNull}")
    private List<GiftCertificateDto> giftCertificateDto;
    private Long userId;

   public OrderDto() {
    }

    public OrderDto(long id, Double price, LocalDateTime purchaseTime, List<GiftCertificateDto> giftCertificateDto, Long userId) {
        this.id = id;
        this.price = price;
        this.purchaseTime = purchaseTime;
        this.giftCertificateDto = giftCertificateDto;
        this.userId = userId;
    }

    public Double getPrice() {
        return price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(LocalDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public List<GiftCertificateDto> getGiftCertificateDto() {
        return giftCertificateDto;
    }

    public void setGiftCertificateDto(List<GiftCertificateDto> giftCertificateDto) {
        this.giftCertificateDto = giftCertificateDto;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDto)) return false;
        OrderDto orderDto = (OrderDto) o;
        return  Objects.equals(getPrice(), orderDto.getPrice())
                && Objects.equals(getPurchaseTime(), orderDto.getPurchaseTime())
                && Objects.equals(getGiftCertificateDto(), orderDto.getGiftCertificateDto())
                && Objects.equals(getUserId(), orderDto.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrice(), getPurchaseTime(),getGiftCertificateDto(),getUserId());
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", price=" + price +
                ", purchaseTime='" + purchaseTime + '\'' +
                ", giftCertificateDto=" + giftCertificateDto +
                ", userId=" + userId +
                '}';
    }
}
