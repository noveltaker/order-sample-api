package com.example.demo.service;

import com.example.demo.repository.ProductRepository;
import com.example.demo.service.dto.PageDTO;
import com.example.demo.service.dto.ProductInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Override
  @Transactional(readOnly = true)
  public Page<ProductInfo> getProducts(PageDTO dto) {
    return productRepository.findAllProjectedBy(dto.getPageRequest(), ProductInfo.class);
  }
}
