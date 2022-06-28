package com.example.demo.config.security;

import com.example.demo.service.dto.LoginDTO;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component("authenticationSuccessHandler")
public class DomainSuccessHandler implements AuthenticationSuccessHandler {
  @Value("${jwt.key}")
  private String jwtKey;

  private final JwtUtil jwtUtil;

  private final MessageUtil messageUtil;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {

    DomainUser user = (DomainUser) authentication.getPrincipal();

    String email = user.getUsername();

    String accessToken = jwtUtil.createdAccessToken(jwtKey, email, null);

    LoginDTO loginMessage = LoginDTO.builder().accessToken(accessToken).build();

    messageUtil.createMessage(HttpStatus.OK, response, loginMessage);
  }
}
