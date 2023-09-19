package com.babyblackdog.ddogdog.wishlist.service;

import static com.babyblackdog.ddogdog.global.exception.ErrorCode.INVALID_WISHLIST_PERMISSION;
import static com.babyblackdog.ddogdog.global.exception.ErrorCode.WISHLIST_ALREADY_EXIST;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.common.auth.JwtSimpleAuthentication;
import com.babyblackdog.ddogdog.global.exception.WishlistException;
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

    private final JwtSimpleAuthentication authentication;

    public WishlistServiceImpl(WishlistReader reader, WishlistStore store, JwtSimpleAuthentication authentication) {
        this.reader = reader;
        this.store = store;
        this.authentication = authentication;
    }

    @Transactional
    @Override
    public WishlistResult registerWishlist(String email, Long placeId) {
        if (Boolean.TRUE.equals(reader.existsByEmailAndPlaceId(email, placeId))) {
           throw new WishlistException(WISHLIST_ALREADY_EXIST);
        }

        Wishlist savedWishlist = store.registerWishlist(new Wishlist(new Email(email), placeId));
        return WishlistResult.of(savedWishlist);
    }

    @Transactional
    @Override
    public void deleteWishlist(Long wishlistId) {

        Email email = authentication.getEmail();

        Wishlist wishlist = reader.findWishlistById(wishlistId);
        if (!email.getValue().equals(wishlist.getEmail())) {
            throw new WishlistException(INVALID_WISHLIST_PERMISSION);
        }

        store.deleteWishlist(wishlistId);
    }

    @Override
    public WishlistResults findWishlistsByEmail(String email, Pageable pageable) {
        Page<Wishlist> retrievedWishlists = reader.findWishlistsByEmail(email, pageable);
        return WishlistResults.of(retrievedWishlists);
    }
}
