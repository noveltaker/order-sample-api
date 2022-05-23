package com.example.demo.mock;

import com.example.demo.domain.Order;
import com.example.demo.enums.OrderState;
import com.example.demo.service.dto.OrderDTO;
import com.example.demo.service.dto.OrderInfo;
import com.example.demo.service.dto.PageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.List;

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

  public static OrderDTO createdOrderDTO() {
    return new OrderDTO(userId, productId, count);
  }

  public static OrderInfo createOrderInfo() {
    return new TempOrderInfo(createdMock());
  }

  public static Page<OrderInfo> createdOrderInfos() {

    List<OrderInfo> list = List.of(createOrderInfo());

    PageRequest pageRequest = PageRequest.of(0, 10);

    int total = list.size();

    return new PageImpl<>(list, pageRequest, total);
  }

  public static PageDTO createdPageDTO() {
    return new PageDTO(0, 10);
  }

  static class TempOrderInfo implements OrderInfo {

    private final Order order;

    TempOrderInfo(Order order) {
      this.order = order;
    }

    @Override
    public Long getId() {
      return this.order.getId();
    }

    @Override
    public Long getUserId() {
      return this.order.getUserId();
    }

    @Override
    public Long getProductId() {
      return this.order.getProductId();
    }

    @Override
    public Integer getCount() {
      return this.order.getCount();
    }

    @Override
    public OrderState getState() {
      return this.order.getState();
    }

    @Override
    public Date getDate() {
      return this.order.getDate();
    }
  }
}
