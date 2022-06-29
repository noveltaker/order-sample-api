package com.example.demo.service;

import com.example.demo.config.exception.ExistsEmailException;
import com.example.demo.domain.RefreshToken;
import com.example.demo.domain.User;
import com.example.demo.mock.RefreshTokenMock;
import com.example.demo.mock.UserMock;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.dto.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {

  private UserService userService;

  @Mock private UserRepository userRepository;

  @Mock private RefreshTokenRepository refreshTokenRepository;

  @Mock private PasswordEncoder passwordEncoder;

  @BeforeEach
  void init() {
    userService = new UserServiceImpl(userRepository, refreshTokenRepository, passwordEncoder);
  }

  @Test
  @DisplayName("회원가입-중복 회원 테스트 케이스")
  void signUp_fail() {

    Boolean isExists = Boolean.TRUE;

    BDDMockito.given(userRepository.existsByEmail(any())).willReturn(isExists);

    UserDTO dto = UserMock.createdMockDTO();

    Assertions.assertThrows(
        ExistsEmailException.class,
        () -> {
          userService.signUp(dto);
        });

    BDDMockito.then(userRepository).should().existsByEmail(any());
  }

  @Test
  @DisplayName("회원가입")
  void signUp_success() {

    Boolean isExists = Boolean.FALSE;

    String encodePassword = "testestestset";

    User mock = UserMock.createdMock();

    BDDMockito.given(userRepository.existsByEmail(any())).willReturn(isExists);

    BDDMockito.given(passwordEncoder.encode(any())).willReturn(encodePassword);

    BDDMockito.given(userRepository.save(any())).willReturn(mock);

    UserDTO dto = UserMock.createdMockDTO();

    User entity = userService.signUp(dto);

    BDDMockito.then(userRepository).should().existsByEmail(any());

    BDDMockito.then(passwordEncoder).should().encode(any());

    BDDMockito.then(userRepository).should().save(any());

    Assertions.assertEquals(entity.getEmail(), mock.getEmail());
    Assertions.assertEquals(entity.getPassword(), encodePassword);
    Assertions.assertEquals(entity.getRoleName(), mock.getRoleName());
    Assertions.assertEquals(entity.getCreatedDate(), mock.getCreatedDate());
    Assertions.assertEquals(entity.getUpdatedDate(), mock.getUpdatedDate());
  }

  @Test
  @DisplayName("로그아웃 로직")
  void logout() {

    Long userId = 1L;

    Optional<RefreshToken> refreshTokenOptional = Optional.of(RefreshTokenMock.createdMock());

    BDDMockito.given(refreshTokenRepository.findById(any())).willReturn(refreshTokenOptional);

    BDDMockito.willDoNothing().given(refreshTokenRepository).delete(any());

    userService.logout(userId);

    BDDMockito.then(refreshTokenRepository).should().findById(any());

    BDDMockito.then(refreshTokenRepository).should().delete(any());
  }
}
