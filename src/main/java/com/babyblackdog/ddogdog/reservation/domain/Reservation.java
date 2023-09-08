package com.babyblackdog.ddogdog.reservation.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import org.apache.commons.lang3.Validate;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private Long paymentId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long roomId;

    @Column(nullable = false)
    private LocalDate checkIn;

    @Column(nullable = false)
    private LocalDate checkOut;

    protected Reservation() {
    }

    public Reservation(Long paymentId, Long userId, Long roomId,
            LocalDate checkIn, LocalDate checkOut) {
        validate(paymentId, userId, roomId, checkIn, checkOut);

        this.paymentId = paymentId;
        this.userId = userId;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public Long getId() {
        return id;
    }

    private void validate(Long paymentId, Long userId, Long roomId, LocalDate checkIn,
            LocalDate checkOut) {
        Validate.notNull(paymentId, "paymentId는 Null일 수 없습니다.");
        Validate.notNull(userId, "userId는 Null일 수 없습니다.");
        Validate.notNull(roomId, "roomId는 Null일 수 없습니다.");
        Validate.notNull(checkIn, "checkIn은 Null일 수 없습니다.");
        Validate.notNull(checkOut, "checkOut은 Null일 수 없습니다.");
    }

}
