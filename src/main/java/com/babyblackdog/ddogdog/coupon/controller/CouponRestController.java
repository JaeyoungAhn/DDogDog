package com.babyblackdog.ddogdog.coupon.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.common.auth.JwtSimpleAuthentication;
import com.babyblackdog.ddogdog.coupon.application.CouponFacade;
import com.babyblackdog.ddogdog.coupon.controller.dto.request.InstantCouponCreationRequest;
import com.babyblackdog.ddogdog.coupon.controller.dto.request.ManualCouponClaimRequest;
import com.babyblackdog.ddogdog.coupon.controller.dto.request.ManualCouponCreationRequest;
import com.babyblackdog.ddogdog.coupon.controller.dto.response.InstantCouponCreationResponse;
import com.babyblackdog.ddogdog.coupon.controller.dto.response.InstantCouponFindResponses;
import com.babyblackdog.ddogdog.coupon.controller.dto.response.ManualCouponClaimResponse;
import com.babyblackdog.ddogdog.coupon.controller.dto.response.ManualCouponCreationResponse;
import com.babyblackdog.ddogdog.coupon.controller.dto.response.ManualCouponFindResponses;
import com.babyblackdog.ddogdog.coupon.service.dto.command.InstantCouponCreationCommand;
import com.babyblackdog.ddogdog.coupon.service.dto.command.ManualCouponCreationCommand;
import com.babyblackdog.ddogdog.coupon.service.dto.result.InstantCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.result.InstantCouponFindResults;
import com.babyblackdog.ddogdog.coupon.service.dto.result.ManualCouponClaimResult;
import com.babyblackdog.ddogdog.coupon.service.dto.result.ManualCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.result.ManualCouponFindResults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/coupons")
public class CouponRestController {

    private final CouponFacade facade;
    private final JwtSimpleAuthentication authentication;

    public CouponRestController(CouponFacade facade, JwtSimpleAuthentication authentication) {
        this.facade = facade;
        this.authentication = authentication;
    }

    @PostMapping(value = "/manual", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ManualCouponCreationResponse> createManualCoupon(
            @RequestBody ManualCouponCreationRequest manualCouponCreationRequest) {

        ManualCouponCreationCommand manualCouponCreationCommand = new ManualCouponCreationCommand(
                manualCouponCreationRequest.couponName(),
                manualCouponCreationRequest.discountType(),
                manualCouponCreationRequest.discountValue(),
                manualCouponCreationRequest.promoCode(),
                manualCouponCreationRequest.issueCount(),
                manualCouponCreationRequest.startDate(),
                manualCouponCreationRequest.endDate()
        );

        ManualCouponCreationResult addedManualCouponResult = facade.registerManualCoupon(manualCouponCreationCommand);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ManualCouponCreationResponse.of(addedManualCouponResult));
    }

    @PostMapping(value = "/instant", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InstantCouponCreationResponse> createInstantCoupon(
            @RequestBody InstantCouponCreationRequest instantCouponCreationRequest) {
        Email email = authentication.getEmail();

        InstantCouponCreationCommand instantCouponCreationCommand = new InstantCouponCreationCommand(
                email,
                instantCouponCreationRequest.roomId(),
                instantCouponCreationRequest.couponName(),
                instantCouponCreationRequest.discountType(),
                instantCouponCreationRequest.discountValue(),
                instantCouponCreationRequest.startDate(),
                instantCouponCreationRequest.endDate()
        );

        InstantCouponCreationResult instantCouponCreationResult = facade.registerInstantCoupon(
                instantCouponCreationCommand);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(InstantCouponCreationResponse.of(instantCouponCreationResult));
    }

    @GetMapping(value = "/manual/available", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ManualCouponFindResponses> getAvailableManualCoupon() {
        Email email = authentication.getEmail();

        ManualCouponFindResults manualCouponFindResults = facade.findAvailableManualCouponsByEmail(email);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ManualCouponFindResponses.of(manualCouponFindResults));
    }

    @GetMapping(value = "/instant", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<InstantCouponFindResponses> getAvailableInstantCoupon(@RequestParam Long hotelId) {

        InstantCouponFindResults instantCouponFindResults = facade.findAvailableInstantCouponsByHotelId(hotelId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(InstantCouponFindResponses.of(instantCouponFindResults));
    }

    @PostMapping(value = "/manual/claim", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ManualCouponClaimResponse> claimManualCoupon(
            @RequestBody ManualCouponClaimRequest manualCouponClaimRequest) {
        Email email = authentication.getEmail();

        ManualCouponClaimResult manualCouponClaimResult = facade.claimManualCoupon(email,
                manualCouponClaimRequest.promoCode());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ManualCouponClaimResponse.of(manualCouponClaimResult));
    }

    @DeleteMapping(value = "/instant/{couponId}")
    public ResponseEntity<Void> removeInstantCoupon(@PathVariable Long couponId) {
        Email email = authentication.getEmail();

        facade.deleteInstantCoupon(email, couponId);

        return ResponseEntity.noContent().build();
    }

}
