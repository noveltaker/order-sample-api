package com.example.demo.service;

import com.example.demo.domain.Order;
import com.example.demo.enums.SearchType;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.dto.OrderDTO;
import com.example.demo.service.dto.OrderInfo;
import com.example.demo.service.dto.OrdersDTO;
import com.example.demo.service.list.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;

  @Override
  @Transactional
  public Order createdOrder(OrderDTO dto) {
    return orderRepository.save(dto.toEntity());
  }

  @Override
  @Transactional(readOnly = true)
  public Page<OrderInfo> getOrderList(OrdersDTO dto, SearchType type) {
    return new Orders(orderRepository, dto, type).getList();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<OrderInfo> getOrderOne(Long id) {
    return orderRepository.findById(id, OrderInfo.class);
  }
}
