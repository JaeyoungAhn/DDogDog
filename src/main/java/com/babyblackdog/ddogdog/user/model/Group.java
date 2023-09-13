package com.babyblackdog.ddogdog.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@Table(name = "user_groups")
public class Group {

  @Id
  @Column(name = "id")
  private Long id;

  @Column(nullable = false, length = 20)
  private String name;

  @OneToMany(mappedBy = "group")
  private List<GroupPermission> permissions = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<SimpleGrantedAuthority> getAuthorities() {
    return permissions.stream()
        .map(groupPermission -> new SimpleGrantedAuthority(
            groupPermission.getPermission().getName()))
        .toList();
  }

  @Override
  public String toString() {
    return "Group{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", permissions=" + permissions +
        '}';
  }
}
