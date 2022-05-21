package com.example.demo.domain;

import com.example.demo.enums.ProductState;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

  // pk
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 제품명
  private String name;

  // 가격
  private Integer amount;

  // 수랑
  private Integer count;

  private ProductState state;

  @PrePersist
  void prePersist() {
    this.state = ProductState.SALE;
  }
}
