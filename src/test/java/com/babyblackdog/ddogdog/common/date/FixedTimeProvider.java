package com.babyblackdog.ddogdog.common.date;

import java.time.LocalDate;

public class FixedTimeProvider implements TimeProvider {

  private final LocalDate fixedDate;

  public FixedTimeProvider(LocalDate fixedDate) {
    this.fixedDate = fixedDate;
  }

  @Override
  public LocalDate getCurrentDate() {
    return fixedDate;
  }
}
