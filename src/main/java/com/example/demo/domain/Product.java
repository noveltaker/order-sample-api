package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

  // 상품의 PK
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 상품명
  @Column(nullable = false)
  private String name;

  // 상품 금액
  private Integer amount;

  @OneToMany(mappedBy = "product")
  private Set<Order> orders = new HashSet<>();

  @PrePersist
  void prePersist() {
    // null 일시 0으로 초기화
    if (this.amount == null) this.amount = 0;
  }

  @Builder
  private Product(String name, Integer amount) {
    this.name = name;
    this.amount = amount;
  }
}
