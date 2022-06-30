package com.example.demo.web;

import com.example.demo.config.security.DomainUser;
import com.example.demo.domain.Order;
import com.example.demo.domain.User;
import com.example.demo.mock.OrderMock;
import com.example.demo.mock.ProductMock;
import com.example.demo.mock.UserMock;
import com.example.demo.service.OrderService;
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
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
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
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(value = OrderController.class)
class OrderControllerTest {

  private MockMvc mockMvc;

  @MockBean private AuthenticationSuccessHandler authenticationSuccessHandler;

  @MockBean private AuthenticationFailureHandler authenticationFailureHandler;

  @MockBean private AuthenticationManager authenticationManager;

  @MockBean private MessageUtil messageUtil;

  @MockBean private JwtUtil jwtUtil;
  @MockBean private AuthUtil authUtil;
  @MockBean private OrderService orderService;

  @BeforeEach
  void init(RestDocumentationContextProvider restDocumentation) {
    mockMvc =
        MockMvcBuilders.standaloneSetup(new OrderController(orderService))
            .addFilter(new CharacterEncodingFilter("UTF-8", true))
            .apply(documentationConfiguration(restDocumentation))
            .alwaysDo(
                document(
                    "{method-name}",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint())))
            .build();

    User user = UserMock.createdMock();

    DomainUser loginUser = new DomainUser(user, authUtil.getAuthority(user.getRoleName()));

    SecurityContextHolder.getContext()
        .setAuthentication(
            new UsernamePasswordAuthenticationToken(
                loginUser, loginUser.getPassword(), loginUser.getAuthorities()));
  }

  @Test
  @DisplayName("회원 가입 API")
  void createdOrder() throws Exception {

    Order mock = OrderMock.createdMock(UserMock.createdMock(), ProductMock.createdMock());

    BDDMockito.given(orderService.createdOrder(any(), any())).willReturn(mock);

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/product/1/order")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
            .andDo(print());

    BDDMockito.then(orderService).should().createdOrder(any(), any());

    action
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$['id']").value(mock.getId()))
        .andExpect(jsonPath("$['user']['id']").value(mock.getUser().getId()))
        .andExpect(jsonPath("$['user']['email']").value(mock.getUser().getEmail()))
        .andExpect(jsonPath("$['user']['password']").value(mock.getUser().getPassword()))
        .andExpect(jsonPath("$['product']['id']").value(mock.getProduct().getId()))
        .andExpect(jsonPath("$['product']['name']").value(mock.getProduct().getName()))
        .andExpect(jsonPath("$['product']['amount']").value(mock.getProduct().getAmount()));
  }
}
