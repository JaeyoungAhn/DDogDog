package com.babyblackdog.ddogdog.user.model;

import static io.micrometer.common.util.StringUtils.isNotEmpty;

import com.babyblackdog.ddogdog.common.auth.Role;
import com.babyblackdog.ddogdog.common.point.Point;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.StringJoiner;
import org.springframework.util.Assert;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "remain_point"))
    private Point point;

    public User(String email, Role role, Point point) {
        Assert.isTrue(isNotEmpty(email), "email must be provided.");
        Assert.notNull(role, "role must be provided");
        Assert.notNull(point, "point must be provided");

        this.email = email;
        this.role = role;
        this.point = point;
    }

    protected User() {
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role.name();
    }

    public Point getPoint() {
        return point;
    }

    public long getPointValue() {
        return point.getValue();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("email='" + email + "'")
                .add("role=" + getRole())
                .add("point=" + getPointValue())
                .toString();
    }

    public void addPoint(Point point) {
        this.point = Point.addPoint(this.point, point);
    }

    public void subPoint(Point point) {
        this.point = Point.subPoint(this.point, point);
    }
}
