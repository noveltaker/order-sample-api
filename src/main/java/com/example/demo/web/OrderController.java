package com.example.demo.web;

import com.example.demo.domain.Order;
import com.example.demo.service.OrderService;
import com.example.demo.service.dto.OrderInfo;
import com.example.demo.service.dto.PageDTO;
import com.example.demo.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @GetMapping("user/{id]/orders")
  public ResponseEntity<Page<OrderInfo>> getOrdersByUser(@PathVariable Long userId, PageDTO dto) {

    Page<OrderInfo> data = orderService.getOrdersByUserId(userId, dto);

    if (data.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    return ResponseEntity.ok().body(data);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("product/{productId}/order")
  public Order saveOrder(@PathVariable Long productId) {

    Long userId = SessionUtil.getLoginId();

    return orderService.createdOrder(userId, productId);
  }
}
