package com.example.demo.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public final class DomainUser extends User {

  private com.example.demo.domain.User loginUser;

  public DomainUser(com.example.demo.domain.User user, List<GrantedAuthority> authorities) {
    super(user.getEmail(), user.getPassword(), authorities);
    this.loginUser = user;
  }

  public com.example.demo.domain.User getLoginUser() {
    return loginUser;
  }

}
