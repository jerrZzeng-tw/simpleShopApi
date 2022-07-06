package com.jerry.shop.repo;

import com.jerry.shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

  Optional<Product> findByName(String name);

  @Lock(LockModeType.PESSIMISTIC_READ)
  @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "3000")})
  @Query("select p from Product p where p.id = :id")
  Optional<Product> findByIdWithLock(Long id);

  void deleteByName(String name);
}
