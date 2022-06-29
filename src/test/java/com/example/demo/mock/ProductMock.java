package com.example.demo.mock;

import com.example.demo.domain.Product;
import com.example.demo.service.dto.ProductInfo;
import com.example.demo.service.dto.ProductInfoTemp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class ProductMock {

  public static Product createdMock() {
    return Product.defaultBuilder().id(1L).name("한라산 한라봉").amount("0월").build();
  }

  public static Page<ProductInfo> createdPageMocks() {

    List<ProductInfo> list = List.of(new ProductInfoTemp(createdMock()));

    PageRequest pageable = PageDTOMock.createdDTO().getPageRequest();

    return new PageImpl<>(list, pageable, list.size());
  }
}
