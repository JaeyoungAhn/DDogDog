package com.babyblackdog.ddogdog.wishlist.repository;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.WISHLIST_NOT_FOUND;

import com.babyblackdog.ddogdog.global.exception.WishlistException;
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

    public Page<Wishlist> findWishlistsByEmail(String email, Pageable pageable) {
        return repository.findWishlistsByEmail(email, pageable);
    }

    @Override
    public Wishlist findWishlistById(Long wishlistId) {
        return repository.findById(wishlistId)
                .orElseThrow(() -> new WishlistException(WISHLIST_NOT_FOUND));
    }

    @Override
    public Boolean existsByEmailAndPlaceId(String email, Long placeId) {
        return repository.existsByEmailAndPlaceId(email, placeId);
    }
}
