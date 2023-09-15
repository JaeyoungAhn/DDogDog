package com.babyblackdog.ddogdog.wishlist.service;

import com.babyblackdog.ddogdog.wishlist.service.dto.WishlistResult;
import com.babyblackdog.ddogdog.wishlist.service.dto.WishlistResults;
import org.springframework.data.domain.Pageable;

public interface WishlistService {

    /**
     * email, hotelId를 받아 wishlist 로 추가한다.
     *
     * @param email, hotelId
     * @return WishlistResult
     */
    WishlistResult registerWishlist(String email, Long hotelId);

    /**
     * wishlistId에 해당하는 wishlist 를 삭제한다.
     *
     * @param wishlistId
     * @return void
     */
    void deleteWishlist(Long wishlistId);

    /**
     * email, pageable 에 해당하는 WishlistResults 를 얻어온다.
     *
     * @param email
     * @param pageable
     * @return WishlistResults
     */
    WishlistResults findWishlistsByEmail(String email, Pageable pageable);

}
