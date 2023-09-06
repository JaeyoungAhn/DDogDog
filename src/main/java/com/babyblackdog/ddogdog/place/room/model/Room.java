package com.babyblackdog.ddogdog.place.room.model;

import com.babyblackdog.ddogdog.common.Point;
import com.babyblackdog.ddogdog.place.hotel.model.Hotel;
import com.babyblackdog.ddogdog.place.room.model.vo.Occupancy;
import com.babyblackdog.ddogdog.place.room.model.vo.RoomNumber;
import com.babyblackdog.ddogdog.place.room.model.vo.RoomType;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "rooms")
public class Room {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "place_id")
  private Hotel hotel;

  @Enumerated
  private RoomType roomType;

  @Column(name = "description", nullable = false)
  private String description;

  @Embedded
  private Occupancy occupancy;

  @Column(name = "has_bed", nullable = false)
  private boolean hasBed;

  @Column(name = "has_amenities", nullable = false)
  private boolean hasAmenities;

  @Column(name = "smoking_available", nullable = false)
  private boolean smokingAvailable;

  @Embedded
  private RoomNumber roomNumber;

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "point"))
  private Point point;

  public Room(Hotel hotel, RoomType roomType, String description, Occupancy occupancy,
      boolean hasBed, boolean hasAmenities, boolean smokingAvailable, RoomNumber roomNumber,
      Point point
  ) {
    this.hotel = hotel;
    this.roomType = roomType;
    this.description = description;
    this.occupancy = occupancy;
    this.hasBed = hasBed;
    this.hasAmenities = hasAmenities;
    this.smokingAvailable = smokingAvailable;
    this.roomNumber = roomNumber;
    this.point = point;
  }

  protected Room() {
  }

  public Long getId() {
    return id;
  }

  public Hotel getHotel() {
    return hotel;
  }

  public String getHotelName() {
    return hotel.getName();
  }

  public String getRoomType() {
    return roomType.getTypeName();
  }

  public String getDescription() {
    return description;
  }

  public int getMaxOccupancy() {
    return occupancy.getMaxOccupancy();
  }

  public boolean isHasBed() {
    return hasBed;
  }

  public boolean isHasAmenities() {
    return hasAmenities;
  }

  public boolean isSmokingAvailable() {
    return smokingAvailable;
  }

  public String getRoomNumber() {
    return roomNumber.getValue();
  }

  public long getPoint() {
    return point.getValue();
  }
}
