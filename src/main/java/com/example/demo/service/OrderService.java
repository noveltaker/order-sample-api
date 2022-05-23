package com.example.demo.service;

import com.example.demo.domain.Order;
import com.example.demo.enums.SearchType;
import com.example.demo.service.dto.OrderDTO;
import com.example.demo.service.dto.OrderInfo;
import com.example.demo.service.dto.OrdersDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface OrderService {

  Order createdOrder(OrderDTO dto);

  Page<OrderInfo> getOrderList(OrdersDTO dto, SearchType type);

  Optional<OrderInfo> getOrderOne(Long id);
}
