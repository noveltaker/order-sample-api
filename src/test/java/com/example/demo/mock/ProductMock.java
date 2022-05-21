package com.example.demo.mock;

import com.example.demo.domain.Product;
import com.example.demo.enums.ProductState;

public class ProductMock {

  private static Long id = 1L;

  private static String name = "Test";

  private static Integer amount = 10000;

  private static Integer count = 1;

  private static ProductState state = ProductState.SALE;

  public static Product createdMock() {
    return Product.builder().id(id).name(name).amount(amount).count(count).state(state).build();
  }
}
