package com.babyblackdog.ddogdog.mapping;

import java.time.LocalDate;

public interface MappingService {

    boolean isReservationAvailableForRoom(Long roomId, LocalDate checkIn, LocalDate checkOut);
}
