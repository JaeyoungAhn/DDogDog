package com.babyblackdog.ddogdog.wishlist.service;

import com.babyblackdog.ddogdog.wishlist.model.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WishlistReader {

    Page<Wishlist> findWishlistsByEmail(String email, Pageable pageable);

    Wishlist findWishlistById(Long wishlistId);

    Boolean existsByEmailAndPlaceId(String email, Long placeId);
}
