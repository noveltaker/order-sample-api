package com.example.demo.contracts;

public enum MsgType implements BaseEnum<String> {

  // data error
  EmptyRequestBody("D001", "request body no data"),
  EmptyParameter("D002", "empty parameter"),

  // system error
  ServerError("S001", "server error");

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
