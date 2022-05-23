package com.example.demo.repository;

import com.example.demo.domain.Order;
import com.example.demo.mock.OrderMock;
import com.example.demo.service.dto.OrderInfo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class OrderRepositoryTest {

  @Autowired private OrderRepository orderRepository;

  @Test
  void save() {

    Order mock = OrderMock.createdMock();

    Order entity = orderRepository.save(mock);

    org.assertj.core.api.Assertions.assertThat(entity).isEqualTo(mock);
    Assertions.assertEquals(mock.getId(), entity.getId());
    Assertions.assertEquals(mock.getUserId(), entity.getUserId());
    Assertions.assertEquals(mock.getCount(), entity.getCount());
    Assertions.assertEquals(mock.getState(), entity.getState());
    Assertions.assertEquals(mock.getDate(), entity.getDate());
    Assertions.assertEquals(mock.getProductId(), entity.getProductId());
  }

  @Nested
  @DisplayName("조회")
  public class Select {

    private Order mock;

    @BeforeEach
    void init() {
      mock = orderRepository.save(OrderMock.createdMock());
    }

    @Test
    void findAllByUserId() {

      final Long userId = 1L;

      PageRequest pageable = PageRequest.of(0, 10);

      Page<OrderInfo> pageList = orderRepository.findAllByUserId(pageable, userId, OrderInfo.class);

      List<OrderInfo> content = pageList.getContent();

      Assertions.assertEquals(1, content.size());

      OrderInfo entity = content.get(0);
      Assertions.assertEquals(mock.getId(), entity.getId());
      Assertions.assertEquals(mock.getUserId(), entity.getUserId());
      Assertions.assertEquals(mock.getCount(), entity.getCount());
      Assertions.assertEquals(mock.getState(), entity.getState());
      Assertions.assertEquals(mock.getDate(), entity.getDate());
      Assertions.assertEquals(mock.getProductId(), entity.getProductId());
    }

    @Test
    void findAllByProductId() {

      final Long productId = 1L;

      PageRequest pageable = PageRequest.of(0, 10);

      Page<OrderInfo> pageList =
          orderRepository.findAllByProductId(pageable, productId, OrderInfo.class);

      List<OrderInfo> content = pageList.getContent();

      Assertions.assertEquals(1, content.size());

      OrderInfo entity = content.get(0);
      Assertions.assertEquals(mock.getId(), entity.getId());
      Assertions.assertEquals(mock.getUserId(), entity.getUserId());
      Assertions.assertEquals(mock.getCount(), entity.getCount());
      Assertions.assertEquals(mock.getState(), entity.getState());
      Assertions.assertEquals(mock.getDate(), entity.getDate());
      Assertions.assertEquals(mock.getProductId(), entity.getProductId());
    }

    @Test
    void findAll() {

      PageRequest pageable = PageRequest.of(0, 10);

      Page<OrderInfo> pageList = orderRepository.findAllProjectedBy(pageable, OrderInfo.class);

      List<OrderInfo> content = pageList.getContent();

      Assertions.assertEquals(1, content.size());

      OrderInfo entity = content.get(0);
      Assertions.assertEquals(mock.getId(), entity.getId());
      Assertions.assertEquals(mock.getUserId(), entity.getUserId());
      Assertions.assertEquals(mock.getCount(), entity.getCount());
      Assertions.assertEquals(mock.getState(), entity.getState());
      Assertions.assertEquals(mock.getDate(), entity.getDate());
      Assertions.assertEquals(mock.getProductId(), entity.getProductId());
    }

    @Test
    void findById() {

      Optional<OrderInfo> entityOptional = orderRepository.findById(1L, OrderInfo.class);

      Assertions.assertTrue(entityOptional.isPresent());

      OrderInfo entity = entityOptional.get();

      Assertions.assertEquals(mock.getId(), entity.getId());
      Assertions.assertEquals(mock.getUserId(), entity.getUserId());
      Assertions.assertEquals(mock.getCount(), entity.getCount());
      Assertions.assertEquals(mock.getState(), entity.getState());
      Assertions.assertEquals(mock.getDate(), entity.getDate());
      Assertions.assertEquals(mock.getProductId(), entity.getProductId());
    }
  }
}
