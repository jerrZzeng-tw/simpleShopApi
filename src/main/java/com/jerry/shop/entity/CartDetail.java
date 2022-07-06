package com.jerry.shop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class CartDetail extends BaseEntity {

  @ManyToOne
  @JoinColumn(name = "shopCartId")
  private ShopCart shopCart;

  @ManyToOne
  @JoinColumn(name = "productId")
  private Product product;
  // 訂購數量
  private int amount;
}
