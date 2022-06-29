package com.example.demo.mock;

import com.example.demo.domain.Order;
import com.example.demo.domain.Product;
import com.example.demo.domain.User;

public class OrderMock {

  public static Order createdMock(User user, Product product) {
    return Order.builder().user(user).product(product).build();
  }
}
