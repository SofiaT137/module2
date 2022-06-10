package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

/**
 * OrderRepository interface extends JpaRepository functionality for the Order entity
 */
public interface OrderRepository extends JpaRepository<Order,Long> {

    /**
     * The method findAllOrderWhereUserId searches all user orders in a 'orders' table
     * @param userId User id (Long value)
     * @param pageable Pageable pageable
     * @return  Page of the Orders entities
     */
    @Query(value = "select o from Order o where o.user.id = :userId")
    Page<Order> findAllOrderWhereUserId(long userId, Pageable pageable);

}
