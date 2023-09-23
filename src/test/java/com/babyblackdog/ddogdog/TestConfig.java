package com.babyblackdog.ddogdog;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.common.auth.Role;
import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.coupon.domain.vo.DiscountType;
import com.babyblackdog.ddogdog.coupon.service.CouponService;
import com.babyblackdog.ddogdog.coupon.service.dto.command.ManualCouponCreationCommand;
import com.babyblackdog.ddogdog.place.model.vo.BusinessName;
import com.babyblackdog.ddogdog.place.model.vo.HotelName;
import com.babyblackdog.ddogdog.place.model.vo.Occupancy;
import com.babyblackdog.ddogdog.place.model.vo.PhoneNumber;
import com.babyblackdog.ddogdog.place.model.vo.Province;
import com.babyblackdog.ddogdog.place.model.vo.RoomNumber;
import com.babyblackdog.ddogdog.place.model.vo.RoomType;
import com.babyblackdog.ddogdog.place.service.PlaceService;
import com.babyblackdog.ddogdog.place.service.dto.AddHotelParam;
import com.babyblackdog.ddogdog.place.service.dto.AddRoomParam;
import com.babyblackdog.ddogdog.place.service.dto.HotelResult;
import com.babyblackdog.ddogdog.place.service.dto.RoomResult;
import com.babyblackdog.ddogdog.user.model.User;
import com.babyblackdog.ddogdog.user.model.vo.HumanName;
import com.babyblackdog.ddogdog.user.service.UserService;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Autowired
    private PlaceService placeService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private UserService userService;

    Long roomId;

    Email userOwnerEmail;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            addUser();

            userOwnerEmail = addOwner();
            Long hotelId = addHotel(userOwnerEmail.getValue());
            roomId = addRoom(hotelId, userOwnerEmail.getValue());

            String userAdminEmail = addAdmin();
            addManualCoupon();
            System.out.println("hello");

        };
    }


    private void addUser() {
        userService.save(new User("youngsoo@gmail.com", Role.USER, new Point(1000000L)));
    }

    private Email addOwner() {
        Email ownerEmail = new Email("boojinlee@gmail.com");
        userService.save(new User(ownerEmail.getValue(), Role.OWNER, new Point(0L)));
        return ownerEmail;
    }

    private String addAdmin() {
        userService.save(new User("admin@gmail.com", Role.ADMIN, new Point(0L)));
        return "admin@gmail.com";
    }

    private Long addHotel(String userOwnerEmail) {
        AddHotelParam addHotelParam = new AddHotelParam(
                new HotelName("서울신라호텔"),
                new Province("서울"),
                new Email(userOwnerEmail),
                new PhoneNumber("010-2023-1593"),
                new HumanName("이부진"),
                new BusinessName("호텔신라")
        );
        HotelResult hotelResult = placeService.registerHotel(addHotelParam);
        return hotelResult.id();
    }

    private Long addRoom(Long hotelId, String userOwnerEmail) {
        AddRoomParam addRoomParam = new AddRoomParam(
                RoomType.DELUXE,
                hotelId,
                "거실이 넓은 디럭스 룸입니다.",
                new Occupancy(5),
                true,
                true,
                true,
                new RoomNumber("205"),
                new Point(200_000)
        );

        RoomResult roomResult = placeService.registerRoomOfHotel(addRoomParam);

        return roomResult.id();
    }

    private void addManualCoupon() {
        ManualCouponCreationCommand manualCouponCreationCommand = new ManualCouponCreationCommand(
                "한정 수량 무료 쿠폰",
                DiscountType.AMOUNT.name(),
                3_000.0,
                "EWERSDFZQAS",
                50L,
                LocalDate.now(),
                LocalDate.now().plusMonths(1)
        );

        couponService.registerManualCoupon(manualCouponCreationCommand);
    }

    public Long getRoomId() {
        return roomId;
    }

    public Email getUserOwnerEmail() {
        return userOwnerEmail;
    }
}
