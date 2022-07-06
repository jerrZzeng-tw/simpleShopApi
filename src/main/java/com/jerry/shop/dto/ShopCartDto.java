package com.jerry.shop.dto;

import com.jerry.shop.entity.ShopCart;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ApiModel("購物車資訊")
public class ShopCartDto {
  @ApiModelProperty(value = "訂單編號")
  private Long id;

  @ApiModelProperty(value = "訂購者")
  private Long userId;

  @ApiModelProperty(value = "訂單建立時間")
  private LocalDateTime creatAt;

  @NotEmpty @Valid private List<CartDetailDto> cartDetailDtoList;

  public static ShopCartDto valueOf(ShopCart shopCart) {
    return ShopCartDto.builder()
        .id(shopCart.getId())
        .userId(shopCart.getUser().getId())
        .creatAt(shopCart.getCreatedAt())
        .cartDetailDtoList(null)
        .cartDetailDtoList(
            Optional.ofNullable(shopCart.getCartDetailList())
                .map(t -> t.stream().map(CartDetailDto::valueOf).collect(Collectors.toList()))
                .orElse(Collections.emptyList()))
        .build();
  }
}
