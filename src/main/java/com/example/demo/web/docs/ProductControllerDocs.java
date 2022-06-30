package com.example.demo.web.docs;

import com.example.demo.service.dto.PageDTO;
import com.example.demo.service.dto.ProductInfo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface ProductControllerDocs {

  @Operation(summary = "상품 조회 API")
  ResponseEntity<Page<ProductInfo>> getProducts(PageDTO dto);
}
