package com.jerry.shop.repo;

import com.jerry.shop.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartDetailRepo extends JpaRepository<CartDetail, Long> {

  @Query("select c from CartDetail c  join c.product p where  c.shopCart.id = :shopCartId")
  List<CartDetail> findByShopCartId(@Param("shopCartId") Long shopCartId);

  @Modifying
  @Query("delete from CartDetail c where c.shopCart.id = :shopCartId")
  void deleteAllByShopCartId(@Param("shopCartId") Long shopCartId);
}
