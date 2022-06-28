package com.example.demo.service;

import com.example.demo.service.dto.PageDTO;
import com.example.demo.service.dto.ProductInfo;
import org.springframework.data.domain.Page;

public interface ProductService {

  Page<ProductInfo> getProducts(PageDTO dto);
}
