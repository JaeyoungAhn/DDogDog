package com.babyblackdog.ddogdog.user.model;

import static io.micrometer.common.util.StringUtils.isNotEmpty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.StringJoiner;
import org.springframework.util.Assert;

@Entity
@Table(name = "users")
public class User {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username")
  private String username;

  @Column(name = "provider")
  private String provider;

  @Column(name = "provider_id")
  private String providerId;

  @Column(name = "email")
  private String email;

  @ManyToOne(optional = false)
  @JoinColumn(name = "group_id", referencedColumnName = "id")
  private Group group;

  protected User() {
  }

  public User(String username, String provider, String providerId, String email, Group group) {
    Assert.isTrue(isNotEmpty(username), "username must be provided.");
    Assert.isTrue(isNotEmpty(provider), "provider must be provided.");
    Assert.isTrue(isNotEmpty(providerId), "providerId must be provided.");
    Assert.isTrue(group != null, "group must be provided.");

    this.username = username;
    this.provider = provider;
    this.providerId = providerId;
    this.email = email;
    this.group = group;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getProvider() {
    return provider;
  }

  public String getProviderId() {
    return providerId;
  }

  public String getEmail() {
    return email;
  }

  public Group getGroup() {
    return group;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("username='" + username + "'")
        .add("provider='" + provider + "'")
        .add("providerId='" + providerId + "'")
        .add("email='" + email + "'")
        .add("group=" + group)
        .toString();
  }
}
