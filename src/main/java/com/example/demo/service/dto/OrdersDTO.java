package com.example.demo.service.dto;

import lombok.Builder;
import lombok.Getter;

public class OrdersDTO extends PageDTO {
  @Getter private Long searchId;

  @Builder
  public OrdersDTO(Integer page, Integer size , Long searchId) {
    super(page, size);
    this.searchId = searchId;
  }

}
