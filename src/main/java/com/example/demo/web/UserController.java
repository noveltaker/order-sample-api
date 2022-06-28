package com.example.demo.web;

import com.example.demo.domain.User;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;
import com.example.demo.service.dto.OrderInfo;
import com.example.demo.service.dto.PageDTO;
import com.example.demo.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  private final OrderService orderService;

  @PostMapping("user")
  @ResponseStatus(HttpStatus.CREATED)
  public User createdUser(@RequestBody UserDTO dto) {
    return userService.signUp(dto);
  }

  @GetMapping("user/{id}/orders")
  public ResponseEntity<Page<OrderInfo>> getOrdersByUser(@PathVariable Long id, PageDTO dto) {

    Page<OrderInfo> data = orderService.getOrdersByUserId(id, dto);

    if (data.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    return ResponseEntity.ok().body(data);
  }
}
