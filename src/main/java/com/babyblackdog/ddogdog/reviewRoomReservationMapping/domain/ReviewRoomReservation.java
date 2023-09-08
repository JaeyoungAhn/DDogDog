package com.babyblackdog.ddogdog.reviewRoomReservationMapping.domain;

import com.babyblackdog.ddogdog.global.error.ReviewRoomReservationErrorCode;
import com.babyblackdog.ddogdog.global.exception.ReviewRoomReservationException;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Objects;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "review_room_reservation")
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

  public ReviewRoomReservation(Long roomId, Long reservationId, Long reviewId) {
    if (Objects.isNull(roomId)) throw new ReviewRoomReservationException(ReviewRoomReservationErrorCode.INVALID_ROOM_ID);
    if (Objects.isNull(reservationId)) throw new ReviewRoomReservationException(ReviewRoomReservationErrorCode.INVALID_RESERVATION_ID);
    if (Objects.isNull(reviewId)) throw new ReviewRoomReservationException(ReviewRoomReservationErrorCode.INVALID_REVIEW_ID);

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

}