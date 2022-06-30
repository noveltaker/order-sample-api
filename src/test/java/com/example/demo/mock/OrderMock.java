package com.example.demo.mock;

import com.example.demo.domain.Order;
import com.example.demo.domain.Product;
import com.example.demo.domain.User;
import com.example.demo.service.dto.OrderInfo;
import com.example.demo.service.dto.OrderInfoTemp;
import com.example.demo.service.dto.PageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public class OrderMock {

  public static Order createdMock(User user, Product product) {
    return Order.builder().id(1L).user(user).product(product).build();
  }

  public static Page<OrderInfo> createdPageMock(User user, Product product) {

    List<OrderInfo> list = List.of(new OrderInfoTemp(createdMock(user, product)));

    PageDTO dto = PageDTOMock.createdDTO();

    return new PageImpl<>(list, dto.getPageRequest(), list.size());
  }
}
