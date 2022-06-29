package com.example.demo.config.security;

import com.example.demo.config.exception.NoDataException;
import com.example.demo.contracts.MsgType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

  public JwtLoginFilter(
      String defaultFilterProcessesUrl,
      AuthenticationManager authenticationManager,
      AuthenticationSuccessHandler authenticationSuccessHandler,
      AuthenticationFailureHandler authenticationFailureHandler) {
    super(defaultFilterProcessesUrl);
    setAuthenticationManager(authenticationManager);
    setAuthenticationSuccessHandler(authenticationSuccessHandler);
    setAuthenticationFailureHandler(authenticationFailureHandler);
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

    String httpMethod = request.getMethod();

    if (!HttpMethod.POST.name().equals(httpMethod)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "not use to method");
    }

    String email;

    String password;

    try {

      Map<String, Object> requestBody = getRequestBodyToMap(request);

      email = (String) requestBody.get("email");

      password = (String) requestBody.get("password");

    } catch (IOException e) {
      throw new NoDataException(MsgType.EmptyRequestBody);
    }

    UsernamePasswordAuthenticationToken authenticate =
        new UsernamePasswordAuthenticationToken(email, password);

    return this.getAuthenticationManager().authenticate(authenticate);
  }

  private Map<String, Object> getRequestBodyToMap(HttpServletRequest request) throws IOException {

    ObjectMapper objectMapper = new ObjectMapper();

    String requestBody =
        request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

    return objectMapper.readValue(requestBody, Map.class);
  }
}
