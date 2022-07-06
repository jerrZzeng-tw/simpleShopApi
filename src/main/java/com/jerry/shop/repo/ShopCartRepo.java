package com.jerry.shop.repo;

import com.jerry.shop.entity.ShopCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface ShopCartRepo extends JpaRepository<ShopCart, Long> {

  @Lock(LockModeType.PESSIMISTIC_READ)
  @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "5000")})
  @Query("select s from ShopCart s where s.id = :id")
  Optional<ShopCart> findByIdWithLock(Long id);

  @Query("select s from ShopCart  s join s.user u where u.id = :userid")
  List<ShopCart> findByUserIdJoinUserJoinCarDetail(@Param("userid") Long userid);
}
