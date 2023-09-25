package com.babyblackdog.ddogdog;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.common.auth.Role;
import com.babyblackdog.ddogdog.common.point.Point;
import com.babyblackdog.ddogdog.coupon.domain.Coupon;
import com.babyblackdog.ddogdog.coupon.domain.vo.DiscountType;
import com.babyblackdog.ddogdog.coupon.service.CouponService;
import com.babyblackdog.ddogdog.coupon.service.dto.command.InstantCouponCreationCommand;
import com.babyblackdog.ddogdog.coupon.service.dto.command.ManualCouponCreationCommand;
import com.babyblackdog.ddogdog.coupon.service.dto.result.InstantCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.result.ManualCouponClaimResult;
import com.babyblackdog.ddogdog.coupon.service.dto.result.ManualCouponCreationResult;
import com.babyblackdog.ddogdog.order.domain.Order;
import com.babyblackdog.ddogdog.order.domain.OrderRepository;
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
import com.babyblackdog.ddogdog.reservation.service.StayPeriod;
import com.babyblackdog.ddogdog.reservation.service.TimeProvider;
import com.babyblackdog.ddogdog.user.model.User;
import com.babyblackdog.ddogdog.user.model.vo.HumanName;
import com.babyblackdog.ddogdog.user.service.UserService;
import com.babyblackdog.ddogdog.wishlist.service.WishlistService;
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


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TimeProvider timeProvider;

    @Autowired
    private WishlistService wishlistService;

    String jwtUserToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJiYWJ5LWJsYWNrZG9nIiwiaWF0IjoxNjk1NjE1NjE5LCJleHAiOjIwNDI2ODQ0MTksImF1ZCI6IiIsInN1YiI6IiIsImVtYWlsIjoieW91bmdzb29AZ21haWwuY29tIiwicm9sZSI6InVzZXIifQ.AU9NkmViz5qx0eJWJO5dTgNzz-EEbdYbJeflM8iVSRutCu-j923FFT387nOOINTbIp-xsa5NuF7bBN419biJxA";

    String jwtOwnerToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJiYWJ5LWJsYWNrZG9nIiwiaWF0IjoxNjk1NjE1NjE5LCJleHAiOjIwNDI2ODQ0MTksImF1ZCI6IiIsInN1YiI6IiIsImVtYWlsIjoiYm9vamlubGVlQGdtYWlsLmNvbSIsInJvbGUiOiJvd25lciJ9.iPRkPycu1IHkzLXhUMQeEAuZc7qqAJ7F0ASMSVED8WAdFHvaaqmYotJY-3tKybRNBB9p3AmVoaDtY1QGnk1TPg";

    String jwtAdminToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJiYWJ5LWJsYWNrZG9nIiwiaWF0IjoxNjk1NjE1NjE5LCJleHAiOjIwNDI2ODQ0MTksImF1ZCI6IiIsInN1YiI6IiIsImVtYWlsIjoiYWRtaW5AZ21haWwuY29tIiwicm9sZSI6IltBRE1JTl0ifQ.ymIW44wJwhIPZrEkpXebWXO_KU5TGgPSME-bfm9rsto6Zs-TpO97PsoTkC77uCaO0BKG2aIa3iA9pSeLhhqcDw";

    Long hotelId;

    RoomResult room;

    Email userEmail;

    Email userOwnerEmail;

    Order order;

    ManualCouponCreationResult manualCoupon;

    ManualCouponClaimResult claimedManualCoupon;

    InstantCouponCreationResult instantCoupon;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            userEmail = addUser();

            userOwnerEmail = addOwner();
            hotelId = addHotel();
            room = addRoom();

            String userAdminEmail = addAdmin();
            manualCoupon = addManualCoupon();
            instantCoupon = addInstantCoupon();

            claimedManualCoupon = claimManualCoupon();

            order = addOrder();
//            addWishlist();
        };
    }

    private void addWishlist() {
        wishlistService.registerWishlist(userEmail.getValue(), hotelId);
    }


    private Email addUser() {
        Email userEmail = new Email("youngsoo@gmail.com");
        userService.save(new User("youngsoo@gmail.com", Role.USER, new Point(1000000L)));
        return userEmail;
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

    private Long addHotel() {
        AddHotelParam addHotelParam = new AddHotelParam(
                new HotelName("서울신라호텔"),
                new Province("서울"),
                new Email(userOwnerEmail.getValue()),
                new PhoneNumber("010-2023-1593"),
                new HumanName("이부진"),
                new BusinessName("호텔신라")
        );
        HotelResult hotelResult = placeService.registerHotel(addHotelParam);
        return hotelResult.id();
    }

    private RoomResult addRoom() {
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

        return placeService.registerRoomOfHotel(addRoomParam);
    }

    private ManualCouponCreationResult addManualCoupon() {
        ManualCouponCreationCommand manualCouponCreationCommand = new ManualCouponCreationCommand(
                "한정 수량 무료 쿠폰",
                DiscountType.AMOUNT.name(),
                3_000.0,
                "EWERSDFZQAS",
                50L,
                LocalDate.now(),
                LocalDate.now().plusMonths(1)
        );

        return couponService.registerManualCoupon(manualCouponCreationCommand);
    }

    private ManualCouponClaimResult claimManualCoupon() {
        Coupon retrievedCoupon = couponService.findCouponByPromoCode(manualCoupon.promoCode());

        return couponService.registerManualCouponUsage(userEmail, retrievedCoupon);
    }

    private InstantCouponCreationResult addInstantCoupon() {
        InstantCouponCreationCommand instantCouponCreationCommand = new InstantCouponCreationCommand(
                room.id(),
                "삭제 예정인 즉시 할인 쿠폰",
                "percent",
                100.0,
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );

        return couponService.registerInstantCoupon(instantCouponCreationCommand);
    }

    private Order addOrder() {
        Order order = new Order(getUserEmail(),
                new StayPeriod(LocalDate.now().minusDays(2), LocalDate.now().minusDays(1), timeProvider),
                new Point(100));
        order.complete();
        return orderRepository.save(order);
    }

    public Long getHotelId() {
        return hotelId;
    }

    public RoomResult getRoom() {
        return room;
    }

    public Email getUserEmail() {
        return userEmail;
    }

    public Email getUserOwnerEmail() {
        return userOwnerEmail;
    }

    public InstantCouponCreationResult getInstantCoupon() {
        return instantCoupon;
    }

    public ManualCouponCreationResult getManualCoupon() {
        return manualCoupon;
    }

    public ManualCouponClaimResult getClaimedManualCoupon() {
        return claimedManualCoupon;
    }

    public String getJwtUserToken() {
        return jwtUserToken;
    }

    public String getJwtOwnerToken() {
        return jwtOwnerToken;
    }

    public String getJwtAdminToken() {
        return jwtAdminToken;
    }

    public Order getOrder() {
        return order;
    }
}
