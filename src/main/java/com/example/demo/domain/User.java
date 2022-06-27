package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String email;

  @Column(name = "password_hash", nullable = false)
  private String password;

  @OneToMany(mappedBy = "user")
  private Set<Order> orders = new HashSet<>();

  @Builder
  private User(Long id, String email, String password) {
    this.id = id;
    this.email = email;
    this.password = password;
  }
}
