package com.example.demo.service.dto;

import com.example.demo.domain.Order;

import java.util.Date;

public class OrderInfoTemp implements OrderInfo {

  private final Order order;

  public OrderInfoTemp(Order order) {
    this.order = order;
  }

  @Override
  public Long getOrderId() {
    return order.getId();
  }

  @Override
  public Date getOrderDate() {
    return order.getCreatedDate();
  }

  @Override
  public String getProductName() {
    return order.getProduct().getName();
  }

  @Override
  public String getAmount() {
    return order.getProduct().getAmount();
  }
}
