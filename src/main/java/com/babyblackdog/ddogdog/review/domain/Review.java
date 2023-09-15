package com.babyblackdog.ddogdog.review.domain;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.review.domain.vo.Content;
import com.babyblackdog.ddogdog.review.domain.vo.RatingScore;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(name = "order_id", unique = true)
    private Long orderId;

    @Column(name = "room_id")
    private Long roomId;

    @Embedded
    private Email email;

    @Embedded
    private Content content;

    @Embedded
    private RatingScore ratingScore;

    @CreatedDate
    private LocalDate createdDate;

    public Review(Long orderId, Long roomId, Content content, RatingScore ratingScore, Email email) {
        this.orderId = orderId;
        this.roomId = roomId;
        this.content = content;
        this.ratingScore = ratingScore;
        this.email = email;
    }

    protected Review() {
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getContent() {
        return content.getValue();
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Double getRating() {
        return ratingScore.getValue();
    }

    public void setRating(RatingScore ratingScore) {
        this.ratingScore = ratingScore;
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