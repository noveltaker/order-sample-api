package com.example.demo.service.dto;

import com.example.demo.domain.Product;

public class ProductInfoTemp implements ProductInfo {

  private final Product product;

  public ProductInfoTemp(Product product) {
    this.product = product;
  }

  @Override
  public Long getId() {
    return product.getId();
  }

  @Override
  public String getName() {
    return product.getName();
  }

  @Override
  public String getAmount() {
    return product.getAmount();
  }
}
