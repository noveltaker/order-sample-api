package com.example.demo.repository;

import com.example.demo.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  Page<Order> findAllByUserId(Pageable pageable, Long userId);

  Page<Order> findAllByProductId(Pageable pageable, Long product);
}
