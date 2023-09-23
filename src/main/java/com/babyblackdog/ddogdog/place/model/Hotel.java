package com.babyblackdog.ddogdog.place.model;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.place.model.vo.BusinessName;
import com.babyblackdog.ddogdog.place.model.vo.HotelName;
import com.babyblackdog.ddogdog.place.model.vo.PhoneNumber;
import com.babyblackdog.ddogdog.place.model.vo.Province;
import com.babyblackdog.ddogdog.user.model.vo.HumanName;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private HotelName name;

    @Embedded
    private Province address;

    @Embedded
    @AttributeOverride(name = "email", column = @Column(name = "admin_email"))
    private Email adminEmail;

    @Embedded
    private PhoneNumber contact;

    @Embedded
    private HumanName representative;

    @Embedded
    private BusinessName businessName;

    @OneToOne(
            fetch = FetchType.EAGER,
            mappedBy = "hotel",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Rating rating;

    public Hotel(HotelName name, Province address, Email adminEmail, PhoneNumber contact,
            HumanName representative, BusinessName businessName) {
        this.name = name;
        this.address = address;
        this.adminEmail = adminEmail;
        this.contact = contact;
        this.representative = representative;
        this.businessName = businessName;
    }

    protected Hotel() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public Province getAddress() {
        return address;
    }

    public String getAddressValue() {
        return address.getValue();
    }

    public Email getEmail() {
        return adminEmail;
    }

    public String getEmailAddress() {
        return adminEmail.getValue();
    }

    public String getContact() {
        return contact.getValue();
    }

    public String getRepresentative() {
        return representative.getValue();
    }

    public String getBusinessName() {
        return businessName.getValue();
    }

    public Rating getRating() {
        return rating;
    }

    public double getRatingScore() {
        return rating.getRatingScore();
    }

    public void setRating(Rating rating) {
        this.rating = rating;
        rating.setHotel(this);
    }
}
