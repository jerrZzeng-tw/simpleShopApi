package com.jerry.shop.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jerry.shop.enums.CodeMessage;
import com.jerry.shop.exception.ApiException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ApiModel(description = "API回傳資訊")
public class ResDto<T> {
    @ApiModelProperty(value = "錯誤代碼")
    private String code;

    @ApiModelProperty(value = "訊息")
    private String message;

    @ApiModelProperty(value = "回應時間")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime dateTime;

    @ApiModelProperty(value = "回應資料")
    private T data;

    public ResDto(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.dateTime = LocalDateTime.now();
        this.data = data;
    }

    public ResDto(ApiException e) {
        this.code = e.getErrorCode();
        this.message = e.getMessage();
        this.dateTime = LocalDateTime.now();
        this.data = null;
    }

    public static ResDto<String> defult_ok(String data) {
        return new ResDto<String>(CodeMessage.SUCCESS.getCode(), CodeMessage.SUCCESS.getMessage(), data);
    }

    public static ResDto<Object> unauthorized() {
        return new ResDto<Object>(CodeMessage.UNAUTHORIZED.getCode(), CodeMessage.UNAUTHORIZED.getMessage(), null);
    }

    public static ResDto<Object> authenticateFail() {
        return new ResDto<Object>(CodeMessage.AUTHENTICATE_FAIL.getCode(), CodeMessage.AUTHENTICATE_FAIL.getMessage(),
                null);
    }
}
