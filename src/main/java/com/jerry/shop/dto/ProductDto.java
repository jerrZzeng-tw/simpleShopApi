package com.jerry.shop.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jerry.shop.entity.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ApiModel("產品資訊")
public class ProductDto {
  @ApiModelProperty("產品ID")
  private Long id;

  @NotEmpty()
  @ApiModelProperty("產品名稱")
  private String name;

  @NotNull
  @ApiModelProperty("產品價格")
  @Range(min = 0, max = 9999)
  private int price;

  @NotNull
  @Range(min = 0, max = 9999)
  @ApiModelProperty("產品庫存數量")
  private int inventory;

  @ApiModelProperty("產品建立者")
  private String createdBy;

  @ApiModelProperty("產品建立時間")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;

  @ApiModelProperty("產品修改者")
  private String updatedBy;

  @ApiModelProperty("產品修改時間")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updatedAt;

  public static ProductDto valueOf(Product product) {
    return ProductDto.builder()
        .id(product.getId())
        .name(product.getName())
        .inventory(product.getInventory())
        .price(product.getPrice())
        .createdBy(product.getCreatedBy())
        .createdAt(product.getCreatedAt())
        .updatedBy(product.getUpdatedBy())
        .updatedAt(product.getUpdatedAt())
        .build();
  }
}
