package com.example.demo.mock;

import com.example.demo.domain.User;
import com.example.demo.service.dto.UserDTO;

public class UserMock {

  private static final String email = "test@naver.com";

  private static final String password = "12345";

  public static User createdMock() {
    return User.defaultBuilder().id(1L).email(email).password(password).build();
  }

  public static UserDTO createdMockDTO() {
    return new UserDTO(email, password);
  }
}
