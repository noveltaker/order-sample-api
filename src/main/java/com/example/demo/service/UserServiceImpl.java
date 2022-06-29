package com.example.demo.service;

import com.example.demo.config.exception.ExistsEmailException;
import com.example.demo.config.exception.NoDataException;
import com.example.demo.contracts.MsgType;
import com.example.demo.domain.RefreshToken;
import com.example.demo.domain.User;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private final RefreshTokenRepository refreshTokenRepository;

  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional
  public User signUp(UserDTO dto) {

    if (userRepository.existsByEmail(dto.getEmail())) {
      throw new ExistsEmailException();
    }

    String encodePassword = passwordEncoder.encode(dto.getPassword());

    User entity = dto.toEntity(encodePassword);

    userRepository.save(entity);

    return entity;
  }

  @Override
  @Transactional
  public void logout(Long userId) {

    RefreshToken refreshToken =
        refreshTokenRepository
            .findById(userId)
            .orElseThrow(() -> new NoDataException(MsgType.NotFoundRefreshTokenData));

    refreshTokenRepository.delete(refreshToken);
  }
}
