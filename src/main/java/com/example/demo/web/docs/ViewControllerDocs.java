package com.example.demo.web.docs;

import com.example.demo.service.dto.LoginDTO;
import io.swagger.v3.oas.annotations.Operation;

public interface ViewControllerDocs {

  @Operation(summary = "로그인 API")
  void login(LoginDTO dto);
}
