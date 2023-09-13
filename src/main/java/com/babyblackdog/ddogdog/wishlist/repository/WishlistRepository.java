package com.babyblackdog.ddogdog.wishlist.repository;

import com.babyblackdog.ddogdog.wishlist.model.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Page<Wishlist> findWishlistsByUserId(Long userId, Pageable pageable);

    Boolean existsByUserIdAndPlaceId(Long userId, Long placeId);
}