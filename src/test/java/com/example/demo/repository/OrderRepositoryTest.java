package com.example.demo.repository;

import com.example.demo.domain.Order;
import com.example.demo.domain.Product;
import com.example.demo.domain.User;
import com.example.demo.mock.OrderMock;
import com.example.demo.mock.ProductMock;
import com.example.demo.mock.UserMock;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class OrderRepositoryTest {

  @Autowired private OrderRepository orderRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private ProductRepository productRepository;

  @Nested
  @DisplayName("저장")
  class Save {

    private User userMock;

    private Product productMock;

    @BeforeEach
    void init() {
      userMock = userRepository.save(UserMock.createdMock());
      productMock = productRepository.save(ProductMock.createdMock());
      userRepository.flush();
      productRepository.flush();
    }

    @Test
    @DisplayName("저장 로직 테스트 케이스")
    void save() {

      Order mock = OrderMock.createdMock(userMock, productMock);

      Order entity = orderRepository.save(mock);

      Assertions.assertEquals(mock.getId(), entity.getId());
      Assertions.assertEquals(mock.getUser(), entity.getUser());
      Assertions.assertEquals(mock.getProduct(), entity.getProduct());
    }
  }
}
