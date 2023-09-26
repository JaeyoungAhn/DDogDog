package com.babyblackdog.ddogdog.review.repository;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.review.domain.Review;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findReviewsByEmail(Email email, Pageable pageable);

    Page<Review> findReviewsByIdIn(List<Long> roomIds, Pageable pageable);


}