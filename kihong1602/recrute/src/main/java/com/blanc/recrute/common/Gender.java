package com.blanc.recrute.common;

public enum Gender {
  FEMALE("여성"),
  MALE("남성");

  private final String gender;

  Gender(String gender) {
    this.gender = gender;
  }

  public String getGender() {
    return gender;
  }
}
