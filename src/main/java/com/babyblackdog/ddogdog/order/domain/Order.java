package com.babyblackdog.ddogdog.order.domain;

import com.babyblackdog.ddogdog.common.point.Point;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long userId;

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "point_used"))
  private Point pointUsed;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  public Order(Long userId) {
    this.userId = userId;
    orderStatus = OrderStatus.PREPARED;
  }

  protected Order() {
  }
}
