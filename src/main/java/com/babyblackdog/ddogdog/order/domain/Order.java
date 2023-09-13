package com.babyblackdog.ddogdog.order.domain;

import com.babyblackdog.ddogdog.common.date.StayPeriod;
import com.babyblackdog.ddogdog.common.point.Point;
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

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

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

    public Order(Long userId, StayPeriod stayPeriod, Point usedPoint) {
        Validate.notNull(userId, "userId는 Null일 수 없습니다.");
        Validate.notNull(stayPeriod, "stayPeriod는 Null일 수 없습니다.");
        Validate.notNull(usedPoint, "pointUsed는 Null일 수 없습니다.");

        orderStatus = OrderStatus.PREPARED;
        this.userId = userId;
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
        orderStatus = OrderStatus.COMPLETED;
    }

    public boolean isOrderAuthorValid(long userId) {
        return this.userId == userId;
    }

    public boolean canCanceled(long userId) {
        if (!isOrderAuthorValid(userId)) {
            return false;
        }
        if (this.orderStatus != OrderStatus.COMPLETED) {
            return false;
        }
        return true;
    }

    public void cancel() {
        this.orderStatus = OrderStatus.CANCELED;
    }
}
