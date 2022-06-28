package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.service.dto.UserDTO;

public interface UserService {

  User signUp(UserDTO dto);
}
