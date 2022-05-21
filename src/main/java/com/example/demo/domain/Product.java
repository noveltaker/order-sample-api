package com.example.demo.domain;

import com.example.demo.enums.ProductState;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "app_product")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

  // pk
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 제품명
  @Column(nullable = false)
  private String name;

  // 가격
  @Column(nullable = false)
  private Integer amount;

  // 수랑
  @Column(nullable = false)
  private Integer count;

  // 상품 상태
  private ProductState state;

  @OneToMany(mappedBy = "product")
  private Set<Order> orderSet = new HashSet<>();

  @PrePersist
  void prePersist() {
    this.state = ProductState.SALE;
  }
}
