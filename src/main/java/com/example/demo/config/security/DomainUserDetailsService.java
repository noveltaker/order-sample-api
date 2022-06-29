package com.example.demo.config.security;

import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  private final AuthUtil authUtil;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user =
        userRepository
            .findByEmail(username.toLowerCase())
            .orElseThrow(() -> new UsernameNotFoundException("login user " + username));

    List<GrantedAuthority> authorities = authUtil.getAuthority(user.getRoleName());

    return new DomainUser(user, authorities);
  }
}
