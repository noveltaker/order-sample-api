package com.example.demo.config.security;

import com.example.demo.domain.User;
import com.example.demo.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@RequiredArgsConstructor
public class JwtValidFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  @Value("${jwt.key}")
  private String jwtKey;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

    Claims claims = jwtUtil.parseJwtToken(authorization, jwtKey);

    String email = (String) claims.get("email");

    User loginUser = User.loginBuilder().email(email).build();

    DomainUser domainUser = new DomainUser(loginUser, new ArrayList<>());

    UsernamePasswordAuthenticationToken authorityUser =
        new UsernamePasswordAuthenticationToken(
            domainUser, domainUser.getPassword(), domainUser.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(authorityUser);

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String uri = request.getRequestURI();
    String method = request.getMethod();
    return !((uri.startsWith("/api/user") && !"POST".equals(method))
        || uri.startsWith("/api/users"));
  }
}
