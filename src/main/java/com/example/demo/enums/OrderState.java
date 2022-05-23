package com.example.demo.enums;

public enum OrderState implements BaseEnum<String> {
  // 배송준비
  READY,
  // 배송시작
  START,
  // 배송종료
  END,
  // 교환
  CHANGE,
  // 환불
  REFUND;

  @Override
  public String getValue() {
    return this.name();
  }
}
