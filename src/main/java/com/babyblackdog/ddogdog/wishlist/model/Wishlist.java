package com.babyblackdog.ddogdog.wishlist.model;

import jakarta.persistence.*;

@Entity
@Table(name = "wishlists")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "place_id")
    private Long placeId;

    public Wishlist(Long userId, Long placeId) {
        this.userId = userId;
        this.placeId = placeId;
    }

    protected Wishlist() {
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getPlaceId() {
        return placeId;
    }
}