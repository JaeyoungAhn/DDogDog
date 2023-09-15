package com.babyblackdog.ddogdog.place.model;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_RATING_COUNT;
import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_RATING_SCORE;

import com.babyblackdog.ddogdog.global.exception.RatingException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_rating", nullable = false)
    private double totalRating;

    @Column(name = "total_count", nullable = false)
    private long totalCount;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    public Rating(double totalRating, long totalCount) {
        validate(totalRating, totalCount);
        this.totalRating = totalRating;
        this.totalCount = totalCount;
    }

    protected Rating() {
    }

    private void validate(double totalRating, long totalCount) {
        if (totalRating < 0) {
            throw new RatingException(INVALID_RATING_SCORE);
        }
        if (totalCount < 0) {
            throw new RatingException(INVALID_RATING_COUNT);
        }
    }

    public Long getId() {
        return id;
    }

    public double getTotalRating() {
        return totalRating;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public double getRatingScore() {
        if (totalCount == 0) {
            return 0.0;
        }
        return Math.round((totalRating / totalCount) * 10.0) / 10.0;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public void addRatingScore(double ratingScore) {
        this.totalRating += ratingScore;
        this.totalCount++;
        validate(totalRating, totalCount);
    }

    public void subRatingScore(double ratingScore) {
        this.totalRating -= ratingScore;
        this.totalCount--;
        validate(totalRating, totalCount);
    }
}
