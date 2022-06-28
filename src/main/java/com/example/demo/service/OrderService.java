package com.example.demo.service;

import com.example.demo.domain.Order;
import com.example.demo.service.dto.OrderInfo;
import com.example.demo.service.dto.PageDTO;
import org.springframework.data.domain.Page;

public interface OrderService {

  Page<OrderInfo> getOrders(Long userId, PageDTO dto);

  Order createdOrder(Long userId, Long productId);
}
