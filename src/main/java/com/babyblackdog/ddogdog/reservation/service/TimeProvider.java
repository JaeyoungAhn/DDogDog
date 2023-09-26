package com.babyblackdog.ddogdog.reservation.service;

import java.time.LocalDate;

public interface TimeProvider {

    LocalDate getCurrentDate();
}
