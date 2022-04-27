package com.epam.esm.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User extends AbstractEntity<Long>{

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private List<Order> orderList;

    public User() {
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void addOrderToUser(Order order){
        if (orderList == null){
            orderList = new ArrayList<>();
        }
        orderList.add(order);
        order.setUser(this);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(getFirstName(), user.getFirstName())
                && Objects.equals(getLastName(), user.getLastName())
                && Objects.equals(getOrderList(), user.getOrderList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFirstName(), getLastName(), getOrderList());
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
