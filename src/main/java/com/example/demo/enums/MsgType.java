package com.example.demo.enums;

public enum MsgType implements BaseEnum<String> {
  EmptyParameter("D002", "empty parameter"),
  EmptyRequestBody("D001", "request body no data"),
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
