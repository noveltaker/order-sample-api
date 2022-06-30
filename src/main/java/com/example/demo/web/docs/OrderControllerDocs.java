package com.example.demo.web.docs;

import com.example.demo.domain.Order;
import com.example.demo.service.dto.OrderInfo;
import com.example.demo.service.dto.PageDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface OrderControllerDocs {

  @Operation(summary = "주문 조회 API")
  ResponseEntity<Page<OrderInfo>> getOrdersByUser(PageDTO dto, Long userId);

  @Operation(summary = "주문 생성 API")
  Order saveOrder(Long productId);
}
