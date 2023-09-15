package com.babyblackdog.ddogdog.wishlist.service.dto;

import com.babyblackdog.ddogdog.wishlist.model.Wishlist;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public record WishlistResults(Page<WishlistResult> wishlistResults) {

    public static WishlistResults of(Page<Wishlist> retrievedWishlists) {
        List<WishlistResult> mappedResults = retrievedWishlists.getContent()
                .stream()
                .map(wishlist -> new WishlistResult(wishlist.getId(), wishlist.getEmail(), wishlist.getPlaceId()))
                .collect(Collectors.toList());

        Page<WishlistResult> wishlistResultPage = new PageImpl<>(
                mappedResults,
                retrievedWishlists.getPageable(),
                retrievedWishlists.getTotalElements()
        );

        return new WishlistResults(wishlistResultPage);
    }
}
