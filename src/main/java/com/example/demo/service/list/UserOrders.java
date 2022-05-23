package com.example.demo.service.list;

import com.example.demo.repository.OrderRepository;
import com.example.demo.service.dto.OrderInfo;
import com.example.demo.service.dto.OrdersDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public final class UserOrders extends AbstractOrders {

  UserOrders(OrderRepository orderRepository, OrdersDTO dto) {
    super(orderRepository, dto);
  }

  @Override
  public Page<OrderInfo> getList() {

    OrdersDTO dto = getDto();

    PageRequest pageRequest = dto.getPageRequest();

    return getOrderRepository().findAllByUserId(pageRequest, dto.getSearchId(), OrderInfo.class);
  }
}
