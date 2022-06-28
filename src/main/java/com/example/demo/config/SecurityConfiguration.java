package com.example.demo.config;

import com.example.demo.config.security.JwtExceptionFilter;
import com.example.demo.config.security.JwtLoginFilter;
import com.example.demo.config.security.JwtValidFilter;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final AuthenticationSuccessHandler authenticationSuccessHandler;

  private final AuthenticationFailureHandler authenticationFailureHandler;

  private final MessageUtil messageUtil;

  private final JwtUtil jwtUtil;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .cors()
        .disable()
        .formLogin()
        .disable()
        .addFilterBefore(
            new JwtExceptionFilter(messageUtil), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(jwtLoginFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(new JwtValidFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  private JwtLoginFilter jwtLoginFilter() throws Exception {
    return new JwtLoginFilter(
        "/login",
        this.authenticationManagerBean(),
        authenticationSuccessHandler,
        authenticationFailureHandler);
  }
}
