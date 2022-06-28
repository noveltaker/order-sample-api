package com.example.demo.config.security;

import com.example.demo.contracts.MsgType;
import com.example.demo.service.dto.MsgDTO;
import com.example.demo.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component("authenticationFailureHandler")
public class DomainFailureHandler implements AuthenticationFailureHandler {

  private final MessageUtil messageUtil;

  @Override
  public void onAuthenticationFailure(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {

    MsgDTO message =
        MsgDTO.builder()
            .code(MsgType.AuthenticationError.getValue())
            .message(MsgType.AuthenticationError.getMessage())
            .build();

    messageUtil.createMessage(HttpStatus.UNAUTHORIZED, response, message);
  }
}
