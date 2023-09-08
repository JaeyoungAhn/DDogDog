package com.babyblackdog.ddogdog.common;

import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class TodayDateProvider implements TimeProvider {

    @Override
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
