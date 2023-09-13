package com.babyblackdog.ddogdog.global.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.util.Date;

public final class JwtAuthenticationProvider {

  private final String header;
  private final String issuer;
  private final int expirySeconds;
  private final Algorithm algorithm;
  private final JWTVerifier jwtVerifier;

  public JwtAuthenticationProvider(
      String header,
      String issuer,
      String clientSecret,
      int expirySeconds
  ) {
    this.header = header;
    this.issuer = issuer;
    this.expirySeconds = expirySeconds;
    this.algorithm = Algorithm.HMAC512(clientSecret);
    this.jwtVerifier = com.auth0.jwt.JWT.require(algorithm)
        .withIssuer(issuer)
        .build();
  }

  public String sign(Claims claims) {
    Date now = new Date();
    JWTCreator.Builder builder = JWT.create();
    builder.withIssuer(issuer);
    builder.withIssuedAt(now);
    if (expirySeconds > 0) {
      builder.withExpiresAt(new Date(now.getTime() + expirySeconds * 1_000L));
    }
    builder.withClaim("username", claims.getUsername());
    builder.withClaim("email", claims.getEmail());
    builder.withClaim("role", claims.getRole());
    return builder.sign(algorithm);
  }

  public Claims verify(String token) throws JWTVerificationException {
    return new Claims(jwtVerifier.verify(token));
  }

  public String getHeader() {
    return header;
  }

}