package com.example.demo.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends AbstractCreatedDate {

  @Id
  @Column(unique = true)
  private Long userId;

  @Column(nullable = false)
  private String token;
}
