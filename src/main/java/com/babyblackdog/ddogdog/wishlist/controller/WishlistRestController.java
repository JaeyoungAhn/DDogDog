package com.babyblackdog.ddogdog.wishlist.controller;

import com.babyblackdog.ddogdog.wishlist.application.WishlistFacade;
import com.babyblackdog.ddogdog.wishlist.controller.dto.WishlistRequest;
import com.babyblackdog.ddogdog.wishlist.controller.dto.WishlistResponse;
import com.babyblackdog.ddogdog.wishlist.controller.dto.WishlistResponses;
import com.babyblackdog.ddogdog.wishlist.service.dto.WishlistResult;
import com.babyblackdog.ddogdog.wishlist.service.dto.WishlistResults;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/reviews")
public class WishlistRestController {
    private final WishlistFacade facade;

    public WishlistRestController(WishlistFacade facade) {
        this.facade = facade;
    }

    @PutMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<WishlistResponse> createWishlist(@RequestBody WishlistRequest wishlistRequest) {
        WishlistResult addedWishlistResult = facade.registerWishlist(
                wishlistRequest.userId(),
                wishlistRequest.placeId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(WishlistResponse.of(addedWishlistResult));
    }

    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<Void> removeWishlist(@PathVariable Long wishlistId) {
        facade.deleteWishlist(wishlistId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{userId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<WishlistResponses> getWishlistsByUserId(@PathVariable Long userId, Pageable pageable) {
            WishlistResults retrievedReviewsResult = facade.findWishlistsByUserId(userId, pageable);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(WishlistResponses.of(retrievedReviewsResult));
    }
}