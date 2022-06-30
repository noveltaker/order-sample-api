package com.example.demo.web;

import com.example.demo.config.security.DomainUser;
import com.example.demo.domain.User;
import com.example.demo.mock.PageDTOMock;
import com.example.demo.mock.ProductMock;
import com.example.demo.mock.UserMock;
import com.example.demo.service.ProductService;
import com.example.demo.service.dto.PageDTO;
import com.example.demo.service.dto.ProductInfo;
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
import org.springframework.data.domain.Page;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureRestDocs
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@WebMvcTest(value = ProductController.class)
class ProductControllerTest {

  private MockMvc mockMvc;

  @MockBean private AuthenticationSuccessHandler authenticationSuccessHandler;

  @MockBean private AuthenticationFailureHandler authenticationFailureHandler;

  @MockBean private AuthenticationManager authenticationManager;

  @MockBean private MessageUtil messageUtil;

  @MockBean private JwtUtil jwtUtil;
  @MockBean private AuthUtil authUtil;
  @MockBean private ProductService productService;

  @BeforeEach
  void init(RestDocumentationContextProvider restDocumentation) {
    mockMvc =
        MockMvcBuilders.standaloneSetup(new ProductController(productService))
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
  @DisplayName("상품 조회 API")
  void getProducts() throws Exception {

    Page<ProductInfo> pageMocks = ProductMock.createdPageMocks();

    BDDMockito.given(productService.getProducts(any())).willReturn(pageMocks);

    PageDTO pageDTO = PageDTOMock.createdDTO();

    ResultActions action =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/products")
                    .param("size", pageDTO.getSize().toString())
                    .param("page", pageDTO.getPage().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8"))
            .andDo(print());

    BDDMockito.then(productService).should().getProducts(any());

    action
        .andExpect(status().isOk())
        .andExpect(jsonPath("$['content'][0]['id']").value(pageMocks.getContent().get(0).getId()))
        .andExpect(
            jsonPath("$['content'][0]['name']").value(pageMocks.getContent().get(0).getName()))
        .andExpect(
            jsonPath("$['content'][0]['amount']").value(pageMocks.getContent().get(0).getAmount()));
  }
}
