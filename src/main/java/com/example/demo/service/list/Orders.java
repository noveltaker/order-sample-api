package com.example.demo.service.list;

import com.example.demo.enums.SearchType;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.dto.OrderInfo;
import com.example.demo.service.dto.OrdersDTO;
import org.springframework.data.domain.Page;

public class Orders extends AbstractOrders {

  private final AbstractOrders orderList;

  public Orders(OrderRepository orderRepository, OrdersDTO dto, SearchType type) {
    super(orderRepository, dto);
    this.orderList = createdOrderList(type);
  }

  private AbstractOrders createdOrderList(SearchType type) {
    switch (type) {
      case USER:
        return new UserOrders(getOrderRepository(), getDto());
      case PRODUCT:
        return new ProductOrders(getOrderRepository(), getDto());
      default:
        return new AllOrders(getOrderRepository(), getDto());
    }
  }

  @Override
  public Page<OrderInfo> getList() {
    return this.orderList.getList();
  }
}
