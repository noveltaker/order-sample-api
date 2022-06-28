package com.example.demo.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class DomainUser extends User {

  public DomainUser(com.example.demo.domain.User user, List<GrantedAuthority> authorities) {
    super(user.getEmail(), user.getPassword(), authorities);
  }

}
