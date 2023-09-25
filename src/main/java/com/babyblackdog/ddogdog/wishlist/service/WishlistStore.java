package com.babyblackdog.ddogdog.wishlist.service;

import com.babyblackdog.ddogdog.wishlist.model.Wishlist;

public interface WishlistStore {

    Wishlist registerWishlist(Wishlist wishlist);

    void deleteWishlist(Wishlist wishlist);
}
