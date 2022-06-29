package com.example.demo.service.dto;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public interface OrderInfo {

  // 주문 번호
  @Value("#{target.id}")
  Long getOrderId();

  // 주문 날짜
  @Value("#{target.createdDate}")
  Date getOrderDate();

  //  상품명
  @Value("#{target.product.name}")
  String getProductName();

  @Value("#{target.product.amount}")
  String getAmount();
}
