package com.babyblackdog.ddogdog.payment.domain;

import com.babyblackdog.ddogdog.common.Point;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

    /*
    추후 쿠폰이 추가된다면 예약에 추가가 될 것이라 생각함(룸 정보에 따른 쿠폰 적용이라)
    그렇게 될 경우 실질적 원래 가격과 적용된 쿠폰은 예약에 있고,
    결제된 금액이 결제에 있어야 하지 않나 생각됨
     */

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "origin_point"))
  private Point originPoint;

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "payment_point"))
  private Point paymentPoint;

  @Column(nullable = false)
  private LocalDate paymentDate;

  protected Payment() {
  }

  public Payment(Point originPoint, Point paymentPoint, LocalDate paymentDate) {
    this.originPoint = originPoint;
    this.paymentPoint = paymentPoint;
    this.paymentDate = paymentDate;
  }
}
