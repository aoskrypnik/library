package com.ukma.library.repository;

import com.ukma.library.model.Order;
import com.ukma.library.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findById(Long id);

    @Query(value = "SELECT o FROM Order o WHERE (:userId is null or o.user.id = :userId) and (:status is null or o.status = :status)")
    List<Order> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") OrderStatus orderStatus);

}
