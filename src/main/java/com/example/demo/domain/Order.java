package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@Entity(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends AbstractCreatedDate {

  // 주문번호
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;
}
