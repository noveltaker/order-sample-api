package com.example.demo.web;

import com.example.demo.service.ProductService;
import com.example.demo.service.dto.PageDTO;
import com.example.demo.service.dto.ProductInfo;
import com.example.demo.web.docs.ProductControllerDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductControllerDocs {

  private final ProductService productService;

  @GetMapping("products")
  public ResponseEntity<Page<ProductInfo>> getProducts(PageDTO dto) {

    Page<ProductInfo> data = productService.getProducts(dto);

    if (data.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    return ResponseEntity.ok(data);
  }
}
