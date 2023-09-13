package com.babyblackdog.ddogdog.global.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Arrays;
import java.util.Date;
import java.util.StringJoiner;
import org.springframework.util.Assert;

public class Claims {

  private String username;
  private String[] roles;
  private Date issuedAt;
  private Date expiresAt;

  private Claims() {
  }

  Claims(DecodedJWT decodedJWT) {
    this.username = getUsernameFromClaim(decodedJWT);
    this.roles = getRolesFromClaim(decodedJWT);
    this.issuedAt = decodedJWT.getIssuedAt();
    this.expiresAt = decodedJWT.getExpiresAt();
  }

  public static Claims from(String username, String[] roles) {
    Claims claims = new Claims();
    claims.username = username;
    claims.roles = roles;
    return claims;
  }

  private String getUsernameFromClaim(DecodedJWT decodedJWT) {
    Claim username = decodedJWT.getClaim("username");
    Assert.notNull(username, "username is not in claim");
    return username.asString();
  }

  private String[] getRolesFromClaim(DecodedJWT decodedJWT) {
    Claim roles = decodedJWT.getClaim("roles");
    Assert.notNull(roles, "roles is not in claim");
    return roles.asArray(String.class);
  }

  String getUsername() {
    return username;
  }

  String[] getRoles() {
    return roles;
  }

  long getIssuedAt() {
    return issuedAt != null ? issuedAt.getTime() : -1;
  }

  long getExpiresAt() {
    return expiresAt != null ? expiresAt.getTime() : -1;
  }

  void eraseIssuedAt() {
    issuedAt = null;
  }

  void eraseExpiresAt() {
    expiresAt = null;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Claims.class.getSimpleName() + "[", "]")
        .add("username='" + username + "'")
        .add("roles=" + Arrays.toString(roles))
        .add("issuedAt=" + issuedAt)
        .add("expiresAt=" + expiresAt)
        .toString();
  }
}
