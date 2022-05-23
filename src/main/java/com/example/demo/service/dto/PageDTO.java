package com.example.demo.service.dto;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
public class PageDTO {

  @NotNull
  private Integer page;

  @NotNull
  private Integer size;

  public PageRequest getPageRequest() {
    return PageRequest.of(page, size);
  }

  public OrdersDTO toOrdersDTO(Long id) {
    return OrdersDTO.builder().page(this.page).size(this.size).searchId(id).build();
  }
}
