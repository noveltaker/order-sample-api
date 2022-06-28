package com.example.demo.domain;

import com.example.demo.contracts.RoleName;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AbstractDateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String email;

  @Column(name = "password_hash", nullable = false)
  private String password;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private RoleName roleName;

  @OneToMany(mappedBy = "user")
  private Set<Order> orders = new HashSet<>();

  @Builder
  private User(String email, String password) {
    this.email = email;
    this.password = password;
  }

  @Builder(builderClassName = "LoginBuilder", builderMethodName = "loginBuilder")
  private User(String email, RoleName roleName) {
    this.email = email;
    this.roleName = roleName;
  }

  @PrePersist
  void prePersist() {
    if (null == this.roleName) {
      this.roleName = RoleName.ROLE_USER;
    }
  }
}
