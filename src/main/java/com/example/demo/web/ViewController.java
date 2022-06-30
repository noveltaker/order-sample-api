package com.example.demo.web;

import com.example.demo.service.dto.LoginDTO;
import com.example.demo.web.docs.ViewControllerDocs;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ViewController implements ViewControllerDocs {

  @PostMapping("/login")
  public void login(@RequestBody LoginDTO dto) {}
}
