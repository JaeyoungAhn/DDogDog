package com.babyblackdog.ddogdog.review.domain;

import com.babyblackdog.ddogdog.review.domain.vo.Content;
import com.babyblackdog.ddogdog.review.domain.vo.Rating;
import jakarta.persistence.*;

import java.time.LocalDate;
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
  private LocalDate createdDate;

  @Column(name = "user_id")
  private Long userId;

  public Review(Content content, Rating rating) {
    this.content = content;
    this.rating = rating;
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

  public LocalDate getCreatedDate() {
    return createdDate;
  }

  public Long getUserId() { return userId; }

  public void setContent(Content content) {
    this.content = content;
  }

  public void setRating(Rating rating) {
    this.rating = rating;
  }
}