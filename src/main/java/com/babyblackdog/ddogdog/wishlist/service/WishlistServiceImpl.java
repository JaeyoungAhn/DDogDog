package com.babyblackdog.ddogdog.wishlist.service;

import com.babyblackdog.ddogdog.wishlist.model.Wishlist;
import com.babyblackdog.ddogdog.wishlist.service.dto.WishlistResult;
import com.babyblackdog.ddogdog.wishlist.service.dto.WishlistResults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class WishlistServiceImpl implements WishlistService {
    private final WishlistReader reader;
    private final WishlistStore store;

    public WishlistServiceImpl(WishlistReader reader, WishlistStore store) {
        this.reader = reader;
        this.store = store;
    }

    @Transactional
    @Override
    public WishlistResult registerWishlist(Long userId, Long placeId) {
        Wishlist savedWishlist = store.registerWishlist(new Wishlist(userId, placeId));
        return WishlistResult.of(savedWishlist);
    }

    @Transactional
    @Override
    public void deleteWishlist(Long wishlistId) {
        store.deleteWishlist(wishlistId);
    }

    @Override
    public WishlistResults findWishlistsByUserId(Long userId, Pageable pageable) {
        Page<Wishlist> retrievedWishlists = reader.findWishlistsByUserId(userId, pageable);
        return WishlistResults.of(retrievedWishlists);
    }

    @Override
    public boolean isInWishlist(Long userId, Long placeId) {
        return reader.isInWishlist(userId, placeId);
    }
}
