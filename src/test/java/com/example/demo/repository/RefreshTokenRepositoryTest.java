package com.example.demo.repository;

import com.example.demo.domain.RefreshToken;
import com.example.demo.mock.RefreshTokenMock;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class RefreshTokenRepositoryTest {

  @Autowired private RefreshTokenRepository refreshTokenRepository;

  @Test
  @DisplayName("저장 테스트 케이스")
  void save() {

    RefreshToken mock = RefreshTokenMock.createdMock();

    RefreshToken entity = refreshTokenRepository.save(mock);

    Assertions.assertEquals(mock.getUserId(), entity.getUserId());
    Assertions.assertEquals(mock.getToken(), entity.getToken());
  }

  @Nested
  @DisplayName("조회 & 삭제")
  class SelectAndDelete {

    private RefreshToken mock;

    @BeforeEach
    void init() {
      mock = refreshTokenRepository.save(RefreshTokenMock.createdMock());
      refreshTokenRepository.flush();
    }

    @Test
    @DisplayName("아이디별 조회")
    void findById() {

      RefreshToken entity = refreshTokenRepository.findById(mock.getUserId()).orElseThrow();

      Assertions.assertEquals(mock.getUserId(), entity.getUserId());
      Assertions.assertEquals(mock.getToken(), entity.getToken());
    }

    @Test
    @DisplayName("삭제")
    void delete() {
      refreshTokenRepository.delete(mock);
    }
  }
}
