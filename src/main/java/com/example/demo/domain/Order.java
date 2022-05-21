package com.example.demo.domain;

import com.example.demo.enums.OrderState;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "app_order")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Order {

  // 주문번호
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 유저 아이디
  @Column(nullable = false)
  private Long userId;

  // 주문 수량
  private Integer count;

  // 주문 상태
  private OrderState state;

  // 주문일자
  @CreatedDate private Date date;

  @ManyToOne
  @JoinColumn(name = "product_id", referencedColumnName = "id")
  private Product product;

  @PrePersist
  void prePersist() {
    this.count = 0;
    this.state = OrderState.READY;
  }
}
