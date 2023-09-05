package com.babyblackdog.ddogdog.review.model;

import com.babyblackdog.ddogdog.reservation.model.Reservation;
import com.babyblackdog.ddogdog.review.model.vo.Content;
import com.babyblackdog.ddogdog.review.model.vo.Rating;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "reviews")
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  private Content content;

  @Embedded
  private Rating rating;

  @CreatedDate
  private LocalDateTime createdDate;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reservation_id", nullable = false)
  private Reservation reservation;

  public Review(Content content, Rating rating, Reservation reservation) {
    this.content = content;
    this.rating = rating;
    this.reservation = reservation;
  }

  protected Review() {
  }

  public Long getId() {
    return id;
  }

  public String getContent() {
    return content.getValue();
  }

  public Double getRating() {
    return rating.getValue();
  }

  public LocalDateTime getCreatedDate() { return createdDate; }

  public Long getReservation() { return reservation.getId(); }

}