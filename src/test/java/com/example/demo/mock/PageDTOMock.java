package com.example.demo.mock;

import com.example.demo.service.dto.PageDTO;

public class PageDTOMock {

  public static PageDTO createdDTO() {
    return new PageDTO(0, 10);
  }
}
