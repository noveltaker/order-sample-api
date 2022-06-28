package com.example.demo.config.exception;

import com.example.demo.contracts.MsgType;

public class BaseException extends RuntimeException {
  private final MsgType type;

  public BaseException(MsgType type) {
    super(type.getMessage());
    this.type = type;
  }

  public MsgType getType() {
    return type;
  }
}
