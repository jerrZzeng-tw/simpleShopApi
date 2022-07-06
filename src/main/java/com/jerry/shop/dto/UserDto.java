package com.jerry.shop.dto;

import com.jerry.shop.entity.User;
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
@ApiModel("使用者資訊")
public class UserDto {
  @ApiModelProperty(value = "使用者名稱")
  private String username;

  @ApiModelProperty(value = "使用者角色")
  private String role;

  public static UserDto valueOf(User user) {
    return UserDto.builder().username(user.getUsername()).role(user.getRole()).build();
  }
}
