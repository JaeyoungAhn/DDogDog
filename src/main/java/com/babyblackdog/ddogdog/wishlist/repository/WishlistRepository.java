package com.babyblackdog.ddogdog.wishlist.repository;

import com.babyblackdog.ddogdog.wishlist.model.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Page<Wishlist> findWishlistsByEmail(String email, Pageable pageable);

    boolean existsByEmailAndPlaceId(String email, Long placeId);
}