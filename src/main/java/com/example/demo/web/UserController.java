package com.example.demo.web;

import com.example.demo.domain.User;
import com.example.demo.service.UserService;
import com.example.demo.service.dto.UserDTO;
import com.example.demo.utils.SessionUtil;
import com.example.demo.web.docs.UserControllerDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController implements UserControllerDocs {

  private final UserService userService;

  @PostMapping("sign-up")
  @ResponseStatus(HttpStatus.CREATED)
  public User createdUser(@RequestBody UserDTO dto) {
    return userService.signUp(dto);
  }

  @PostMapping("logout")
  public void logout() {

    Long userId = SessionUtil.getLoginId();

    userService.logout(userId);
  }
}
