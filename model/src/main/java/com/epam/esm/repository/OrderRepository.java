package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query(value = "select o from Order o where o.user.id = :userId")
    Page<Order> findAllByUserId(long userId, Pageable pageable);

}
