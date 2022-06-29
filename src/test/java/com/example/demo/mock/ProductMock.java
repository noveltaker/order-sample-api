package com.example.demo.mock;

import com.example.demo.domain.Product;

public class ProductMock {

  public static Product createdMock() {
    return Product.defaultBuilder().id(1L).name("한라산 한라봉").amount("0월").build();
  }
}
