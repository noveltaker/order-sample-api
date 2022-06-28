package com.example.demo.repository;

import com.example.demo.domain.User;
import com.example.demo.mock.UserMock;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class UserRepositoryTest {

  @Autowired private UserRepository userRepository;

  @Test
  @DisplayName("회원가입 로직 테스트 케이스")
  void save() {

    User mock = UserMock.createdMock();

    User entity = userRepository.save(mock);

    Assertions.assertEquals(entity.getId(), mock.getId());
    Assertions.assertEquals(entity.getEmail(), mock.getEmail());
    Assertions.assertEquals(entity.getPassword(), mock.getPassword());
    Assertions.assertEquals(entity.getRoleName(), mock.getRoleName());
    Assertions.assertEquals(entity.getCreatedDate(), mock.getCreatedDate());
    Assertions.assertEquals(entity.getUpdatedDate(), mock.getUpdatedDate());
  }

  @Nested
  @DisplayName("조회")
  class Select {

    private User mock;

    @BeforeEach
    void init() {
      mock = userRepository.save(UserMock.createdMock());
      userRepository.flush();
    }

    @Test
    @DisplayName("이메일이 존재 할때")
    void existsByEmail_True() {
      String email = mock.getEmail();
      boolean isExists = userRepository.existsByEmail(email);
      Assertions.assertTrue(isExists);
    }

    @Test
    @DisplayName("이메일이 존재 하지 않을 때")
    void existsByEmail_False() {
      String email = "test";
      boolean isExists = userRepository.existsByEmail(email);
      Assertions.assertFalse(isExists);
    }
  }
}
