package com.ukma.library.repository;

import com.ukma.library.model.Order;
import com.ukma.library.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findById(Long id);

    @Query(value = "SELECT * FROM ORDERS WHERE orders.user_id = ?1 AND orders.status = ?2",
            nativeQuery = true)
    List<Order> findByUserIdAndStatus(Long userId, String orderStatus);

    @Query(value = "SELECT * FROM ORDERS WHERE orders.user_id = ?1",
            nativeQuery = true)
    List<Order> findByUserId(Long userId);

    @Query(value = "SELECT * FROM ORDERS WHERE orders.status = ?1",
            nativeQuery = true)
    List<Order> findByStatus(String orderStatus);

}
