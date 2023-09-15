package com.babyblackdog.ddogdog.wishlist.controller.dto;

import com.babyblackdog.ddogdog.wishlist.service.dto.WishlistResult;

public record WishlistResponse(Long placeId) {
    public static WishlistResponse of(WishlistResult wishlistResult) {
        return new WishlistResponse(wishlistResult.placeId());
    }
}
