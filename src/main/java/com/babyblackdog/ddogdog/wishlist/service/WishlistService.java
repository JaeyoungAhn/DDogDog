package com.babyblackdog.ddogdog.wishlist.service;

import com.babyblackdog.ddogdog.wishlist.service.dto.WishlistResult;
import com.babyblackdog.ddogdog.wishlist.service.dto.WishlistResults;
import org.springframework.data.domain.Pageable;

public interface WishlistService {
  /**
   * userId, hotelId를 받아 wishlist 로 추가한다.
   *
   * @param userId, hotelId
   * @return WishlistResult
   */
  WishlistResult registerWishlist(Long userId, Long hotelId);

  /**
   * wishlistId에 해당하는 wishlist 를 삭제한다.
   *
   * @param wishlistId
   * @return void
   */
  void deleteWishlist(Long wishlistId);

  /**
   * userId, pageable 에 해당하는 WishlistResults 를 얻어온다.
   *
   * @param userId
   * @param pageable
   * @return WishlistResults
   */
  WishlistResults findWishlistsByUserId(Long userId, Pageable pageable);

  /**
   * 해당 사용자에 대해서 주어진 숙소가 찜이 되어있는지 여부 반환 찜에 해당 열이 없으면 false 찜에 해당 열이 있으면 true
   *
   * @param userId
   * @param placeId
   * @return boolean
   */
  boolean isInWishlist(Long userId, Long placeId);
}
