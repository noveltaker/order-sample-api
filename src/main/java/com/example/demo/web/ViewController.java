package com.example.demo.web;

import com.example.demo.service.dto.LoginDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class ViewController {

  @PostMapping("/login")
  public void login(@RequestBody LoginDTO dto) {}
}
