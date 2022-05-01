package com.epam.esm.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order extends AbstractEntity<Long>{

    @Column(name = "price")
    private double price;

    @Column(name = "purchase_time")
    private LocalDateTime purchaseTime;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "order_certificate",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "gift_certificate_id")
    )
    private List<GiftCertificate> giftCertificateList;

    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JoinColumn(name = "user_id")
    private User user;

    public Order() {
    }

    public Order(double price, LocalDateTime purchaseTime) {
        this.price = price;
        this.purchaseTime = purchaseTime;
    }

    public void addGiftCertificateToOrder(GiftCertificate giftCertificate){
        if (giftCertificateList == null){
            giftCertificateList = new ArrayList<>();
        }
        giftCertificateList.add(giftCertificate);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(LocalDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Double.compare(order.getPrice(), getPrice()) == 0
                && Objects.equals(getPurchaseTime(), order.getPurchaseTime())
                && Objects.equals(getUser(), order.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPrice(), getPurchaseTime(), getUser());
    }

    @Override
    public String toString() {
        return "Order{" +
                "price=" + price +
                ", purchaseTime=" + purchaseTime +
                ", user=" + user +
                '}';
    }
}
