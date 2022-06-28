package com.example.demo.repository;

import com.example.demo.domain.Order;
import com.example.demo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  <T> Page<T> findAllProjectedByUser(Pageable pageable, User user, Class<T> type);
}
