package com.babyblackdog.ddogdog.wishlist.service;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.wishlist.model.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WishlistReader {

    Page<Wishlist> findWishlistsByEmail(Email email, Pageable pageable);

    Boolean existsByEmailAndPlaceId(Email email, Long placeId);

    Wishlist findWishlistByPlaceId(Long placeId);
}
