package com.example.demo.service;

import com.example.demo.mock.PageDTOMock;
import com.example.demo.mock.ProductMock;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.dto.PageDTO;
import com.example.demo.service.dto.ProductInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ProductServiceTest {

  private ProductService productService;

  @Mock private ProductRepository productRepository;

  @BeforeEach
  void init() {
    productService = new ProductServiceImpl(productRepository);
  }

  @Test
  @DisplayName("상품 조회")
  void getProducts() {

    Page<ProductInfo> pageMocks = ProductMock.createdPageMocks();

    BDDMockito.given(productRepository.findAllProjectedBy(any(), eq(ProductInfo.class)))
        .willReturn(pageMocks);

    PageDTO dto = PageDTOMock.createdDTO();

    Page<ProductInfo> pageEntities = productService.getProducts(dto);

    BDDMockito.then(productRepository).should().findAllProjectedBy(any(), eq(ProductInfo.class));

    List<ProductInfo> mocks = pageMocks.getContent();
    List<ProductInfo> entities = pageEntities.getContent();

    ProductInfo mock = mocks.get(0);
    ProductInfo entity = entities.get(0);

    Assertions.assertEquals(pageMocks, pageEntities);
    Assertions.assertEquals(mocks, entities);
    Assertions.assertEquals(mock, entity);
    Assertions.assertEquals(mock.getId(), entity.getId());
    Assertions.assertEquals(mock.getName(), entity.getName());
    Assertions.assertEquals(mock.getAmount(), entity.getAmount());
  }
}
