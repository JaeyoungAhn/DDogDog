package com.babyblackdog.ddogdog.wishlist.model;

import jakarta.persistence.*;

@Entity
@Table(name = "wishlists")
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    @Column(name = "place_id")
    private Long placeId;

    public Wishlist(Email email, Long placeId) {
        this.email = email;
        this.placeId = placeId;
    }

    protected Wishlist() {
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getValue();
    }

    public Long getPlaceId() {
        return placeId;
    }
}