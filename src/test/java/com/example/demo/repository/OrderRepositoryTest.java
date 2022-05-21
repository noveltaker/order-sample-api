package com.example.demo.repository;

import com.example.demo.domain.Order;
import com.example.demo.domain.Product;
import com.example.demo.mock.OrderMock;
import com.example.demo.mock.ProductMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class OrderRepositoryTest {

  @Autowired private OrderRepository orderRepository;

  @Autowired private ProductRepository productRepository;

  private Product product;

  @BeforeEach
  void init() {
    product = productRepository.save(ProductMock.createdMock());
    productRepository.flush();
  }

  @Test
  @DisplayName("주문하기")
  void save() {

    Order mock = OrderMock.createdMock(product);

    Order entity = orderRepository.save(mock);

    org.assertj.core.api.Assertions.assertThat(entity).isEqualTo(mock);
    Assertions.assertEquals(mock.getId(), entity.getId());
    Assertions.assertEquals(mock.getUserId(), entity.getUserId());
    Assertions.assertEquals(mock.getCount(), entity.getCount());
    Assertions.assertEquals(mock.getState(), entity.getState());
    Assertions.assertEquals(mock.getDate(), entity.getDate());
    Assertions.assertEquals(mock.getProduct(), entity.getProduct());
  }
}
