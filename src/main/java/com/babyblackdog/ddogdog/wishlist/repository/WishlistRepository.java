package com.babyblackdog.ddogdog.wishlist.repository;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.wishlist.model.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Page<Wishlist> findWishlistsByEmail(Email email, Pageable pageable);

    boolean existsByEmailAndPlaceId(Email email, Long placeId);
}