package com.example.demo.web;

import com.example.demo.domain.Order;
import com.example.demo.enums.SearchType;
import com.example.demo.service.OrderService;
import com.example.demo.service.dto.OrderDTO;
import com.example.demo.service.dto.OrderInfo;
import com.example.demo.service.dto.PageDTO;
import com.example.demo.web.docs.OrderControllerDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrderControllerDocs {

  private final OrderService orderService;

  @PostMapping("/order")
  public Order createdOrder(@RequestBody @Valid OrderDTO dto) {
    return orderService.createdOrder(dto);
  }

  @GetMapping("/order/{id}")
  public ResponseEntity<OrderInfo> getOrderOne(@PathVariable Long id) {
    Optional<OrderInfo> data = orderService.getOrderOne(id);
    if (data.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    return ResponseEntity.of(data);
  }

  @GetMapping("/orders")
  public Page<OrderInfo> getOrders(@Valid PageDTO dto) {
    return orderService.getOrderList(dto.toOrdersDTO(null), SearchType.NONE);
  }

  @GetMapping("/product/{id}/orders")
  public Page<OrderInfo> getProductOrders(@Valid PageDTO dto, @PathVariable Long id) {
    return orderService.getOrderList(dto.toOrdersDTO(id), SearchType.PRODUCT);
  }

  @GetMapping("/user/{id}/orders")
  public Page<OrderInfo> getUserOrders(@Valid PageDTO dto, @PathVariable Long id) {
    return orderService.getOrderList(dto.toOrdersDTO(id), SearchType.USER);
  }
}
