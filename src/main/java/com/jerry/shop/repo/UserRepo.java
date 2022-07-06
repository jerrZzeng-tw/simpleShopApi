package com.jerry.shop.repo;

import com.jerry.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  List<User> findByRole(String role);

  @Query("select distinct role from User")
  List<String> findRoles();

  @Query("select u from User u join u.ShopCartList s where u.username =:username")
  Optional<User> findByIdJoinShopCart(@Param("username") String username);
}
