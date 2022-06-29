package com.example.demo.utils;

import com.example.demo.contracts.RoleName;
import com.example.demo.contracts.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
public class JwtUtil {

  public String createdAccessToken(String key, Long id, String email, RoleName roleName) {
    return createToken(
        key,
        id,
        email,
        roleName,
        new Date(new Date().getTime() + Duration.ofMinutes(30).toMillis()));
  }

  public String createdRefreshToken(String key, Long id, String email, RoleName roleName) {
    return createToken(
        key,
        id,
        email,
        roleName,
        new Date(new Date().getTime() + Duration.ofMinutes(60).toMillis()));
  }

  private String createToken(
          String secretKey, Long id, String email, RoleName roleName, Date expiration) {
    Date now = new Date();
    return Jwts.builder()
        .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
        .setIssuer("fresh")
        .setIssuedAt(now)
        .setExpiration(expiration)
        .claim("id", id)
        .claim("email", email)
        .claim("roleName", roleName)
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  public Claims parseJwtToken(String authorizationHeader, String jwtKey) {
    validationAuthorizationHeader(authorizationHeader);
    String passerHeader = extractToken(authorizationHeader);
    return Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(passerHeader).getBody();
  }

  private void validationAuthorizationHeader(String header) {
    if (null == header || "" == header || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
      throw new IllegalArgumentException();
    }
  }

  private String extractToken(String header) {
    return header.substring(SecurityConstants.TOKEN_PREFIX.length());
  }
}
