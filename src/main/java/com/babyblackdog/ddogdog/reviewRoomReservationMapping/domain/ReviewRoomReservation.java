package com.babyblackdog.ddogdog.reviewRoomReservationMapping.domain;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_RESERVATION_ID;
import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_REVIEW_ID;
import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_ROOM_ID;

import com.babyblackdog.ddogdog.global.exception.ReviewRoomReservationException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Entity
@EnableJpaAuditing
@Table(name = "review_room_reservation", indexes = {
    @Index(name = "idx_room_reviewed_date", columnList = "room_id, reviewed_date")
})
public class ReviewRoomReservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "room_id")
  private Long roomId;

  @Column(name = "reservation_id")
  private Long reservationId;

  @Column(name = "review_id")
  private Long reviewId;

  @CreatedDate
  private LocalDate reviewed_date;

  public ReviewRoomReservation(Long roomId, Long reservationId, Long reviewId) {
    if (Objects.isNull(roomId)) {
      throw new ReviewRoomReservationException(INVALID_ROOM_ID);
    }
    if (Objects.isNull(reservationId)) {
      throw new ReviewRoomReservationException(INVALID_RESERVATION_ID);
    }
    if (Objects.isNull(reviewId)) {
      throw new ReviewRoomReservationException(INVALID_REVIEW_ID);
    }

    this.roomId = roomId;
    this.reservationId = reservationId;
    this.reviewId = reviewId;
  }

  public ReviewRoomReservation(Long roomId, Long reservationId) {
    this.roomId = roomId;
    this.reservationId = reservationId;
  }

  protected ReviewRoomReservation() {
  }

  public Long getId() {
    return id;
  }

  public void setReviewId(Long reviewId) {
    this.reviewId = reviewId;
  }
}