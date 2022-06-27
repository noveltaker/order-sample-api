package com.example.demo.repository;

import com.example.demo.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

//  <T> Optional<T> findById(Long id, Class<T> type);
//
//  <T> Page<T> findAllProjectedBy(Pageable pageable, Class<T> type);
//
//  <T> Page<T> findAllByUserId(Pageable pageable, Long userId, Class<T> type);
//
//  <T> Page<T> findAllByProductId(Pageable pageable, Long product, Class<T> type);
}
