package com.example.demo.service.dto;

import com.example.demo.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
  @NotNull private Long userId;

  @NotNull private Long productId;

  @NotNull private Integer count;

  public Order toEntity() {
    return Order.builder().userId(userId).productId(productId).count(count).build();
  }
}
