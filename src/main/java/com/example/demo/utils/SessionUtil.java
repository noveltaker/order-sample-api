package com.example.demo.utils;

import com.example.demo.config.security.DomainUser;
import com.example.demo.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public final class SessionUtil {

  private SessionUtil() {}

  public static Optional<User> getLoginUser() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null) {
      return Optional.empty();
    }

    DomainUser user = (DomainUser) authentication.getPrincipal();

    User entity = user.getLoginUser();

    return Optional.of(entity);
  }

  public static String getLoginEmail() {
    return getLoginUser().map(User::getEmail).orElseThrow();
  }

  public static Long getLoginId() {
    return getLoginUser().map(User::getId).orElseThrow();
  }
}
