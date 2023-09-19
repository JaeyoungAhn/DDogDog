package com.babyblackdog.ddogdog.coupon.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.babyblackdog.ddogdog.common.auth.Email;
import com.babyblackdog.ddogdog.common.auth.JwtSimpleAuthentication;
import com.babyblackdog.ddogdog.coupon.application.CouponFacade;
import com.babyblackdog.ddogdog.coupon.controller.dto.InstantCouponCreationRequest;
import com.babyblackdog.ddogdog.coupon.controller.dto.InstantCouponCreationResponse;
import com.babyblackdog.ddogdog.coupon.controller.dto.InstantCouponDeletionResponse;
import com.babyblackdog.ddogdog.coupon.controller.dto.InstantCouponUsageResponse;
import com.babyblackdog.ddogdog.coupon.controller.dto.ManualCouponClaimRequest;
import com.babyblackdog.ddogdog.coupon.controller.dto.ManualCouponClaimResponse;
import com.babyblackdog.ddogdog.coupon.controller.dto.ManualCouponCreationRequest;
import com.babyblackdog.ddogdog.coupon.controller.dto.ManualCouponCreationResponse;
import com.babyblackdog.ddogdog.coupon.controller.dto.ManualCouponResponses;
import com.babyblackdog.ddogdog.coupon.controller.dto.ManualCouponUsageResponse;
import com.babyblackdog.ddogdog.coupon.controller.dto.instantCouponResponses;
import com.babyblackdog.ddogdog.coupon.service.ManualCouponUsageResult;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponDeletionResult;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponResults;
import com.babyblackdog.ddogdog.coupon.service.dto.InstantCouponUsageResult;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponClaimResult;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponCreationResult;
import com.babyblackdog.ddogdog.coupon.service.dto.ManualCouponResults;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

        Email email = authentication.getEmail();

        ManualCouponCreationResult addedManualCouponResult = facade.registerManualCoupon();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ManualCouponCreationResponse.of(addedManualCouponResult));
    }

    @PostMapping(value = "/instant", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InstantCouponCreationResponse> createInstantCoupon(
            @RequestBody InstantCouponCreationRequest instantCouponCreationRequest) {

        Email email = authentication.getEmail();

        InstantCouponCreationResult instantCouponCreationResult = facade.registerInstantCoupon();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(InstantCouponCreationResponse.of(instantCouponCreationResult));
    }

    @GetMapping(value = "/manual/available", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ManualCouponResponses> getAvailableManualCoupon() {
        Email email = authentication.getEmail();

        ManualCouponResults manualCouponResults = facade.findAvailableManualCouponsByEmail(email.getValue());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ManualCouponResponses.of(manualCouponResults));
    }

    @GetMapping(value = "/instant", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<instantCouponResponses> getAvailableInstantCoupon(@RequestParam Long hotelId) {

        InstantCouponResults instantCouponResults = facade.findAvailableInstantCouponsByHotelId(hotelId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(instantCouponResponses.of(instantCouponResults));
    }

    @PostMapping(value = "/manual/claim", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ManualCouponClaimResponse> claimManualCoupon(
            @RequestBody ManualCouponClaimRequest manualCouponClaimRequest) {

        Email email = authentication.getEmail();

        ManualCouponClaimResult manualCouponClaimResult = facade.registerManualCoupon(email.getValue());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ManualCouponClaimResponse.of(manualCouponClaimResult));
    }

    @PatchMapping(value = "/manual/{couponUsageId}", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ManualCouponUsageResponse> useManualCoupon(@PathVariable Long couponUsageId) {
        ManualCouponUsageResult manualCouponUsageResult = facade.useManualCoupon(couponUsageId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ManualCouponUsageResponse.of(manualCouponUsageResult));
    }

    @PostMapping(value = "/{couponId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InstantCouponUsageResponse> useInstantCoupon(@PathVariable Long couponId) {

        Email email = authentication.getEmail();

        InstantCouponUsageResult instantCouponUsageResult = facade.useInstantCoupon();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(InstantCouponUsageResponse.of(instantCouponUsageResult));
    }

    @DeleteMapping(value = "/{couponId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InstantCouponDeletionResponse> removeInstantCoupon(@PathVariable Long couponId) {

        Email email = authentication.getEmail();

        InstantCouponDeletionResult instantCouponDeletionResult = facade.deleteInstantCoupon(couponId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(InstantCouponDeletionResponse.of(instantCouponDeletionResult));
    }

}
