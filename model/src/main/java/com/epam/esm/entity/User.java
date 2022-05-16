package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;

/**
 * User class extends AbstractEntity and presents creation of the User entity
 */
@Entity
@Table(name = "users")
public class User extends AbstractEntity<Long> {

    @Column(name = "user_name")
    private String name;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Order> orderList;

    public User() {}

    public User(Long id){
        super(id);
    }

    public User(Long id, String name) {
        super(id);
        this.name = name;
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(getName(), user.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
