package com.babyblackdog.ddogdog.user.model;

import static io.micrometer.common.util.StringUtils.isNotEmpty;

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

    @Column(name = "username")
    private String username;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "remain_point"))
    private Point point;

    public User(String email, String username, Role role, Point point) {
        Assert.isTrue(isNotEmpty(username), "username must be provided.");
        Assert.isTrue(isNotEmpty(email), "email must be provided.");
        Assert.notNull(role, "role must be provided");
        Assert.notNull(point, "point must be provided");

        this.email = email;
        this.username = username;
        this.role = role;
        this.point = point;
    }

    protected User() {
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role.name();
    }

    public long getPoint() {
        return point.getValue();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("username='" + username + "'")
                .add("email='" + email + "'")
                .add("role=" + getRole())
                .add("point=" + getPoint())
                .toString();
    }

    public void addPoint(long charge) {
        this.point = new Point(getPoint() + charge);
    }
}
