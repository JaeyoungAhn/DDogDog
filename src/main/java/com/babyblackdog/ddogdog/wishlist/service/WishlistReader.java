package com.babyblackdog.ddogdog.wishlist.service;

import com.babyblackdog.ddogdog.wishlist.model.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WishlistReader {

    Boolean isInWishlist(Long userId, Long placeId);

    Page<Wishlist> findWishlistsByUserId(Long userId, Pageable pageable);
}
