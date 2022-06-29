package com.example.demo.mock;

import java.io.Serializable;

public class LoginMock implements Serializable {
  private String email;

  private String password;

  public LoginMock(String email, String password) {
    this.email = email;
    this.password = password;
  }
}