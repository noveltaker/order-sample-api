package com.example.demo.mock;

import com.example.demo.domain.RefreshToken;

public class RefreshTokenMock {

  private static final Long userId = 1L;

  private static final String token = "testest";

  public static RefreshToken createdMock() {
    return RefreshToken.builder().userId(userId).token(token).build();
  }
}
