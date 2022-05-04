package com.epam.esm.dto.impl;

import com.epam.esm.dto.AbstractDto;

import java.util.List;
import java.util.Objects;

public class OrderDto extends AbstractDto<Long> {

    private Double price;
    private String purchaseTime;
    private List<Long> giftCertificateId;
    private Long userId;

    public OrderDto() {
    }

    public OrderDto(Double price, String purchaseTime) {
        this.price = price;
        this.purchaseTime = purchaseTime;
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
        return Objects.equals(getPrice(), orderDto.getPrice())
                && Objects.equals(getPurchaseTime(), orderDto.getPurchaseTime())
                && Objects.equals(getGiftCertificateId(), orderDto.getGiftCertificateId())
                && Objects.equals(getUserId(), orderDto.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrice(), getPurchaseTime(), getGiftCertificateId(), getUserId());
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", price=" + price +
                ", purchaseTime='" + purchaseTime + '\'' +
                '}';
    }
}
