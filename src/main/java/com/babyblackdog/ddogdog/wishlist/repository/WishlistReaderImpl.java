package com.babyblackdog.ddogdog.wishlist.repository;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.WISHLIST_NOT_FOUND;

import com.babyblackdog.ddogdog.common.auth.Email;
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

    public Page<Wishlist> findWishlistsByEmail(Email email, Pageable pageable) {
        return repository.findWishlistsByEmail(email, pageable);
    }

    @Override
    public Wishlist findWishlistByPlaceId(Long placeId) {
        return repository.findByPlaceId(placeId)
                .orElseThrow(() -> new WishlistException(WISHLIST_NOT_FOUND));
    }

    @Override
    public Boolean existsByEmailAndPlaceId(Email email, Long placeId) {
        return repository.existsByEmailAndPlaceId(email, placeId);
    }
}
