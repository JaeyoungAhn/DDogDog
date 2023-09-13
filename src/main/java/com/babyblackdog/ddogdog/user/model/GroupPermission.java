package com.babyblackdog.ddogdog.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "group_permission")
public class GroupPermission {

  @Id
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "group_id", referencedColumnName = "id")
  private Group group;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "permission_id", referencedColumnName = "id")
  private Permission permission;

  public Long getId() {
    return id;
  }

  public Group getGroup() {
    return group;
  }

  public Permission getPermission() {
    return permission;
  }

}
