package com.example.demo.service.dto;

import com.example.demo.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class UserDTO {

  private final String email;

  private final String password;

  public User toEntity(String encodePassword) {
    return User.builder().email(this.email).password(encodePassword).build();
  }
}
