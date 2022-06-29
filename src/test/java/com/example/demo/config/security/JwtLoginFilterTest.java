package com.example.demo.config.security;

import com.example.demo.config.exception.NoDataException;
import com.example.demo.domain.User;
import com.example.demo.mock.LoginMock;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;

@ActiveProfiles("test")
@SpringBootTest
class JwtLoginFilterTest {

  @Value("${user.email}")
  private String email;

  @Value("${user.password}")
  private String password;

  private JwtLoginFilter filter;

  @Autowired private AuthenticationSuccessHandler authenticationSuccessHandler;

  @Autowired private AuthenticationFailureHandler authenticationFailureHandler;

  @Autowired private AuthenticationManager authenticationManagerBean;

  @Autowired private UserRepository userRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private JsonUtil jsonUtil;

  private MockHttpServletRequest req;

  private MockHttpServletResponse res;

  @BeforeEach
  void init() {
    filter =
        new JwtLoginFilter(
            "/login",
            authenticationManagerBean,
            authenticationSuccessHandler,
            authenticationFailureHandler);

    req = new MockHttpServletRequest();
    res = new MockHttpServletResponse();

    req.setRequestURI("/login");
  }

  @Test
  @DisplayName("post 메소드가 아닐시 테스트 케이스")
  void loginFilter_ResponseStatusException() {
    Assertions.assertThrows(
        ResponseStatusException.class, () -> filter.attemptAuthentication(req, res));
  }

  @Test
  @DisplayName("request body 가 없을 시 테스트 케이스")
  void loginFilter_NotFoundException() {

    req.setMethod(HttpMethod.POST.name());

    Assertions.assertThrows(NoDataException.class, () -> filter.attemptAuthentication(req, res));
  }

  @Test
  @DisplayName("로그인 로직 테스트 케이스")
  void loginFilter() {

    userRepository.save(
        User.builder().email(email).password(passwordEncoder.encode(password)).build());

    userRepository.flush();

    req.setMethod(HttpMethod.POST.name());

    req.setContent(
        jsonUtil
            .convertObjectToJson(new LoginMock(email, password))
            .getBytes(StandardCharsets.UTF_8));

    filter.attemptAuthentication(req, res);
  }
}
