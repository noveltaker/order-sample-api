package com.example.demo.enums;

public enum ProductState implements BaseEnum<String> {
  // 판매
  SALE,
  // 제고 소진
  SOLD_OUT;

  @Override
  public String getValue() {
    return this.name();
  }
}
