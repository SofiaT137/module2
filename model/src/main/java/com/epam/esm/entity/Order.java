package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Order class extends AbstractEntity and presents creation of the Order entity
 */
@Entity
@Table(name = "orders")
public class Order extends AbstractEntity {

    @Column(name = "price")
    private double price;

    @ManyToMany
    @JoinTable(
            name = "order_certificate",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "gift_certificate_id")
    )
    private List<GiftCertificate> giftCertificateList = new ArrayList<>();

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    public Order() {
    }

    public Order(long id,double price, User user) {
        super(id);
        this.price = price;
        this.user = user;
    }

    public Order(double price, User user) {
        this.price = price;
        this.user = user;
    }

    public void addGiftCertificateToOrder(GiftCertificate giftCertificate){
        giftCertificateList.add(giftCertificate);
        giftCertificate.getOrderList().add(this);
    }

    public void addUserToOrder(User user){
        user.getOrderList().add(this);
        this.setUser(user);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<GiftCertificate> getGiftCertificateList() {
        return giftCertificateList;
    }

    public void setGiftCertificateList(List<GiftCertificate> giftCertificateList) {
        this.giftCertificateList = giftCertificateList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Double.compare(order.getPrice(), getPrice()) == 0
                && Objects.equals(getUser(), order.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPrice(), getUser());
    }

    @Override
    public String toString() {
        return "Order{" +
                ", price=" + price +
                ", user=" + user +
                '}';
    }
}
