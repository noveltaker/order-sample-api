package com.example.demo.converter;

import com.example.demo.enums.OrderState;

public class OrderStateConverter extends BaseEnumConverter<OrderState, String> {
  @Override
  Class<OrderState> getClassEnums() {
    return OrderState.class;
  }
}
