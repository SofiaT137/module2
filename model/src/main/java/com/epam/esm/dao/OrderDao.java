package com.epam.esm.dao;

import com.epam.esm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderDao extends JpaRepository<Order,Long> {

    @Query(value = "select o from Order o where o.user.id = :userId")
    List<Order> findAllByUserId(long userId, Pageable pageable);

}
