package com.jerry.shop.enums;

import lombok.Getter;

public enum CodeMessage {
  SUCCESS("00", "SUCCESS"),
  FAIL("01", "FAIL"),
  NODATA("02", "NO_DATA"),
  ERROR("99", "ERROR");

  @Getter private final String code;
  @Getter private final String message;

  CodeMessage(String code, String message) {
    this.code = code;
    this.message = message;
  }
}
