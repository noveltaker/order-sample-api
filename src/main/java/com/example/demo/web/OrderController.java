package com.example.demo.web;

import com.example.demo.service.OrderService;
import com.example.demo.service.dto.OrderInfo;
import com.example.demo.service.dto.PageDTO;
import com.example.demo.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @GetMapping("my-orders")
  public ResponseEntity<Page<OrderInfo>> getOrdersByUser(PageDTO dto) {

    Long id = SessionUtil.getLoginId();

    Page<OrderInfo> data = orderService.getOrdersByUserId(id, dto);

    if (data.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    return ResponseEntity.ok().body(data);
  }
}
