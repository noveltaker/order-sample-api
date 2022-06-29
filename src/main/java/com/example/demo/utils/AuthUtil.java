package com.example.demo.utils;

import com.example.demo.contracts.RoleName;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthUtil {

  public List<GrantedAuthority> getAuthority(RoleName roleName) {
    return List.of(new SimpleGrantedAuthority(roleName.name()));
  }
}
