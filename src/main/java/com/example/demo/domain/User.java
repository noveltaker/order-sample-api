package com.example.demo.domain;

import com.example.demo.contracts.RoleName;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

  @Column(nullable = false, unique = true)
  private String email;

  @Column(name = "password_hash", nullable = false)
  private String password;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private RoleName roleName;

  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private Set<Order> orders = new HashSet<>();

  @Builder
  private User(String email, String password) {
    this.email = email;
    this.password = password;
  }

  @Builder(builderClassName = "LoginBuilder", builderMethodName = "loginBuilder")
  private User(Long id, String email, RoleName roleName) {
    this.id = id;
    this.email = email;
    this.password = "";
    this.roleName = roleName;
  }

  @Builder(builderClassName = "defaultBuilder", builderMethodName = "defaultBuilder")
  private User(Long id, String email, String password) {
    this.id = id;
    this.email = email;
    this.password = password;
  }

  @PrePersist
  void prePersist() {
    if (null == this.roleName) {
      this.roleName = RoleName.ROLE_USER;
    }
  }
}
