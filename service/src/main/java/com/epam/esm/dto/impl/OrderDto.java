package com.epam.esm.dto.impl;

import org.aspectj.weaver.ast.Or;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;
import java.util.Objects;

public class OrderDto extends RepresentationModel<OrderDto> {

    private Long id;
    private Double price;
    private String purchaseTime;
    private List<Long> giftCertificateId;
    private Long userId;

   public OrderDto() {
    }

    public OrderDto(long id,Double price, String purchaseTime, List<Long> giftCertificateId, Long userId) {
        this.id = id;
        this.price = price;
        this.purchaseTime = purchaseTime;
        this.giftCertificateId = giftCertificateId;
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

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public List<Long> getGiftCertificateId() {
        return giftCertificateId;
    }

    public void setGiftCertificateId(List<Long> giftCertificateId) {
        this.giftCertificateId = giftCertificateId;
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
        return getId() == orderDto.getId() && Objects.equals(getPrice(), orderDto.getPrice())
                && Objects.equals(getPurchaseTime(), orderDto.getPurchaseTime())
                && Objects.equals(getGiftCertificateId(), orderDto.getGiftCertificateId())
                && Objects.equals(getUserId(), orderDto.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPrice(), getPurchaseTime(), getGiftCertificateId(), getUserId());
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", price=" + price +
                ", purchaseTime='" + purchaseTime + '\'' +
                ", giftCertificateId=" + giftCertificateId +
                ", userId=" + userId +
                '}';
    }
}
