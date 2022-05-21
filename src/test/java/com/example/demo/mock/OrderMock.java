package com.example.demo.mock;

import com.example.demo.domain.Order;
import com.example.demo.enums.OrderState;

public class OrderMock {

  private static final Long id = 1L;

  private static final Long userId = 1L;

  private static final Long productId = 1L;

  private static final Integer count = 10;

  private static final OrderState state = OrderState.READY;

  public static Order createdMock() {
    return Order.builder()
        .id(id)
        .userId(userId)
        .count(count)
        .state(state)
        .productId(productId)
        .build();
  }
}
