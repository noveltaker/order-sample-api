package com.example.demo.web.docs;

import com.example.demo.domain.Order;
import com.example.demo.service.dto.OrderDTO;
import com.example.demo.service.dto.OrderInfo;
import com.example.demo.service.dto.PageDTO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface OrderControllerDocs {
  @Operation(summary = "주문하기 API")
  Order createdOrder(OrderDTO dto);

  @Operation(summary = "단일 준문내역 API")
  ResponseEntity<OrderInfo> getOrderOne(Long id);

  @Operation(summary = "전체 준문내역 API")
  Page<OrderInfo> getOrders(PageDTO dto);

  @Operation(summary = "상품별 준문내역 API")
  Page<OrderInfo> getProductOrders(PageDTO dto, Long id);

  @Operation(summary = "유저별 준문내역 API")
  Page<OrderInfo> getUserOrders(PageDTO dto, Long id);
}
