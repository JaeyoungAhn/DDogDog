package com.babyblackdog.ddogdog.wishlist.service.dto;

import com.babyblackdog.ddogdog.wishlist.model.Wishlist;

public record WishlistResult(Long id, String email, Long placeId) {

    public static WishlistResult of(Wishlist entity) {
        return new WishlistResult(
                entity.getId(),
                entity.getEmail(),
                entity.getPlaceId()
        );
    }
}
