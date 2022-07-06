package com.jerry.shop.exception;

import com.jerry.shop.enums.CodeMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApiException extends RuntimeException {

  private String errorCode;

  public ApiException(CodeMessage codeMessage) {
    super(codeMessage.getMessage());
    this.errorCode = codeMessage.getCode();
  }

  public ApiException(CodeMessage codeMessage, String message) {
    super(message);
    this.errorCode = codeMessage.getCode();
  }

  public static ApiException noData() {
    return new ApiException(CodeMessage.NODATA);
  }

  public static ApiException fail() {
    return new ApiException(CodeMessage.FAIL);
  }

  public static ApiException fail(String message) {
    return new ApiException(CodeMessage.FAIL, message);
  }
}
