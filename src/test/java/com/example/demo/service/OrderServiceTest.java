package com.example.demo.service;

import com.example.demo.config.exception.NoDataException;
import com.example.demo.domain.Order;
import com.example.demo.enums.SearchType;
import com.example.demo.mock.OrderMock;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.dto.OrderDTO;
import com.example.demo.service.dto.OrderInfo;
import com.example.demo.service.dto.PageDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class OrderServiceTest {

  private OrderService orderService;

  @Mock private OrderRepository orderRepository;

  @BeforeEach
  void init() {
    MockitoAnnotations.openMocks(this);
    orderService = new OrderServiceImpl(orderRepository);
  }

  @Test
  void createdOrder() {

    Order mock = OrderMock.createdMock();

    BDDMockito.given(orderRepository.save(any())).willReturn(mock);

    OrderDTO dto = OrderMock.createdOrderDTO();

    Order entity = orderService.createdOrder(dto);

    BDDMockito.then(orderRepository).should().save(any());
    org.assertj.core.api.Assertions.assertThat(entity).isEqualTo(mock);
    Assertions.assertEquals(mock.getId(), entity.getId());
    Assertions.assertEquals(mock.getUserId(), entity.getUserId());
    Assertions.assertEquals(mock.getCount(), entity.getCount());
    Assertions.assertEquals(mock.getState(), entity.getState());
    Assertions.assertEquals(mock.getDate(), entity.getDate());
    Assertions.assertEquals(mock.getProductId(), entity.getProductId());
  }

  @Test
  void getOrderList_All() {

    Page<OrderInfo> mocks = OrderMock.createdOrderInfos();

    BDDMockito.given(orderRepository.findAllProjectedBy(any(), eq(OrderInfo.class)))
        .willReturn(mocks);

    PageDTO dto = OrderMock.createdPageDTO();

    Page<OrderInfo> entities = orderService.getOrderList(dto.toOrdersDTO(null), SearchType.NONE);

    BDDMockito.then(orderRepository).should().findAllProjectedBy(any(), eq(OrderInfo.class));

    List<OrderInfo> mockContent = mocks.getContent();

    List<OrderInfo> content = entities.getContent();

    Assertions.assertEquals(mockContent.size(), content.size());

    OrderInfo entity = content.get(0);
    OrderInfo mock = mockContent.get(0);

    Assertions.assertEquals(mock.getId(), entity.getId());
    Assertions.assertEquals(mock.getUserId(), entity.getUserId());
    Assertions.assertEquals(mock.getCount(), entity.getCount());
    Assertions.assertEquals(mock.getState(), entity.getState());
    Assertions.assertEquals(mock.getDate(), entity.getDate());
    Assertions.assertEquals(mock.getProductId(), entity.getProductId());
  }

  @Test
  void getOrderList_Product() {

    Page<OrderInfo> mocks = OrderMock.createdOrderInfos();

    BDDMockito.given(orderRepository.findAllByProductId(any(), any(), eq(OrderInfo.class)))
        .willReturn(mocks);

    PageDTO dto = OrderMock.createdPageDTO();

    Page<OrderInfo> entities = orderService.getOrderList(dto.toOrdersDTO(1L), SearchType.PRODUCT);

    BDDMockito.then(orderRepository).should().findAllByProductId(any(), any(), eq(OrderInfo.class));

    List<OrderInfo> mockContent = mocks.getContent();

    List<OrderInfo> content = entities.getContent();

    Assertions.assertEquals(mockContent.size(), content.size());

    OrderInfo entity = content.get(0);
    OrderInfo mock = mockContent.get(0);

    Assertions.assertEquals(mock.getId(), entity.getId());
    Assertions.assertEquals(mock.getUserId(), entity.getUserId());
    Assertions.assertEquals(mock.getCount(), entity.getCount());
    Assertions.assertEquals(mock.getState(), entity.getState());
    Assertions.assertEquals(mock.getDate(), entity.getDate());
    Assertions.assertEquals(mock.getProductId(), entity.getProductId());
  }

  @Test
  void getOrderList_User() {

    Page<OrderInfo> mocks = OrderMock.createdOrderInfos();

    BDDMockito.given(orderRepository.findAllByUserId(any(), any(), eq(OrderInfo.class)))
        .willReturn(mocks);

    PageDTO dto = OrderMock.createdPageDTO();

    Page<OrderInfo> entities = orderService.getOrderList(dto.toOrdersDTO(1L), SearchType.USER);

    BDDMockito.then(orderRepository).should().findAllByUserId(any(), any(), eq(OrderInfo.class));

    List<OrderInfo> mockContent = mocks.getContent();

    List<OrderInfo> content = entities.getContent();

    Assertions.assertEquals(mockContent.size(), content.size());

    OrderInfo entity = content.get(0);
    OrderInfo mock = mockContent.get(0);

    Assertions.assertEquals(mock.getId(), entity.getId());
    Assertions.assertEquals(mock.getUserId(), entity.getUserId());
    Assertions.assertEquals(mock.getCount(), entity.getCount());
    Assertions.assertEquals(mock.getState(), entity.getState());
    Assertions.assertEquals(mock.getDate(), entity.getDate());
    Assertions.assertEquals(mock.getProductId(), entity.getProductId());
  }

  @Test
  void getOrderOne() {

    Optional<OrderInfo> mockOptional = Optional.of(OrderMock.createOrderInfo());

    BDDMockito.given(orderRepository.findById(any(), eq(OrderInfo.class))).willReturn(mockOptional);

    Optional<OrderInfo> entityOptional = orderService.getOrderOne(1L);

    OrderInfo mock = mockOptional.get();

    OrderInfo entity = entityOptional.get();

    BDDMockito.then(orderRepository).should().findById(any(), eq(OrderInfo.class));

    Assertions.assertEquals(mock.getId(), entity.getId());
    Assertions.assertEquals(mock.getUserId(), entity.getUserId());
    Assertions.assertEquals(mock.getCount(), entity.getCount());
    Assertions.assertEquals(mock.getState(), entity.getState());
    Assertions.assertEquals(mock.getDate(), entity.getDate());
    Assertions.assertEquals(mock.getProductId(), entity.getProductId());
  }
}
