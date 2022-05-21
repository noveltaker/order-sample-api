package com.example.demo.repository;

import com.example.demo.domain.Order;
import com.example.demo.mock.OrderMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
}
