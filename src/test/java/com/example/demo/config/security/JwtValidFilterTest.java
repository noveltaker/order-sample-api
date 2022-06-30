package com.example.demo.config.security;

import com.example.demo.contracts.RoleName;
import com.example.demo.contracts.SecurityConstants;
import com.example.demo.utils.AuthUtil;
import com.example.demo.utils.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;

import javax.servlet.ServletException;
import java.io.IOException;

@SpringBootTest
@ActiveProfiles("test")
class JwtValidFilterTest {

  @Value("${jwt.key}")
  private String key;

  @Value("${user.id}")
  private String id;

  @Value("${user.email}")
  private String email;

  @Autowired private JwtUtil jwtUtil;

  @Autowired private AuthUtil authUtil;

  private JwtValidFilter filter;

  private String token;

  private MockHttpServletRequest req;
  private MockHttpServletResponse res;
  private MockFilterChain chain;

  @BeforeEach
  void init() {

    this.filter = new JwtValidFilter(jwtUtil, key, authUtil);
    this.token =
        SecurityConstants.TOKEN_PREFIX
            + jwtUtil.createdAccessToken(key, Long.valueOf(id), email, RoleName.ROLE_USER);
    this.req = new MockHttpServletRequest();
    this.res = new MockHttpServletResponse();
    this.chain = new MockFilterChain();
  }

  @Test
  @DisplayName("필터 기본 작동 동작 케이스")
  void validFilter() throws ServletException, IOException {
    req.addHeader(HttpHeaders.AUTHORIZATION, token);
    filter.doFilter(req, res, chain);
  }

  @Test
  @DisplayName("필터 토큰 없을 시 예외 처리")
  void validFilter_NotFoundTokenException() throws ServletException, IOException {
    Assertions.assertThrows(IllegalArgumentException.class, () -> filter.doFilter(req, res, chain));
  }

  @Test
  @DisplayName("login url 체크")
  void validFilter_Login_Path() throws ServletException, IOException {
    req.setRequestURI("/login");
    filter.doFilter(req, res, chain);
  }

  @Test
  @DisplayName("회원가입 url 체크")
  void validFilter_SignUp_Path() throws ServletException, IOException {
    req.setRequestURI("/sign-up");
    filter.doFilter(req, res, chain);
  }
}
