package com.example.demo.service;

import com.example.demo.config.exception.ExistsEmailException;
import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  @Transactional
  public User signUp(UserDTO dto) {

    if (userRepository.existsByEmail(dto.getEmail())) {
      throw new ExistsEmailException();
    }

    return userRepository.save(dto.toEntity());
  }
}
