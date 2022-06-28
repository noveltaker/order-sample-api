package com.example.demo.service;

import com.example.demo.config.exception.NoDataException;
import com.example.demo.contracts.MsgType;
import com.example.demo.domain.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.dto.OrderInfo;
import com.example.demo.service.dto.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final UserRepository userRepository;

  private final OrderRepository orderRepository;

  @Override
  @Transactional(readOnly = true)
  public Page<OrderInfo> getOrdersByUserId(Long userId, PageDTO dto) {

    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new NoDataException(MsgType.NotFoundUserData));

    return orderRepository.findAllProjectedByUser(dto.getPageRequest(), user, OrderInfo.class);
  }
}
