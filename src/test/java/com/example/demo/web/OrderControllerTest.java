package com.example.demo.web;

import com.example.demo.domain.Order;
import com.example.demo.mock.OrderMock;
import com.example.demo.service.OrderService;
import com.example.demo.service.dto.OrderDTO;
import com.example.demo.service.dto.OrderInfo;
import com.example.demo.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
class OrderControllerTest {

  private MockMvc mockMvc;

  @MockBean private OrderService orderService;

  @BeforeEach
  void init() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(new OrderController(orderService))
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .build();
  }

  @Test
  void createdOrder() throws Exception {

    Order mock = OrderMock.createdMock();

    BDDMockito.given(orderService.createdOrder(any())).willReturn(mock);

    OrderDTO dto = OrderMock.createdOrderDTO();

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/order")
                    .content(JsonUtil.convertObjectToJson(dto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
            .andDo(print());

    BDDMockito.then(orderService).should().createdOrder(any());

    action
        .andExpect(status().isOk())
        .andExpect(jsonPath("$['id']").value(mock.getId()))
        .andExpect(jsonPath("$['userId']").value(mock.getUserId()))
        .andExpect(jsonPath("$['productId']").value(mock.getProductId()))
        .andExpect(jsonPath("$['count']").value(mock.getCount()))
        .andExpect(jsonPath("$['state']").value(mock.getState().getValue()));
  }

  @Test
  void createdOrder_4xxClientError() throws Exception {

    Order mock = null;

    BDDMockito.given(orderService.createdOrder(any())).willReturn(mock);

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/order")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
            .andDo(print());

    action.andExpect(status().is4xxClientError());
  }

  @Test
  void getOrderOne() throws Exception {

    Optional<OrderInfo> mockOptional = Optional.of(OrderMock.createOrderInfo());

    BDDMockito.given(orderService.getOrderOne(any())).willReturn(mockOptional);

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/order/" + 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
            .andDo(print());

    BDDMockito.then(orderService).should().getOrderOne(any());

    OrderInfo mock = mockOptional.get();

    action
        .andExpect(status().isOk())
        .andExpect(jsonPath("$['id']").value(mock.getId()))
        .andExpect(jsonPath("$['userId']").value(mock.getUserId()))
        .andExpect(jsonPath("$['productId']").value(mock.getProductId()))
        .andExpect(jsonPath("$['count']").value(mock.getCount()))
        .andExpect(jsonPath("$['state']").value(mock.getState().getValue()));
  }

  @Test
  void getOrderOne_NoContent() throws Exception {

    Optional<OrderInfo> mockOptional = Optional.ofNullable(null);

    BDDMockito.given(orderService.getOrderOne(any())).willReturn(mockOptional);

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/order/" + 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
            .andDo(print());

    BDDMockito.then(orderService).should().getOrderOne(any());

    action.andExpect(status().isNoContent());
  }

  @Test
  void getOrderList() throws Exception {

    Page<OrderInfo> mocks = OrderMock.createdOrderInfos();

    BDDMockito.given(orderService.getOrderList(any(), any())).willReturn(mocks);

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/orders")
                    .param("page", "0")
                    .param("size", "10")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
            .andDo(print());

    BDDMockito.then(orderService).should().getOrderList(any(), any());

    OrderInfo mock = mocks.getContent().get(0);

    action
        .andExpect(status().isOk())
        .andExpect(jsonPath("$['content'][0]['id']").value(mock.getId()))
        .andExpect(jsonPath("$['content'][0]['userId']").value(mock.getUserId()))
        .andExpect(jsonPath("$['content'][0]['productId']").value(mock.getProductId()))
        .andExpect(jsonPath("$['content'][0]['count']").value(mock.getCount()))
        .andExpect(jsonPath("$['content'][0]['state']").value(mock.getState().getValue()));
  }

  @Test
  void getOrderList_4xxClientError() throws Exception {

    Page<OrderInfo> mocks = null;

    BDDMockito.given(orderService.getOrderList(any(), any())).willReturn(mocks);

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
            .andDo(print());

    action.andExpect(status().is4xxClientError());
  }

  @Test
  void getProductOrders() throws Exception {
    Page<OrderInfo> mocks = OrderMock.createdOrderInfos();

    BDDMockito.given(orderService.getOrderList(any(), any())).willReturn(mocks);

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/product/" + 1L + "/orders")
                    .param("page", "0")
                    .param("size", "10")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
            .andDo(print());

    BDDMockito.then(orderService).should().getOrderList(any(), any());

    OrderInfo mock = mocks.getContent().get(0);

    action
        .andExpect(status().isOk())
        .andExpect(jsonPath("$['content'][0]['id']").value(mock.getId()))
        .andExpect(jsonPath("$['content'][0]['userId']").value(mock.getUserId()))
        .andExpect(jsonPath("$['content'][0]['productId']").value(mock.getProductId()))
        .andExpect(jsonPath("$['content'][0]['count']").value(mock.getCount()))
        .andExpect(jsonPath("$['content'][0]['state']").value(mock.getState().getValue()));
  }

  @Test
  void getUserOrders() throws Exception {
    Page<OrderInfo> mocks = OrderMock.createdOrderInfos();

    BDDMockito.given(orderService.getOrderList(any(), any())).willReturn(mocks);

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/user/" + 1L + "/orders")
                    .param("page", "0")
                    .param("size", "10")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
            .andDo(print());

    BDDMockito.then(orderService).should().getOrderList(any(), any());

    OrderInfo mock = mocks.getContent().get(0);

    action
        .andExpect(status().isOk())
        .andExpect(jsonPath("$['content'][0]['id']").value(mock.getId()))
        .andExpect(jsonPath("$['content'][0]['userId']").value(mock.getUserId()))
        .andExpect(jsonPath("$['content'][0]['productId']").value(mock.getProductId()))
        .andExpect(jsonPath("$['content'][0]['count']").value(mock.getCount()))
        .andExpect(jsonPath("$['content'][0]['state']").value(mock.getState().getValue()));
  }
}
