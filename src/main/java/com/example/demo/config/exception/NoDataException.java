package com.example.demo.config.exception;

import com.example.demo.contracts.MsgType;

public class NoDataException extends BaseException {

  public NoDataException(MsgType type) {
    super(type);
  }
}
