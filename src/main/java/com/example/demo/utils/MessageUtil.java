package com.example.demo.utils;

import com.example.demo.contracts.MsgType;
import com.example.demo.service.dto.MsgDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MessageUtil {

  public void createErrorMessage(
      HttpStatus status, HttpServletResponse response, Exception ex, MsgType errorType)
      throws IOException {
    MsgDTO message;
    message = setMessage(ex, errorType);
    createMessage(status, response, message);
  }

  public MsgDTO setMessage(Exception ex, MsgType errorType) {
    ex.printStackTrace();

    return MsgDTO.builder().code(errorType.getValue()).message(errorType.getMessage()).build();
  }

  public void createMessage(HttpStatus status, HttpServletResponse response, Object message)
      throws IOException {
    response.reset();

    response.setStatus(status.value());

    response.setContentType("application/json");

    response.getWriter().write(JsonUtil.convertObjectToJson(message));
  }
}
