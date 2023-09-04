package com.babyblackdog.ddogdog.wishlist;

public interface WishlistService {

  /**
   * 해당 사용자에 대해서 주어진 숙소가 찜이 되어있는지 여부 반환
   * 찜에 해당 열이 없으면 false
   * 찜에 해당 열이 있으면 true
   *
   * @param userId
   * @param placeId
   * @return boolean
   */
  boolean isInWishlist(Long userId, Long placeId);

}
