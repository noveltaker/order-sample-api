package com.example.demo.service.dto;

import com.example.demo.enums.OrderState;

import java.util.Date;

public interface OrderInfo {

  Long getId();

  Long getUserId();

  Long getProductId();

  Integer getCount();

  OrderState getState();

  Date getDate();
}
