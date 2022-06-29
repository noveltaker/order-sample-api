package com.example.demo.repository;

import com.example.demo.domain.Order;
import com.example.demo.domain.Product;
import com.example.demo.domain.User;
import com.example.demo.mock.OrderMock;
import com.example.demo.mock.PageDTOMock;
import com.example.demo.mock.ProductMock;
import com.example.demo.mock.UserMock;
import com.example.demo.service.dto.OrderInfo;
import com.example.demo.service.dto.PageDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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

  @Nested
  @DisplayName("조회")
  class Select {

    private User userMock;

    private Product productMock;

    private Order mock;

    @BeforeEach
    void init() {
      userMock = userRepository.save(UserMock.createdMock());
      productMock = productRepository.save(ProductMock.createdMock());
      mock = orderRepository.save(OrderMock.createdMock(userMock, productMock));
      userRepository.flush();
      productRepository.flush();
      orderRepository.flush();
    }

    @Test
    @DisplayName("전체 조회")
    void findAllProjectedBy() {

      PageDTO pageable = PageDTOMock.createdDTO();

      Page<OrderInfo> pageEntities =
          orderRepository.findAllProjectedBy(pageable.getPageRequest(), OrderInfo.class);

      List<OrderInfo> content = pageEntities.getContent();
      OrderInfo entity = content.get(0);

      Assertions.assertFalse(pageEntities.isEmpty());
      Assertions.assertEquals(content.size(), 1);
      Assertions.assertEquals(entity.getOrderId(), mock.getId());
      Assertions.assertEquals(entity.getOrderDate(), mock.getCreatedDate());
      Assertions.assertEquals(entity.getProductName(), mock.getProduct().getName());
      Assertions.assertEquals(entity.getAmount(), mock.getProduct().getAmount());
    }

    @Test
    @DisplayName("유저 아이디 별로 주문 조회 테스트 케이스")
    void findAllProjectedByUser() {

      PageDTO pageable = PageDTOMock.createdDTO();

      Page<OrderInfo> pageEntities =
          orderRepository.findAllProjectedByUser(
              pageable.getPageRequest(), userMock, OrderInfo.class);

      List<OrderInfo> content = pageEntities.getContent();
      OrderInfo entity = content.get(0);

      Assertions.assertFalse(pageEntities.isEmpty());
      Assertions.assertEquals(content.size(), 1);
      Assertions.assertEquals(entity.getOrderId(), mock.getId());
      Assertions.assertEquals(entity.getOrderDate(), mock.getCreatedDate());
      Assertions.assertEquals(entity.getProductName(), mock.getProduct().getName());
      Assertions.assertEquals(entity.getAmount(), mock.getProduct().getAmount());
    }
  }
}
