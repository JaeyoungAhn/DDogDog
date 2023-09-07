package com.babyblackdog.ddogdog.reservation.domain;

import com.babyblackdog.ddogdog.payment.domain.Payment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;

@Entity
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @Column(nullable = false)
  private Long paymentId;

  @Column(nullable = false)
  private Long userId;

  @Column(nullable = false)
  private Long roomId;

  @Column(nullable = false)
  private LocalDate checkIn;

  @Column(nullable = false)
  private LocalDate checkOut;

  protected Reservation() {
  }

  public Reservation(Long paymentId, Long userId, Long roomId,
      LocalDate checkIn, LocalDate checkOut) {
    this.paymentId = paymentId;
    this.userId = userId;
    this.roomId = roomId;
    this.checkIn = checkIn;
    this.checkOut = checkOut;
  }

  public Long getId() {
    return id;
  }
}
