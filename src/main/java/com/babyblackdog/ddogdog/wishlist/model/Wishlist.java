package com.babyblackdog.ddogdog.wishlist.model;

import com.babyblackdog.ddogdog.common.auth.Email;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "wishlists", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email", "place_id"})
})
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

    public Email getEmail() {
        return email;
    }

    public Long getPlaceId() {
        return placeId;
    }
}
