package com.babyblackdog.ddogdog.wishlist.controller.dto;

import com.babyblackdog.ddogdog.wishlist.service.dto.WishlistResult;
import com.babyblackdog.ddogdog.wishlist.service.dto.WishlistResults;
import java.util.List;
import java.util.stream.Collectors;

public record WishlistResponses(List<WishlistResponse> wishlistResponses) {

    public static WishlistResponses of(WishlistResults wishlistResult) {
        List<WishlistResponse> convertedResponses = wishlistResult.wishlistResults().stream()
                .map(WishlistResponses::convertToWishlistResponse)
                .collect(Collectors.toList());

        return new WishlistResponses(convertedResponses);
    }

    private static WishlistResponse convertToWishlistResponse(WishlistResult wishlistResult) {
        return new WishlistResponse(
                wishlistResult.placeId()
        );
    }
}
