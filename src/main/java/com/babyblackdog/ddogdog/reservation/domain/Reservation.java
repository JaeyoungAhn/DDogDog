package com.babyblackdog.ddogdog.reservation.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import org.apache.commons.lang3.Validate;

@Entity(name = "reservations")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"roomId", "date"}))
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long roomId;

    @Column(nullable = false)
    private LocalDate date;

    private Long orderId;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    protected Reservation() {
    }

    public Reservation(Long roomId, LocalDate reservationDate) {
        Validate.notNull(roomId, "roomId는 Null일 수 없습니다.");
        Validate.notNull(reservationDate, "reservationDate는 Null일 수 없습니다.");

        this.roomId = roomId;
        this.date = reservationDate;
        this.status = ReservationStatus.UNRESERVED;
    }

    public boolean isReserved() {
        return this.status == ReservationStatus.RESERVED;
    }

    public Long getId() {
        return id;
    }

    public void reserve(Long orderId) {
        if (isReserved()) {
            throw new IllegalStateException("이미 예약이 되어 있습니다.");
        }
        this.status = ReservationStatus.RESERVED;
        this.orderId = orderId;
    }
}
