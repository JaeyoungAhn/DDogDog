package com.babyblackdog.ddogdog.review.domain;

import com.babyblackdog.ddogdog.review.domain.vo.Content;
import com.babyblackdog.ddogdog.review.domain.vo.Email;
import com.babyblackdog.ddogdog.review.domain.vo.Rating;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "reviews")
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "room_id")
  private Long roomId;

  @Embedded
  private Email email;

  @Embedded
  private Content content;

  @Embedded
  private Rating rating;

  @CreatedDate
  private LocalDate createdDate;


  public Review(Long roomId, Content content, Rating rating, Email email) {
    this.roomId = roomId;
    this.content = content;
    this.rating = rating;
    this.email = email;
  }

  protected Review() {
  }

  public Long getId() {
    return id;
  }

  public String getContent() {
    return content.getValue();
  }

  public void setContent(Content content) {
    this.content = content;
  }

  public Double getRating() {
    return rating.getValue();
  }

  public void setRating(Rating rating) {
    this.rating = rating;
  }

  public LocalDate getCreatedDate() {
    return createdDate;
  }

  public Long getRoomId() {
    return roomId;
  }

  public String getEmail() {
    return email.getValue();
  }

}