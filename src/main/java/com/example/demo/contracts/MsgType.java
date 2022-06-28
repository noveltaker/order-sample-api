package com.example.demo.contracts;

public enum MsgType implements BaseEnum<String> {

  // data error
  EmptyRequestBody("D001", "request body no data"),
  EmptyParameter("D002", "empty parameter"),
  // domain
  NotFoundUserData("D003", "not found user data"),
  ExistsEmail("D004", "exists email data"),
  NotFoundProductData("D005", "not found product data"),
  NotFoundRefreshTokenData("D006", "not found refresh token data"),

  // system error
  ServerError("S001", "server error"),
  // jwt error
  JwtUnsupported("J001", "jwt not supported"),
  JwtMalformed("J002", "jwt malformed"),
  JwtExpired("J003", "jwt expired"),
  JwtSignature("J003", "jwt signature"),
  // Authentication
  AuthenticationError("A001", "authentication error");

  private final String code;

  private final String message;

  MsgType(String code, String message) {
    this.code = code;
    this.message = message;
  }

  @Override
  public String getValue() {
    return this.code;
  }

  public String getMessage() {
    return this.message;
  }
}
