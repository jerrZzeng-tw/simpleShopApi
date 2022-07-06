package com.jerry.shop.dto;

import com.jerry.shop.entity.CartDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@ApiModel("訂單明細")
public class CartDetailDto {
  @ApiModelProperty("購物車ID")
  private Long shopCartId;

  @NotNull(groups = ValidUpdate.class)
  @ApiModelProperty("購物車明細ID")
  private Long Id;

  @NotNull(groups = ValidAdd.class)
  @Min(value = 1, groups = ValidAdd.class)
  @ApiModelProperty("訂購產品ID")
  private Long productId;

  @NotNull(groups = ValidAdd.class)
  @Range(
      min = 0,
      max = 9999,
      groups = {ValidAdd.class, ValidUpdate.class})
  @ApiModelProperty("訂購數量")
  private int amount;

  public static CartDetailDto valueOf(CartDetail cartDetail) {
    return CartDetailDto.builder()
        .shopCartId(cartDetail.getShopCart().getId())
        .Id(cartDetail.getId())
        .productId(cartDetail.getProduct().getId())
        .amount(cartDetail.getAmount())
        .build();
  }

  public interface ValidAdd {}

  public interface ValidUpdate {}
}
