package com.example.demo.service;

import com.example.demo.domain.Order;
import com.example.demo.domain.Product;
import com.example.demo.domain.User;
import com.example.demo.mock.OrderMock;
import com.example.demo.mock.PageDTOMock;
import com.example.demo.mock.ProductMock;
import com.example.demo.mock.UserMock;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.dto.OrderInfo;
import com.example.demo.service.dto.PageDTO;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

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

  @Test
  @DisplayName("전체 주문 조회")
  void getOrders() {

    Page<OrderInfo> pageMocks =
        OrderMock.createdPageMock(UserMock.createdMock(), ProductMock.createdMock());

    BDDMockito.given(orderRepository.findAllProjectedBy(any(), eq(OrderInfo.class)))
        .willReturn(pageMocks);

    PageDTO dto = PageDTOMock.createdDTO();

    Page<OrderInfo> pageEntities = orderService.getOrders(null, dto);

    List<OrderInfo> mocks = pageMocks.getContent();

    List<OrderInfo> entities = pageEntities.getContent();

    OrderInfo mock = mocks.get(0);

    OrderInfo entity = entities.get(0);

    BDDMockito.then(orderRepository).should().findAllProjectedBy(any(), eq(OrderInfo.class));

    Assertions.assertEquals(pageMocks, pageEntities);
    Assertions.assertEquals(mocks, entities);
    Assertions.assertEquals(mock.getOrderId(), entity.getOrderId());
    Assertions.assertEquals(mock.getOrderDate(), entity.getOrderDate());
    Assertions.assertEquals(mock.getProductName(), entity.getProductName());
    Assertions.assertEquals(mock.getAmount(), entity.getAmount());
  }

  @Test
  @DisplayName("유저 아이디 별 주문 조회")
  void getOrders_userId() {

    Page<OrderInfo> pageMocks =
        OrderMock.createdPageMock(UserMock.createdMock(), ProductMock.createdMock());

    Optional<User> userOptional = Optional.of(UserMock.createdMock());

    BDDMockito.given(userRepository.findById(any())).willReturn(userOptional);

    BDDMockito.given(orderRepository.findAllProjectedByUser(any(), any(), eq(OrderInfo.class)))
        .willReturn(pageMocks);

    PageDTO dto = PageDTOMock.createdDTO();

    Page<OrderInfo> pageEntities = orderService.getOrders(1L, dto);

    List<OrderInfo> mocks = pageMocks.getContent();

    List<OrderInfo> entities = pageEntities.getContent();

    OrderInfo mock = mocks.get(0);

    OrderInfo entity = entities.get(0);

    BDDMockito.then(userRepository).should().findById(any());

    BDDMockito.then(orderRepository)
        .should()
        .findAllProjectedByUser(any(), any(), eq(OrderInfo.class));

    Assertions.assertEquals(pageMocks, pageEntities);
    Assertions.assertEquals(mocks, entities);
    Assertions.assertEquals(mock.getOrderId(), entity.getOrderId());
    Assertions.assertEquals(mock.getOrderDate(), entity.getOrderDate());
    Assertions.assertEquals(mock.getProductName(), entity.getProductName());
    Assertions.assertEquals(mock.getAmount(), entity.getAmount());
  }
}
