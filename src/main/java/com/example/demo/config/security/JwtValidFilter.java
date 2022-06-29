package com.example.demo.config.security;

import com.example.demo.contracts.RoleName;
import com.example.demo.domain.User;
import com.example.demo.utils.AuthUtil;
import com.example.demo.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@RequiredArgsConstructor
public class JwtValidFilter extends OncePerRequestFilter {

  private final Set<String> skipUrls = Set.of("/login", "/sign-up", "/logout");

  private final JwtUtil jwtUtil;

  private final AntPathMatcher pathMatcher = new AntPathMatcher();

  private final String jwtKey;

  private final AuthUtil authUtil;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

    Claims claims = jwtUtil.parseJwtToken(authorization, jwtKey);

    Long id = Long.valueOf((Integer) claims.get("id"));

    String email = (String) claims.get("email");

    RoleName roleName = RoleName.valueOf((String) claims.get("roleName"));

    User loginUser = User.loginBuilder().id(id).email(email).roleName(roleName).build();

    DomainUser domainUser = new DomainUser(loginUser, authUtil.getAuthority(roleName));

    UsernamePasswordAuthenticationToken authorityUser =
        new UsernamePasswordAuthenticationToken(
            domainUser, domainUser.getPassword(), domainUser.getAuthorities());

    SecurityContextHolder.getContext().setAuthentication(authorityUser);

    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return skipUrls.stream()
        .anyMatch(pattern -> pathMatcher.match(pattern, request.getRequestURI()));
  }
}
