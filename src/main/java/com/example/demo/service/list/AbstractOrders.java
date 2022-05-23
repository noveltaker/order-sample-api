package com.example.demo.service.list;

import com.example.demo.repository.OrderRepository;
import com.example.demo.service.dto.OrdersDTO;

public abstract class AbstractOrders implements Caller {

  private final OrderRepository orderRepository;

  private final OrdersDTO dto;

  protected AbstractOrders(OrderRepository orderRepository, OrdersDTO dto) {
    this.orderRepository = orderRepository;
    this.dto = dto;
  }

  protected final OrderRepository getOrderRepository() {
    return orderRepository;
  }

  protected final OrdersDTO getDto() {
    return dto;
  }
}
