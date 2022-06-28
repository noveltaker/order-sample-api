package com.example.demo.mock;

import com.example.demo.domain.User;

public class UserMock {

  private static final String email = "test@naver.com";

  private static final String password = "12345";

  public static User createdMock() {
    return User.builder().email(email).password(password).build();
  }
}
