package com.babyblackdog.ddogdog.wishlist.application;

import com.babyblackdog.ddogdog.wishlist.service.WishlistService;
import com.babyblackdog.ddogdog.wishlist.service.dto.WishlistResult;
import com.babyblackdog.ddogdog.wishlist.service.dto.WishlistResults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WishlistFacade {

    private final WishlistService service;

    public WishlistFacade(WishlistService service) {
        this.service = service;
    }

    public WishlistResult registerWishlist(String email, Long hotelId) {
        return service.registerWishlist(email, hotelId);
    }

    public void deleteWishlist(Long wishlistId) {
        service.deleteWishlist(wishlistId);
    }

    public WishlistResults findWishlistsByEmail(String email, Pageable pageable) {
        return service.findWishlistsByEmail(email, pageable);
    }
}
