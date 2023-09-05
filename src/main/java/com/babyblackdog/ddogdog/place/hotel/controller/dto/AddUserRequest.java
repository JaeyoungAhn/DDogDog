package com.babyblackdog.ddogdog.place.hotel.controller.dto;

public class AddUserRequest {

  //  @NotBlank
  private String name;

  public AddUserRequest(String name) {
    System.out.println("name = " + name);
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("생성자 가드에 걸림!");
    }
    this.name = name;
  }

  protected AddUserRequest() {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("생성자 가드에 걸림!!!!");
    }
  }

  public String getName() {
    return name;
  }
}
