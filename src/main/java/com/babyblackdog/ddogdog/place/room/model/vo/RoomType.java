package com.babyblackdog.ddogdog.place.room.model.vo;

public enum RoomType {
  SINGLE("싱글"),
  TWIN("트윈"),
  DELUXE("디럭스"),
  DOUBLE("더블");

  private final String typeName;

  RoomType(String typeName) {
    this.typeName = typeName;
  }

  public String getTypeName() {
    return typeName;
  }
}
