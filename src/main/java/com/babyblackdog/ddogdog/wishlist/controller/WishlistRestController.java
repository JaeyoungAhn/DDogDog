package com.babyblackdog.ddogdog.wishlist.controller;

import com.babyblackdog.ddogdog.wishlist.application.WishlistFacade;
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
    public ResponseEntity<WishlistResponse> createWishlist(@RequestBody Long placeId) {
        // todo: JWT 이메일 넣어주는 로직 구현
        String email = "test@ddog.dog";
        WishlistResult addedWishlistResult = facade.registerWishlist(email, placeId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(WishlistResponse.of(addedWishlistResult));
    }

    @DeleteMapping("/{wishlistId}")
    public ResponseEntity<Void> removeWishlist(@PathVariable Long wishlistId) {
        facade.deleteWishlist(wishlistId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/me}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<WishlistResponses> getWishlists(Pageable pageable) {
        // todo: JWT 이메일 넣어주는 로직 구현
        String email = "test@ddog.dog";

        WishlistResults retrievedReviewsResult = facade.findWishlistsByEmail(email, pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(WishlistResponses.of(retrievedReviewsResult));
    }
}