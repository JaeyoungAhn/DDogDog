package com.babyblackdog.ddogdog.reservation.domain;

import com.babyblackdog.ddogdog.global.exception.ErrorCode;
import com.babyblackdog.ddogdog.global.exception.ReserveException;
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
import java.util.Objects;
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
            throw new ReserveException(ErrorCode.ALREADY_RESERVED);
        }
        this.status = ReservationStatus.RESERVED;
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation that = (Reservation) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(roomId, that.roomId)
                && Objects.equals(date, that.date) && Objects.equals(orderId, that.orderId)
                && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), roomId, date, orderId, status);
    }

    public boolean isAvailable() {
        return !isReserved();
    }
}
