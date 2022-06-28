package com.example.demo.web;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import com.example.demo.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("user")
  @ResponseStatus(HttpStatus.CREATED)
  public User createdUser(@RequestBody UserDTO dto) {
    return userService.signUp(dto);
  }
}
