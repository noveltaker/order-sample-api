package com.example.demo.web;

import com.example.demo.config.security.DomainUser;
import com.example.demo.domain.User;
import com.example.demo.mock.JsonMock;
import com.example.demo.mock.UserMock;
import com.example.demo.service.UserService;
import com.example.demo.service.dto.UserDTO;
import com.example.demo.utils.AuthUtil;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.MessageUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureRestDocs
@ExtendWith({SpringExtension.class})
@WebMvcTest(value = UserController.class)
class UserControllerTest {

  private MockMvc mockMvc;

  @MockBean private AuthenticationSuccessHandler authenticationSuccessHandler;

  @MockBean private AuthenticationFailureHandler authenticationFailureHandler;

  @MockBean private AuthenticationManager authenticationManager;

  @MockBean private MessageUtil messageUtil;

  @MockBean private JwtUtil jwtUtil;
  @MockBean private AuthUtil authUtil;

  @MockBean private UserService userService;

  @BeforeEach
  void init() {
    mockMvc =
        MockMvcBuilders.standaloneSetup(new UserController(userService))
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .build();

    User user = UserMock.createdMock();

    DomainUser loginUser = new DomainUser(user, authUtil.getAuthority(user.getRoleName()));

    SecurityContextHolder.getContext()
        .setAuthentication(
            new UsernamePasswordAuthenticationToken(
                loginUser, loginUser.getPassword(), loginUser.getAuthorities()));
  }

  @Test
  @DisplayName("회원가입 API")
  void signUp() throws Exception {

    User mock = UserMock.createdMock();

    BDDMockito.given(userService.signUp(any())).willReturn(mock);

    UserDTO dto = UserMock.createdMockDTO();

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/sign-up")
                    .content(JsonMock.convertObjectToJson(dto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
            .andDo(print());

    action
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$['id']").value(mock.getId()))
        .andExpect(jsonPath("$['email']").value(mock.getEmail()))
        .andExpect(jsonPath("$['password']").value(mock.getPassword()));
  }

  @Test
  @DisplayName("로그아웃")
  void logout() throws Exception {

    BDDMockito.willDoNothing().given(userService).logout(any());

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/logout")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
            .andDo(print());

    BDDMockito.then(userService).should().logout(any());

    action.andExpect(status().isOk());
  }
}
