package com.babyblackdog.ddogdog.wishlist.service;

import com.babyblackdog.ddogdog.wishlist.model.Email;
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
    public WishlistResult registerWishlist(String email, Long placeId) {
        // todo: JWT 내 Email과 요청하는 이메일이 일치하는지 확인
        Wishlist savedWishlist = store.registerWishlist(new Wishlist(new Email(email), placeId));
        return WishlistResult.of(savedWishlist);
    }

    @Transactional
    @Override
    public void deleteWishlist(Long wishlistId) {
        // todo: JWT 내 Email과 wishlistId를 가진 Email이 동일한지 확인
        store.deleteWishlist(wishlistId);
    }

    @Override
    public WishlistResults findWishlistsByEmail(String email, Pageable pageable) {
        // todo: JWT 내 Email과 요청하는 이메일이 일치하는지 확인
        Page<Wishlist> retrievedWishlists = reader.findWishlistsByEmail(email, pageable);
        return WishlistResults.of(retrievedWishlists);
    }
}
