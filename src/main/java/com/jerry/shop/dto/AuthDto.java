package com.jerry.shop.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ApiModel("使用者認證資訊")
public class AuthDto {
    @ApiModelProperty(value = "使用者名稱")
    private String userName;

    @ApiModelProperty(value = "使用者密碼")
    private String password;
    @ApiModelProperty(value = "JWT token")
    private String token;
}
