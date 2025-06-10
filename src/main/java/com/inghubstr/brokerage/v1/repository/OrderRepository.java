package com.inghubstr.brokerage.v1.repository;

import com.inghubstr.brokerage.v1.model.Order;
import com.inghubstr.brokerage.v1.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserIdAndCreatedDateBetween(Long userId, LocalDateTime start, LocalDateTime end);
    Optional<Order> findByIdAndStatus(Long orderId, OrderStatus status);
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByUserId(Long userId);
    Optional<Order> findByIdAndUserId(Long id, Long userId);

}