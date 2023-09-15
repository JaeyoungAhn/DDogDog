package com.babyblackdog.ddogdog.order.domain;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.reservation.service.StayPeriod;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.apache.commons.lang3.Validate;

@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @AttributeOverride(name = "value", column = @Column(name = "user_email"))
    private Email userEmail;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "point_used"))
    private Point usedPoint;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "checkIn", column = @Column(name = "check_in")),
            @AttributeOverride(name = "checkOut", column = @Column(name = "check_out"))
    })
    private StayPeriod stayPeriod;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public Order(Email userEmail, StayPeriod stayPeriod, Point usedPoint) {
        Validate.notNull(userEmail, "userId는 Null일 수 없습니다.");
        Validate.notNull(stayPeriod, "stayPeriod는 Null일 수 없습니다.");
        Validate.notNull(usedPoint, "pointUsed는 Null일 수 없습니다.");

        orderStatus = OrderStatus.PREPARED;
        this.userEmail = userEmail;
        this.stayPeriod = stayPeriod;
        this.usedPoint = usedPoint;
    }

    protected Order() {
    }

    public Long getId() {
        return id;
    }

    public Point getUsedPoint() {
        return usedPoint;
    }

    public StayPeriod getStayPeriod() {
        return stayPeriod;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void complete() {
        if (this.orderStatus != OrderStatus.PREPARED) {
            throw new IllegalStateException("주문을 완료할 수 없습니다.");
        }
        this.orderStatus = OrderStatus.COMPLETED;
    }

    public boolean isOrderAuthorValid(Email userEmail) {
        return this.userEmail.equals(userEmail);
    }

    public void cancel(Email userEmail) {
        if (!canCanceled(userEmail)) {
            throw new IllegalStateException("주문을 취소할 수 없습니다.");
        }
        this.orderStatus = OrderStatus.CANCELED;
    }

    private boolean canCanceled(Email userEmail) {
        if (!isOrderAuthorValid(userEmail)) {
            return false;
        }
        if (this.orderStatus != OrderStatus.COMPLETED) {
            return false;
        }
        return true;
    }
}
