package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

  // 상품 금액 (원화)
  private String amount;

  @JsonIgnore
  @OneToMany(mappedBy = "product")
  private Set<Order> orders = new HashSet<>();

  @PrePersist
  void prePersist() {
    // null 일시 0으로 초기화
    if (null == this.amount) {
      this.amount = "0원";
    }
  }

  @Builder
  private Product(String name, String amount) {
    this.name = name;
    this.amount = amount;
  }

  @Builder(builderClassName = "defaultBuilder", builderMethodName = "defaultBuilder")
  public Product(Long id, String name, String amount) {
    this.id = id;
    this.name = name;
    this.amount = amount;
  }
}
