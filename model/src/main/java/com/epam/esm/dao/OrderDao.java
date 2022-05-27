package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order,Long> {

    @Query(value = "SELECT * FROM orders AS o WHERE o.user_id = :userId",
            nativeQuery = true)
    List<Order> ordersByUserId(long userId);

}
