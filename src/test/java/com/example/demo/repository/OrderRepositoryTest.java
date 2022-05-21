package com.example.demo.repository;

import com.example.demo.domain.Order;
import com.example.demo.mock.OrderMock;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class OrderRepositoryTest {

  @Autowired private OrderRepository orderRepository;

  @Test
  @DisplayName("주문하기")
  void save() {

    Order mock = OrderMock.createdMock();

    Order entity = orderRepository.save(mock);

    org.assertj.core.api.Assertions.assertThat(entity).isEqualTo(mock);
    Assertions.assertEquals(mock.getId(), entity.getId());
    Assertions.assertEquals(mock.getUserId(), entity.getUserId());
    Assertions.assertEquals(mock.getCount(), entity.getCount());
    Assertions.assertEquals(mock.getState(), entity.getState());
    Assertions.assertEquals(mock.getDate(), entity.getDate());
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
    @DisplayName("유저별 주문 모록 조회")
    void findAllByUserId() {

      final Long userId = 1L;

      PageRequest pageable = PageRequest.of(0, 10);

      Page<Order> pageList = orderRepository.findAllByUserId(pageable, userId);

      List<Order> content = pageList.getContent();

      Assertions.assertEquals(1, content.size());

      Order entity = content.get(0);
      org.assertj.core.api.Assertions.assertThat(entity).isEqualTo(mock);
      Assertions.assertEquals(mock.getId(), entity.getId());
      Assertions.assertEquals(mock.getUserId(), entity.getUserId());
      Assertions.assertEquals(mock.getCount(), entity.getCount());
      Assertions.assertEquals(mock.getState(), entity.getState());
      Assertions.assertEquals(mock.getDate(), entity.getDate());
    }
  }
}
