package com.babyblackdog.ddogdog.wishlist.repository;

import com.babyblackdog.ddogdog.wishlist.model.Wishlist;
import com.babyblackdog.ddogdog.wishlist.service.WishlistReader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class WishlistReaderImpl implements WishlistReader {
    private final WishlistRepository repository;

    public WishlistReaderImpl(WishlistRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Wishlist> findWishlistsByUserId(Long userId, Pageable pageable) {
        return repository.findWishlistsByUserId(userId, pageable);
    }

    @Override
    public Boolean isInWishlist(Long userId, Long placeId) {
        return repository.existsByUserIdAndPlaceId(userId, placeId);
    }

}
