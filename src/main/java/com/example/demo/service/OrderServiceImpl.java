package com.example.demo.service;

import com.example.demo.config.exception.NoDataException;
import com.example.demo.contracts.MsgType;
import com.example.demo.domain.Order;
import com.example.demo.domain.Product;
import com.example.demo.domain.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.dto.OrderInfo;
import com.example.demo.service.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final UserRepository userRepository;

  private final OrderRepository orderRepository;

  private final ProductRepository productRepository;

  @Override
  @Transactional(readOnly = true)
  public Page<OrderInfo> getOrders(Long userId, PageDTO dto) {

    PageRequest pageable = dto.getPageRequest();

    if (userId == null) {
      return productRepository.findAllProjectedBy(pageable, OrderInfo.class);
    }

    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NoDataException(MsgType.NotFoundUserData));

    return orderRepository.findAllProjectedByUser(pageable, user, OrderInfo.class);
  }

  @Override
  @Transactional
  public Order createdOrder(Long userId, Long productId) {

    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NoDataException(MsgType.NotFoundUserData));

    Product product =
        productRepository
            .findById(productId)
            .orElseThrow(() -> new NoDataException(MsgType.NotFoundProductData));

    Order order = orderRepository.save(Order.builder().user(user).product(product).build());

    return order;
  }
}
