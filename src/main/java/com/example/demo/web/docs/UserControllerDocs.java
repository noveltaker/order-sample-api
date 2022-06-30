package com.example.demo.web.docs;

import com.example.demo.domain.User;
import com.example.demo.service.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;

public interface UserControllerDocs {

  @Operation(summary = "유저 회원 가입 API")
  User createdUser(UserDTO dto);

  @Operation(summary = "로그아웃 API")
  void logout();
}
