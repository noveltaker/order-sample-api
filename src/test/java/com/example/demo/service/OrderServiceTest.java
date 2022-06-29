package com.example.demo.service;

import com.example.demo.domain.Order;
import com.example.demo.domain.Product;
import com.example.demo.domain.User;
import com.example.demo.mock.OrderMock;
import com.example.demo.mock.ProductMock;
import com.example.demo.mock.UserMock;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OrderServiceTest {

  private OrderService orderService;

  @Mock private UserRepository userRepository;

  @Mock private OrderRepository orderRepository;

  @Mock private ProductRepository productRepository;

  @BeforeEach
  void init() {
    orderService = new OrderServiceImpl(userRepository, orderRepository, productRepository);
  }

  @Test
  @DisplayName("주문 로직")
  void createdOrder() {

    Optional<User> userOptional = Optional.of(UserMock.createdMock());

    Optional<Product> productOptional = Optional.of(ProductMock.createdMock());

    Long userId = userOptional.map(User::getId).orElseThrow();

    Long productId = productOptional.map(Product::getId).orElseThrow();

    Order mock = OrderMock.createdMock(userOptional.get(), productOptional.get());

    BDDMockito.given(userRepository.findById(any())).willReturn(userOptional);

    BDDMockito.given(productRepository.findById(any())).willReturn(productOptional);

    BDDMockito.given(orderRepository.save(any())).willReturn(mock);

    Order entity = orderService.createdOrder(userId, productId);

    BDDMockito.then(userRepository).should().findById(any());

    BDDMockito.then(productRepository).should().findById(any());

    BDDMockito.then(orderRepository).should().save(any());

    org.assertj.core.api.Assertions.assertThat(entity).isEqualTo(mock);

    Assertions.assertEquals(mock.getId(), entity.getId());
    Assertions.assertEquals(mock.getUser(), entity.getUser());
    Assertions.assertEquals(mock.getProduct(), entity.getProduct());
  }
}
