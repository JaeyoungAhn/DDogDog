package com.babyblackdog.ddogdog.wishlist.service.dto;

import com.babyblackdog.ddogdog.wishlist.model.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public record WishlistResults(Page<WishlistResult> wishlistResults) {

    public static WishlistResults of(Page<Wishlist> retrievedWishlists) {
        List<WishlistResult> mappedResults = retrievedWishlists.getContent()
                .stream()
                .map(wishlist -> new WishlistResult(wishlist.getId(), wishlist.getUserId(), wishlist.getPlaceId()))
                .collect(Collectors.toList());

        Page<WishlistResult> wishlistResultPage = new PageImpl<>(
                mappedResults,
                retrievedWishlists.getPageable(),
                retrievedWishlists.getTotalElements()
        );

        return new WishlistResults(wishlistResultPage);
    }
}
