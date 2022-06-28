package com.example.demo.config.exception;

import com.example.demo.contracts.MsgType;

public class ExistsEmailException extends BaseException {

  public ExistsEmailException() {
    super(MsgType.ExistsEmail);
  }
}
