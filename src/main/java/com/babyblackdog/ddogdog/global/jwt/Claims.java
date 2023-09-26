package com.babyblackdog.ddogdog.global.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

public class Claims {

    private String email;
    private List<GrantedAuthority> role;
    private Date issuedAt;
    private Date expiresAt;

    Claims(DecodedJWT decodedJWT) {
        this.email = getEmailFromClaim(decodedJWT);
        this.role = getRolesFromClaim(decodedJWT);
        this.issuedAt = decodedJWT.getIssuedAt();
        this.expiresAt = decodedJWT.getExpiresAt();
    }

    private Claims() {
    }

    public static Claims from(String email, List<GrantedAuthority> role) {
        Claims claims = new Claims();
        claims.email = email;
        claims.role = role;
        return claims;
    }

    private String getEmailFromClaim(DecodedJWT decodedJWT) {
        Claim email = decodedJWT.getClaim("email");
        Assert.notNull(email, "email is not in claim");
        return email.asString();
    }

    private List<GrantedAuthority> getRolesFromClaim(DecodedJWT decodedJWT) {
        Claim role = decodedJWT.getClaim("role");
        Assert.notNull(role, "roles is not in claim");
        return List.of(new SimpleGrantedAuthority(role.asString()));
    }

    public String getEmail() {
        return email;
    }

    public List<GrantedAuthority> getRole() {
        return role;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Claims.class.getSimpleName() + "[", "]")
                .add("email='" + email + "'")
                .add("role='" + role + "'")
                .add("issuedAt=" + issuedAt)
                .add("expiresAt=" + expiresAt)
                .toString();
    }
}
