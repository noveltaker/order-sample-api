package com.example.demo.repository;

import com.example.demo.domain.Product;
import com.example.demo.mock.PageDTOMock;
import com.example.demo.mock.ProductMock;
import com.example.demo.service.dto.PageDTO;
import com.example.demo.service.dto.ProductInfo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class ProductRepositoryTest {

  @Autowired private ProductRepository productRepository;

  @Test
  @DisplayName("저장 테스트 케이스")
  void save() {

    Product mock = ProductMock.createdMock();

    Product entity = productRepository.save(mock);

    Assertions.assertEquals(entity.getId(), mock.getId());
    Assertions.assertEquals(entity.getName(), mock.getName());
    Assertions.assertEquals(entity.getAmount(), mock.getAmount());
  }

  @Nested
  class Select {

    private Product mock;

    @BeforeEach
    void init() {
      mock = productRepository.save(ProductMock.createdMock());
      productRepository.flush();
    }

    @Test
    @DisplayName("주문 전체 조회 테스트 케이스")
    void findAllProjectedBy() {

      PageDTO pageable = PageDTOMock.createdDTO();

      Page<ProductInfo> pageEntities =
          productRepository.findAllProjectedBy(pageable.getPageRequest(), ProductInfo.class);

      List<ProductInfo> content = pageEntities.getContent();
      ProductInfo entity = content.get(0);

      Assertions.assertFalse(pageEntities.isEmpty());
      Assertions.assertEquals(content.size(), 1);
      Assertions.assertEquals(entity.getId(), mock.getId());
      Assertions.assertEquals(entity.getName(), mock.getName());
      Assertions.assertEquals(entity.getAmount(), mock.getAmount());
    }
  }
}
