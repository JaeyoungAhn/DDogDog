package com.babyblackdog.ddogdog.reservation.controller;

import com.babyblackdog.ddogdog.reservation.service.TimeProvider;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class TodayDateProvider implements TimeProvider {

    @Override
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
